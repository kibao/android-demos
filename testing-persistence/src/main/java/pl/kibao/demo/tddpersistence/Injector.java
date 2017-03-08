package pl.kibao.demo.tddpersistence;

import android.content.Context;
import android.database.sqlite.SQLiteOpenHelper;

import pl.kibao.demo.tddpersistence.domain.TaskRepository;
import pl.kibao.demo.tddpersistence.persistence.SQLiteTaskMapper;
import pl.kibao.demo.tddpersistence.persistence.SQLiteTaskRepository;
import pl.kibao.demo.tddpersistence.persistence.TasksDatabaseHelper;

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
