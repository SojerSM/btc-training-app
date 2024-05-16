package com.btc.backend.app.task.core.repository;

import com.btc.backend.app.task.core.model.entity.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {

    List<Task> findAllByIsFinished(boolean isFinished);
    List<Task> findAllByDeadlineBefore(LocalDateTime date);
}
