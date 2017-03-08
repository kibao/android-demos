package pl.kibao.demo.tddpersistence.ui;

import java.util.List;

import pl.kibao.demo.tddpersistence.domain.Task;

public interface TasksView {
    void setTasks(List<Task> tasks);
}
