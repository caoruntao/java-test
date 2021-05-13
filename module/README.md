很多技术都是概念包装的，本身并没有什么新的东西，所以对待新技术不能一味的推崇。技术为业务服务。

private < default < protected < public

Java只存在值传递：在方法参数引用类型调用中，将引用指向其他对象，外部引用依然指向原对象。

@DependsOn
	表示该bean依赖于其他bean，这样该bean会在被依赖的bean实例化后在实例化。

AspectJ切点表达式：
	execution(modifiers-pattern? ret-type-pattern declaring-type-pattern?name-pattern(param-pattern) throws-pattern?)
	「?」表示当前选项可有可无，其余各项含义如下：
		modifiers-pattern：方法的可见性，如public，protected；
		ret-type-pattern：方法的返回值类型，如int，void等；
		declaring-type-pattern：方法所在类的全路径名，如com.xuuuuu.demo；
		name-pattern：方法名类型，如test()；
		param-pattern：方法的参数类型，如java.lang.String；
		throws-pattern：方法抛出的异常类型，如java.lang.Exception。

JWT的几个问题：
	一：如何设置请求头
		由前端去设置
	二：如何进行续约
		登录成功后将token(用用户的密码做密钥)放入缓存中，有效期是token有效期的2倍。缓存的key由一些前缀和token组成，这样子可以保证不同设备同时接入。
		对于前端token只验证合法性，不验证有效性。
		有效性验证取缓存中的token验证，验证通过无需处理，不通过(前端token验证了合法性，此处的不通过指有效期过了)则重新生成token覆盖旧值。

spring cloud组件：
	服务配置中心 config Apoll(携程) Disconf(百度) nacos(阿里)
	服务注册发现 eureka zookeeper nacos consul 
	负载均衡 ribbon
	服务调用 feign
	服务熔断 hystrix
	服务网关 gateway/zuul
	服务流 stream
	服务链路追踪 sleuth
	消息总线 bus

获取类的方法：
	Class.forName()
	类名.class
	实例.getClass()

线程池执行流程：
	先看当前线程数是否小于corePoolSize
		如果小于则新建线程执行该任务。
		如果不小于，则看是否可以加入任务队列
			如果可以加入，就加入到任务队列
			如果不可以加入，则看当前线程数是否小于maximumPoolSize：
				如果小于则新建线程执行任务
				如果不小于，则选择拒绝策略

Maven：
	dependencies和dependencyManagement区别：
		子项目会自动导入父项目中的dependencies的依赖。却不会自动导入dependencyManagement依赖，如果要导入dependencyManagement的依赖，则不用指定版本号，因为会使用dependencyManagement中依赖的版本号。

Redis：
	批量删除：
		./redis-cli -h 192.168.252.31 -p 20081 -n 6 keys "micro-service:service-product:*" | xargs ./redis-cli -h 192.168.252.31 -p 20081 -n 6 del

分布式锁：
	注意事项：
		互斥
		A的锁被B取消了
		超时处理
	

http响应码分布：
	1xx：处理中
	2xx：处理成功
	3xx：重定向
	4xx：客户端问题
	5xx：服务端问题
http状态码:
304 not modify,可以用作缓存
http先读取header信息后读取body信息。请求第一次完整(header+body)读取信息，后续，先读取header，如果状态码为403，则不读取body，取上一次的body信息
读取的具体流程？

Rest请求：
动作：GET/POST/PUT/DELETE
请求：
	请求头(多值，(1)key:(n)value)
	请求体
@ResponseBody -->  ResponseEntity(@Nullable T body, @Nullable MultiValueMap<String, String> headers, HttpStatus status)

URL和URI：
	URI：Uniform Resource Identifier。URL：Uniform Resource Locator。
	URI是张三，URL是河南的张三或者上海的张三。

Spring：
	包扫描：
	ComponentScan -> Configuration basePackages -> Component Bean -> BeanDefiniion -> BeanDefinitionRegistry -> Beans -> BeanFactory -> getBean或@AutoWired

	Bean生命周期：
		实例化前  InstantiationAwareBeanPostProcessor#postProcessBeforeInstantiation
		实例化
		实例化后  InstantiationAwareBeanPostProcessor#postProcessAfterInstantiation
		初始化前  BeanPostProcessor#postProcessBeforeInitialization
		初始化
		初始化后  BeanPostProcessor#postProcessAfterInitialization
		销毁

	事件组件：
		事件：ApplicationEvent。事件监听器：ApplicationListener。事件发布者：ApplicationEventMulticaster。

	全局异常处理：
		在类上标记@ControllerAdvice，即可为所有的标记@Controller的类进行增强。在该类的方法上标记@ExceptionHandler，即可处理异常。

	DispathcerServlet对应多个HandlerMapping，HandlerMapping对应多个HandlerInterceptor。
	抽象类最值得看


Spring Boot：
	启动方式：
		SpringApplication
		SpringApplicationBuilder
	端点：
		actuator默认开放health和info两个endpoint，想要开启其他需要添加依赖(spring-boot-starter-actuator)并开启web访问权限。
	应用上下文：
		作用：
			应用上下文虽然实现BeanFactory接口，但本身并不具备相应的能力，而是要委派给DefaultListableBeanFactory去提供服务。并通过控制DefaultListableBeanFactory去管理Bean的生命周期。
			管理Environment，一个Environment对应多个PropertieSource，可通过调整PropertieSource的顺序，实现配置数据的优先级(PropertieSource越靠前优先级越高)。外部化配置。
		场景：
			spring boot 可以开启两个上下文即服务上下文(默认开启)和管理上下文
			spring cloud 可开启bootstrap上下文
			层次关系：
				applicationContext -parent-> bootstrap -parent-> null
		外部化配置：
			JDK提供了零碎的配置API
			Apache common configuration提供了完整的配置API，但是无法进行动态更新和写入
			Netfix archu** 提供了动态更新和写入，但是没有类型转换
			SpringFramework提供了类型转换

			疑问：
				Environment的层次性。ApplicationContext继承了BeanFactory，包含了Environment。
				application.properties可以覆盖bootstrap.properties。ApplicationContext的层次性
				指定的profile文件会覆盖默认的profile文件。springboot外部化配置顺序   
				答：Profile-specific application properties outside of your packaged jar (application-{profile}.properties and YAML variants).
				    Profile-specific application properties packaged inside your jar (application-{profile}.properties and YAML variants).
					Application properties outside of your packaged jar (application.properties and YAML variants).
					Application properties packaged inside your jar (application.properties and YAML variants).

Spring Cloud：
	配置中心：
		配置中心配置在bootstrap.properties，因为Bootstrap ApplicationContext会优先于Service ApplicationContext启动。
	负载均衡：
		ribbon是客户端负载均衡，通过给restTemplate设置interceptor。
	熔断断流：
		超时熔断/QPS超过阈值熔断
		hystrix超时熔断底层是使用Future实现的(Observer最后也会转化为Future)
	Stream:
		提供了统一的API接口，屏蔽各大MQ厂商的API细节。
		监听器：
			不同的编程模式间(接口编程，SpringCloud注解编程，Reactive注解编程)会轮训收到消息，同种编程模型都会收到消息
		API：
			@Source：来源，发布者
			@Sink：流向，订阅者
			@StreamListener：监听器
		RabiitMQ:
			一个Queue被多个Consumer监听，则会分摊消费。如果一个消息匹配多个bindingkey到一个Queue，则消息只会投递一份。
			支持回调，在消息属性中(AMQP定义了14个属性)，其中replyTo和correlationId定义了回调确认消息要发送到的Queue和已消费消息的Id。
			@RabbitListener可标注在方法上进行消息监听处理，但是方法声明只能声明一种类型的信息。可以在类上标注@RabbitListener，然后在方法上标注@RabbitHandler，这样就可以写多个方法进行处理。
	
	Bus:
		通过事件/监听机制来进行程序间信息的传递。使用事件可以屏蔽底层的细节(MQ需要依赖API，HTTP需要依赖HTTPClient，RPC需要依赖框架)。

		JAVA事件：
			EventObject(监听器可以监听多种事件，监听器入参为事件/事件源，无返回值，不会抛出异常)。该事件监听是基于观察者模式。
		Spring事件：
			事件ApplicationEvent，监听器ApplicationListener，发布者ApplicationEventMulticaster(一个监听器只监听一种类型的事件(单一职责))。
			标记@EventListener的方法，会被转换为ApplicationListenerMethodAdapter。
			可通过实现SmartApplicationListener进行多事件监听(因为Spring中的事件监听器只监听一种事件，所以需要实现以监听多事件)。
		使用：
			首先给自己发送事件，然后监听器监听后再发送(HTTP/RPC/MQ)事件(将收到的数据转换为事件)给其他应用。可以结合Spring Cloud Stream使用


Java security:
	加密/解密，权限。很多代码都有访问权限,如类加载器，反射，会抛出SecurityException
	权限定义文件：
		java.policy
		java.security
	API：
	java.lang.SecurityManager
	java.security.Permission:
		#checkPermission
	java.security.AccessController:
		#doPrivileged
	沙箱机制：
		ClassLoader 安全:
			类加载器分为启动类加载器和其他类加载器,这里说的是启动类加载器
			类加载器的双亲(不是指继承，而是组合)委派机制由代码实现(Bootstrap ClassLoader是C++写的，因此获取的Bootstrap ClassLoader对象是null):
				当类加载器接收到一个加载请求时，会将请求委托给父类加载器(如果有)，如果父加载器也存在其父类加载器，则进一步委托，依次类推，直到顶级类加载器。
				顶级类加载器尝试去加载，如果无法加载，子加载器才会去加载。
				谁加载的类由谁管理(安全)。
					ClassLoader#defineClass()可以修改字节码文件，但是java.lang.String无法被修改，因为java.lang包由Bootstrap类加载器加载，而Bootstrap类加载器对象为null，因此无法修改String的字节码文件。


