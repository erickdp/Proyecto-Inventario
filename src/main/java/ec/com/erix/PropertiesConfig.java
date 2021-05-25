package ec.com.erix;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

/**
 *
 * @author Erick Diaz
 */
@Configuration
@PropertySource("classpath:texto.properties")
public class PropertiesConfig {
    
//    Se pude agregar properySources({}) para definir mas archivos
    
}
