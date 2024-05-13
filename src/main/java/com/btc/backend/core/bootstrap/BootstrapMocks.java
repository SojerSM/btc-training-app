package com.btc.backend.core.bootstrap;

import com.btc.backend.app.task.core.model.entity.Task;
import com.btc.backend.app.task.core.repository.TaskRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Component
public class BootstrapMocks implements CommandLineRunner {

    private final TaskRepository taskRepository;

    public BootstrapMocks(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        loadMockedTasks();
    }

    private void loadMockedTasks() {
        if (taskRepository.findAll().isEmpty()) {
            List<Task> mocked = new ArrayList<>();

            mocked.add(new Task("Test1", LocalDateTime.now(), false));
            mocked.add(new Task("Test2", LocalDateTime.now(), false));
            mocked.add(new Task("Test3", LocalDateTime.now(), true));
            mocked.add(new Task("Test4", LocalDateTime.now(), false));

            taskRepository.saveAll(mocked);
        }
    }
}
