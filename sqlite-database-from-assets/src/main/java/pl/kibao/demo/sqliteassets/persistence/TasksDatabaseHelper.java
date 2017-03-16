package pl.kibao.demo.sqliteassets.persistence;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

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

    private final Context context;
    private final String databasePath;

    public static synchronized TasksDatabaseHelper getInstance(Context context) {
        if (singleton == null) {
            singleton = new TasksDatabaseHelper(context);
        }
        return singleton;
    }


    public TasksDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
        databasePath = context.getDatabasePath(DATABASE_NAME).getPath();
        initializeDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //noop
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //noop
    }

    private void initializeDatabase() {
        if (currentDatabaseVersion() == DATABASE_VERSION) {
            return;
        }

        context.deleteDatabase(DATABASE_NAME);

        try {
            copyDatabase();

            // set version to current
            SQLiteDatabase database =
                SQLiteDatabase.openDatabase(databasePath, null, SQLiteDatabase.OPEN_READWRITE);
            database.setVersion(DATABASE_VERSION);
            database.close();
        } catch (IOException e) {
            throw new RuntimeException("Error copying database", e);
        }
    }

    /**
     * Returns current database version.
     * If database doesn't exist, creates new empty one and return 0.
     *
     * @return current database version
     */
    private int currentDatabaseVersion() {
        SQLiteDatabase db = context.openOrCreateDatabase(DATABASE_NAME, 0, null, null);
        int dbVersion = db.getVersion();
        db.close();
        return dbVersion;
    }

    private void copyDatabase() throws IOException {
        // Or context.getResources().openRawResource(R.raw.tasks) if db is placed in res/raw
        InputStream source = context.getAssets().open(DATABASE_NAME);
        OutputStream destination = new FileOutputStream(databasePath);
        byte[] buffer = new byte[1024];
        int length;

        while ((length = source.read(buffer)) > 0) {
            destination.write(buffer, 0, length);
        }

        destination.flush();
        destination.close();
        source.close();
    }
}
