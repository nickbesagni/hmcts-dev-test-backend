package uk.gov.hmcts.reform.dev.controllers;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import uk.gov.hmcts.reform.dev.models.Task;
import uk.gov.hmcts.reform.dev.repositories.TaskRepository;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class TaskControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private TaskRepository taskRepository;

    @Test
    @DisplayName("Should create a new task")
    void createTask() throws Exception {
        String requestBody = "{\"title\":\"Title\",\"description\":\"Description\",\"status\":\"Pending\",\"dueDateTime\":\"2025-04-16T22:00:00\"}";

        MvcResult result = mockMvc.perform(post("/tasks")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody))
                .andExpect(status().isOk())
                .andReturn();

        assertThat(result.getResponse().getContentAsString()).contains("Title");
    }

    @Test
    @DisplayName("Should get a task by ID")
    void getTask() throws Exception {
        Task task = new Task(null, "Title", "Description", "Pending", LocalDateTime.now());
        task = taskRepository.save(task);

        MvcResult result = mockMvc.perform(get("/tasks/" + task.getId()))
                .andExpect(status().isOk())
                .andReturn();

        assertThat(result.getResponse().getContentAsString()).contains("Title");
    }

    @Test
    @DisplayName("Should update a task")
    void updateTask() throws Exception {
        Task task = new Task(null, "Title", "Description", "Pending", LocalDateTime.now());
        task = taskRepository.save(task);

        String updateRequestBody = "{\"title\":\"Updated Title\",\"description\":\"Updated Description\",\"status\":\"Completed\",\"dueDateTime\":\"2025-04-16T22:00:00\"}";

        MvcResult result = mockMvc.perform(put("/tasks/" + task.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(updateRequestBody))
                .andExpect(status().isOk())
                .andReturn();

        assertThat(result.getResponse().getContentAsString()).contains("Updated Title");
    }

    @Test
    @DisplayName("Should delete a task")
    void deleteTask() throws Exception {
        Task task = new Task(null, "Title", "Description", "Pending", LocalDateTime.now());
        task = taskRepository.save(task);

        mockMvc.perform(delete("/tasks/" + task.getId()))
                .andExpect(status().isOk());

        Optional<Task> deletedTask = taskRepository.findById(task.getId());
        assertThat(deletedTask).isEmpty();
    }

    @Test
    @DisplayName("Should get all tasks")
    void getAllTasks() throws Exception {
        Task task1 = new Task(null, "Title1", "Description1", "Pending", LocalDateTime.now());
        Task task2 = new Task(null, "Title2", "Description2", "Completed", LocalDateTime.now());
        taskRepository.save(task1);
        taskRepository.save(task2);

        MvcResult result = mockMvc.perform(get("/tasks"))
                .andExpect(status().isOk())
                .andReturn();

        assertThat(result.getResponse().getContentAsString()).contains("Title1", "Title2");
    }
}
