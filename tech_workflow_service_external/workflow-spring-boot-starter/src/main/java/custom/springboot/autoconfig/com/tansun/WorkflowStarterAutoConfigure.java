package custom.springboot.autoconfig.com.tansun;

import com.tansun.wf.platform.web.JsonWorkflowController;
import com.tansun.workflow.api.AbstractWorkflowEditorService;
import com.tansun.workflow.api.IWorkflowEditorService;
import com.tansun.workflow.model.EditorBean;
import com.tansun.workflow.model.EditorInfo;
import com.tansun.workflow.service.WorkFlowClientService;
import com.tansun.workflow.utils.RestTemplateUtils;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.*;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.interceptor.TransactionInterceptor;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.DispatcherServlet;

import javax.sql.DataSource;
import java.util.ArrayList;


@Configuration
@PropertySource(value = {"classpath:workflow.properties"} ,ignoreResourceNotFound = true)
@ImportResource(locations = {"classpath*:spring/applicationContext-workflow.xml"})
//@ComponentScan(value = {"com.tansun.wf.platform","org.camunda.bpm.rest"})
@ComponentScan(value = {"com.tansun.wf.platform"})
@MapperScan(value = {"com.tansun.wf.platform.mapper","com.tansun.wf.mocktest.mapper"})
@EnableTransactionManagement
@EnableAspectJAutoProxy(proxyTargetClass = true)
public class WorkflowStarterAutoConfigure {

    @Value("${use.cloud.restTemplate:Y}")
    private String useCloudRestTemplate;

    @Bean
    @LoadBalanced
    @ConditionalOnMissingBean(value = RestTemplate.class ,name = "restTemplate")
    public RestTemplate restTemplate(){
        RestTemplate restTemplate=new RestTemplate();
        if ("Y".equals(useCloudRestTemplate)){
            RestTemplateUtils.replaceWithCloudRestTemplate(restTemplate);
        }

        return  restTemplate;
    }

    /**
     * 接口根路径
     *
     * @return
     */
    @Bean
    public ServletRegistrationBean workflowServlet(DispatcherServlet dispatcherServlet) {
        ServletRegistrationBean bean = new ServletRegistrationBean(dispatcherServlet);
        bean.addUrlMappings("/workflow/*");
        bean.setName("workflowServlet");
        return bean;
    }

    /**
     * h2console
     * @return
     */
//    @Bean
//    public ServletRegistrationBean h2console() {
//        ServletRegistrationBean registrationBean = new ServletRegistrationBean( new WebServlet());
//        registrationBean.addUrlMappings("/h2console/*");
//        return registrationBean;
//    }


    /**
     * 提供空的组织机构实现
     *
     * @return
     */
    @Bean
    @ConditionalOnMissingBean(value = IWorkflowEditorService.class, name = "emptyEditorService")
    public IWorkflowEditorService emptyEditorService() {
        return new AbstractWorkflowEditorService() {
            @Override
            public EditorBean findOrganizationList(EditorInfo editorInfo) {
                EditorBean editorBean = new EditorBean();
                editorBean.setUserList(new ArrayList<>());
                return editorBean;
            }

            @Override
            public EditorBean findDepartmentList(EditorInfo editorInfo) {
                EditorBean editorBean = new EditorBean();
                editorBean.setDeptList(new ArrayList<>());
                return editorBean;
            }

            @Override
            public EditorBean findUsersList(EditorInfo editorInfo) {
                EditorBean editorBean = new EditorBean();
                editorBean.setUserList(new ArrayList<>());
                return editorBean;
            }

            @Override
            public EditorBean findRoleList(EditorInfo editorInfo) {
                EditorBean editorBean = new EditorBean();
                editorBean.setRoleList(new ArrayList<>());
                return editorBean;
            }

            @Override
            public EditorBean findUserOrgRoleList(EditorInfo editorInfo) {
                EditorBean editorBean = new EditorBean();
                editorBean.setUserOrgRoleTree(new ArrayList<>());
                return super.findUserOrgRoleList(editorInfo);
            }
        };
    }


    @Bean
    public JsonWorkflowController jsonWorkflowController(WorkFlowClientService workFlowClientService) {
        JsonWorkflowController jsonWorkflowController = new JsonWorkflowController();
        jsonWorkflowController.setWorkFlowClientService(workFlowClientService);
        return jsonWorkflowController;
    }


    @Bean
    @ConditionalOnMissingBean(value = PlatformTransactionManager.class, name = "transactionManager")
    public PlatformTransactionManager transactionManager(DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }


    @Bean
    @Primary
    @ConditionalOnBean(value = SqlSessionFactory.class, name = "sqlSessionFactory")
    public SqlSessionFactory priamrySqlSessionFactory(SqlSessionFactory sqlSessionFactory) {
        return sqlSessionFactory;
    }

    @Bean
    @Primary
    @ConditionalOnBean(value = PlatformTransactionManager.class, name = "transactionManager")
    public PlatformTransactionManager priamryTransactionManager(PlatformTransactionManager transactionManager) {
        return transactionManager;
    }


    @Bean
    @Primary
    @ConditionalOnBean(value = TransactionInterceptor.class, name = "transactionInterceptor")
    public TransactionInterceptor priamryTransactionInterceptor(TransactionInterceptor transactionInterceptor) {
        return transactionInterceptor;
    }

}
