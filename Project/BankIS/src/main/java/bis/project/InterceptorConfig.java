package bis.project;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration 
public class InterceptorConfig extends WebMvcConfigurerAdapter  {  

	@Bean
    RestInterceptor localInterceptor() {
         return new RestInterceptor();
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
       registry.addInterceptor(localInterceptor()).addPathPatterns("/**");
    }
 
}
