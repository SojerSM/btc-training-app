package com.btc.backend.app.task.api;

import com.btc.backend.app.task.core.model.dto.TaskDTO;

import java.util.List;
import java.util.Optional;

public interface TaskService {

    List<TaskDTO> findAll();
    Optional<TaskDTO> findById(long id);
}
