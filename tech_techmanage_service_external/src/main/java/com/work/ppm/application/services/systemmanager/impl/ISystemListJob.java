package com.work.ppm.application.services.systemmanager.impl;

import com.google.common.collect.Lists;
import com.work.ppm.application.model.rdmanager.CommSysPwDto;
import com.work.ppm.application.model.rdmanager.SyspicListDto;
import com.work.ppm.application.model.rdmanager.SystemListDto;
import com.work.ppm.application.quartz.interfaces.IPpmSystemListJob;
import com.work.ppm.application.services.systemmanager.ISystemListService;
import com.work.ppm.commons.exception.BizRuntimeException;
import com.work.ppm.commons.utils.CryptoUtil;
import com.work.ppm.commons.utils.FileUtil;
import com.work.ppm.commons.utils.Sm4KeyDerivation;
import com.work.ppm.commons.utils.StringUtil;
import com.work.ppm.commons.utils.UUIDUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.MessageDigest;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

@Slf4j
@Service("ISystemListJob")
public class ISystemListJob implements IPpmSystemListJob {

    @Value("${file.sys_list}")
    private String sysList;

    @Autowired
    private ISystemListService systemListService;


    @Override
    @Transactional(rollbackFor = Exception.class)
    public void syncSystemListJob() throws IOException {
        String fileName = sysList + "dj6_sys_config_info.txt";
        log.info("文件名:{}", fileName);
//        List<String> sources = FileUtil.readLines(fileName,"GBK");
        List<String> sources = Files.readAllLines(Paths.get(fileName), Charset.forName("GBK"));
        List<SystemListDto> systemListDtos = Lists.newArrayList();
        List<SyspicListDto> syspicListDtos = Lists.newArrayList();
        if (CollectionUtils.isEmpty(sources)) {
            log.info("文件名:{}为空", fileName);
            return;
        }
        sources.forEach(context -> {
            try {
                String[] lineStr = context.split("[\\u0007,]");
                if (lineStr.length <= 1) {
                    return;
                }
                if (!(StringUtil.isNotBlank(lineStr[0]) && StringUtil.isNotBlank(lineStr[1]))) {
                    return;
                }
                SystemListDto systemListDto = SystemListDto.builder()
                        .id(UUIDUtils.generateUUID())
                        .systemName(lineStr[0])
                        .systemNo(lineStr[1])
                        .build();
                // 从索引1开始（第二位），到末尾
                String[] subArray = Arrays.copyOfRange(lineStr, 2, lineStr.length);
                String[] uniqueArray;
                if (subArray.length > 0) {
                    uniqueArray = Arrays.stream(subArray)
                            .distinct() // 去重
                            .toArray(String[]::new); // 转回数组

                } else {
                    return;
                }
                for (String s : uniqueArray) {
                    if (!StringUtil.isNotBlank(s)) {
                        continue;
                    }
                    String id = lineStr[1] + ":" + s.split("[()]")[1];
                    SyspicListDto syspicListDto = SyspicListDto.builder()
                            .id(CryptoUtil.digestByMD5(id))
                            .systemNo(lineStr[1])
                            .nameResponsible(s.split("[()]")[0])
                            .jobidResponsible(s.split("[()]")[1])
                            .build();
                    syspicListDtos.add(syspicListDto);
                }
                systemListDtos.add(systemListDto);
            } catch (Exception e) {
                log.info("报错行:{}",context);
                log.error(e.getMessage(), e);
                throw new BizRuntimeException(e.getMessage());
            }
        });

        // 全量更新
        if (!CollectionUtils.isEmpty(systemListDtos)) {
            systemListService.deleteSystemListDto(new SystemListDto());
            systemListService.deleteSyspicListDto(new SyspicListDto());
            systemListService.insertSystemListDto(systemListDtos);
            systemListService.insertSyspicListDto(syspicListDtos);
        }
    }

    @Override
    @Transactional
    public void syncRenewKeyJob() throws Exception {
        int page = 1;

        while (true) {
            List<CommSysPwDto> rows = systemListService.selectCommSysPwDto(page);
            if (CollectionUtils.isEmpty(rows)) {
                break;
            }
            for (CommSysPwDto sysPwDto : rows) {
                int year = sysPwDto.getValidtime();
                // 判断密码是否生效

                String encrypte = sysPwDto.getPsdKey();
                // 主密钥
                byte[] workingKey = Sm4KeyDerivation.loadMasterKey(year);
                // sm4生产密钥
                String encrypteBase64 = Sm4KeyDerivation.decrypt(encrypte, workingKey);
                byte[] Key = Sm4KeyDerivation.getWorkingKey(encrypteBase64, year);
                // sm4解密
                String sm4Psd = Sm4KeyDerivation.decrypt(sysPwDto.getPsd(), Key);
                // 本年主密钥加密
                String bytesKey = Sm4KeyDerivation.generateRandomSm4Key();
                byte[] keyBase64 = Sm4KeyDerivation.getWorkingKey(bytesKey, LocalDate.now().getYear());
                String nowEncrypteBase64 = Sm4KeyDerivation.deriveSm4Key(sm4Psd, keyBase64);
                sysPwDto.setPsd(nowEncrypteBase64);
                sysPwDto.setValidtime(LocalDate.now().getYear());
                byte[] nowWorkingKey = Sm4KeyDerivation.loadMasterKey(LocalDate.now().getYear());
                String psdKey = Sm4KeyDerivation.deriveSm4Key(bytesKey, nowWorkingKey);
                sysPwDto.setPsdKey(psdKey);
                systemListService.updateCommSysPwDto(sysPwDto);
            }
            page++;
        }

    }
}
