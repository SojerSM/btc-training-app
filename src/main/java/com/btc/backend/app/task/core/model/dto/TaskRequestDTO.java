package com.btc.backend.app.task.core.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public class TaskRequestDTO {

    @NotNull
    private String title;

    @NotNull
    private LocalDateTime deadline;
    private boolean finished;
    private long accountId;

    public TaskRequestDTO() {
        // empty constructor
    }

    public @NotNull String getTitle() {
        return title;
    }

    public void setTitle(@NotNull String title) {
        this.title = title;
    }

    public @NotNull LocalDateTime getDeadline() {
        return deadline;
    }

    public void setDeadline(@NotNull LocalDateTime deadline) {
        this.deadline = deadline;
    }

    public boolean getFinished() {
        return finished;
    }

    public void setFinished(boolean finished) {
        this.finished = finished;
    }

    public long getAccountId() {
        return accountId;
    }

    public void setAccountId(long accountId) {
        this.accountId = accountId;
    }
}
