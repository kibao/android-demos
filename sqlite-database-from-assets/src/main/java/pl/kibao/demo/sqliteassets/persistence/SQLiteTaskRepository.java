package pl.kibao.demo.sqliteassets.persistence;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

import pl.kibao.demo.sqliteassets.domain.Task;
import pl.kibao.demo.sqliteassets.domain.TaskRepository;
import pl.kibao.demo.sqliteassets.persistence.TasksDatabaseHelper.Tables;
import pl.kibao.demo.sqliteassets.persistence.TasksDatabaseHelper.TasksColumns;

public class SQLiteTaskRepository implements TaskRepository {
    private static final String TABLE_NAME = Tables.TASKS;
    private SQLiteOpenHelper dbHelper;
    private SQLiteTaskMapper mapper;
    private static final String[] PROJECTION = new String[]{
        TasksColumns._ID,
        TasksColumns.TASK_NAME
    };

    public SQLiteTaskRepository(@NonNull SQLiteOpenHelper dbHelper, @NonNull SQLiteTaskMapper mapper) {
        this.dbHelper = dbHelper;
        this.mapper = mapper;
    }

    @NonNull
    @Override
    public List<Task> allTasks() {
        SQLiteDatabase database = dbHelper.getReadableDatabase();

        Cursor cursor = database.query(TABLE_NAME, PROJECTION, null, null, null, null, null);

        try {
            return toTasksList(cursor);
        } finally {
            cursor.close();
        }

    }

    @NonNull
    private List<Task> toTasksList(@NonNull Cursor cursor) {
        List<Task> tasks = new ArrayList<Task>();

        if (cursor.moveToFirst()) {
            while (!cursor.isAfterLast()) {
                tasks.add(mapper.toTask(cursor));
                cursor.moveToNext();
            }
        }

        return tasks;
    }


}
