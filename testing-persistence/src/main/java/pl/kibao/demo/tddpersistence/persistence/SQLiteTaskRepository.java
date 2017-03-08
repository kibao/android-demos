package pl.kibao.demo.tddpersistence.persistence;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import pl.kibao.demo.tddpersistence.domain.Task;
import pl.kibao.demo.tddpersistence.domain.TaskRepository;
import pl.kibao.demo.tddpersistence.persistence.TasksDatabaseHelper.TasksColumns;

public class SQLiteTaskRepository implements TaskRepository {
    private static final String TABLE_NAME = "tasks";
    private SQLiteOpenHelper dbHelper;
    private SQLiteTaskMapper mapper;
    private static final String[] PROJECTION = new String[]{
        TasksColumns._ID,
        TasksColumns.TASK_NAME,
        TasksColumns.TASK_EXPIRATION
    };

    public SQLiteTaskRepository(@NonNull SQLiteOpenHelper dbHelper, @NonNull SQLiteTaskMapper mapper) {
        this.dbHelper = dbHelper;
        this.mapper = mapper;
    }

    @NonNull
    @Override
    public String nextIdentity() {
        return UUID.randomUUID().toString().toUpperCase();
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

    @Nullable
    @Override
    public Task taskOfId(@NonNull String id) {
        SQLiteDatabase database = dbHelper.getReadableDatabase();

        String selection = TasksColumns._ID + " = ?";
        String[] selectionArgs = {id};

        Cursor cursor =
            database.query(TABLE_NAME, PROJECTION, selection, selectionArgs, null, null, null);

        Task task = null;

        try {
            if (cursor.moveToFirst()) {
                task = mapper.toTask(cursor);
            }
        } finally {
            cursor.close();
        }

        return task;
    }


    @Override
    public void insert(@NonNull Task task) {
        SQLiteDatabase database = dbHelper.getWritableDatabase();

        ContentValues values = mapper.map(task);

        database.insertOrThrow(TABLE_NAME, null, values);
    }

    @Override
    public void remove(@NonNull Task task) {
        SQLiteDatabase database = dbHelper.getWritableDatabase();

        String id = String.valueOf(task.id());

        String selection = TasksColumns._ID + " = ?";
        String[] selectionArgs = {id};

        database.delete(TABLE_NAME, selection, selectionArgs);
    }

    @Override
    public void update(Task task) {
        SQLiteDatabase database = dbHelper.getWritableDatabase();

        ContentValues values = mapper.map(task);
        values.remove(TasksColumns._ID); //Don't want to update _id

        String id = String.valueOf(task.id());

        String selection = TasksColumns._ID + " = ?";
        String[] selectionArgs = {id};

        database.update(TABLE_NAME, values, selection, selectionArgs);
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
