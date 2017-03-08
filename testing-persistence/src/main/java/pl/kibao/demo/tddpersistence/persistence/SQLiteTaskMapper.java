package pl.kibao.demo.tddpersistence.persistence;

import android.content.ContentValues;
import android.database.Cursor;

import pl.kibao.demo.tddpersistence.domain.Task;
import pl.kibao.demo.tddpersistence.persistence.TasksDatabaseHelper.TasksColumns;

public class SQLiteTaskMapper {
    public ContentValues map(Task task) {
        ContentValues values = new ContentValues();

        values.put(TasksColumns._ID, task.id());
        values.put(TasksColumns.TASK_NAME, task.name());

        return values;
    }

    public Task toTask(Cursor cursor) {
        String id = cursor.getString(cursor.getColumnIndexOrThrow(TasksColumns._ID));
        String name = cursor.getString(cursor.getColumnIndexOrThrow(TasksColumns.TASK_NAME));
        return new Task(id, name);
    }
}
