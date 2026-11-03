package com.work.ppm.application.services.rdmanager;

import com.baomidou.mybatisplus.extension.service.IService;
import com.work.ppm.application.model.rdmanager.RdContractPayRecord;

public interface IRdContractPayRecordService extends IService<RdContractPayRecord> {


    /**
     * 保存支付笔数
     * @param req
     */
    void saveRdContractPayRecord(RdContractPayRecord req);

    /**
     * 删除
     * @param pkId
     */
    void deleteById(String pkId);
}
