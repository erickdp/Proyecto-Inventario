package ec.com.erix.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 *
 * @author Erick Diaz
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    
    @Autowired
    private UserDetailsService userDetalSerivce;
    
//    Metodo para encripaticion de la contrasena, se debe generar un psvm para tomar la contrasena 
    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    
    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder build) throws Exception {
        build.userDetailsService(this.userDetalSerivce).passwordEncoder(passwordEncoder());
    }
    
    // AUORIZACION se quita el login por default y debo de crear el mio propio
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/", "/inventario/**", "/proInventario/**") // Defino que se puede acceder a todos las subrutas de inventario, incluye /inventario
                .hasAnyRole("USER", "ADMIN")
                .and()
                .formLogin()
                .loginPage("/login")
//                .defaultSuccessUrl("/inventario/", Boolean.TRUE) // El segudno parametro es para que siempre rediriga a esa URL
                .and()
                .exceptionHandling().accessDeniedPage("/errors/403");
    }
    
    
}