Spring：
	关系图：
		BeanFactory
			ListableBeanFactory
				ConfigurableListableBeanFactory
			AutowireCapableBeanFactory
				DefaultListableBeanFactory
			HierarchicalBeanFactory
				ConfigurableBeanFactory <- SingletonBeanRegistry
					ConfigurableListableBeanFactory <- AutowireCapableBeanFactory
		AbstractBeanFactory  <- ConfigurableBeanFactory
			AbstractAutowireCapableBeanFactory <- AutowireCapableBeanFactory
				DefaultListableBeanFactory <- ConfigurableListableBeanFactory,BeanDefinitionRegistry

		ApplicationContext -> HierarchicalBeanFactory,ListableBeanFactory,ApplicationEventPublisher,EnvironmentCapable,MessageSource,ResourcePatternResolver
			ConfigurableApplicationContext   <- Lifecycle,Closeable
				AbstractApplicationContext   <- DefaultResourceLoader
					GenericApplicationContext  <- BeanDefinationRegistry
						AnnotationConfigurableApplicationContext   <- AnnotationConfigRegistry
	Spring核心模块：
	　　　Spring-Core:资源管理(Resource)，泛型(GenericTypeResolver)
	　　　Spring-Beans:依赖查找(BeanFactory)，依赖注入(@Autowired)
	　　　Spring-AOP:动态代理，AOP字节码提升(cglib依赖asm)
	　　　Spring-Context:事件驱动(ApplicationEvent)，注解驱动(@Component,@ComponentScan)，模式驱动(@EnableCache)
	　　　Spring-Expression:表达式

	Java Beans：
		BeanInfo
		PropertyDescriptor
		Introspector

	Ioc容器：
		IOC，控制反转，将自身的控制权交给外界对象，如Spring IOC容器，容器负责管理Bean的生命周期和依赖处理，依赖处理分为依赖注入和依赖查找。
		依赖查找：
			拉模式
		依赖注入：
			推模式
		依赖来源：
			自定义Bean
			容器内建Bean
			容器内建依赖：ResolvableDependency。BeanFactory/ResourceLoader/ApplicationEventPublisher/ApplicationContext。

	BeanDefinition：
		Spring Bean的定义，包含BeanClassName，Scope，LazyInit，是否自动注入，属性值等
		构建：
			配置文件：xml,properties
			注解：@Component
			API：BeanDefinitionBuilder，GenericBeanDefinition
		解析(读取)：
			配置文件：
				BeanDefinitionReader,有三个对应实现XmlBeanDefinitionReader，PropertiesBeanDefinitionReader，GroovyBeanDefinitionReader
			注解：
				AnnotatedBeanDefinitionReader
		注册：
			BeanDefinitionRegistry#registerBeanDefinition

	BeanDefinition中的beanClass原本是String类型，经过ClassLoader加载，变成对应的Class对象
		AbstractBeanDefinition#resolveBeanClass


	实例化Bean的方式：
		普通：
			类的静态方法 factory-method
			实例方法 xxxFactory
			FactoryBean  implement FactoryBean
		特殊:
			ServiceLoader:
				META-INF/services下创建名为接口全类名的文件，内容为具体实现类，如果实现类重复会自动去重，不会报错。
				ServiceLoader#load
			ServiceLoaderFactory:
				META-INF/services下创建名为接口全类名的文件，内容为具体实现类，如果实现类重复会报错。
		AutowireCapableBeanFactory:
			ApplicationContext#getAutowireCapableBeanFactory
			ApplicationContext#createBean
		BeanDefinitionRegistry:
			BeanDefinitionRegistry#registerBeanDefinition

	Bean初始化/销毁方法回调：
		初始化：
			添加方法并加注解：@javax.annotation.PostConstruct(CommonAnnotationBeanPostProcessor)
			实现接口：org.springframework.beans.factory.InitializingBean
			指定注解属性：@org.springframework.context.annotation.Bean#initMethod
			优先级按以上顺序
		销毁(prototype不会回调，因为prototype对象创建出来，生命周期就不由容器管理了)：
			添加方法并加注解：@javax.annotation.PreDestroy
			实现接口：org.springframework.beans.factory.DisposableBean
			指定注解属性：@org.springframework.context.annotation.Bean#destroyMethod
			优先级按以上顺序

	依赖查找：
		类型：
			单一类型
			集合类型
			层次类型
			延迟查找：
				ObjectFactory
				ObjectProvider
	内建Bean：
		AnnotationConfigUtils#CONFIGURATION_ANNOTATION_PROCESSOR_BEAN_NAME:ConfigurationClassPostProcessor.class
		AnnotationConfigUtils#AUTOWIRED_ANNOTATION_PROCESSOR_BEAN_NAME:AutowiredAnnotationBeanPostProcessor.class
		AnnotationConfigUtils#COMMON_ANNOTATION_PROCESSOR_BEAN_NAME:CommonAnnotationBeanPostProcessor.class
		AnnotationConfigUtils#PERSISTENCE_ANNOTATION_PROCESSOR_BEAN_NAME:PersistenceAnnotationBeanPostProcessor.class
		AnnotationConfigUtils#EVENT_LISTENER_PROCESSOR_BEAN_NAME:EventListenerMethodProcessor.class
		AnnotationConfigUtils#EVENT_LISTENER_FACTORY_BEAN_NAME:DefaultEventListenerFactory.class


	异常类型：
		NoSuchBeanDefinitionException
		NoUniqueBeanDefinitionException
		BeanCreationException:非具体实现类(可能是抽象类或接口)
		BeanInitializationException:初始化失败
		BeanDefinitionStoreException:读取BeanDefinition信息失败,信息可能来自XML

	题：
		ObjectFactory和BeanFactory区别：
			ObjectFactory和BeanFactory都能提供依赖查找。ObjectFactory只能提供单一类型的依赖查找，但是可以延迟查找，ObjectFactory本身并不具备依赖查找的能力，而是依赖于BeanFactory来提供。BeanFactory可以提供单一类型，集合类型，层次类型的查找。
		BeanFactory#getBean是线程安全的。


	依赖注入：
		setter方法注入：
			适用于依赖项较多，顺序无法保证
			手动：
				xml:<property name="" ref="">
				注解:方法上加@Bean，然后方法参数为需要注入的字段，初始化后，调用setter方法赋值
				API:BeanDefinitionBuilder#addPropertyReference
			自动：
				xml:autowir**="byName"/"byType"
		构造器注入：
			适用于依赖项较少，强依赖，顺序可以保证
			手动：
				xml:<contructor-arg>
				注解:方法上加@Bean，然后方法参数为需要注入的字段，初始化时使用参数赋值
				API:BeanDefinitionBuilder#addConstructorArgReference
			自动：
				xml:autowir**="constructor"
		字段注入：
			@Autowired
			@Resource
			@Inject(JSR-330):需要依赖jsr包
		方法注入：
			@Autowied
			@Resource
			@Inject(JSR-330)
			@Bean
		接口回调注入：
			ApplicationContextAware
			BeanFactoryAware
	常量注入：
		@Value("${key}")表示注入外部化配置中key所对应的值
		@Value("#{bean.field}")表示spel表达式，bean.field表示取bean实例的field字段的值

	@Qualifier：
		配合@Autowired使用。@Autowired是根据类型注入，如果想要根据名字注入，就需要添加@Qualifier("beanName")。
		还可以用于分组，在需要分组的bean上标记@Qualifier，然后在分组集合上也标记@Qualifier，这样集合中只会含有标记@Qualifier的bean
		扩展时，在派生注解上标记@Qualifier。使用派上注解标记bean时，使用@Qualifier也能获取到该bean

	依赖处理入口：
		AbstractAutowireCapableBeanFactory#resolveDependency
		DependencyDescriptor 依赖描述符

		@Autowired：
			AutowiredAnnotationBeanPostProcessor#postProcessMergedBeanDefinition 子类合并父类的属性
			AutowiredAnnotationBeanPostProcessor#postProcessProperties

		自定义依赖注入：
			在自定义注解上添加@Autowired，使其成为派生类
			创建自定义注解，创建AutowiredAnnotationBeanPostProcessor，设置优先级PriorityOrdered，调用setAutowiredAnnotationTypes注入自定义注解(set方法会覆盖掉所有的types，不过默认的AutowiredAnnotationBeanPostProcessor会处理原有的注解)
				注意：自定义的AutowiredAnnotationBeanPostProcessor如果在实例中，需要设置为static，如果不设置，那么AutowiredAnnotationBeanPostProcessor的包含类不会被处理。因为AutowiredAnnotationBeanPostProcessor属于BeanPostProcessor，
					AbstractApplicationContext#refresh:
						#registerBeanPostProcessors:
							PostProcessorRegistrationDelegate#registerBeanPostProcessors:
								beanFactory.getBean(ppName, BeanPostProcessor.class):获取BeanPostProcessor，因为还未实例化，这里进行实例化
									在该阶段，如果AutowiredAnnotationBeanPostProcessor属于实例，那么会实例化实例，而此时BeanPostProcessors并未被注册，因此不会处理该实例。
									AbstractAutowireCapableBeanFactory#createBeanInstance:
										#instantiateUsingFactoryMethod:	
											String factoryBeanName = mbd.getFactoryBeanName()
											factoryBean = this.beanFactory.getBean(factoryBeanName)
								registerBeanPostProcessors(beanFactory, priorityOrderedPostProcessors):注册BeanPostProcessors

	依赖注入来源：
		依赖查找：
			BeanDefinition：xml配置，java注解，api
			单列对象
		依赖注入：
			BeanDefinition：org.springframework.beans.factory.support.BeanDefinitionRegistry#registerBeanDefinition
			单列对象：org.springframework.beans.factory.config.SingletonBeanRegistry#registerSingleton
			ResolvableDependency：org.springframework.beans.factory.config.ConfigurableListableBeanFactory#registerResolvableDependency
			外部化配置
		单列对象可以在容器启动后注册吗？
			可以。和BeanDefinition不同，因为BeanDefinition注册会调用ConfigurableListableBeanFactory#freezeConfiguration，所以不能后注册。ConfigurableListableBeanFactory#freezeConfiguration设置configurationFrozen状态，configurationFrozen属于DefaultListableBeanFactory，而非ConfigurableListableBeanFactory(继承于ConfigurableBeanFactory(继承于SingletonBeanRegistry))。子类属性无法控制父类行为。

	Bean的作用范围：
		@Scop
		派生类：@ApplicationScope,@RequestScope,@SessionScope
		singleton：
			单列，一个BeanFactory中只存在一个对应实例。
			类比JVM，静态变量在ClassLoader中是唯一的，一个类只会被一个ClassLoader加载，所以静态变量在jvm是唯一的
		prototype：
			原型，每次依赖查找/注入都会产生新的对象。
			原型实例创建后就不归Spring管理，因此，Spring只会调用对应的初始化方法，而不会调用销毁方法。
			如果想要调用原型实例的销毁方法，可以在原型对象使用类上实现销毁方法，然后在销毁方法中调用原型实例的销毁方法。
		request:
			在后端注入时是同一个对象，返回前端时是该对象的代理对象，返回前端时每次都会产生新的代理对象，会调用初始化方法和销毁方法。
			在AbstractRequestAttributesScope中返回前端数据时，可以验证。
		session:
			在同一个回话中只存在一个实例。
		application:
			在一个应用上下文中唯一。
		websocket:

		自定义Scope：
			实现Scope接口:
				org.springframework.beans.factory.config.Scope
			注册Scope:
				org.springframework.beans.factory.config.ConfigurableBeanFactory#registerScope
			SpringCloud的RefreshScope就是自定义Scope

		单列在BeanFactory中是唯一的在应用中不一定。在同一个BeanFactory中，如果允许覆盖，则后注册的会覆盖掉先前注册的。

	

	CommonAnnotationBeanPostProcessor处理的@PostConstruct是在初始化前阶段，@PreDestroy是在销毁化前阶段

	Aware接口：
		Spring中以Aware结尾的接口可以理解为回调接口。和BeanFacotry有关的回调接口包括BeanNameAware，BeanClassLoaderAware，BeanFacotryAware。和ApplicationContext有关的接口包括EnvironmentAware，EmbeddedValueResolverAware，ResourceLoaderAware，ApplicationEventPublisherAware，MessageSourceAware，ApplicationContextAware。

	Bean创建：
		主要分为InstantiationAwareBeanPostProcessor处理和SmartInitializingSingleton处理。
		实例化前阶段：
			InstantiationAwareBeanPostProcessor#postProcessBeforeInstantiation：
				如果返回值为null，则走Spring容器的默认实现，否则就使用返回对象作为实例。
		属性赋值：
			AbstractAutowireCapableBeanFactory#populateBean：
		实例化后阶段：
			InstantiationAwareBeanPostProcessor#postProcessAfterInstantiation
		Aware回调：
			AbstractAutowireCapableBeanFactory#invokeAwareMethods
			ApplicationContextAwareProcessor#invokeAwareInterfaces(引用上下文Aware回调)
		初始化前阶段：	
			AbstractAutowireCapableBeanFactory#applyBeanPostProcessorsBeforeInitialization
		初始化后阶段：	
			AbstractAutowireCapableBeanFactory#applyBeanPostProcessorsAfterInitialization
		后阶段：
			SmartInitializingSingleton#afterSingletonsInstantiated：
				DefaultListableBeanFactory#preInstantiateSingletons：需要显示调用，可以检验Bean初始化是否完整

	Bean销毁：
		实现DestructionAwareBeanPostProcessor，并添加到BeanFactory。ConfigurableBeanFactory#addBeanPostProcessor是FIFO，因此添加时需要注意顺序，如先添加CommonAnnotationBeanPostProcessor，后添加DestructionAwareBeanPostProcessor，则会先回调@PreDestroy方法，后回调postProcessBeforeDestruction方法。

		调用ConfigurableBeanFactory#destroyBean会销毁容器中的实例，具体实现在DisposableBeanAdapter#destroy。但是销毁实例并不代表被GC回收，只是从容器中删除。

	Spring Bean的创建：
		AbstractApplicationContext#finishBeanFactoryInitialization：
			ConfigurableListableBeanFactory#preInstantiateSingletons：
				AbstractBeanFactory#getBean：
					#doGetBean：
						DefaultSingletonBeanRegistry#getSingleton：
							ObjectFactory#getObject：

		AbstractAutowireCapableBeanFactory#createBean：
			resolveBeforeInstantiation：
				applyBeanPostProcessorsBeforeInstantiation：
					InstantiationAwareBeanPostProcessor#postProcessBeforeInstantiation：如果返回不为null，调用applyBeanPostProcessorsAfterInitialization后，跳过Spring默认初始化，直接返回该实例。
			doCreateBean：
				createBeanInstance：
					instantiateBean：默认调用SimpleInstantiationStrategy#instantiate实例化。
				applyMergedBeanDefinitionPostProcessors：Allow post-processors to modify the merged bean definition.
				populateBean：
					InstantiationAwareBeanPostProcessor#postProcessAfterInstantiation
					autowireByName/autowireByType：将依赖对象放入到MutablePropertyValues中
					InstantiationAwareBeanPostProcessor#postProcessProperties
					applyPropertyValues：对象赋值
						AbstractPropertyAccessor#setPropertyValues：
				initializeBean：
					invokeAwareMethods：Aware接口回调(BeanFactory级别的)，如BeanNameAware，BeanClassLoaderAware，BeanFactoryAware。
						PS:EnvironmentAware是ApplicationContext级别的，EnvironmentAware -> ApplicationContextAwareProcessor#invokeAwareInterfaces -> ApplicationContextAwareProcessor#postProcessBeforeInitialization。ApplicationContextAwareProcessor由prepareBeanFactory调用插入。

					applyBeanPostProcessorsBeforeInitialization：调用对应的BeanPostProcessor#postProcessBeforeInitialization，如InstantiationAwareBeanPostProcessor，CommonAnnotationBeanPostProcessor。

					invokeInitMethods：调用InitializingBean#afterPropertiesSet，以及invokeCustomInitMethod(通过指定initMethodName自定义的初始化方法)

					applyBeanPostProcessorsAfterInitialization：调用对应的BeanPostProcessor#postProcessAfterInitialization，如InstantiationAwareBeanPostProcessor。

	Spring Bean的销毁：
		AbstractApplicationContext#close：
			AbstractApplicationContext#doClose：
				DefaultListableBeanFactory#destroySingletons：
					DefaultSingletonBeanRegistry#destroySingletons：
						DefaultSingletonBeanRegistry#destroyBean：
							DisposableBean#destroy：
								DestructionAwareBeanPostProcessor#postProcessBeforeDestruction：
								DisposableBeanAdapter#invokeCustomDestroyMethod：

		AbstractAutowireCapableBeanFactory#destroyBean：
			DisposableBeanAdapter#destroy：
				DestructionAwareBeanPostProcessor#postProcessBeforeDestruction：处理@PreDestroy，CommonAnnotationBeanPostProcessor -> InitDestroyAnnotationBeanPostProcessor -> DestructionAwareBeanPostProcessor。
				DisposableBean#destroy：
				invokeCustomDestroyMethod：通过指定destroyMethodName自定义的销毁方法

	题1：BeanPostProcessor：在Spring Bean初始化前阶段或者后阶段时对关心的Bean进行操作，分别提供了postProcessBeforeInitialization,postProcessAfterInitialization方法。和ApplicationContext的相关Aware回调就是ApplicationContextProcessor进行处理的，它是BeanPostProcessor的实现类。
	题2：BeanFactoryPostProcessor：可以对Spring BeanFactory进行一些后置操作，比如扩展，依赖查找。提供了postProcessBeanFactory(ConfigurableListableBeanFactory)，可以对ConfigurableListableBeanFactory添加BeanPostProcessor进行扩展，或者依赖查找。
	可以调用AbstractApplicationContext#addBeanFactoryPostProcessor进行添加。
	BeanFactoryPostProcessor通常和ApplicationContext相关，因为BeanFactory无法使用BeanFactoryPostProcessor对自己进行后置操作。
	BeanPostProcessor和BeanFactory相关，是N：1的关系。

	Spring Bean元信息：
		BeanDefinition：
			AbstractBeanDefinition：
				GenericBeanDefinition：定义时默认是该类型的BeanDefinition
				RootBeanDefinition：不带有parent的BeanDefinition，经过合并后都会变成该类型的BeanDefinition。AbstractBeanFactory#getMergedBeanDefinition
			AnnotatedBeanDefinition

		从资源中读取元信息：
			BeanDefinitionReader：
				AbstractBeanDefinitionReader：
					PropertiesBeanDefinitionReader：
					XmlBeanDefinitionReader：XML资源读取
						BeanDefinitionDocumentReader：读取Document资源
							BeanDefinitionParserDelegate：设置BeanDefinition默认值
		从注解中读取元信息：
			AnnotatedBeanDefinitionReader：因为注解属于类，类不属于资源，因此没有实现BeanDefinitionReader。
				ConditionEvaluator：ConditionEvaluator#shouldSkip判断是否跳过注册，结合@Conditional使用。

