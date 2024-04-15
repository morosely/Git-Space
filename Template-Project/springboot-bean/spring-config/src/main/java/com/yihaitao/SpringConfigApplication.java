package com.yihaitao;

import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class SpringConfigApplication {

    public static void main(String[] args) {
        //ctrl+alt+v
        ConfigurableApplicationContext context = SpringApplication.run(SpringConfigApplication.class, args);
        /*String user = context.getEnvironment().getProperty("user");
        String password = context.getEnvironment().getProperty("password");
        System.out.println("==========> 【user】 "+user + " ==========> 【password】 "+password);*/
        //AnnotationConfigServletWebServerApplicationContext
        System.out.println("==========> context 【BeanFactory】 = " + context);
        //DefaultListableBeanFactory
        SpringConfigApplication bean = context.getBean(SpringConfigApplication.class);
        System.out.println("==========> SpringConfigApplication = " + bean);

        //获取BeanDefinition对象
        ConfigurableListableBeanFactory beanFactory = context.getBeanFactory();
        BeanDefinition messageBdf = beanFactory.getBeanDefinition("message");
        BeanDefinition myControllerBdf = beanFactory.getBeanDefinition("myController");
        System.out.println("==========> @Bean : " + messageBdf.getClass());
        System.out.println("==========> @Component : " + myControllerBdf.getClass());
        System.out.println("==========> Teacher : "+context.getBean("teacher"));

    }

    public static boolean beanExists(ApplicationContext context, String beanName) {
        return context.containsBean(beanName);
    }

    public static <T> boolean beanExists(ApplicationContext context,Class<T> beanType) {
        try {
            context.getBean(beanType);
            return true;
        } catch (NoSuchBeanDefinitionException e) {
            return false;
        }
    }

}
/* ApplicationContextInitializer
 * SpringBoot框架在设计之初,为了有更好的兼容性,在不同的运行阶段,提供了非常多的扩展点, 可以让程序员根据自己的需求, 在整个Spring应用程序运行过程中执行程序员自定义的代码
 * IOC容器对象创建完成后执行,可以对上下文环境做一些操作, 例如运行环境属性注册等
 * 1.ApplicationContextInitializer如何使用?
 *  自定义类,实现ApplicationContextInitializer接口
 *  在META-INF/spring.factories配置文件中配置自定义的类
 * 2.initialize方法什么时候执行?
 * IOC容器对象创建完成后执行, 常用于环境属性注册

 * ApplicationListener
 * 监听容器发布的事件, 允许程序员执行自己的代码,完成事件驱动开发, 它可以监听容器初始化完成、初始化失败等事件. 通常情况下可以使用监听器加载资源,开启定时任务等
 * 1.ApplicationListener如何使用?
 *  自定义类,实现ApplicationListener接口
 *  META-INF/spring.factories配置文件中配置自定义的类
 * 2.onApplicationEvent方法什么时候执行?
 *  IOC容器发布事件之后执行, 通常用于资源加载, 定时任务发布等

 * BeanFactory
 * Bean容器的根接口, 提供Bean对象的创建、配置、依赖注入等功能
 * 1.BeanFactory的作用?
 *  Bean容器的根接口, 提供Bean对象的创建、配置、依赖注入等功能
 *  AnnotationConfigServletWebServerApplicationContext  DefaultListableBeanFactory
 * 2.BeanFactory常见的两个实现?
 *  ApplicationConfigServletWebServerApplicationContext
 *  DefaultListableBeanFactory

 * BeanDefinition
 * 用于描述Bean，包括Bean的名称，Bean的属性，Bean的行为，实现的接口，添加的注解等等，Spring中，Bean在创
 * 建之前，都需要封装成对应的BeanDefinition，然后根据BeanDefinition进一步创建Bean对象
 *
 * BeanFactoryPostProcessor
 * Bean工厂后置处理器，当BeanFactory准备好了后(Bean初始化之前)，会调用该接口的postProcessBeanFactory方法，
 * 经常用于新增BeanDefinition
 * ConfigurationClassPostProcessor 扫描启动类所在包下的注解
 * ServletComponentRegisteringPostProcessor  扫描@WebServlet、@WebFilter、@WebListener
 * CachingMetadataReaderFactoryPostProcessor 配置ConfigurationClassPostProcessor
 * ConfigurationWarningsPostProcessor 配置警告提示
 *
 * Aware
 * 感知接口，Spring提供的一种机制，通过实现该接口，重写方法，可以感知Spring应用程序执行过程中的一些变化。
 * Spring会判断当前的Bean有没有实现Aware接口，如果实现了，会在特定的时机回调接口对应的方法
 * BeanNameAware Bean名称的感知接口
 * BeanClassLoaderAware Bean类加载器的感知接口
 * BeanFactoryAware Bean工厂的感知接口
 *
 * InitializingBean/DisposableBean
 * 1.初始化接口，当Bean被实例化好后，会回调里面的函数，经常用于做一些加载资源的工作
 * 2.销毁接口，当Bean被销毁之前，会回调里面的函数，经常用于做一些释放资源的工作
 *
 * BeanPostProcessor
 * Bean的后置处理器，当Bean对象初始化之前以及初始化之后，会回调该接口对应的方法
 * 1.postProcessBeforeInitialization: Bean对象初始化之前调用
 * 2.postProcessAfterInitialization: Bean对象初始化之后
 * AutowiredAnnotationBeanPostProcessor 用来完成依赖注入
 * AbstractAutoProxyCreator 用来完成代理对象的创建
 * AbstractAdvisingBeanPostProcessor 将Aop中的通知作用于特定的Bean上
 */
