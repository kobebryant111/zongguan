package com.tansun.wf.activiti.cmd;

import org.camunda.bpm.engine.impl.interceptor.Command;
import org.camunda.bpm.engine.impl.interceptor.CommandContext;
import org.camunda.bpm.engine.impl.persistence.entity.ByteArrayEntity;

public class GetByteArrayCommand implements Command<ByteArrayEntity> {

    protected String byteArrayId;

    public GetByteArrayCommand(String byteArrayId) {
      this.byteArrayId = byteArrayId;
    }

    public ByteArrayEntity execute(CommandContext commandContext) {
      return (ByteArrayEntity) commandContext.getDbEntityManager()
        .selectOne("selectByteArray", byteArrayId);
    }

}