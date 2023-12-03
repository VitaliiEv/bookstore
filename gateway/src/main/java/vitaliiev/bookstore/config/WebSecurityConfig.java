package vitaliiev.bookstore.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;

@Configuration
//@EnableWebFluxSecurity
public class WebSecurityConfig {

    @Bean
    public SecurityWebFilterChain securityFilterChain(ServerHttpSecurity http) throws Exception {
        http.authorizeExchange(c -> c.pathMatchers("/discovery/eureka/**").denyAll());
        http.authorizeExchange(c -> c.pathMatchers("/*/actuator/**","/actuator/**").permitAll());
        http.authorizeExchange(c -> c.pathMatchers("/auth/**", "/bookstore/**","/discovery/**").permitAll());
        http.csrf(c -> c.disable())
                .httpBasic(c-> c.disable())
                .formLogin(c -> c.disable());
        return http.build();
    }

}
