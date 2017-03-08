package pl.kibao.demo.tddpersistence.ui;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.List;
import java.util.Random;

import pl.kibao.demo.tddpersistence.R;
import pl.kibao.demo.tddpersistence.TasksApp;
import pl.kibao.demo.tddpersistence.domain.Task;

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

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.new_task);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.createTask(generateTaskName());
            }
        });

        adapter = new ArrayAdapter<>(this, R.layout.task_list_item, R.id.name);

        list = (ListView) findViewById(R.id.list);
        list.setAdapter(adapter);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Object item = parent.getAdapter().getItem(position);
                if (item instanceof Task) {
                    presenter.removeTask((Task) item);
                }
            }
        });

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

    @NonNull
    private String generateTaskName() {
        String[] names = {
            "Take some photos",
            "Sleep",
            "Drink coffee",
            "Write some code",
            "Test your app",
            "Wake up",
            "Call Jeniffer",
            "Call Mom for B-day",
            "Take dog to vet",
            "Drink glass of water",
            "Exercise",
            "45m Reading"
        };
        return names[new Random().nextInt(names.length)];
    }
}
