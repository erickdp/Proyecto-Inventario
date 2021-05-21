package ec.com.erix.web;

import java.util.Locale;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Bean(name = "localeResolver")
    public LocaleResolver localeResolver() {
        var slr = new SessionLocaleResolver();
        slr.setDefaultLocale(new Locale("es")); // Defino el idioma principal
        return slr;
    }

    @Bean
    public LocaleChangeInterceptor localeChangeInterceptor() {
        var lci = new LocaleChangeInterceptor();
        lci.setParamName("lang"); // Con esto se podra cambiar de lenguaje seleccionando en el footer
        return lci;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(localeChangeInterceptor());
    }

//    Aqui se definen todas las rutas que no necesitan ser pasadas por seguridad o que no existen
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/login");
        registry.addViewController("/error/403").setViewName("/error/403");
        registry.addViewController("/error/404").setViewName("/error/404");
    }
}
