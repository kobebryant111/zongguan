package com.work.ppm.application.services.systemmanager.impl;

import com.work.ppm.application.services.systemmanager.RefreshMapperService;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.binding.MapperRegistry;
import org.apache.ibatis.builder.xml.XMLMapperBuilder;
import org.apache.ibatis.builder.xml.XMLMapperEntityResolver;
import org.apache.ibatis.executor.keygen.SelectKeyGenerator;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.parsing.XNode;
import org.apache.ibatis.parsing.XPathParser;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 刷新mapper文件
 *
 */
@Slf4j
@Service
public class RefreshMapperServiceImpl implements RefreshMapperService {

    /**
     * xml加载路径
     */
    @Value("${sys.mapper-locations}")
    private String mapperPath;

    /**
     * xml加载路径
     */
    @Value("${sys.mapper-refresh:false}")
    private Boolean mapperRefresh = false;

    /**
     * session
     */
    @Autowired
    private SqlSessionFactory sqlSessionFactory;


    /**
     * 存放文件状态
     */
    private Map<String,String> fileMap = new HashMap<>();

    /**
     * 禁止重复执行
     */
    private boolean lock;


    /**
     * 刷新sql配置
     */
    @Override
    public  void  refreshMapper() {
        if(lock){
            return;
        }
        lock = true;
        try {

            Configuration configuration = sqlSessionFactory.getConfiguration();
            Resource[] mappers = new PathMatchingResourcePatternResolver().getResources(mapperPath);
//            clearCaches(configuration);
            for (Resource res : mappers) {
                refresh(res);
//                XMLMapperBuilder xmlMapperBuilder = new XMLMapperBuilder(res.getInputStream(),
//                        configuration, res.toString(), configuration.getSqlFragments());
//                xmlMapperBuilder.parse();
                log.debug("重新加载mapper({})", res.getFilename());
            }
            for (String name : configuration.getMappedStatementNames()) {
                log.debug("刷新{}", name);
            }
        }catch (Exception e){
            log.error("刷新配置失败",e);
        }finally {
            lock = false;
        }

    }


    /**
     * 刷新mapper
     *
     * @throws Exception
     */
    @SuppressWarnings("rawtypes")
    private void refresh(Resource resource) {
        Configuration configuration = sqlSessionFactory.getConfiguration();
        boolean isSupper = configuration.getClass().getSuperclass() == Configuration.class;
        try {
            Field loadedResourcesField = isSupper ? configuration.getClass().
                    getSuperclass().getDeclaredField("loadedResources")
                    : configuration.getClass().getDeclaredField("loadedResources");
            loadedResourcesField.setAccessible(true);
            Set loadedResourcesSet = ((Set) loadedResourcesField.get(configuration));
            XPathParser xPathParser = new XPathParser(resource.getInputStream(),
                    true, configuration.getVariables(),
                    new XMLMapperEntityResolver());
            XNode context = xPathParser.evalNode("/mapper");
            String namespace = context.getStringAttribute("namespace");
            Field field = MapperRegistry.class.getDeclaredField("knownMappers");
            field.setAccessible(true);
            Map mapConfig = (Map) field.get(configuration.getMapperRegistry());
            mapConfig.remove(Resources.classForName(namespace));
            loadedResourcesSet.remove(resource.toString());
            configuration.getCacheNames().remove(namespace);

            cleanParameterMap(context.evalNodes("/mapper/parameterMap"), namespace);
            cleanResultMap(context.evalNodes("/mapper/resultMap"), namespace);
            cleanKeyGenerators(context.evalNodes("insert|update|select"), namespace);
            cleanSqlElement(context.evalNodes("/mapper/sql"), namespace);
            XMLMapperBuilder xmlMapperBuilder = new XMLMapperBuilder(resource.getInputStream(),
                    sqlSessionFactory.getConfiguration(),
                    resource.toString(), sqlSessionFactory.getConfiguration().getSqlFragments());
            xmlMapperBuilder.parse();
            log.debug("加载: '" + resource + "', 成功!");
        } catch (Exception e) {
            log.error("加载 "+resource+" 失败 :" + e.getMessage());
        }
    }

    /**
     * 清理parameterMap
     *
     * @param list
     * @param namespace
     */
    private void cleanParameterMap(List<XNode> list, String namespace) {
        for (XNode parameterMapNode : list) {
            String id = parameterMapNode.getStringAttribute("id");
            getConfiguration().getParameterMaps().remove(namespace + "." + id);
        }
    }

    /**
     * 清理resultMap
     *
     * @param list
     * @param namespace
     */
    private void cleanResultMap(List<XNode> list, String namespace) {
        for (XNode resultMapNode : list) {
            String id = resultMapNode.getStringAttribute("id", resultMapNode.getValueBasedIdentifier());
            getConfiguration().getResultMapNames().remove(id);
            getConfiguration().getResultMapNames().remove(namespace + "." + id);
            clearResultMap(resultMapNode, namespace);
        }
    }

