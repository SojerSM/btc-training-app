package com.btc.backend.app.task.core.model.mapper;

import com.btc.backend.app.account.api.AccountService;
import com.btc.backend.app.task.core.model.dto.TaskDTO;
import com.btc.backend.app.task.core.model.dto.TaskRequestDTO;
import com.btc.backend.app.task.core.model.entity.Task;
import org.springframework.stereotype.Service;

@Service
public class TaskMapper {

    private final AccountService accountService;

    public TaskMapper(AccountService accountService) {
        this.accountService = accountService;
    }

    public Task map(TaskDTO dto) {
        Task task = new Task();
        task.setId(dto.getId());
        task.setTitle(dto.getTitle());
        task.setDeadline(dto.getDeadline());
        task.setFinished(dto.getFinished());
        return task;
    }

    public TaskDTO map(Task task) {
        TaskDTO dto = new TaskDTO();
        dto.setId(task.getId());
        dto.setTitle(task.getTitle());
        dto.setDeadline(task.getDeadline());
        dto.setFinished(task.getFinished());
        dto.setAccountId(task.getAccount().getId());
        return dto;
    }

    public Task map(TaskRequestDTO taskRequestDTO) {
        Task task = new Task();
        task.setTitle(taskRequestDTO.getTitle());
        task.setDeadline(taskRequestDTO.getDeadline());
        task.setFinished(taskRequestDTO.getFinished());
        task.setAccount(accountService.findById(taskRequestDTO.getAccountId()).orElseThrow());
        return task;
    }
}
