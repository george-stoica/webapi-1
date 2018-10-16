package playstudios.configs;

import org.springframework.boot.web.embedded.undertow.UndertowServletWebServerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;

@EnableAsync
@Configuration
public class ApplicationConfig {

//    @Bean
//    public UndertowServletWebServerFactory embeddedServletContainerFactory() {
//        UndertowServletWebServerFactory servletFactory = new UndertowServletWebServerFactory();
//
//        servletFactory.addBuilderCustomizers(builder -> builder.addHttpListener(8081, "localhost"));
//
//        return servletFactory;
//    }
}
