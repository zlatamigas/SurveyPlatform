package epam.zlatamigas.surveyplatform.model.command;


import epam.zlatamigas.surveyplatform.model.command.impl.DefaultCommand;
import epam.zlatamigas.surveyplatform.model.command.impl.LoginCommand;
import epam.zlatamigas.surveyplatform.model.command.impl.LogoutCommand;

public enum CommandType {
    LOGIN(new LoginCommand()),
    LOGOUT(new LogoutCommand()),
    DEFAULT(new DefaultCommand());

    private Command command;

    CommandType(Command command){
        this.command = command;
    }

    public static Command define(String commandStr){
        CommandType commandType;
        try {
            commandType = CommandType.valueOf(commandStr.toUpperCase());
        } catch (IllegalArgumentException e){
            commandType = DEFAULT;
        }
        return commandType.command;
    }
}
