package com.work.ppm.commons.dao;

import com.work.ppm.application.model.othermanager.EosUniqueTable;
import com.work.ppm.application.services.othermanager.IEosUniqueTableService;
import com.work.ppm.commons.annotaion.Id;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import static org.springframework.transaction.annotation.Isolation.SERIALIZABLE;

/**
 * service的父类
 */
@Slf4j
@Component
public class BaseDaoSupper {
    /**
     * eosUniqueTable code 默认扩容 20
     */
    private final long POOL_SIZE = 20;
    /**
     * map
     */
    private Map<String,PkInfo> keyMap = new ConcurrentHashMap<>();

    /**
     * 唯一主键表接口
     */
    @Autowired
    private IEosUniqueTableService eosUniqueTableService;
    /**
     * 获取下一个序列号
     * @param className 类名
     * @return  long
     */
    @Transactional(value = "defaultTx", isolation = SERIALIZABLE, propagation = Propagation.REQUIRES_NEW)
    public long getNextSequence(Class<?> className) {
        List<String> primaryKeys = this.queryPrimaryKey(className);
        String tableName;
        for (int i = primaryKeys.size() - 1; i >= 0; i--) {
            tableName = className.getSimpleName().concat(".").concat(primaryKeys.get(i));
            PkInfo pk = keyMap.get(tableName);
            if(null == pk) {
                synchronized (this.keyMap) {
                    pk = keyMap.get(tableName);
                    if(null == pk) {
                        pk = new PkInfo(tableName, POOL_SIZE);
                        this.keyMap.put(tableName, pk);
                    }
                }
            }
            synchronized (pk) {
                long id = pk.getNextId();
                log.info("【新应用】主键生成>>>>>>>>类名称：{}：主键值{}：",className,id );
                return id;
            }

        }
        return 1L;
    }




    /**
     * 获取实体类主键
     * @param className 类名
     * @return List<String>
     */
    private List<String> queryPrimaryKey(Class<?> className) {
        Field[] fields = className.getDeclaredFields();
        List<String> fieldList = new ArrayList<>();
        for (int i = 0; i < fields.length; i++) {
            Field field = fields[i];
            if (Modifier.isPrivate(field.getModifiers())) {
//                log.debug("字段名称：{}", field.getName());
                if (field.isAnnotationPresent(Id.class)) {
                    fieldList.add(field.getName());
                    continue;
                }
            }
        }
        return fieldList;
    }

    /**
     * 内部类
     */
    class PkInfo {
        /**
         * 每次更新后的最大id
         */
        private long maxId;
        /**
         * 累计增加的id
         */
        private long currentId = 1L;
        /**
         * size
         */
        private long poolSize;
        /**
         * 表名
         */
        private String tableName;

        /**
         * 构造函数
         */
        public PkInfo(String tableName, long poolSize) {
            this.tableName = tableName;
            this.poolSize = poolSize;
        }

        /**
         * 获取下一个id
         * @return long
         */
        public long getNextId() {
            EosUniqueTable eos = new EosUniqueTable();
            eos.setName(this.tableName);
            EosUniqueTable table =  eosUniqueTableService.queryDetail(eos);
            if(this.currentId > this.maxId || table == null || table.getCode() > this.maxId) {
                updateMaxId();
            }
            return this.currentId++;
        }

        /**
         * 更新最大的id
         */
        private void updateMaxId() {
            EosUniqueTable eos = new EosUniqueTable();
            // 是否更新累计增加值
            boolean isResetCurrentId = false;
            eos.setName(this.tableName);
            EosUniqueTable table =  eosUniqueTableService.queryDetail(eos);
            // 插入
            if ( null == table) {
                eos.setCode(POOL_SIZE);
                eos.setName(this.tableName);
                eosUniqueTableService.addEosUniqueTable(eos);
                this.maxId = eos.getCode();
            } else {
                // 更新
                eos.setCode(table.getCode() + POOL_SIZE);
                eos.setName(this.tableName);

                this.maxId = eos.getCode().longValue();
                // 需要更新累加的id
                isResetCurrentId = true;
                eosUniqueTableService.updateEosUniqueTable(eos);
            }

            if(isResetCurrentId) {
                this.currentId = maxId - POOL_SIZE  + 1L;
            }
        }


        public long getMaxId() {
            return maxId;
        }

        public void setMaxId(long maxId) {
            this.maxId = maxId;
        }

        public long getCurrentId() {
            return currentId;
        }

        public void setCurrentId(long currentId) {
            this.currentId = currentId;
        }

        public long getPoolSize() {
            return poolSize;
        }

        public void setPoolSize(long poolSize) {
            this.poolSize = poolSize;
        }

        public String getTableName() {
            return tableName;
        }

        public void setTableName(String tableName) {
            this.tableName = tableName;
        }
    }


}
