package pl.kibao.demo.tddpersistence.persistence;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;
import android.support.annotation.VisibleForTesting;

public class TasksDatabaseHelper extends SQLiteOpenHelper {

    public interface Tables {
        String TASKS = "tasks";
    }

    public interface TasksColumns extends BaseColumns {
        String TASK_NAME = "task_name";
    }

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "tasks.db";
    private static TasksDatabaseHelper singleton;

    public static synchronized TasksDatabaseHelper getInstance(Context context) {
        if (singleton == null) {
            singleton = new TasksDatabaseHelper(context, DATABASE_NAME);
        }
        return singleton;
    }

    /**
     * Returns a new instance for unit tests.
     */
    @VisibleForTesting(otherwise = VisibleForTesting.NONE)
    static TasksDatabaseHelper getNewInstanceForTest(Context context) {
        return new TasksDatabaseHelper(context, null);
    }

    private TasksDatabaseHelper(Context context, String databaseName) {
        super(context, databaseName, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + Tables.TASKS + " ("
            + TasksColumns._ID + "  TEXT PRIMARY KEY, "
            + TasksColumns.TASK_NAME + " TEXT "
            + ");");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
