package pl.kibao.demo.sqliteassets.ui;

import java.util.List;

import pl.kibao.demo.sqliteassets.domain.Task;


public interface TasksView {
    void setTasks(List<Task> tasks);
}
