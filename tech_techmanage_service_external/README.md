# 脚手架说明

本工程为水星平台 单体 脚手架demo工程，适用于DDD的服务。


## 代码规范

- 项目包名请使用单数
- 资源类名称请使用单数
- 资源URL名称请使用复数
- 变量和方法的命名请使用驼峰规范
- 请使用lombok，取代Getter，Setter和toString等
- 实体的ID生成器，可以使用工具类里面的生成器，`SnowflakeIdGenerator`或者`UUIDGenerator`
- 命名规范：
  - XXXApi(资源接口)
  - I XXXService(基础层服务实现)
   - 创建数据表，主键id 必须是一列
   - 数据库字段只能用 “_” 下划线分割，不准用其他特殊符号
   - 响应输出 一律使用 ApiResult 对象
   - ApiCode 为 输出状态类，需统一管理
   - 方法之间传参以及返回参数，不得使用map
   - 方法抛出异常， throws Exception,由全局异常类 处理
   - @Deprecated 接口上默认，使用该接口时请删除注解
   - service 实现类 静态变量值，请以对应的实体类名称为变量，不要轻易改动

## Restful风格

| 场景         | 动作 | 路径      | 响应                        |
| ------------ | ---- | --------- | --------------------------- |
| 查询用户信息 | GET  | /v1/users | 200， 400， 401， 403， 5XX |
| 添加用户     | POST | /v1/users | 200， 400， 401， 403， 5XX |



## 分层架构

```
├─application  应用层
│  ├─api  接口
│  │  ├─assembler
│  │  │  └─mapper
│  │  ├─request
│  │  └─response
│  ├─external  外部依赖(接口，允许为feign接口)
│  │  ├─assembler
│  │  │  └─mapper
│  │  ├─request
│  │  └─response
│  ├─repository  资源（接口）
│  └─service  应用服务
├─common  公共
│  ├─config  bean
│  ├─constant  常量
│  ├─exception  异常
│  └─util  工具
├─domain  领域层
│  ├─entity  实体
│  ├─external  外部依赖（接口，不允许使用feign接口，因为参数不属于领域层）
│  ├─factory  工厂
│  ├─repository  资源（接口）
│  ├─service  领域服务
│  └─vo  值对象
└─infrastructure  基础设施层
    ├─external  外部依赖(实现)
    │  ├─assembler
    │  │  └─mapper
    │  ├─request
    │  └─response
    └─persistence  资源库（实现）
        ├─assembler
        │  └─mapper
        └─po
```



## 架构守护

`ARCHUNIT`是一个基于 Java 的测试库，用于检查代码的结构特性，如包和类的依赖关系、注解验证，还能检查代码分层是否一致，它可以在现有的测试环境中以单元测试的方式运。

`ARCHUNIT`的测试案例统一放在`com.work.demo`包下。

- 依赖错误测试如图：

![](doc/image/分层依赖错误.PNG)

- 命名错误测试如图：

![](docmage/命名错误.PNG)



## 聚合根原则

- 聚合：由一组相关的领域对象组成，需要通过聚合根来进行统一的导航。
- 一个聚合根对应一个资源库。
- 聚合根之间的引用必须通过ID，而不是对象引用。
- 实体不一定是聚合根，聚合根一定是实体。
- 不同聚合的Domain层不在一个包里，彼此的访问通过Application service转发，或者基于事件进行异步通信。
- 实现依赖Domain层，Domain层不依赖实现 - 依赖倒置。



## 本地强制检测

- Checkstyle编码风格，`checkstyle.xml`文件在工程目录`/scripts/pipeline`下。
- 提交日志检测。e.g:“[开发任务编号] commit messages”。
- Push前，本地强制运行mvn clean package。



## 流水线

流水线统一放置在工程目录`/scripts/pipeline`下，在无特殊需求的情况下，只需要调整最上方的变量值即可。

![](docmage/流水线变量定义.PNG)



## 提交规范

脚手架基于mvn实现了commit和push的规范检查，在工程检出后需要先执行一次mvn命令才会在使用git命令触发规范检查。

### commit规范

要求提交代码时 带上开发任务编号，规则为`[开发任务编号]  改动说明`

建议在命令行中使用`git commit`提交，未按规则时会有错误提示，使用IDEA的工具提交无法显示错误提示。

![](docmage/commit错误提示.PNG)

### push规范

push时会在本地执行checkstyle和单元测试，以保证dev流水线不行这两个问题执行失败，使用命令行push时，会打印过程日志，使用IDEA工具提交不会打印。

![](docmage/push日志.PNG)



## 脚手架demo代码

脚手架中带有一定的示例代码，开发过程中请记得移除。