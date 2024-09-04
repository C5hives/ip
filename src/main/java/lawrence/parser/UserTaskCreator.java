package lawrence.parser;

import lawrence.task.Task;
import lawrence.task.TaskType;
import lawrence.task.Todo;
import lawrence.task.Deadline;
import lawrence.task.Event;
import lawrence.utils.DateParser;

import java.time.LocalDateTime;

public class UserTaskCreator implements TaskCreator {
    private static final int NUMBER_OF_DEADLINE_PARAMETERS = 2;
    private static final int NUMBER_OF_EVENT_PARAMETERS = 3;

    @Override
    public Task createTask(String input) {
        if (input.isEmpty()) {
            throw new IllegalArgumentException("Cannot create task from empty input!");
        }

        // separate the string containing information about the task type into index 0
        String[] inputComponents = input.split(" ", 2);

        if (inputComponents.length != 2) {
            throw new IllegalArgumentException("Unable to parse input from user");
        }

        // parse the type of task that needs to be created
        TaskType type = TaskType.fromString(inputComponents[0]);

        Task t;
        switch (type) {
        case TODO:
            t = createTodoTask(inputComponents[1]);
            break;
        case DEADLINE:
            t = createDeadlineTask(inputComponents[1]);
            break;
        case EVENT:
            t = createEventTask(inputComponents[1]);
            break;
        default:
            throw new IllegalArgumentException("Unknown task type: " + type);
        }
        return t;
    }

    private Todo createTodoTask(String input) {
        return new Todo(input);
    }

    private Deadline createDeadlineTask(String input) {
        String[] parameters = input.split(" /by ", NUMBER_OF_DEADLINE_PARAMETERS);

        if (parameters.length != NUMBER_OF_DEADLINE_PARAMETERS) {
            throw new IllegalArgumentException(
                    String.format("Unable to create deadline from user input. Expected %d parameters, got %d",
                            NUMBER_OF_DEADLINE_PARAMETERS,
                            parameters.length));
        }

        // deconstruct array elements into their respective attributes
        String description = parameters[0];
        LocalDateTime by = DateParser.parseUserInputDate(parameters[1]);

        return new Deadline(description, by);
    }

    private Event createEventTask(String input) {
        String[] parameters = input.split(" /from | /to ", NUMBER_OF_EVENT_PARAMETERS);
        if (parameters.length != NUMBER_OF_EVENT_PARAMETERS) {
            throw new IllegalArgumentException(
                    String.format("Unable to create event from user input. Expected %d parameters, got %d",
                            NUMBER_OF_EVENT_PARAMETERS,
                            parameters.length));
        }

        // deconstruct array elements into their respective attributes
        String description = parameters[0];
        LocalDateTime from = DateParser.parseUserInputDate(parameters[1]);
        LocalDateTime to = DateParser.parseUserInputDate(parameters[2]);

        return new Event(description, from, to);
    }
}