Spring IOC容器元信息：
	资源配置元信息：
		beans    spring-beans   https://www.springframework.org/schema/beans/spring-beans.xsd
		context  spring-context https://www.springframework.org/schema/context/spring-context.xsd
		aop      spring-aop     https://www.springframework.org/schema/aop/spring-aop.xsd
		tx	     spring-tx      https://www.springframework.org/schema/tx/spring-tx.xsd
		tool     spring-tool    https://www.springframework.org/schema/tool/spring-tool.xsd
		util     spring-util    https://www.springframework.org/schema/util/spring-util.xsd
		以上schema对应的处理类在META-INF/spring.handlers可以找到。spring.schema可以找到shcema的映射关系。
	注解配置元信息：
		@ImportResource
		@ComponentScan
		@PropertySource
			在JDK8之前是通过@PropertySources来标注多个@PropertySource，JDK8出现了@Repeatable，因此可以在一个类上打多个注解
    Spring Schema元素扩展：
    	1.创建xsd文件
    	2.在spring.schema指定xsd和命名空间的映射关系
    	3.在spring.handlers中指定xsd的NamespaceHandlerSupport
    	4.重写NamespaceHandlerSupport的init方法，注册 元素和AbstractSimpleBeanDefinitionParser的映射

    	调用链：
    		AbstractApplicationContext#refresh：
    			#obtainFreshBeanFactory：
    				AbstractRefreshableApplicationContext#refreshBeanFactory：
    					AbstractXmlApplicationContext#loadBeanDefinitions：
    						AbstractBeanDefinitionReader#loadBeanDefinitions：
    							XmlBeanDefinitionReader#loadBeanDefinitions：
    								#doLoadBeanDefinitions：
    									#registerBeanDefinitions：
    										DefaultBeanDefinitionDocumentReader#registerBeanDefinitions：
    											#doRegisterBeanDefinitions：
    												#parseBeanDefinitions：
    													BeanDefinitionParserDelegate#parseCustomElement：
    														DefaultNamespaceHandlerResolver#resolve：
    															NamespaceHandler#init：
   
	Spring 外部化配置：
		注解：
			@PropertySource
		接口：
			PropertySource

		添加PropertySource：
			EnvironmentCapable#getEnvironment：
				AbstractEnvironment#getPropertySources：
					MutablePropertySources#addFirst：
			因为MutablePropertySources以ArrayList存储PropertySource，实现为有数据就返回，因此，添加到首位具有最高优先级。

	    扩展yaml处理器：
	    	org.springframework.beans.factory.config.YamlProcessor:
	    		YamlMapFactoryBean
	    		YamlPropertiesFactoryBean

	    	org.springframework.core.io.support.PropertySourceFactory:
	    		#onApplicationEvent：

	    	PropertyResourceConfigurer

	    SpringBoot外部化配置加载：
	    	org.springframework.boot.SpringApplication#run:
	    		#prepareEnvironment:
	    			#getOrCreateEnvironment:
	    				new StandardServletEnvironment();
	    					父类构造函数会调用AbstractEnvironment#customizePropertySources
	    			SpringApplicationRunListeners#environmentPrepared:
	    				EventPublishingRunListener#environmentPrepared:发布ApplicationEnvironmentPreparedEvent事件

	    	org.springframework.boot.context.config.ConfigFileApplicationListener:
	    		#onApplicationEvent:
	    			#onApplicationEnvironmentPreparedEvent:
	    				#postProcessEnvironment:
	    					#addPropertySources:
	    						ConfigFileApplicationListener.Loader#load():
	    							#FilteredPropertySource#apply:
	    								#load:
		    								#getSearchLocations:文件路径。"file:./config/","file:./config/*/","file:./","classpath:/config/","classpath:/"
		    								#getSearchNames:文件名。application
		    								#load:
		    									#loadForFileExtension:
		    										#load:
		    											#getResources:
		    											#addActiveProfiles:
		    											#addToLoaded:
		    												#addLast:
		    							#addLoadedPropertySources:真正添加Propertie


	    题1：Spring相关schema，context，aop，tx，util，tool
	    题2：Spring有哪些配置元信息，BeanDefinition元信息，Spring容器元信息，外部化配置元信息。
	    题3：schema扩展机制


	Spring资源管理：
		URLStreamHandler
			JMXConnectorFactory#PROTOCOL_PROVIDER_PACKAGES指定自定义协议，多个协议用 | 隔开
			sun.net.www.propocol.{}.Handler

		InputStreamSource：
			Resource：
				ClassPathResource：
				FileSystemResource：
				UrlResource：

		资源加载器：
			ResourceLoader：
				DefaultResourceLoader：
					AbstractApplicationContext：

	Spring 国际化：
		MessageSource：
			HierarchicalMessageSource：
				AbstractMessageSource：
					ResourceBundleMessageSource：
					ReloadableResourceBundleMessageSource：
						可重新加载的。根据最后修改时间进行修改，但是有的文件不支持最后修改时间。
			ApplicationContext：

		Java国际化标准接口：
			ResourceBundle：
				ListResourceBundle：列举
				PropertyResourceBundle：property资源

			java.util.ResourceBundle.Control：
			SPI：
				ResourceBundleProvider：

		AbstractApplicationContext#refresh：
			initMessageSource：DelegatingMessageSource(Use empty MessageSource to be able to accept getMessage calls)
		
		SpringBoot：
			MessageSourceAutoConfiguration：创建ResourceBundleMessageSource。	


	Spring 校验：
		Validator：
		Errors：Errors本身是负责校验和存储错误信息，显示需要借助于ResourceBundle进行显示，通过errorCode和errorArgs属性对应文案
			ObjectError：对象的异常，可通过Errors#reject添加，可通过Errors#getGlobalErrors获取所有对象异常
			FieldError：属性的异常，Errors#rejectValue添加，可通过Errors#getFieldErrors获取所有属性异常
			Errors#getAllErrors：可获取所有异常，包括ObjectError和FieldError，因为FieldError is ObjectError，因此返回值类型为ObjectError
			Errors#pushNestedPath/popNestedPath：添加或者删除嵌套路径，和表达式有关

		LocalValidatorFactoryBean

	Spring 数据绑定：
		PropertyValues：

		DataBinder：
			ignoreUnknownFields：是否忽略未知字段。
			autoGrowNestedPaths：是否自动生成嵌套路径。为true时，a.b.c，如果a.b不存在，设置c时自动生成。
			ignoreInvalidFields：是否忽略非法字段，如果为false,并且setAutoGrowNestedPaths为true，且嵌套路径不存在时，则报错。
			allowedFields：白名单
			disallowedFields：黑名单，忽略名单中的字段填充
			requiredFields：必填字段

			#bind(PropertyValues)：将PropertyValues绑定到实例属性。
			
			BindingResult：绑定结果
				BeanWrapper：
					关联了JavaBeans的API

	JavaBeans：
		Introspector：内省
			Introspector#getBeanInfo(java.lang.Class<?>)：获取BeanInfo
		BeanInfo：
			java.beans.BeanInfo#getBeanDescriptor
			java.beans.BeanInfo#getPropertyDescriptors：get/set方法，不求成对出现，通过方法名去设置属性
			java.beans.BeanInfo#getMethodDescriptors
			java.beans.BeanInfo#getEventSetDescriptors

	数据绑定处理:
		#populateBean:
				#applyPropertyValues:
					BeanDefinitionValueResolver#resolveValueIfNecessary:
					#convertForProperty:
					PropertyValue#setConvertedValue:
					PropertyAccessor#setPropertyValues:
						AbstractNestablePropertyAccessor#setPropertyValue:
							#processLocalProperty:
								PropertyHandler#setValue:
									Method#invoke:
