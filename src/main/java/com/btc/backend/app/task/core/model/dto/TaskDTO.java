package com.btc.backend.app.task.core.model.dto;

import java.time.LocalDateTime;

public class TaskDTO {

    private long id;
    private String title;
    private LocalDateTime deadline;
    private boolean isFinished;

    public TaskDTO() {
        // empty constructor
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public LocalDateTime getDeadline() {
        return deadline;
    }

    public void setDeadline(LocalDateTime deadline) {
        this.deadline = deadline;
    }

    public boolean isFinished() {
        return isFinished;
    }

    public void setFinished(boolean finished) {
        isFinished = finished;
    }
}
