<h1 align="center">工作流管理平台</h1>

## 介绍 
企业级流程管理解决方案，采用activiti开源流程引擎进行扩展，基于**easycare**开发框架实现

## 特性
- 降级开发人员的流程门槛
- 加速了流程应用的开发过程
- 满足复杂的流程审批需求

## 文档资料
方便大家快速了解掌握工作流，参考文档

http://106.39.97.163:58090/docs/workflow/quickstart

## 提需求、bug
在开发、使用过程中有新需求和bug，请前往“议题”模块处理
操作步骤：
1. 前往“议题”--》“列表”页面
2. 点击页面右上角“new Issue”按钮，进入新建提问页面
3. 添加标题
4. 详细描述问题并提供异常日志
5. 指定产品经理为第一处理人 

## 参与共建
### 环境准备
首先你需要提前准备好开发环境
- eclipse或IntelliJ IDEA
- JDK 1.7版本以上
- tomcat 7.0版本以上
- maven 3.3.9版本以上
- git客户端
- mysql数据库

### gitLab账户公钥配置
详细参考博客 https://www.cnblogs.com/xiuxingzhe/p/9303278.html

### maven配置
打开.m2/settings.xml配置文件，在标签里面添加如下配置：
```
<mirror>
  <id>tansun-mirror</id>
  <!-- 镜像中央仓库 -->
  <mirrorOf>central</mirrorOf>
  <name>nexus-mirror</name>
  <!-- 公司私服外网地址 -->
  <url>http://27.154.242.142:8081/nexus/content/groups/public</url>
</mirror>
```

### 下载工程
- `git clone git@:root/workflow-easycare.git`
- 使用eclipse/IntelliJ IDEA 以maven格式导入工程
- 进入workflow-easycare\workflow-console\sql 目录执行脚本
- 脚本目录含义，例如：mysql\3.6.4表示数据库为`mysql`工作流版为`3.6.4`
- 修改数据源配置，文件位置：workflow-easycare\ workflow-console\ src\ main\resources\ easycare.properties
- 启动workflow-console项目
- 访问地址http://127.0.0.1:8080/workflow-console  账户: administrator 密码:111111

## 项目结构
工作流管理平台的工程是通过Maven管理的，每个子工程的含义如下：
- workflow-easycare：所有工作流项目的父工程(pom类型)，统一管理maven依赖。内部没有任何代码和业务逻辑
- workflow-console：工作流管理平台(war类型),负责流程图的设计、发布、管理、流程状态跟踪、数据性能分析等
- workflow-core：流程执行引擎包（jar类型），负责流程流转、任务分发
- workflow-api：流程接口包（jar类型），业务系统只能调用接口包的类和方法
- workflow-junit：流程引擎的单元测试工程（jar类型）
 
 *如果IDE是eclipse 每个子工程都需要导入*
## 演示地址
http://27.154.242.142:18360/workflow-console</br>
账户：administrator</br>
密码：111111</br>

