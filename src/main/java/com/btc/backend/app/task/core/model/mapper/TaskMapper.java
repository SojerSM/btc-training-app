package com.btc.backend.app.task.core.model.mapper;

import com.btc.backend.app.task.core.model.dto.TaskDTO;
import com.btc.backend.app.task.core.model.dto.TaskRequestDTO;
import com.btc.backend.app.task.core.model.entity.Task;
import org.springframework.stereotype.Service;

@Service
public class TaskMapper {

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
        return dto;
    }

    public Task map(TaskRequestDTO taskRequestDTO) {
        Task task = new Task();
        task.setTitle(taskRequestDTO.getTitle());
        task.setDeadline(taskRequestDTO.getDeadline());
        task.setFinished(taskRequestDTO.getFinished());
        return task;
    }
}
