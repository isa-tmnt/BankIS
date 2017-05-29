package bis.project;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@Configuration
public class RestConfig  {
	 @Bean
	    public CorsFilter corsFilter() {

	        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
	        CorsConfiguration config = new CorsConfiguration();
	        config.setAllowCredentials(true); // you USUALLY want this
	        config.addAllowedOrigin("*");
	        config.addAllowedHeader("*");
	        config.addAllowedMethod("GET");
	        config.addAllowedMethod("PUT");
	        config.addAllowedMethod("POST");
	        config.addAllowedMethod("OPTIONS");
	        config.addAllowedMethod("DELETE");        
	        config.addAllowedOrigin("http://localhost:8888");
	        source.registerCorsConfiguration("/**", config);
	        return new CorsFilter(source);
	    }
}
