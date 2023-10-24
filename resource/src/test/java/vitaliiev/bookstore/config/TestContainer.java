package vitaliiev.bookstore.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.containers.output.Slf4jLogConsumer;

public class TestContainer extends PostgreSQLContainer<TestContainer> {

    private static final Logger log =  LoggerFactory.getLogger(TestContainer.class);

    private final static String IMAGE = "postgres:latest";

    private final static String NAME = "bookstore-test";

    private final static String USERNAME= "bookstore-test";

    private final static  String PASSWORD ="bookstore-test";

    private static TestContainer container;

    private TestContainer() {
        super(IMAGE);
    }

    public static TestContainer getInstance() {
        if (container == null) {
            container = new TestContainer()
                    .withDatabaseName(NAME)
                    .withUsername(USERNAME)
                    .withPassword(PASSWORD)
                    .withReuse(true)
                    .withLogConsumer(new Slf4jLogConsumer(log));
        }
        return container;
    }

}
