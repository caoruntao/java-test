import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;

/**
 * @author Reed
 * @date 2021/8/12 下午4:12
 */
public class Application {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext();
        applicationContext.register(Application.class);

        applicationContext.refresh();

        Object sum = applicationContext.getBean("sum");
        System.out.println(sum);

        applicationContext.close();
    }

    @Bean
    public String one(){
        return "1";
    }

    @Bean
    public String two(){
        return "2";
    }

    // Integer
    @Bean
    public int sum(String one, String two){
        Integer x = Integer.valueOf(one);
        Integer y = Integer.valueOf(two);
        return x + y;
    }
}
