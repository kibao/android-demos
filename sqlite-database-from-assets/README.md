# Copy SQLite Database from Assets directory

[TasksDatabaseHelper](src/main/java/pl/kibao/demo/sqliteassets/persistence/TasksDatabaseHelper.java)
contains whole logic of:
- creating new database from assets when database doesn't exist,
- replacing current database with new one from assets when versions doesn`t equal.

### Regenerate Sample Database

Run provided `regenerate-sqlite-db.sh` script and your output should tell how many tasks were
generated:

    $ ./regenerate-sqlite-db.sh
    Generated tasks 19

Next step is to increase database version in [TasksDatabaseHelper](src/main/java/pl/kibao/demo/sqliteassets/persistence/TasksDatabaseHelper.java):

    public class TasksDatabaseHelper extends SQLiteOpenHelper {
        // ...
        private static final int DATABASE_VERSION = 1; // Value to increase
        // ...
    }

Then when you build and run application it will use the new one version of the database.
