package com.work.ppm.application.services.systemmanager.impl;

import com.work.ppm.application.model.systemmanager.WeakPassword;
import com.work.ppm.application.services.systemmanager.ICommWeakpasswordService;
import com.work.ppm.commons.dao.IMyBatisDao;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 弱口令
 *
 */
@Slf4j
@Service("commWeakpasswordService")
public class CommWeakpasswordService implements ICommWeakpasswordService {
    /**
     * 数据接口
     */
    @Autowired
    private IMyBatisDao myBatisDao;
    /**
     * 实体类路径
     */
    private static String WEAKPASSWORD = "com.work.ppm.application.model.systemmanager.WeakPassword";

    @Override
    public int addWeakPasswords(List<WeakPassword> weakPasswords) {
        return myBatisDao.executionPojo(WEAKPASSWORD, weakPasswords, IMyBatisDao.SQL.INSTER, "upsert");
    }
}
