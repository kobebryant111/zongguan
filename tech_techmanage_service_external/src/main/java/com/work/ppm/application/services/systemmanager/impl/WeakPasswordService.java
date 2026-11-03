package com.work.ppm.application.services.systemmanager.impl;

import com.google.common.collect.Lists;
import com.work.ppm.application.model.systemmanager.WeakPassword;
import com.work.ppm.application.quartz.interfaces.IWeakPasswordService;
import com.work.ppm.application.services.systemmanager.ICommWeakpasswordService;
import com.work.ppm.commons.ApiCode;
import com.work.ppm.commons.exception.BizException;
import com.work.ppm.commons.utils.FileUtil;
import com.work.ppm.commons.utils.UUIDUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import java.io.IOException;
import java.net.BindException;
import java.util.List;

@Slf4j
@Service("weakPasswordService")
public class WeakPasswordService implements IWeakPasswordService {

    @Value("${file.weak-password}")
    private String weakFile;

    @Autowired
    private ICommWeakpasswordService commWeakpasswordService;

    @Override
    public void syncWeakPasswordJob() throws IOException {
        try {
            String fileName = weakFile + "dj6_weak_paw_dic.txt";
            log.info("文件名:{}", fileName);
            List<String> sources = FileUtil.parseTxt(fileName);
            List<WeakPassword> weakPasswords = Lists.newArrayList();
            if (CollectionUtils.isEmpty(sources)) {
                log.info("文件名:{}为空", fileName);
                return;
            }
            sources.forEach(context -> {
                WeakPassword weakPassword = WeakPassword.builder()
                        .Id(UUIDUtils.generateUUID())
                        .weakPassword(context)
                        .build();
                weakPasswords.add(weakPassword);
            });
            if (!CollectionUtils.isEmpty(weakPasswords)) {
                int count = commWeakpasswordService.addWeakPasswords(weakPasswords);
                log.info("弱口令插入条数：" + count);
            }
        } catch (IOException e) {
            log.error(e.getMessage());
            throw new BizException(ApiCode.LOGIN_FAIL,"用户超过180天未登陆被停用");
        }
    }
}
