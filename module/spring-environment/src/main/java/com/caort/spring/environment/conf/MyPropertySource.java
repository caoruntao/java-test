package com.caort.spring.environment.conf;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.Environment;
import org.springframework.core.env.MutablePropertySources;
import org.springframework.core.env.PropertiesPropertySource;

import java.util.Properties;

/**
 * @author Reed
 * @date 2021/7/28 上午9:28
 */
@Configuration
public class MyPropertySource implements EnvironmentAware, InitializingBean, ApplicationContextAware {
    private ConfigurableApplicationContext applicationContext;

    @Override
    public void setEnvironment(Environment environment) {
        // 不推荐，该方法由ApplicationContextAwareProcessor#postProcessBeforeInitialization处理
        // 太依赖于BeanDefinition的注册顺序，如果该bean初始化的较晚，则不起任何作用
        /*if(environment instanceof ConfigurableEnvironment){
            this.environment = (ConfigurableEnvironment)environment;
            Properties properties = new Properties(1);
            properties.put("user.id", 2);

            PropertiesPropertySource myPropertySource = new PropertiesPropertySource("myPropertySource", properties);

            MutablePropertySources propertySources = this.environment.getPropertySources();
            propertySources.addFirst(myPropertySource);
        }else {
            throw new IllegalArgumentException("environment is not ConfigurableEnvironment");
        }*/
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        // 不推荐，AbstractAutowireCapableBeanFactory.invokeInitMethods处理
        // 太依赖于BeanDefinition的注册顺序，如果该bean初始化的较晚，则不起任何作用
        /*Properties properties = new Properties(1);
        properties.put("user.id", 2);

        PropertiesPropertySource myPropertySource = new PropertiesPropertySource("myPropertySource", properties);

        MutablePropertySources propertySources = environment.getPropertySources();
        propertySources.addFirst(myPropertySource);*/

    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        if (applicationContext instanceof ConfigurableApplicationContext) {
            this.applicationContext = (ConfigurableApplicationContext) applicationContext;
            Environment environment = this.applicationContext.getEnvironment();
            if (environment instanceof ConfigurableEnvironment) {
                ConfigurableEnvironment configurableEnvironment = (ConfigurableEnvironment) environment;
                Properties properties = new Properties(1);
                properties.put("user.id", 2);

                PropertiesPropertySource myPropertySource = new PropertiesPropertySource("myPropertySource", properties);

                MutablePropertySources propertySources = configurableEnvironment.getPropertySources();
                propertySources.addFirst(myPropertySource);
            }
        } else {
            throw new IllegalArgumentException("applicationContext is not ConfigurableApplicationContext");
        }
    }
}
