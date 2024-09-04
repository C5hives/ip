package lawrence.task;

import java.util.ArrayList;
import java.util.List;

public class TaskList {
    private ArrayList<Task> list;

    public TaskList(Task[] tasks) {
        list = new ArrayList<>(List.of(tasks));
    }

    public void addTask(Task task) {
        list.add(task);
    }

    public Task[] getTasks() {
        return list.toArray(new Task[0]);
    }

    public Task deleteTask(int taskNumber) {
        if (list.isEmpty()) {
            throw new IllegalStateException("There are no tasks that can be chosen for deletion.");
        }

        if (taskNumber < 1 || taskNumber > list.size()) {
            throw new IllegalArgumentException(
                    String.format("Task does not exist. Number must be within the range 1 to %s.", list.size()));
        }

        return list.remove(taskNumber - 1);
    }

    public Task completeTask(int taskNumber) {
        if (list.isEmpty()) {
            throw new IllegalStateException("There are no tasks that can be chosen to be marked as complete.");
        }

        if (taskNumber < 1 || taskNumber > list.size()) {
            throw new IllegalArgumentException(
                    String.format("Task does not exist. Number must be within the range 1 to %s.", list.size()));
        }

        Task t = list.get(taskNumber - 1);
        t.setComplete(true);
        return t;
    }

    public Task uncompleteTask(int taskNumber) {
        if (list.isEmpty()) {
            throw new IllegalStateException("There are no tasks that can be chosen to be marked as incomplete.");
        }

        if (taskNumber < 1 || taskNumber > list.size()) {
            throw new IllegalArgumentException(
                    String.format("Task does not exist. Number must be within the range 1 to %s.", list.size()));
        }

        Task t = list.get(taskNumber - 1);
        t.setComplete(false);
        return t;
    }

    public int getSize() {
        return list.size();
    }

    @Override
    public String toString() {
        if (list.isEmpty()) {
            return "";
        }

        StringBuilder result = new StringBuilder();
        for (int i = 0; i < list.size(); i++) {
            result.append(String.format("%d.%s%n", i + 1, list.get(i)));
        }

        // exclude the last newline character from getting printed
        return result.substring(0, result.length() - 2);
    }
}
