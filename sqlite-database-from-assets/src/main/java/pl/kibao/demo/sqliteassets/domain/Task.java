package pl.kibao.demo.sqliteassets.domain;

import android.support.annotation.NonNull;

public class Task {
    private final String id;
    private final String name;

    public Task(@NonNull String id, @NonNull String name) {
        this.id = id;
        this.name = name;
    }

    public String id() {
        return id;
    }

    public String name() {
        return name;
    }

    @Override
    public String toString() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Task task = (Task) o;

        if (!id.equals(task.id)) return false;
        return name.equals(task.name);

    }

    @Override
    public int hashCode() {
        int result = id.hashCode();
        result = 31 * result + name.hashCode();
        return result;
    }
}
