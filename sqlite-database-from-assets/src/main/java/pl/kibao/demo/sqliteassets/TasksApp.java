package pl.kibao.demo.sqliteassets;

import android.app.Application;


public class TasksApp extends Application {
    private static Injector injector;

    @Override
    public void onCreate() {
        super.onCreate();
        injector = new Injector(this);
    }

    public static Injector injector() {
        return injector;
    }
}
