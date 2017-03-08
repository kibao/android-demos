package pl.kibao.demo.tddpersistence.domain;

import java.util.List;

public interface TaskRepository {
    String nextIdentity();

    List<Task> allTasks();

    Task taskOfId(String id);

    void insert(Task task);

    void remove(Task task);

    void update(Task task);
}
