package lawrence.command;

import lawrence.database.TaskFileManager;
import lawrence.parser.InputSource;
import lawrence.parser.TaskParser;
import lawrence.task.Task;
import lawrence.task.TaskList;
import lawrence.ui.UserInterface;
import lawrence.utils.DateParser;

import java.io.IOException;
import java.time.format.DateTimeParseException;

public class AddTaskCommand extends Command {
    private final String input;
    public AddTaskCommand(String input) {
        this.input = input;
    }
    @Override
    public void execute(TaskList taskList, TaskFileManager manager, UserInterface ui) {
        try {
            Task t = TaskParser.createTask(input, InputSource.USER);
            taskList.addTask(t);
            saveTasks(taskList, manager);

            // Format components of message to display
            int numberOfTasks = taskList.getSize();
            String verb = numberOfTasks == 1 ? "is" : "are";
            String plural = numberOfTasks == 1 ? "" : "s";
            ui.showMessage(String.format("Alright, added task:%n%s%nto the list.%n"
                    + "There %s currently %d task%s in the list.", t, verb, numberOfTasks, plural));
        } catch (DateTimeParseException e) {
            ui.showMessage(
                    String.format("Invalid date and/or time provided. DateTime should be in the format: %s",
                            DateParser.USER_INPUT_FORMAT_STRING));
        } catch (IllegalArgumentException e) {
            ui.showMessage(e.getMessage());
        } catch (IOException e) {
            ui.showMessage("Failed to save tasks to file. Please try again.");
        }
    }
}
