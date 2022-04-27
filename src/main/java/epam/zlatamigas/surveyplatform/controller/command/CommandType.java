package epam.zlatamigas.surveyplatform.controller.command;


import epam.zlatamigas.surveyplatform.controller.command.impl.DefaultCommand;
import epam.zlatamigas.surveyplatform.controller.command.impl.LoginCommand;
import epam.zlatamigas.surveyplatform.controller.command.impl.LogoutCommand;
import epam.zlatamigas.surveyplatform.controller.command.impl.StartAuthenticationCommand;

public enum CommandType {
    DEFAULT(new DefaultCommand()),
    START_AUTHENTICATION(new StartAuthenticationCommand()),
    LOGIN(new LoginCommand()),
    LOGOUT(new LogoutCommand());

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
