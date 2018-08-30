# MDubboClient
仿JSF+Dubbo之client客户端

本框架设计如下：
1、以zookeeper为注册中心，提供服务发现与注册功能。
2、开发了单独的所有服务器MDubboIndexServer，用于向客户端提供注册中心地址列表。
3、MdubboClient客户端，为本框架的核心，提供RPC服务。

使用步骤：

第一步，安装zookeeper服务端，由于本项目以zookeeper作为注册中心，因此首先需要在安装zookeeper服务端。可以参考网上的教程。
假设zookeeper运行在集群环境，地址分别为：
192.168.12.121:2181，
192.168.12.122:2181，
192.168.12.123:2181。

第二步，将注册中心zookeeper的地址和端口加入到索引服务器的配置文件中（webapp/centerList.txt），如下：

#此行为注释,每行一个地址，#号的行为注释
address=192.168.12.121:2181
address=192.168.12.122:2181
address=192.168.12.123:2181

第三步，启动索引服务器MDubboIndexServer，可在浏览器输入http://主机：端口/MDubboIndexServer/IndexServer,即可获取到所有的注册中心地址列表。

第四步，在自己的项目的pom.xml中引入mdubbo客户端依赖，这是本框架的核心，服务提供端和服务消费端都需要引入，如下。

     <dependency>
      <groupId>com.ckj.projects</groupId>
      <artifactId>MDubboClient</artifactId>
      <version>1.1-SNAPSHOT</version>
     </dependency>
     
第五步，

1）、服务订阅端provider配置。
在spring配置文件中配置provider,如下：

<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xmlns:mdubbo="http://ckj.mdubbo.com/MDubbo/schema/mdubbo" xmlns:context="http://www.springframework.org/schema/context"
       xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd
       http://www.springframework.org/schema/context http://www.springframework.org/schema/context/spring-context.xsd
       http://ckj.mdubbo.com/MDubbo/schema/mdubbo http://ckj.mdubbo.com/MDubbo/schema/mdubbo/mdubbo.xsd">

    <context:component-scan base-package="com.ckj.projects"/>
    
    <!--索引服务器地址，此地址为索引服务器获取注册中心列表的地址，即上文提到在浏览器输入的地址-->
    <mdubbo:indexServer id="indexServer" address="http://主机：端口/MDubboIndexServer/IndexServer"/>
    
    <!--本地服务配置,ipAddress默认自动获取，可不填-->
    <mdubbo:server id="server" ipAddress="" port="9000"/>
    
    <!--服务提供者1-->
    <mdubbo:provider id="providerService" interface="com.ckj.projects.api.ProviderService" ref="providerServiceImpl"/>
    
    <!--服务提供者2-->
    <mdubbo:provider id="taskExcutorService" interface="com.ckj.projects.api.TaskExcutorService" ref="taskExcutorServiceImpl"/>
</beans>

2）、服务订阅端subscriber配置
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xmlns:mdubbo="http://ckj.mdubbo.com/MDubbo/schema/mdubbo" xmlns:context="http://www.springframework.org/schema/context"
       xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd
       http://www.springframework.org/schema/context http://www.springframework.org/schema/context/spring-context.xsd
       http://ckj.mdubbo.com/MDubbo/schema/mdubbo http://ckj.mdubbo.com/MDubbo/schema/mdubbo/mdubbo.xsd">

    <context:component-scan base-package="com.ckj.projects"/>

    <!--索引服务器地址-->
    <mdubbo:indexServer id="indexServer" address="http://localhost:8080/MDubboIndexServer/IndexServer"/>

    <!--服务订阅者-->
    <mdubbo:subscriber id="providerService" interface="com.ckj.projects.api.ProviderService"/>

    <!--服务订阅者-->
    <mdubbo:subscriber id="taskExcutorService" interface="com.ckj.projects.api.TaskExcutorService"/>

</beans>

第六步，服务RPC调用

    @Autowired
    ProviderService providerService;
    @Resource
    TaskExcutorService taskExcutorService;

    @Test
    public void testProviderService(){
        System.out.println(providerService.getClass().getName());
        String res=providerService.doService("hello boy!");
        System.out.println("providerService.doService():"+res);
        System.out.println("providerService.getMsg():"+providerService.getMsg());
        Message[] msglist=providerService.getMsgList();
        System.out.println("providerService.getMsgList():"+ Arrays.toString(msglist));
        providerService.sendListMsg(msglist);
        System.out.println("完成。。。");
        System.out.println(taskExcutorService.doTask(new Message(),new Message()));
    }
    
    开发完成：链接地址：
    索引服务器：https://github.com/chenfengluoye/MDubboIndexServer
    MdubboClient:https://github.com/chenfengluoye/MDubboClient
    MdubboDemo:https://github.com/chenfengluoye/MDubboDemo

   
