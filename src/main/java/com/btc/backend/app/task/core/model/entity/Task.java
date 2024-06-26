package com.btc.backend.app.task.core.model.entity;

import com.btc.backend.app.account.core.model.entity.Account;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "TASK")
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String title;
    private LocalDateTime deadline;
    private boolean finished;

    @ManyToOne
    @JoinColumn(name = "account_id")
    private Account account;

    public Task() {
        // empty constructor
    }

    public Task(String title, LocalDateTime deadline, boolean finished, Account account) {
        this.title = title;
        this.deadline = deadline;
        this.finished = finished;
        this.account = account;
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

    public boolean getFinished() {
        return finished;
    }

    public void setFinished(boolean finished) {
        this.finished = finished;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }
}
