package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

@Component
public class ComponentAccessor extends Object {

	/** The application context. */
	public static ApplicationContext applicationContext;

	/**
	 * Inits the component.
	 *
	 * @param applicationContext the application context
	 */
	@Autowired
	private void initComponent(ApplicationContext applicationContext) {
		ComponentAccessor.applicationContext = applicationContext;
	}
	
	/**
	 * Gets the component.
	 *
	 * @param <T> the generic type
	 * @param componentClass the component class
	 * @return the component
	 */
	public static <T> T getComponent(Class<T> componentClass) {
		return applicationContext.getBean(componentClass);
	}
}
