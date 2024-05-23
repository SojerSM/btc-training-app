package com.btc.backend.app.task.core.controller;

import com.btc.backend.app.task.api.TaskService;
import com.btc.backend.app.task.core.model.dto.TaskDTO;
import com.btc.backend.app.task.core.model.dto.TaskRequestDTO;
import com.btc.backend.core.rest.RestEndpoints;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = RestEndpoints.TASK_PATH)
public class TaskController {

    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @GetMapping
    public List<TaskDTO> findAll(@RequestParam(name = "accountId") long accountId,
                                 @RequestParam(name = "filter", required = false) String filter,
                                 @RequestParam(name = "title", required = false) String title) {
        return taskService.findAll(filter, title, accountId);
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

    @PutMapping(value = "/{id}")
    public ResponseEntity<?> update(@PathVariable(name = "id") long id,
                                    @RequestBody @Validated TaskRequestDTO taskRequestDTO) {
        return taskService.update(id, taskRequestDTO);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<?> delete(@PathVariable(name = "id") long id) {
        return taskService.delete(id);
    }

    @DeleteMapping
    public ResponseEntity<?> deleteMany(@RequestBody long[] idArr) {
        return taskService.deleteMany(idArr);
    }
}
