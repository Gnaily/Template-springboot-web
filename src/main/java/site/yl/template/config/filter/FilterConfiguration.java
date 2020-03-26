package site.yl.template.config.filter;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.Filter;

@Configuration
public class FilterConfiguration {

	@Bean
	public FilterRegistrationBean logFilterReg() {

	    FilterRegistrationBean registration = new FilterRegistrationBean();
	    registration.setFilter(logFilter());
	    registration.addUrlPatterns("/*");
	    registration.setOrder(1);
	    return registration;
	}

	  @Bean()
	  public Filter logFilter() {
	    return new LogFilter();
	  }
}