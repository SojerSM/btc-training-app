package com.btc.backend.core.bootstrap;

import com.btc.backend.app.account.core.model.entity.Account;
import com.btc.backend.app.account.core.repository.AccountRepository;
import com.btc.backend.app.task.core.model.entity.Task;
import com.btc.backend.app.task.core.repository.TaskRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Component
public class BootstrapMocks implements CommandLineRunner {

    private final TaskRepository taskRepository;
    private final AccountRepository accountRepository;
    private final PasswordEncoder passwordEncoder;

    public BootstrapMocks(TaskRepository taskRepository,
                          AccountRepository accountRepository,
                          PasswordEncoder passwordEncoder) {
        this.taskRepository = taskRepository;
        this.accountRepository = accountRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) throws Exception {
        loadMockedTasks();
        loadMockedAccount();
    }

    private void loadMockedTasks() {
        if (taskRepository.findAll().isEmpty()) {
            List<Task> mocked = new ArrayList<>();

            mocked.add(new Task("Zakupy", LocalDateTime.now(), false));
            mocked.add(new Task("Spotkanie z klientem", LocalDateTime.now(), false));
            mocked.add(new Task("Kajaki", LocalDateTime.now(), true));
            mocked.add(new Task("Naprawić zlew", LocalDateTime.now(), false));

            taskRepository.saveAll(mocked);
        }
    }

    private void loadMockedAccount() {
        if (accountRepository.findAll().isEmpty()) {
            Account registered = new Account();
            registered.setUsername("user");
            registered.setPassword(passwordEncoder.encode("password"));

            accountRepository.save(registered);
        }
    }
}
