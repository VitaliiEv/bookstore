package vitaliiev.bookstore.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;
import org.springframework.data.rest.webmvc.config.RepositoryRestConfigurer;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import vitaliiev.bookstore.entity.Author;
import vitaliiev.bookstore.entity.Book;

@Configuration
public class RestRepositoryConfig {

    @Bean
    public RepositoryRestConfigurer repositoryRestConfigurer() {

        return new RepositoryRestConfigurer() {

            @Override
            public void configureRepositoryRestConfiguration(RepositoryRestConfiguration config, CorsRegistry cors) {
                config.setBasePath("/api")
                        .setReturnBodyOnCreate(true)
                        .setReturnBodyOnUpdate(true)
                        .setReturnBodyForPutAndPost(true)
                        .setReturnBodyOnDelete(false);
                config.exposeIdsFor(Author.class, Book.class);
                config.getExposureConfiguration()
                        .disablePutForCreation();
            }
        };
    }
}
