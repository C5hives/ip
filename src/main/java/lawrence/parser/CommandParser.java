package lawrence.parser;


import lawrence.command.Command;
import lawrence.command.CommandType;
import lawrence.command.DisplayTasksCommand;
import lawrence.command.ExitSessionCommand;
import lawrence.command.CompleteTaskCommand;
import lawrence.command.UncompleteTaskCommand;
import lawrence.command.DeleteTaskCommand;
import lawrence.command.AddTaskCommand;

public class CommandParser {
    public static Command createCommand(String input) {
        if (input.isEmpty()) {
            throw new IllegalArgumentException("Command input cannot be empty!");
        }
        // Parse command at start of the line
        String[] inputComponents = input.split(" ", 2);

        // determine the type of command
        String commandString = inputComponents[0];
        CommandType type;
        type = CommandType.fromString(commandString);

        Command c;
        switch(type) {
        case EXIT:
            c = new ExitSessionCommand();
            break;
        case DISPLAY:
            c = new DisplayTasksCommand();
            break;
        case MARK_COMPLETE:
            c = new CompleteTaskCommand(input);
            break;
        case MARK_INCOMPLETE:
            c = new UncompleteTaskCommand(input);
            break;
        case DELETE:
            c = new DeleteTaskCommand(input);
            break;
        case ADD_TODO:
            // Intentional falling through of case
        case ADD_DEADLINE:
            // Intentional falling through of case
        case ADD_EVENT:
            c = new AddTaskCommand(input);
            break;
        default:
            // This case should never be reached
            throw new IllegalStateException("Unexpected command: " + type);
        }
        return c;
    }
}
