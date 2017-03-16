package pl.kibao.demo.sqliteassets.ui;

import java.lang.ref.WeakReference;
import java.util.List;

import pl.kibao.demo.sqliteassets.domain.Task;
import pl.kibao.demo.sqliteassets.domain.TaskRepository;

class TasksPresenter {
    private TaskRepository taskRepository;
    private WeakReference<TasksView> viewRef;

    public TasksPresenter(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    public void attachView(TasksView view) {
        viewRef = new WeakReference<>(view);
        loadTasks();
    }

    public void detachView() {
        viewRef.clear();
        viewRef = null;
    }

    public void loadTasks() {
        List<Task> tasks = taskRepository.allTasks();
        if (viewRef != null && viewRef.get() != null) {
            viewRef.get().setTasks(tasks);
        }
    }
}
