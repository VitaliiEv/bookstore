package vitaliiev.bookstore.config;

import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.security.oauth2.resource.servlet.JwkSetUriJwtDecoderBuilderCustomizer;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtValidators;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.jwt.SupplierJwtDecoder;
import org.springframework.security.web.SecurityFilterChain;

import java.net.URI;

@Configuration
public class ResourceServerConfig {

    @Value("${spring.security.oauth2.resourceserver.jwt.issuer-uri}")
    private String issuerUri;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(c -> c
                .requestMatchers("/actuator/**").permitAll());
        http.authorizeHttpRequests(c -> c
                        .requestMatchers("/api/**").authenticated())
                .oauth2ResourceServer(c -> c.jwt(Customizer.withDefaults()))
                .csrf(c -> c.ignoringRequestMatchers("/api/**"));
        http.authorizeHttpRequests(c -> c.anyRequest().authenticated());
        return http.build();
    }

//    @LoadBalanced
//    @Bean
//    public RestTemplate loadbalancedRestTemplate() {
//        return new RestTemplate();
//    }
//
//    @Bean
//    public JwkSetUriJwtDecoderBuilderCustomizer jwkSetUriJwtDecoderBuilderCustomizer(RestTemplate restTemplate) {
//        return builder -> builder.restOperations(restTemplate);
//    }

    @Bean
    public JwtDecoder jwtDecoder(LoadBalancerClient loadBalancerClient,
            ObjectProvider<JwkSetUriJwtDecoderBuilderCustomizer> customizers) {
        return new SupplierJwtDecoder(() -> {
            URI uri = URI.create(issuerUri);
            // auth server must be started at that moment
            ServiceInstance authServer = loadBalancerClient.choose(uri.getHost());
            String resolvedUri = authServer.getUri().toString();

            NimbusJwtDecoder.JwkSetUriJwtDecoderBuilder builder = NimbusJwtDecoder.withIssuerLocation(resolvedUri);
            customizers.orderedStream().forEach(customizer -> customizer.customize(builder));
            NimbusJwtDecoder jwtDecoder = builder.build();

            jwtDecoder.setJwtValidator(JwtValidators.createDefaultWithIssuer(resolvedUri));

            // do not validate issuer uri
//            jwtDecoder.setJwtValidator(JwtValidators.createDefault());
            return jwtDecoder;
        });

    }


}