Spring 类型转换：
	JavaBeans：
		PropertyEditor：
			java.beans.PropertyEditor#setAsText：
				将文本数据(String)转换为对应的类型，然后通过PropertyEditor#setValue将转换后的数据保存起来。
			java.beans.PropertyEditor#getAsText：
				获取文本数据，通过PropertyEditor#getValue获取转换后的数据，然后在转换为文本数据。
		扩展PropertyEditor：
			1.propertyEditorSupport：PropertyEditor的实现类。如果想要扩展，可以继承该类。
			2.(可选)PropertyEditorRegistrar：PropertyEditor注册器。
			3.CustomEditorConfigurer：PropertyEditor配置器。
				1.可通过setCustomEditors(Map<Class<?>, Class<? extends PropertyEditor>> customEditors)直接配置PropertyEditor，key为PropertyEditor处理的类型。
				2.可通过setPropertyEditorRegistrars(PropertyEditorRegistrar[] propertyEditorRegistrars)配置PropertyEditorRegistrar

	Spring3.0之前就是通过继承PropertyEditorSupport来做类型转换的，因为PropertyEditor的局限性(只能String和类型间进行转换)，因此3.0以后提供了新的类型转换接口(Converter)。

	org.springframework.core.convert.converter.Converter：
		T convert(S source)：
			类型转换，可以灵活的在两个类型之间进行转换。但是只能转换单一类型(对集合类型无能为力)，并且只能转换，没有前置校验。
	ConditionalConverter：充当了Converter的前置校验。
		boolean matches(TypeDescriptor sourceType, TypeDescriptor targetType)
			TypeDescriptor：类型描述符
	GenericConverter：可以处理复杂(集合)类型，集合类型虽然有泛型声明，但是有泛型擦除，集合类型的元素存储在底层依旧是Object。
		Set<ConvertiblePair> getConvertibleTypes()：可以处理的类型。可以处理一对多类型，如一个source类型对应多个target类型。
			ConvertiblePair：sourceType和targetType的类型对。
		Object convert(@Nullable Object source, TypeDescriptor sourceType, TypeDescriptor targetType)：类型转换

	ConversionService：
		ConfigurableConversionService：继承了ConversionService和ConverterRegistry接口
			GenericConversionService：
				#addConverter：添加GenericConverter

	ConversionServiceFactoryBean：ConversionService的工厂Bean(有点类似注册器)。
	扩展Converter：
		1.实现ConditionalGenericConverter接口。
		2.注册ConversionServiceFactoryBean，调用setConverters(Set<?> converters)将实现类填充。
	
	TypeConverter：
		Spring使用的类型转换接口。
	TypeConverterSupport：
		TypeConverterDelegate：委派实现，被委派者可能是PropertyEditor，也可能是ConversionService。
			TypeConverterDelegate(PropertyEditorRegistrySupport propertyEditorRegistry)：
				PropertyEditorRegistrySupport：
					#getConversionService：获取ConversionService
					#getDefaultEditor：获取PropertyEditor

	Spring进行类型转换的过程：
		1.AbstractApplicationContext#refresh：启动应用上下文
			#finishBeanFactoryInitialization：
				初始化上下文中的conversionservice。查找名为conversionService，类型为ConversionService的Bean，并调用ConfigurableBeanFactory#setConversionService完成初始化.

		2.BeanDefinition->BeanWrapper：
			创建BeanWrapperImpl时，会注册内建的PropertyEditor
			AbstractAutowireCapableBeanFactory#instantiateBean：
				AbstractBeanFactory#initBeanWrapper：
					ConfigurablePropertyAccessor#setConversionService：设置的为ConfigurableBeanFactory的ConversionService。
					#registerCustomEditors：注册内建PropertyEditor

		3.setPropertyValues(PropertyValues)：
			TypeConverterDelegate#convertIfNecessnary：
				1.PropertyEditor
				2.ConversionService

	属性赋值:
		前置处理:
		AbstractAutowireCapableBeanFactory#doCreateBean:
			#createBeanInstance:
				#instantiateBean:
					new BeanWrapperImpl():
						new new TypeConverterDelegate():
					#initBeanWrapper:
						ConfigurablePropertyAccessor#setConversionService:
							ConversionService来源:
								AbstractApplicationContext#finishBeanFactoryInitialization:
									ConfigurableBeanFactory#setConversionService:默认是不存在的
						#registerCustomEditors:
							ResourceEditorRegistrar#registerCustomEditors:注册一系列Editor
								ResourceEditorRegistrar来源:
									AbstractApplicationContext#refresh:
										#prepareBeanFactory
										ConfigurableBeanFactory#addPropertyEditorRegistrar:
		赋值:
		#populateBean:
			#applyPropertyValues:
				BeanDefinitionValueResolver#resolveValueIfNecessary:
				#convertForProperty:类型转换
					BeanWrapperImpl#convertForProperty:
						AbstractNestablePropertyAccessor#convertForProperty:
							#convertIfNecessary:
								TypeConverterDelegate#convertIfNecessary:
									PropertyEditorRegistrySupport#findCustomEditor:寻找自定义的Editor
									PropertyEditorRegistrySupport#getConversionService:获取ConversionService
									TypeConverterDelegate#findDefaultEditor:获取默认的Editor
										PropertyEditorRegistrySupport#getDefaultEditor:
											this.overriddenDefaultEditors.get:获取ResourceEditorRegistrar注册的Editor，看是否有匹配的
											PropertyEditorRegistrySupport#createDefaultEditors:主持默认的Edtor
									TypeConverterDelegate#doConvertValue:
										TypeConverterDelegate#doConvertTextValue:
											java.beans.PropertyEditor#setAsText:
											java.beans.PropertyEditor#getValue:
				PropertyValue#setConvertedValue:
				PropertyAccessor#setPropertyValues:
					AbstractNestablePropertyAccessor#setPropertyValue:
						#processLocalProperty:
							PropertyHandler#setValue:
								Method#invoke:反射注入

	Spring 泛型处理：
		JAVA5引入泛型，为了不在运行时添加底层的负担，因此运行时并没有保存相关元信息，因此泛型是编译时才存在的(编译器检查)，在底层全部存的都是Object。List<String>  -> List,便可以存储其他类型的数据，因此底层存的Object。
	    JAVA默认不保存接口的参数元信息，如参数名称。可以通过添加JVM参数-parameters来促使jvm保存元信息

		GenericTypeResolver
		GenericCollectionTypeResolver

		MethodParameter：方法和构造器，二选一
		ResolvableType：Spring4.0以后提供的。

	Spring 事件：
		JAVA事件：
			对象：
				EventObject：事件。	
				EventListener：事件监听器，标记接口，没有强约束。
			模式：
				没有标准的事件监听模型，依托于观察者模式。Observable(被观察者，可以理解为数据发送源)，Observer(观察者)。
		Spring事件：
			对象：
				ApplicationEvent：Spring的事件。抽象类，不能直接使用。
					ApplicationContextEvent：
						Spring应用上下文事件，继承于ApplicationEvent，同为抽象类。
						ContextRefreshedEvent，ContextStartedEvent，ContextStoppedEvent，ContextClosedEvent：
							Spring应用上下文具体事件。在各个阶段处理时，框架会自动发布对应事件。
					PayloadApplicationEvent：
						自定义事件。DOC上说是内部使用事件，但是却用public修饰，如果想要扩展该类，应该使用
						class MyPayloadApplicationEvent<String> extends PayloadApplicationEvent<String>
						而不是class MyPayloadApplicationEvent extends PayloadApplicationEvent<String>
						最好不自己扩展该类，而是使用ApplicationEventPublisher#publishEvent(Object event)方法，该方法会自动将主体包装成PayloadApplicationEvent。

				ApplicationListener<E extends ApplicationEvent>：
					Spring事件监听器。可以监听E及其子类事件。
						可通过ConfigurableApplicationContext#addApplicationListener添加监听器。
						也可通过注册为Spring Bean，从而添加监听器。

				ApplicationEventPublisher：
					事件发布器。可通过ApplicationEventPublisherAware回调得到ApplicationEventPublisher对象。
						#publishEvent(ApplicationEvent event)：
							发布Spring事件。
						#publishEvent(Object event)：
							如果参数不是Application的子类，将会包装成PayloadApplicationEvent。
				ApplicationEventMulticaster：
					事件广播器。真正处理事件广播的类。ApplicationEventPublisher可以理解为标记接口，处理时会委派给ApplicationEventMulticaster。
			注解：
				@org.springframework.context.event.EventListener，标注在方法上，参数为监听的事件类型。
				如果同时存在注解和接口监听器，则优先输出注解的。注解之间可通过@org.springframework.core.annotation.Order指定顺序，越小越优先。接口之间，先注册先服务。
				可以在方法上再标记@org.springframework.scheduling.annotation.Async，同时在主类上标记@org.springframework.scheduling.annotation.EnableAsync，使EventListener变为异步执行的。
				处理:将注解标记的方法转换为ApplicationListenerMethodAdapter。EventListenerMethodProcessor
					AbstractApplicationContext#refresh:
						#finishBeanFactoryInitialization:
							DefaultListableBeanFactory#preInstantiateSingletons:
								EventListenerMethodProcessor#afterSingletonsInstantiated:
									#processBean:
										DefaultEventListenerFactory#createApplicationListener:
											new ApplicationListenerMethodAdapter(beanName, type, method)

				ApplicationListenerMethodAdapter处理事件:
					ApplicationListenerMethodAdapter#onApplicationEvent:
						#processEvent:
							#doInvoke:
								Method#invoke

		Spring事件传播：
			在多层次的应用上下文中，事件会具有传播性。如果当前应用上下文具有父应用上下文时，如果当前应用上下文发布事件，那么也会在父应用上下文发布该事件。表现的行为是，如果当前应用上下文和父应用上下文对同一类型的事件监听时，发布该事件时，会处理两次。可以通过设置信号量的方式去控制。

		ApplicationEventPublisher和ApplicationEventMulticaster的关系：
			ApplicationContext继承了ApplicationEventPublisher，说明具有发布事件的能力。但是具体实现是委派给pplicationEventMulticaster去实现的。getApplicationEventMulticaster().multicastEvent(applicationEvent, eventType);

		ApplicationEventMulticaster的初始化：
			AbstractApplicationContext#refresh：
				#initApplicationEventMulticaster： 
					如果上下文中存在名为applicationEventMulticaster，类型为ApplicationEventMulticaster的Bean，则使用该Bean。否则，将new SimpleApplicationEventMulticaster(beanFactory)，通过beanFactory#registerSingleton将外部Bean(生命周期不由容器管理)注册到容器中。

		应用上下文的早期事件：Spring3.0启用
			AbstractApplicationContext#earlyApplicationEvents。因为代码执行顺序的原因，可能有些事件在发布器初始化前就已经产生了，如BeanPostProcessor，如果3.0之前，将会产生一些意想不到的错误。在之后，便用earlyApplicationEvents存储起来，当事件发布器初始化完成时则再将事件发布。

		监听事件的同步/异步执行：
			对于接口：
				默认同步执行。调用SimpleApplicationEventMulticaster#setTaskExecutor，这样在执行时就会提交task给线程池处理。
				可以做到全局统一控制，但是setTaskExecutor属于SimpleApplicationEventMulticaster，因此必须是该类才可以使用异步。
			对于注解：
				添加@Async注解，并在主类上添加@EnableAsync。如果要指定线程池，则需创建一个名为taskExector，类型为Executor的Bean，否则框架会默认创建一个线程池。使用注解方法只能局部控制，无法进行全局控制。
		监听事件的异常处理：
			调用SimpleApplicationEventMulticaster#setErrorHandler，设置一个ErrorHandler。

