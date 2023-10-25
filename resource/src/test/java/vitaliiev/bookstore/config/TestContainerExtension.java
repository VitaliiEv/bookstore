package vitaliiev.bookstore.config;

import org.junit.jupiter.api.extension.AfterAllCallback;
import org.junit.jupiter.api.extension.BeforeAllCallback;
import org.junit.jupiter.api.extension.ExtensionContext;

public class TestContainerExtension implements BeforeAllCallback, AfterAllCallback {

    private TestContainer testContainer = TestContainer.getInstance();

    @Override
    public void beforeAll(ExtensionContext context) {
        testContainer = TestContainer.getInstance();
        testContainer.start();
        System.setProperty("spring.datasource.url", testContainer.getJdbcUrl());
        System.setProperty("spring.datasource.username", testContainer.getUsername());
        System.setProperty("spring.datasource.password", testContainer.getPassword());
    }

    @Override
    public void afterAll(ExtensionContext context) {
        testContainer.stop();
    }

}
