package pl.kibao.demo.sqliteassets.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.List;

import pl.kibao.demo.sqliteassets.R;
import pl.kibao.demo.sqliteassets.TasksApp;
import pl.kibao.demo.sqliteassets.domain.Task;

public class TasksActivity extends AppCompatActivity implements TasksView {

    private ListView list;
    private TasksPresenter presenter;
    private ArrayAdapter<Task> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tasks);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        adapter = new ArrayAdapter<>(this, R.layout.task_list_item, R.id.name);

        list = (ListView) findViewById(R.id.list);
        list.setAdapter(adapter);

        presenter = new TasksPresenter(TasksApp.injector().provideTaskRepository());
        presenter.attachView(this);
    }

    @Override
    protected void onDestroy() {
        presenter.detachView();
        super.onDestroy();
    }

    @Override
    public void setTasks(List<Task> tasks) {
        adapter.setNotifyOnChange(false);
        adapter.clear();
        adapter.setNotifyOnChange(true);
        adapter.addAll(tasks);
    }
}
