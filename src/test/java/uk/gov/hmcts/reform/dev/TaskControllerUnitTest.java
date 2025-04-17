package uk.gov.hmcts.reform.dev;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import uk.gov.hmcts.reform.dev.config.TestConfig;
import uk.gov.hmcts.reform.dev.models.Task;
import uk.gov.hmcts.reform.dev.repositories.TaskRepository;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
@Import(TestConfig.class)
class TaskControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private TaskRepository taskRepository;

    @Test
    @DisplayName("Should create a new task")
    void createTask() throws Exception {
        Task task = new Task(null, "Title", "Description", "Pending", LocalDateTime.now());
        when(taskRepository.save(any(Task.class))).thenReturn(task);

        MvcResult result = mockMvc.perform(post("/tasks")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"title\":\"Title\",\"description\":\"Description\",\"status\":\"Pending\",\"dueDateTime\":\"2025-04-16T22:00:00\"}"))
                .andExpect(status().isOk())
                .andReturn();

        assertThat(result.getResponse().getContentAsString()).contains("Title");
    }

    @Test
    @DisplayName("Should get a task by ID")
    void getTask() throws Exception {
        Task task = new Task(1L, "Title", "Description", "Pending", LocalDateTime.now());
        when(taskRepository.findById(1L)).thenReturn(Optional.of(task));

        MvcResult result = mockMvc.perform(get("/tasks/1"))
                .andExpect(status().isOk())
                .andReturn();

        assertThat(result.getResponse().getContentAsString()).contains("Title");
    }

    @Test
    @DisplayName("Should update a task")
    void updateTask() throws Exception {
        Task task = new Task(1L, "Title", "Description", "Pending", LocalDateTime.now());
        when(taskRepository.findById(1L)).thenReturn(Optional.of(task));
        when(taskRepository.save(any(Task.class))).thenReturn(task);

        MvcResult result = mockMvc.perform(put("/tasks/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"title\":\"Updated Title\",\"description\":\"Updated Description\",\"status\":\"Completed\",\"dueDateTime\":\"2025-04-16T22:00:00\"}"))
                .andExpect(status().isOk())
                .andReturn();

        assertThat(result.getResponse().getContentAsString()).contains("Updated Title");
    }

    @Test
    @DisplayName("Should delete a task")
    void deleteTask() throws Exception {
        mockMvc.perform(delete("/tasks/1"))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("Should get all tasks")
    void getAllTasks() throws Exception {
        Task task1 = new Task(1L, "Title1", "Description1", "Pending", LocalDateTime.now());
        Task task2 = new Task(2L, "Title2", "Description2", "Completed", LocalDateTime.now());
        when(taskRepository.findAll()).thenReturn(Arrays.asList(task1, task2));

        MvcResult result = mockMvc.perform(get("/tasks"))
                .andExpect(status().isOk())
                .andReturn();

        assertThat(result.getResponse().getContentAsString()).contains("Title1", "Title2");
    }
}