Spring注解：
	模式注解：
		@Component：将被标注的类注册为Spring Bean
		@Component的派生类：
			@Repository，@Service，@Controller，@Configuration，它们实现上没有什么差别。只是具有逻辑概念。
	资源导入：
		@Import：导入配置类(@Configuration标注)
		@ImportResource：导入资源(如XML文件)
		@ImportSelector：根据全限定类名加载bean
		@ImportBeanDifinitionRegistrar：根据BeanDifinition注册bean

		处理:
			ConfigurationClassPostProcessor#postProcessBeanDefinitionRegistry:
				#processConfigBeanDefinitions:
					ConfigurationClassParser#parse:
						#processConfigurationClass:
							#doProcessConfigurationClass:
								#processImports:
									ImportSelector:会继续调用processImports处理selectImports返回的类名集合
									ImportBeanDefinitionRegistrar:在后续阶段处理
									导入的普通类:会处理成ConfigurationClass，在loadBeanDefinitions转换成BeanDeifinition
					#loadBeanDefinitions:
						#loadBeanDefinitionsForConfigurationClass:
							#registerBeanDefinitionForImportedConfigurationClass:处理导入的普通类
							#loadBeanDefinitionsFromRegistrars:处理ImportBeanDefinitionRegistrar
	装配：
		@Autowired
		@Qualifier

	注解分类：
		元注解：
			@Document
			@Inherited：指类的继承关系，而非注解上的派生关系。如果父类被标记，子类自动被标记。
			@Repeatable：JDK1.8提供的注解，可以让一个类上标记多个相同的注解。

		模式注解：
			@Component以及它的派生类

		组合注解：
			多个不同的注解组合在一起。如@SpringBootApplication，由@SpringBootConfiguration，@EnableAutoConfiguration，@ComponentScan组成。@SpringBootConfiguration是@Component的多层次派生。

			注解属性：
				AnnotationAttributes。当多个注解组合在一起时，如何在子注解中灵活的为父注解赋值，如在@SpringBootApplication中，如何给@ComponentScan的basePackages灵活的赋值？可以使用属性别名，及@AliasFor注解。
					@AliasFor(annotation = ComponentScan.class, attribute = "basePackages") 
					String[] scanBasePackages() default {};

				属性别名：分为显性和隐性
					显性即同一个注解中的两个属性相互使用@AliasFor映射，两者都可感知对方的存在。如@ComponentScan中的value属性和basePackages属性。
					隐性是在子注解中使用@AliasFor标注父注解中的属性，因为只有子注解中的属性能感知到父注解的属性，因此是隐形的。如@SpringBootApplication的scanBasePackages属性感知@ComponentScan的basePackages的属性。
				属性覆盖：
					显性：
					隐性：
		@Enable注解：
			1.创建@EnableXXX
			2.标记@Import
			3.导入相关实现类：@Configuration Class/ImportSelector实现类/ImportBeanDefinitionRegistrar实现类

		条件注解：
			@Profile：对应处理类为ProfileCondition
			@Conditional：实现Condition接口，重写matches方法。

		@EventListener原理：
			EventListenerMethodProcessor，将@EventListener标记的方法转化为ApplicationListener，并注册到上下文中。
			AbstractApplicationContext#refresh：
				#finishBeanFactoryInitialization：
					DefaultListableBeanFactory#preInstantiateSingletons：
						EventListenerMethodProcessor#afterSingletonsInstantiated
							#processBean：                  
								EventListenerFactory#createApplicationListener
									ApplicationListenerMethodAdapter
										ConfigurableApplicationContext#addApplicationListener

