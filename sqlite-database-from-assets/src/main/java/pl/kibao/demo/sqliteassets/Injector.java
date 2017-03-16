package pl.kibao.demo.sqliteassets;

import android.content.Context;
import android.database.sqlite.SQLiteOpenHelper;

import pl.kibao.demo.sqliteassets.domain.TaskRepository;
import pl.kibao.demo.sqliteassets.persistence.SQLiteTaskMapper;
import pl.kibao.demo.sqliteassets.persistence.SQLiteTaskRepository;
import pl.kibao.demo.sqliteassets.persistence.TasksDatabaseHelper;

public class Injector {
    private TaskRepository taskRepository;

    Injector(Context context) {
        SQLiteOpenHelper dbHelper = TasksDatabaseHelper.getInstance(context);
        SQLiteTaskMapper mapper = new SQLiteTaskMapper();

        taskRepository = new SQLiteTaskRepository(dbHelper, mapper);
    }

    public TaskRepository provideTaskRepository() {
        return taskRepository;
    }
}
