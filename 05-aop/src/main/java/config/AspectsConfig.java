package config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.stereotype.Component;
import rewards.internal.monitor.MonitorFactory;
import rewards.internal.monitor.jamon.JamonMonitorFactory;

// TODO-04: Update Aspect related configuration
// - Add a class-level annotation to scan for components
//	 located in the rewards.internal.aspects package.
// - Add @EnableAspectJAutoProxy to this class to instruct Spring
//	 to process beans that have the @Aspect annotation.
//   (Note that this annotation is redundant for Spring Boot
//    application since it will be automatically added through
//    auto configuration.)
@Configuration
@ComponentScan("rewards.internal.aspects")
@EnableAspectJAutoProxy
public class AspectsConfig {

	@Bean
	public MonitorFactory monitorFactory(){
		return new JamonMonitorFactory();
	}
	
}
