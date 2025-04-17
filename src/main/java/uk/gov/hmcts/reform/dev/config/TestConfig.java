package uk.gov.hmcts.reform.dev.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import uk.gov.hmcts.reform.dev.repositories.TaskRepository;
import org.mockito.Mockito;

@Configuration
public class TestConfig {

    @Bean
    public TaskRepository taskRepository() {
        return Mockito.mock(TaskRepository.class);
    }
}
// This configuration class is used to create a mock of the TaskRepository for unit tests. It uses Mockito to create the mock object, which can be injected into test classes. This allows for isolated testing of components without needing a real database connection or repository implementation