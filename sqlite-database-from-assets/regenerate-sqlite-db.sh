#!/bin/bash

DATABASE_PATH="src/main/assets/tasks.db"
TASKS_TABLE_SCHEMA="CREATE TABLE tasks (_id INTEGER PRIMARY KEY AUTOINCREMENT, task_name TEXT);"
TASKS=("Take some photos" "Sleep" "Drink coffee" "Write some code" "Test your app" "Wake up" "Call Jeniffer" "Call Mom for B-day" "Take dog to vet" "Drink glass of water" "Exercise" "45m Reading")
TASKS_SIZE=${#TASKS[*]}

# Create empty database file
cat /dev/null > $DATABASE_PATH

# Create tasks table
sqlite3 $DATABASE_PATH "$TASKS_TABLE_SCHEMA"

# How many items to add.
items_to_add=$(( $RANDOM % 20 + 4 ));

# Add random items
for i in `seq 1 $items_to_add`
do
    random_task_idx=$(( $RANDOM % $TASKS_SIZE ))
    TASK_NAME="${TASKS[$random_task_idx]} $i"
    sqlite3 $DATABASE_PATH "INSERT INTO tasks (task_name) VALUES('$TASK_NAME')"
done

echo "Generated tasks $items_to_add"