    /**
     * 清理结果
     * @param xNode
     * @param namespace
     */
    private void clearResultMap(XNode xNode, String namespace) {
        for (XNode resultChild : xNode.getChildren()) {
            if ("association".equals(resultChild.getName()) || "collection".equals(resultChild.getName())
                    || "case".equals(resultChild.getName())) {
                if (resultChild.getStringAttribute("select") == null) {
                    getConfiguration().getResultMapNames().remove(
                            resultChild.getStringAttribute("id", resultChild.getValueBasedIdentifier()));
                    getConfiguration().getResultMapNames().remove(
                            namespace + "." + resultChild.getStringAttribute("id",
                                    resultChild.getValueBasedIdentifier()));
                    if (resultChild.getChildren() != null && !resultChild.getChildren().isEmpty()) {
                        clearResultMap(resultChild, namespace);
                    }
                }
            }
        }
    }

    /**
     * 清理selectKey
     *
     * @param list
     * @param namespace
     */
    private void cleanKeyGenerators(List<XNode> list, String namespace) {
        Collection<String> list2 = getConfiguration().getMappedStatementNames();
        for (XNode context : list) {
            String id = context.getStringAttribute("id");
            list2.remove(namespace+"."+id);
            getConfiguration().getKeyGeneratorNames().remove(id + SelectKeyGenerator.SELECT_KEY_SUFFIX);
            getConfiguration().getKeyGeneratorNames().remove(namespace + "."
                    + id + SelectKeyGenerator.SELECT_KEY_SUFFIX);
        }
    }

    /**
     * 清理sql节点缓存
     *
     * @param list
     * @param namespace
     */
    private void cleanSqlElement(List<XNode> list, String namespace) {
        for (XNode context : list) {
            String id = context.getStringAttribute("id");
            getConfiguration().getSqlFragments().remove(id);
            getConfiguration().getSqlFragments().remove(namespace + "." + id);
        }
    }

    /**
     * 配置文件
     * @return Configuration
     */
    public Configuration getConfiguration(){
        return  sqlSessionFactory.getConfiguration();
    }


//    /**
//     * 清理缓存
//     * @param configuration
//     */
//    public void clearCaches(Configuration configuration){
//
//            Class cls = configuration.getClass();
//            if(cls.getName().equals(MybatisConfiguration.class.getName())  ){
//                cls = cls.getSuperclass();
//            }
//            clearField(configuration,cls,"mappedStatements");
//            clearField(configuration,cls,"caches");
//            clearField(configuration,cls,"resultMaps");
//            clearField(configuration,cls,"parameterMaps");
//            clearField(configuration,cls,"keyGenerators");
//            clearField(configuration,cls,"loadedResources");
//            clearField(configuration,cls,"sqlFragments");
//
//
//    }
//
//    /**
//     * 清理缓存
//     * @param configuration
//     * @param cls
//     * @param fieldName
//     */
//    public void clearField(Configuration configuration,Class cls,String fieldName){
//        try{
//            Field field =cls.getDeclaredField(fieldName);
//            field.setAccessible(true);
//            Object obj =  field.get(configuration);
//            if(obj instanceof Map){
//                Map map = (Map) obj;
//                List keyList = (List) map.keySet().stream().
//                        filter(key1 -> key1.toString().contains("com.work.cffmap.apm.") )
//                        .collect(Collectors.toList());
//                for( Object key : keyList){
//                    map.remove(key);
//                }
////                map.clear();
//            }else if(obj instanceof Set){
//                Set set = (Set) obj;
//                List keyList = (List) set.stream().
//                        filter(key1 -> key1.toString().contains("com.work.cffmap.apm.")
//                        ||key1.toString().contains("\\com\\work\\cffmap\\apm"))
//                        .collect(Collectors.toList());
//                for(Object key : keyList){
//                    set.remove(key);
//                }
////                set.clear();
//            }
//
//        } catch (NoSuchFieldException e) {
//            log.error(e.getMessage(),e);
//        } catch (IllegalAccessException e) {
//            log.error(e.getMessage(),e);
//        }
//
//    }


    /**
     * 获得修改文件
     * @return boolean
     * @throws IOException
     */
    public List<Resource> changedRes() throws IOException {
        List<Resource> result = new ArrayList<>();
        Resource[] mappers = new PathMatchingResourcePatternResolver().getResources(mapperPath);
        for(Resource res : mappers){
            String fileName = res.getFilename();
            String old = fileMap.get(fileName);
            String last = res.contentLength()+"_"+res.lastModified();
            if(old==null||!last.equals(old)){
                fileMap.put(fileName,last);
                result.add(res);
            }
        }

        return result;
    }
    /**
     * 定时刷新  10s刷新一次
     */
    @Scheduled(cron="0/5 * * * * ?")
    public void taskRefresh() throws IOException {
        if(!mapperRefresh){
            return;
        }
        List<Resource> resList = changedRes();
        for(Resource res : resList){
            refresh(res);
        }
    }

}
