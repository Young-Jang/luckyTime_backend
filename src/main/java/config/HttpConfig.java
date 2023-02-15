package toyproject.almigty.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.request.RequestContextListener;
import org.springframework.web.filter.HiddenHttpMethodFilter;

@Configuration
public class HttpConfig {
    @Bean
    public RequestContextListener requestContextListener(){
        return new RequestContextListener();
    }

    @Bean
    public HiddenHttpMethodFilter hiddenHttpMethodFilter(){ return new HiddenHttpMethodFilter(); }

}
