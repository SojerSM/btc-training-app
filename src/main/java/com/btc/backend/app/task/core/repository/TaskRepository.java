package com.btc.backend.app.task.core.repository;

import com.btc.backend.app.task.core.model.entity.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {

    List<Task> findAllByAccountId(long accountId);
    List<Task> findAllByFinished(boolean isFinished);
    List<Task> findAllByDeadlineBefore(LocalDateTime date);

    @Query("SELECT e from Task e WHERE LOWER(e.title) LIKE LOWER(CONCAT('%', :title, '%'))")
    List<Task> findAllByTitleContaining(@Param("title") String title);
}
