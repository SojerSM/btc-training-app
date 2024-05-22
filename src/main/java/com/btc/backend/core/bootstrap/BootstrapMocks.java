package com.btc.backend.core.bootstrap;

import com.btc.backend.app.account.core.model.entity.Account;
import com.btc.backend.app.account.core.repository.AccountRepository;
import com.btc.backend.app.task.core.model.entity.Task;
import com.btc.backend.app.task.core.repository.TaskRepository;
import com.btc.backend.core.common.model.entity.Provider;
import com.btc.backend.core.common.repository.ProviderRepository;
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
    private final ProviderRepository providerRepository;

    public BootstrapMocks(TaskRepository taskRepository,
                          AccountRepository accountRepository,
                          PasswordEncoder passwordEncoder,
                          ProviderRepository providerRepository) {
        this.taskRepository = taskRepository;
        this.accountRepository = accountRepository;
        this.passwordEncoder = passwordEncoder;
        this.providerRepository = providerRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        loadMockedAccount();
        loadMockedTasks();
    }

    private void loadMockedTasks() {
        if (taskRepository.findAll().isEmpty()) {
            List<Task> mocked = new ArrayList<>();

            mocked.add(new Task("Zakupy", LocalDateTime.now(), false, accountRepository.findById(1L).orElseThrow()));
            mocked.add(new Task("Spotkanie z klientem", LocalDateTime.now(), false, accountRepository.findById(1L).orElseThrow()));
            mocked.add(new Task("Kajaki", LocalDateTime.now(), true, accountRepository.findById(2L).orElseThrow()));
            mocked.add(new Task("Naprawić zlew", LocalDateTime.now(), false,accountRepository.findById(2L).orElseThrow()));

            taskRepository.saveAll(mocked);
        }
    }

    private void loadMockedAccount() {
        if (accountRepository.findAll().isEmpty()) {
            List<Provider> providers = providerRepository.findAll();

            Account first = new Account();
            first.setUsername("user");
            first.setPassword(passwordEncoder.encode("password"));
            first.setEmail("seb.maz1996@gmail.com");
            first.setAllowedAuthProviders(providers);

            Account second = new Account();
            second.setUsername("user2");
            second.setPassword(passwordEncoder.encode("password"));
            second.setEmail("s.mazur.studia@gmail.com");
            second.setAllowedAuthProviders(List.of(providers.getFirst()));

            accountRepository.save(first);
            accountRepository.save(second);
        }
    }
}
