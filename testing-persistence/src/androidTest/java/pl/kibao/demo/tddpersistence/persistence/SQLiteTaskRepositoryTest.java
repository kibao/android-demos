package pl.kibao.demo.tddpersistence.persistence;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;

import pl.kibao.demo.tddpersistence.domain.Task;

import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;


@RunWith(AndroidJUnit4.class)
public class SQLiteTaskRepositoryTest {

    private SQLiteTaskRepository repository;

    @Before
    public void setUp() {
        Context appContext = InstrumentationRegistry.getTargetContext();
        TasksDatabaseHelper dbHelper = TasksDatabaseHelper.getNewInstanceForTest(appContext);
        SQLiteTaskMapper mapper = new SQLiteTaskMapper();
        repository = new SQLiteTaskRepository(dbHelper, mapper);
    }

    @Test
    public void should_return_null_when_task_does_not_exist() {
        String wrongId = "xxxxxxx";

        Task savedTask = repository.taskOfId(wrongId);

        assertNull(savedTask);
    }

    @Test
    public void should_save_task_and_find_it_by_id() {
        Task originalTask = new Task(repository.nextIdentity(), "Sample Task");

        repository.insert(originalTask);

        Task savedTask = repository.taskOfId(originalTask.id());

        assertNotNull("Task wasn't saved or could not find it", savedTask);
        assertEquals("Saved task differs from original", originalTask, savedTask);
    }

    @Test
    public void should_return_empty_list_when_no_tasks() {
        List<Task> tasks = repository.allTasks();

        assertNotNull("Should return list, got null", tasks);
        assertEquals("There should be no tasks", 0, tasks.size());
    }

    @Test
    public void should_return_all_existing_tasks() {
        Task[] originalTasks = {
            new Task(repository.nextIdentity(), "Sample Task"),
            new Task(repository.nextIdentity(), "Sample Task 2"),
            new Task(repository.nextIdentity(), "Sample Task 3")
        };
        insertAllTasks(originalTasks);

        List<Task> tasks = repository.allTasks();

        assertThat("allTasks does not return all existing tasks", tasks, containsInAnyOrder(originalTasks));
    }

    @Test
    public void should_update_task() {
        Task originalTask = new Task(repository.nextIdentity(), "Sample Task");
        repository.insert(originalTask);

        Task modifiedTask = new Task(originalTask.id(), "Updated task");
        repository.update(modifiedTask);

        Task updatedTask = repository.taskOfId(modifiedTask.id());

        assertNotNull("Unable to retrieve task after modification", updatedTask);
        assertNotEquals("Task was not updated", originalTask, updatedTask);
        assertEquals("Updated task differs from modified", modifiedTask, updatedTask);
    }

    @Test
    public void should_remove_task() {
        Task originalTask = new Task(repository.nextIdentity(), "Sample Task");
        repository.insert(originalTask);

        repository.remove(originalTask);

        Task task = repository.taskOfId(originalTask.id());

        assertNull("Task was not deleted", task);
    }


    private void insertAllTasks(Task[] tasks) {
        for (int i = 0; i < tasks.length; i++) {
            repository.insert(tasks[i]);
        }
    }

}
