package info.chengzhi.chengzhi_litter_bin;

import info.chengzhi.chengzhi_litter_bin.app.application.filter.TicketCodeInterceptor;
import info.chengzhi.chengzhi_litter_bin.app.application.filter.TraceIdInterceptor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@SpringBootApplication
public class ChengzhiLitterBinApplication extends WebMvcConfigurationSupport {

    public static void main(String[] args) {
        SpringApplication.run(ChengzhiLitterBinApplication.class, args);
    }

    @Bean
    public TraceIdInterceptor traceIdInterceptor() {
        return new TraceIdInterceptor();
    }

    @Bean
    TicketCodeInterceptor ticketCodeInterceptor() {
        return new TicketCodeInterceptor();
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry){
        registry.addInterceptor(traceIdInterceptor()).addPathPatterns("/**");
        registry.addInterceptor(ticketCodeInterceptor()).addPathPatterns("/**");
    }
}
