package com.btc.backend.app.task.core.model.dto;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public class TaskRequestDTO {

    @NotNull
    private String title;

    @NotNull @FutureOrPresent
    private LocalDateTime deadline;

    public TaskRequestDTO() {
        // empty constructor
    }

    public @NotNull String getTitle() {
        return title;
    }

    public void setTitle(@NotNull String title) {
        this.title = title;
    }

    public @NotNull @FutureOrPresent LocalDateTime getDeadline() {
        return deadline;
    }

    public void setDeadline(@NotNull @FutureOrPresent LocalDateTime deadline) {
        this.deadline = deadline;
    }
}