Spring Environment抽象：
	Environment：
	PropertySource：

	依赖注入：
		直接：
			1.通过@Autowired注入Environment
			2.实现EnvironmentAware接口，进行回调
		间接：
			1.通过@Autowired注入ApplicationContext
			2.实现ApplicationContextAware接口，进行回调
			然后通过ApplicationContext.getEnvironment获取Environment
	依赖查找：
		直接：
			1.BeanFactory#getBean(Environment.class)获取
			2.BeanFactory#getBean(ApplicationContext.class)获取
	Environment的生成：
			AbstractApplicationContext#prepareBeanFactory：
				#getEnvironment：
					#createEnvironment：

	@Value的处理：
		QualifierAnnotationAutowireCandidateResolver

		AbstractAutowireCapableBeanFactory#createBean：
			#doCreateBean：
				#populateBean：
					AutowiredAnnotationBeanPostProcessor#postProcessProperties：
						InjectionMetadata#inject：
							AutowiredAnnotationBeanPostProcessor.AutowiredFieldElement#inject：
								AutowireCapableBeanFactory#resolveDependency：
									DefaultListableBeanFactory#doResolveDependency：
										AutowireCandidateResolver#getSuggestedValue：
											QualifierAnnotationAutowireCandidateResolver#findValue：
												#extractValue：
										AbstractBeanFactory#resolveEmbeddedValue：
											StringValueResolver#resolveStringValue

	@PropertySource的处理：
		ConfigurationClassParser#doProcessConfigurationClass：
			#processPropertySource：
				PropertySourceFactory#createPropertySource：可以制定PropertySource，如yml。默认的只支持properties文件
				ConfigurationClassParser#addPropertySource：

	自定义添加PropertySource：
		1.获取ApplicationContext(ApplicationContextAware/依赖注入)
		2.获取Environment，Capable#getEnvironment
		3.获取MutablePropertySources，AbstractEnvironment#getPropertySources
		4.添加PropertySource，MutablePropertySources#addFirst

	@TestPropertySource在PropertySources的优先级挺高的。


Spring 应用上下文生命周期：
	AbstractApplicationContext#refresh：
		AbstractApplicationContext#prepareRefresh：
			记录启动事件，类似日志，因为上下文可能有多个，因此不方便用日志记录。
			修改应用上下文的状态
			#initPropertySources：Initialize any placeholder property sources in the context environment.
			#getEnvironment().validateRequiredProperties()：
			创建earlyApplicationListeners或者添加applicationListeners
			创建earlyApplicationEvents
		#obtainFreshBeanFactory：
			AbstractRefreshableApplicationContext#refreshBeanFactory：
				如果存在BeanFactory，将BeanFactory的Bean销毁，并则将该BeanFactory关闭。
				#createBeanFactory：
					创建BeanFactory，类型一般为DefaultListableBeanFactory。
				用ApplicationContext的ID去设置BeanFactory的ID
				#customizeBeanFactory
					设置特性(是否允许BeanDefinition覆盖/是否允许循环依赖)
				#loadBeanDefinitions：
					加载BeanDefinition。子类AbstractXmlApplicationContext加载XML资源。子类AnnotationConfigWebApplicationContext加载注解资源。
				将BeanFactory于ApplicationContext关联。

			#getBeanFactory()：
				获取BeanFactory，要求必须存在，如果不存在，则抛出异常。
		#prepareBeanFactory：
		#postProcessBeanFactory：beanFactory的后置处理(继承方式)，需要继承AbstractApplicationContext，并且重写postProcessBeanFactory方法，因为AbstractApplicationContext#postProcessBeanFactory并没有进行任何操作。

		#invokeBeanFactoryPostProcessors：beanFactory的后置处理(组合方式)。
			PostProcessorRegistrationDelegate#invokeBeanFactoryPostProcessors：执行BeanFactoryPostProcessor。优先处理实现PriorityOrdered接口的，其次处理实现Ordered接口的，最后处理常规的。

		#registerBeanPostProcessors：注册BeanPostProcessor，优先添加实现PriorityOrdered的BeanPostProcessor，然后添加实现Ordered的BeanPostProcessor，最后添加常规BeanPostProcessor。postProcessorNames是priorityOrderedPostProcessor和orderedPostProcessorNames和nonOrderedPostProcessorNames的全集，三者没有交集。
			PostProcessorRegistrationDelegate#registerBeanPostProcessors：
				ConfigurableBeanFactory#addBeanPostProcessor：添加BeanPostProcessor。 

		#initMessageSource：初始化MessageSource，虽然会有MessageSource对象，但是没有内容，因此无法使用。

		#initApplicationEventMulticaster：初始化事件发布器，如果不存在该类型的对象，则创建SimpleApplicationEventMulticaster，并且以单列对象的形式关联到beanFactory。

		#onRefresh：空实现，可以继承该类来调整实现。WebApplication会有实现。

		#registerListeners：先添加应用上下文关联的ApplicationListener，然后添加beanFactory中的ApplicationListener，最后广播早期事件(如果有的话)。

		#finishBeanFactoryInitialization：
			ConfigurableBeanFactory#setConversionService：
			ConfigurableBeanFactory#addEmbeddedValueResolver：
			ConfigurableListableBeanFactory#freezeConfiguration：冻结配置，获取beanNames时会返回一个复制的数组。
			ConfigurableListableBeanFactory#preInstantiateSingletons：实例化单列对象。

		#finishRefresh：
			DefaultResourceLoader#clearResourceCaches：
			AbstractApplicationContext#initLifecycleProcessor：初始化LifecycleProcessor，beanFactory中如果不存在该类型的对象，则创建DefaultLifecycleProcessor，并且以单列对象的形式关联到beanFactory。
			LifecycleProcessor#onRefresh：
				DefaultLifecycleProcessor#startBeans：对于实现了Lifecycle的Bean，则会调用Lifecycle#start。
			AbstractApplicationContext#publishEvent：发布ContextRefreshedEvent事件。
			LiveBeansView#registerApplicationContext：将应用上下文的信息注册到LiveBeansView(需要在环境变量配置)。类似springboot的endpoint。属于JMX范围，JAVA Manager Bean。javax.management.MBeanServer


	AbstractApplicationContext#close：
		AbstractApplicationContext#doClose：
			LiveBeansView#unregisterApplicationContext：
			#publishEvent：发布ContextClosedEvent事件。
			LifecycleProcessor#onClose：执行实现Lifecycle接口的Bean。
			#destroyBeans：
				ConfigurableBeanFactory#destroySingletons：
					DefaultSingletonBeanRegistry#destroySingletons：
						#removeSingleton：
					#clearSingletonCache：
				DefaultListableBeanFactory#clearByTypeCache：
			#closeBeanFactory：
			#onClose：空实现，可由子类扩展。
			AtomicBoolean#set：设置状态位。

		Runtime#removeShutdownHook：如果设置了ShutdownHook，则会移除ShutdownHook。


		ShutdownHook：
			关闭时的钩子，可通过传入信号量然后调用AbstractApplicationContext#doClose，可以优雅安全的关闭应用。
			使用时需要通过AbstractApplicationContext#registerShutdownHook注册，然后可以使用kill -3/5/7 进行调用。


Spring延迟查找：
	interface ObjectProvider<T> extends ObjectFactory<T>, Iterable<T>
	

	ObjectFactory和ObjectProvider：
		依赖注入后不会立刻查找对象，而是在调用#getObject时进行依赖查找。

Spring对象缓存：
	单列对象会被缓存，原型对象不会被缓存，而且原型对象创建后就与容器脱钩，声明周期不由容器管理，因此原型对象的销毁方法不会被调用。


@Bean处理：
	被@Bean标记的方法会被转换为BeanDefinition。
	ConfigurationClassPostProcessor#postProcessBeanFactory：
		#processConfigBeanDefinitions：
			ConfigurationClassParser#parse：
				#processConfigurationClass：
					#doProcessConfigurationClass：
						#retrieveBeanMethodMetadata：
			ConfigurationClassBeanDefinitionReader#loadBeanDefinitions：
				#loadBeanDefinitionsForConfigurationClass：
					#loadBeanDefinitionsForBeanMethod：@Bean标记的方法分为静态方法和实例方法，静态方法会找提供的静态工厂方法，实例方法会设置当前所在类为工厂。
						BeanDefinitionRegistry#registerBeanDefinition：

