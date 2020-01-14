package site.yl.template.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.Formatter;
import org.springframework.format.FormatterRegistry;
import org.springframework.format.datetime.DateFormatter;
import org.springframework.format.number.CurrencyStyleFormatter;
import org.springframework.format.number.NumberStyleFormatter;
import org.springframework.format.number.PercentStyleFormatter;
import org.springframework.http.MediaType;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.web.servlet.config.annotation.*;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;

import java.text.ParseException;
import java.util.Locale;
import java.util.Objects;

@Configuration
public class WebAppMvcConfig implements WebMvcConfigurer {

	/**
	 * Cross-origin resource sharing urls config
	 */
	@Override
	public void addCorsMappings(CorsRegistry registry) {
		//Enable cross-origin request handling for the specified path pattern
		registry.addMapping("/api/xx");
	}


	/**
	 * asynchronous request handling config
	 * 
	 */
	@Override
	public void configureAsyncSupport(AsyncSupportConfigurer configurer) {
		configurer.setDefaultTimeout(30*1000L);
		configurer.setTaskExecutor(mvcTaskExecutor());
	}
	
	@Bean
  public ThreadPoolTaskExecutor mvcTaskExecutor(){
      ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
      executor.setCorePoolSize(10);
      executor.setQueueCapacity(100);
      executor.setMaxPoolSize(25);
      return executor;
  }
	
	
	/**
	 * Interceptors config
	 */
	@Override
	public void addInterceptors(InterceptorRegistry registry){
		registry.addInterceptor(new LocaleChangeInterceptor());
	}

	

	@Override
	public void configureContentNegotiation(ContentNegotiationConfigurer configurer) {
		configurer.favorPathExtension(true).favorParameter(false)
        .parameterName("mediaType").ignoreAcceptHeader(false)
        .defaultContentType(MediaType.APPLICATION_JSON_UTF8)
        .mediaType("xml", MediaType.APPLICATION_XML)
        .mediaType("json", MediaType.APPLICATION_JSON);
	}

	@Override
	public void addFormatters(FormatterRegistry registry) {
		//Add  Formatter to format fields of a specific type
		registry.addFormatter(new NumberStyleFormatter());
		registry.addFormatter(new PercentStyleFormatter());
		registry.addFormatter(new CurrencyStyleFormatter());
		registry.addFormatter(new DateFormatter());
		registry.addFormatter(new StringFormatter());
	}


	private class  StringFormatter implements Formatter<String> {
		@Override
		public String parse(String text, Locale locale) throws ParseException {
			if(Objects.isNull(text)){
				return  null;
			}else {
				return text.trim();
			}
		}
		@Override
		public String print(String object, Locale locale) {
			return object;
		}
	}

}
