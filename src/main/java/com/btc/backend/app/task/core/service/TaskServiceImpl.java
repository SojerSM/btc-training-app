package com.btc.backend.app.task.core.service;

import com.btc.backend.app.task.api.TaskService;
import com.btc.backend.app.task.core.model.dto.TaskDTO;
import com.btc.backend.app.task.core.model.dto.TaskRequestDTO;
import com.btc.backend.app.task.core.model.entity.Task;
import com.btc.backend.app.task.core.model.mapper.TaskMapper;
import com.btc.backend.app.task.core.repository.TaskRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TaskServiceImpl implements TaskService {

    private final TaskRepository taskRepository;
    private final TaskMapper taskMapper;

    public TaskServiceImpl(TaskRepository taskRepository, TaskMapper taskMapper) {
        this.taskRepository = taskRepository;
        this.taskMapper = taskMapper;
    }

    @Override
    public List<TaskDTO> findAll() {
        return taskRepository.findAll().stream()
                .map(taskMapper::map)
                .toList();
    }

    @Override
    public Optional<TaskDTO> findById(long id) {
        return taskRepository.findById(id)
                .map(taskMapper::map);
    }

    @Override
    public ResponseEntity<?> add(TaskRequestDTO taskRequestDTO) {
        Task task = taskRepository.save(taskMapper.map(taskRequestDTO));
        return ResponseEntity.ok().body(task);
    }

    @Override
    public ResponseEntity<?> delete(long id) {
        taskRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @Override
    public ResponseEntity<?> deleteMany(long[] idArr) {
        for (long id : idArr) {
            taskRepository.deleteById(id);
        }
        return ResponseEntity.noContent().build();
    }
}