Spring 循环依赖：
	AbstractAutowireCapableBeanFactory#allowCircularReferences：是否允许循环引用，如果不允许，则有循环依赖时会抛出错误，默认为true。

	循环依赖的解决流程：主要依赖singletonObjects，earlySingletonObjects，singletonFactories。构造器循环依赖无法被解决。
		首先实例化A，如果允许循环依赖，则会调用#addSingletonFactory，在singletonFactories添加A实例的ObjectFactory，然后填充A的属性，在字段注入B实例的时候，发现没有B实例，则会实例化B，同样调用#addSingletonFactory，添加B实例的ObjectFactory，在填充B属性时，会注入A实例，这时候调用#getSingleton，会依次查找singletonObjects，earlySingletonObjects，singletonFactories。在singletonFactories找到后，会获取A实例，将A实例的ObjectFactory从singletonFactories移除，并且将A实例添加到earlySingletonObjects，这样B对象就能完成初始化，因为是新对象，所以会添加到singletonObjects，并且删除earlySingletonObjects的数据。同时A也能获取B的实例。

A实例：
	AbstractApplicationContext#refresh：
		#finishBeanFactoryInitialization：
			DefaultListableBeanFactory#preInstantiateSingletons：
				AbstractBeanFactory#getBean：
					#doGetBean：
						DefaultSingletonBeanRegistry#getSingleton：
							AbstractAutowireCapableBeanFactory#createBean：
								AbstractAutowireCapableBeanFactory#doCreateBean：
									DefaultSingletonBeanRegistry#addSingletonFactory：
										AbstractAutowireCapableBeanFactory#populateBean：
											#autowireByType：
												DefaultListableBeanFactory#resolveDependency：
													#doResolveDependency：
														DependencyDescriptor#resolveCandidate：
															执行完B对象的初始化，获取B对象
					DefaultSingletonBeanRegistry#addSingleton：
						singletonObjects.put


B实例：
	AbstractBeanFactory#getBean：
		#doGetBean：
			DefaultSingletonBeanRegistry#getSingleton：
				AbstractAutowireCapableBeanFactory#createBean：
					AbstractAutowireCapableBeanFactory#doCreateBean：
						DefaultSingletonBeanRegistry#addSingletonFactory：
							AbstractAutowireCapableBeanFactory#populateBean：
								#autowireByType：
									DefaultListableBeanFactory#resolveDependency：
										#doResolveDependency：
											DependencyDescriptor#resolveCandidate：
												AbstractBeanFactory#getBean：
													#doGetBean：
														DefaultSingletonBeanRegistry#getSingleton：
															ObjectFactory#getObject：
																AbstractAutowireCapableBeanFactory#getEarlyBeanReference：
																	earlySingletonObjects.put
														DefaultSingletonBeanRegistry#addSingleton：
															singletonObjects.put


