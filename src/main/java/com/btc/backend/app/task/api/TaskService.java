package com.btc.backend.app.task.api;

import com.btc.backend.app.task.core.model.dto.TaskDTO;
import com.btc.backend.app.task.core.model.dto.TaskRequestDTO;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;

public interface TaskService {

    List<TaskDTO> findAll(String filter);
    Optional<TaskDTO> findById(long id);
    ResponseEntity<?> add(TaskRequestDTO taskRequestDTO);
    ResponseEntity<?> delete(long id);
    ResponseEntity<?> deleteMany(long[] idArr);
}
