package com.btc.backend.app.task.core.controller;

import com.btc.backend.app.task.api.TaskService;
import com.btc.backend.app.task.core.model.dto.TaskDTO;
import com.btc.backend.app.task.core.model.dto.TaskRequestDTO;
import com.btc.backend.core.config.RestEndpoints;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(name = RestEndpoints.TASK_PATH)
public class TaskController {

    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @GetMapping
    public List<TaskDTO> findAll() {
        return taskService.findAll();
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<?> findById(@PathVariable(name = "id") long id) {
        return taskService.findById(id)
                .map(task -> ResponseEntity.ok().body(task))
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<?> add(@RequestBody @Validated TaskRequestDTO taskRequestDTO) {
        return taskService.add(taskRequestDTO);
    }
}