SpringAOP：只支持方法级别。
	表现形式：
		代理模式：
		判断模式：
		拦截模式：
	编程模型：
		注解：
			@Before
			@After
			@Around
		xml配置：
			定义：spring-aop.xsd
			处理：org.springframework.aop.config.AopNamespaceHandler
		接口：
			org.aopalliance.aop.Advice：
				org.springframework.aop.BeforeAdvice：
					MethodBeforeAdvice：
						org.springframework.aop.aspectj.AspectJMethodBeforeAdvice
				org.springframework.aop.AfterAdvice：
					org.springframework.aop.aspectj.AspectJAfterAdvice：
					org.springframework.aop.aspectj.AspectJAfterThrowingAdvice：
					AfterReturningAdvice：
						org.springframework.aop.aspectj.AspectJAfterReturningAdvice
					ThrowsAdvice：

	代理模式类型：
		JDK动态代理：代理接口
			java.lang.reflect.Proxy#newProxyInstance
			org.springframework.aop.framework.JdkDynamicAopProxy
		CGLIB代理：代理类，字节码提升
			org.springframework.aop.framework.CglibAopProxy
		ASPECTJ代理：
			org.springframework.aop.aspectj.annotation.AspectJAdvisorFactory

	JDK动态代理：
		代理接口(Class<?>[])，生成的对象如果未命名，则默认为org.sun.proxy.$Proxy数字(nextUniqueNumber.getAndIncrement();)，结构为
		$Proxy数字 extend Proxy implements Class<?>[]

		java.lang.reflect.Proxy#newProxyInstance：
			#getProxyConstructor：
				ProxyBuilder#build：
					ProxyBuilder#defineProxyClass：
						ProxyGenerator#generateProxyClass：
							生成代理类的字节码文件
					Unsafe#defineClass：
						加载代理类的字节码文件
						Unsafe#defineClass0：
			java.lang.reflect.Constructor#newInstance：
				需要传入new Object[]{h}，因为生成的代理对象继承于Proxy，Proxy的构造函数需要该参数。

		java.lang.reflect.Proxy#newProxyInstance(ClassLoader, Class<?>[], InvocationHandler)：
			ClassLoader：加载代理对象。同时会重新加载一遍Class<?>[]，因为Class<?>[]可能和该类不在同一个ClassLoader范围内。
			Class<?>[]：要代理的接口
			InvocationHandler：增强处理器
		CGLIB代理：
			org.springframework.cglib.proxy.Enhancer

			org.springframework.cglib.proxy.Enhancer#setCallback：设置回调函数，一般实现org.springframework.cglib.proxy.MethodInterceptor。使用时调MethodProxy，如果调Method会因为重复拦截进入死循环。
		ASPECTJ代理：
			org.springframework.aop.aspectj.annotation.AspectJProxyFactory

	ASPECTJ组件：
		Aspect：切面
		JoinPoint：接入点
			org.aopalliance.intercept.Joinpoint
		PointCut：接入点的筛选器
			org.springframework.aop.Pointcut
		Advice：行为
			Around：
				主动调用，Around可以控制执行的流向，通常配合org.aspectj.lang.ProceedingJoinPoint使用，调用org.aspectj.lang.ProceedingJoinPoint#proceed可以执行被代理的方法。在同一个Aspect中，Around优先级最高，不同Aspect优先级由Order决定。
			Before：
				被动执行，如果被代理的方法执行，那么Before一定会执行。如果在Around中没有执行被代理的方法，那么Before也不会被执行。
			After：
				被动执行，被代理的方法执行时会执行。相当于try catch finally中的finally，优先级高于AfterReturning和AfterThrowing。
			AfterReturning：
				被动执行。相当于try catch finally中try，方法正常返回时会调用。
			AfterThrowing：
				被动执行。相当于try catch finally中cathch，方法抛出异常时调用。
			Advisor：
				绑定PointCut和Advice。
		Advisor：Advice容器，连接PointCut和Advice，和Advice为1:1
			org.springframework.aop.Advisor

	API:
		org.springframework.aop.framework.ProxyFactory：Spring AOP底层API
		org.springframework.aop.framework.ProxyFactoryBean：和IOC容器整合的API
		org.springframework.aop.support.DefaultPointcutAdvisor：适配PointCut和Adivce
		org.springframework.context.annotation.EnableAspectJAutoProxy/<aop:aspectj-autoproxy>:支持AspectJ的注解。如@Aspect,@PointCut, @Around,@Before,@After等
		org.springframework.aop.TargetSource：

		Interceptor和Advice协同工作：
			如MethodBeforeAdviceInterceptor和MethodBeforeAdvice，MethodBeforeAdviceInterceptor会注入一个MethodBeforeAdvice，这样Interceptor负责拦截，Advice负责执行。因为MethodInterceptor和AroundAdvice的功能相似，因此没有AroundAdivce。

	自动代理：
		org.springframework.aop.framework.autoproxy.BeanNameAutoProxyCreator：自动代理指定名称的Bean，需要指定Advice。
		org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator：

	API设计与实现：
		org.aopalliance.intercept.Joinpoint：
			Invocation：
				MethodInvocation：
					org.springframework.aop.ProxyMethodInvocation：
						org.springframework.aop.framework.ReflectiveMethodInvocation：
							CglibAopProxy.CglibMethodInvocation：

		PointCut：
			包含ClassFilter和MethodMatcher，通过两者进行过滤
			org.springframework.aop.support.ComposablePointcut：
				组合PointCut，可通过设置并集(|)，交集(&)。
			org.springframework.aop.support.StaticMethodMatcherPointcut：
				org.springframework.aop.ClassFilter：
				org.springframework.aop.MethodMatcher：
					#isRuntime为true走不带参数的#matches，false则走带参数的#matches。
			org.springframework.aop.support.JdkRegexpMethodPointcut：
				正则表达式
			org.springframework.aop.support.ControlFlowPointcut：
				控制流，好像和异常堆栈有关

		Advice：
			Around：
				org.aopalliance.intercept.Interceptor：
					org.aopalliance.intercept.MethodInterceptor：
						因为Around是控制方法的流转，一般配合ProceedingJoinPoint使用，其中ProceedingJoinPoint#proceed是执行该方法，和MethodInterceptor#invoke类似，#invoke包含执行时所需要的参数。
					org.aopalliance.intercept.ConstructorInterceptor：
						无效，因为Spring AOP只支持方法的，不支持构造器的。
			Before：
				org.springframework.aop.BeforeAdvice：
					org.springframework.aop.framework.adapter.MethodBeforeAdviceInterceptor：
						具体实现类。当用户设置MethodBeforeAdvice时，会被包装成该类，该类由框架内部调用，执行#invoke，参数为MethodInvocation(JointPoint的子接口)，#invoke会调用MethodBeforeAdvice#before和Joinpoint#proceed。

			After：
				org.springframework.aop.AfterAdvice：
					org.springframework.aop.AfterReturningAdvice：
						org.springframework.aop.framework.adapter.AfterReturningAdviceInterceptor
					org.springframework.aop.ThrowsAdvice：
						ThrowsAdvice接口是标记接口，如果要实现ThrowsAdvice，需要添加
						public void afterThrowing(Exception ex)
						public void afterThrowing(Method method, Object[] args, Object target, ServletException ex)
						框架在调用时会讲ex和method存储到map中，如果出现多个ex会出现覆盖的情况。如果ex找不到，则会找他的父类异常method。

						org.springframework.aop.framework.adapter.ThrowsAdviceInterceptor

			AspectJ类型的Advice：
				org.springframework.aop.aspectj.AspectJMethodBeforeAdvice
				org.springframework.aop.aspectj.AspectJAfterAdvice
				org.springframework.aop.aspectj.AspectJAfterReturningAdvice
				org.springframework.aop.aspectj.AspectJAfterThrowingAdvice
				org.springframework.aop.aspectj.AspectJAroundAdvice
				上述类为Advice的子类，所以会被Spring框架包装为MethodInterceptor，也是通过MethodInterceptor#invoke调用Advice的实现。AspectJ会通过反射获取注解源信息，从而获取对应的Advice方法。
				具体流程如下(以BeforeAdvice为例)：
					MethodBeforeAdviceInterceptor#invoke：
						AspectJMethodBeforeAdvice#before：
							AbstractAspectJAdvice#invokeAdviceMethod：
								invokeAdviceMethodWithGivenArgs：

		Advisor：
			org.springframework.aop.Advisor：
				#getAdvice
				org.springframework.aop.PointcutAdvisor：
					#getPointcut
					org.springframework.aop.support.AbstractPointcutAdvisor：
						#setOrder：可以设置Order，控制顺序。
						#getOrder：获取order，如果未设置，则以内置的Advice的Order为准
						org.springframework.aop.support.AbstractGenericPointcutAdvisor：
							#setAdvice
							org.springframework.aop.support.DefaultPointcutAdvisor：
								#setPointcut

			org.springframework.aop.IntroductionInfo：
				#getInterfaces
				org.springframework.aop.IntroductionAdvisor：
					#getClassFilter
					org.springframework.aop.support.DefaultIntroductionAdvisor：
						#DefaultIntroductionAdvisor(Advice)。
						该类可以控制要代理的接口，如类实现了接口A和接口B，使用该类可以控制只代理接口A(构造时传入IntroductionInfo，实现中写入要代理的接口)。

		AdvisorAdapt：
			org.springframework.aop.framework.adapter.AdvisorAdapter：
				#supportsAdvice：是否识别该Advice
				#getInterceptor：获取Advisor对应的拦截器
				AfterReturningAdviceAdapter：
				MethodBeforeAdviceAdapter：
				ThrowsAdviceAdapter：

			org.springframework.aop.framework.adapter.DefaultAdvisorAdapterRegistry：
				可以自定义扩展AdvisorAdapter，并注册

		AopProxy：
			org.springframework.aop.framework.AopProxy：
				#getProxy：采用默认的类加载器。
					ClassUtils#getDefaultClassLoader：
						java.lang.Thread#getContextClassLoader：获取当前线程的类加载器
						java.lang.Class#getClassLoader：获取加载当前类的类加载器
						java.lang.ClassLoader#getSystemClassLoader：获取系统的类加载器
				#getProxy(ClassLoader)：采用传递的类加载器。
				org.springframework.aop.framework.JdkDynamicAopProxy：
				org.springframework.aop.framework.CglibAopProxy：
					org.springframework.aop.framework.ObjenesisCglibAopProxy：Spring4.0提供

			org.springframework.aop.framework.AopProxyFactory：
				#createAopProxy(AdvisedSupport)：Create an {@link AopProxy} for the given AOP configuration
				org.springframework.aop.framework.DefaultAopProxyFactory：
					#createAopProxy：生成JdkDynamicAopProxy或CglibAopProxy对象。
			org.springframework.aop.framework.JdkDynamicAopProxy：
			org.springframework.aop.framework.CglibAopProxy：


		体系：
			org.springframework.aop.framework.ProxyConfig：
				org.springframework.aop.framework.AdvisedSupport：
					org.springframework.aop.framework.ProxyCreatorSupport：手动代理
						org.springframework.aop.framework.ProxyFactory：只是简化了api的使用，并没有进行什么扩展。
						org.springframework.aop.framework.ProxyFactoryBean：和Ioc容器进行联通。
						org.springframework.aop.aspectj.annotation.AspectJProxyFactory：整合AspectJ

				org.springframework.aop.framework.ProxyProcessorSupport：
					org.springframework.aop.framework.autoproxy.AbstractAutoProxyCreator：自动代理
						org.springframework.aop.framework.autoproxy.BeanNameAutoProxyCreator：
						org.springframework.aop.framework.autoproxy.AbstractAdvisorAutoProxyCreator：
							org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator：
							org.springframework.aop.framework.autoproxy.InfrastructureAdvisorAutoProxyCreator：
							org.springframework.aop.aspectj.autoproxy.AspectJAwareAdvisorAutoProxyCreator：
								org.springframework.aop.aspectj.annotation.AnnotationAwareAspectJAutoProxyCreator：

		org.springframework.aop.framework.AdvisedSupportListener：
			#activated：Invoked when the first proxy is created.(AopProxy)
			#adviceChanged：当代理对象发生改变/advisor增加或删除/配置发生变化时，会调用
			由ProxyCreatorSupport管理AdvisedSupportListener。

		AdvisedSupport：代理对象的配置。
			TargetSource：
			AdvisorChainFactory：
				AdvisorAdapterRegistry：

		org.springframework.aop.TargetSource：
			org.springframework.aop.target.HotSwappableTargetSource：
			SingletonTargetSource：
			AbstractBeanFactoryBasedTargetSource：
				AbstractPrototypeBasedTargetSource：
					PrototypeTargetSource：
					ThreadLocalTargetSource：
					AbstractPoolingTargetSource：
						CommonsPool2TargetSource：

		org.springframework.aop.framework.ProxyProcessorSupport：

		org.springframework.aop.framework.autoproxy.TargetSourceCreator：

		org.springframework.aop.framework.AopInfrastructureBean：

		PartiallyComparableAdvisorHolder：
			#Advisor：
			#Comparator：
				AspectJPrecedenceComparator：
					AnnotationAwareOrderComparator：

		关联：
			Advice -> AdvisedSupport -> Advisor -> AdvisorAdapter -> AdvisorAdapterRegistry -> Interceptor

		手动代理：
			org.springframework.aop.framework.ProxyCreatorSupport：
				org.springframework.aop.framework.ProxyFactory：
					#getProxy()：
						org.springframework.aop.framework.ProxyCreatorSupport#createAopProxy：
							#getAopProxyFactory：
							org.springframework.aop.framework.AopProxyFactory#createAopProxy：
						AopProxy#getProxy()：
							AopProxy分为JdkDynamicAopProxy和CglibAopProxy。
			
		自动代理：
			org.springframework.aop.framework.autoproxy.AbstractAdvisorAutoProxyCreator：
				#postProcessBeforeInstantiation：
					#getAdvicesAndAdvisorsForBean：
						AbstractAdvisorAutoProxyCreator#findEligibleAdvisors：
							#findCandidateAdvisors：
								BeanFactoryAdvisorRetrievalHelper#findAdvisorBeans：
									BeanFactoryUtils#beanNamesForTypeIncludingAncestors：
										ListableBeanFactory#getBeanNamesForType：
							#findAdvisorsThatCanApply：
								AopUtils#findAdvisorsThatCanApply：
									#canApply：
					#createProxy：
						#buildAdvisors：
						ProxyFactory#getProxy：

			生成代理对象在postProcessBeforeInstantiation阶段
			postProcessBeforeInstantiation，如果返回值不为null，则跳过实例化
			postProcessAfterInstantiation，如果返回值为false，则不对实例对象进行填充


		辅助工具类：
			AopContext：利用ThreadLocal存储当前的代理对象，需要开启ProxyConfig#exposeProxy。
				#setCurrentProxy：存储当前的代理对象，如果为空，则相当于删除。
				#setCurrentProxy：获取当前存储的代理对象。
			AopProxyUtils：代理对象的工具类。
				#getSingletonTarget:
				#ultimateTargetClass:
				#completeProxiedInterfaces:
				#proxiedUserInterfaces:
			AopUtils:
				#isAopProxy：
					判断是否是Spring Aop对象。Spring Aop对象均实现了SpringProxy接口(标记接口)。生成代理对象时调用AopProxyUtils#completeProxiedInterfaces添加了SpringProxy接口。
				#isJdkDynamicProxy：
					是否是jdk动态代理。jdk代理对象继承了Proxy,实现了业务接口.
				#isCglibProxy：
					是否是cglib字节码提升。cglib继承了业务类。
				#invokeJoinpointUsingReflection：
					通过反射的方式调用方法。
		@EnableAspectJAutoProxy：
			AspectJAutoProxyRegistrar#registerBeanDefinitions：
				AopConfigUtils#registerAspectJAnnotationAutoProxyCreatorIfNecessary：
					#registerOrEscalateApcAsRequired：注册AnnotationAwareAspectJAutoProxyCreator.class的BeanDefinition
		<aop:aspectj-autoproxy/>：
			AspectJAutoProxyBeanDefinitionParser#parse：
				AopNamespaceUtils#registerAspectJAnnotationAutoProxyCreatorIfNecessary：
					AopConfigUtils#registerAspectJAnnotationAutoProxyCreatorIfNecessary：
						#registerOrEscalateApcAsRequired：注册AnnotationAwareAspectJAutoProxyCreator.class的BeanDefinition
		<aop:config>：
			ConfigBeanDefinitionParser#parse：
				#configureAutoProxyCreator：
					AopNamespaceUtils#registerAspectJAutoProxyCreatorIfNecessary：
						AopConfigUtils#registerAspectJAutoProxyCreatorIfNecessary：
							#registerOrEscalateApcAsRequired：注册AspectJAwareAdvisorAutoProxyCreator.class的BeanDefinition