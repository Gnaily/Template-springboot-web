package site.yl.template;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

@Component
public class SpringCtxHelper implements ApplicationContextAware{

	private static ApplicationContext context;
	@Override
	public void setApplicationContext(ApplicationContext context) throws BeansException {
		SpringCtxHelper.context=context;
	}

	
	public static ApplicationContext getContext(){
		return context;
	}
	
    @SuppressWarnings("unchecked")
	public static <T> T getBean(String name) {
        return (T) context.getBean(name);
    }

    public static <T> T getBean(Class<T> requiredType) {
        return context.getBean(requiredType);
    }
	
}
