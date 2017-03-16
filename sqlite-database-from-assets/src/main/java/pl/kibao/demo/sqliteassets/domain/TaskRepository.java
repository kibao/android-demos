package pl.kibao.demo.sqliteassets.domain;

import java.util.List;

public interface TaskRepository {
    List<Task> allTasks();
}
