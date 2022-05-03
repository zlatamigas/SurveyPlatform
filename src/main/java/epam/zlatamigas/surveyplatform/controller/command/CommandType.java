package epam.zlatamigas.surveyplatform.controller.command;


import epam.zlatamigas.surveyplatform.controller.command.impl.*;
import epam.zlatamigas.surveyplatform.controller.command.impl.list.*;
import epam.zlatamigas.surveyplatform.controller.command.impl.start.*;

public enum CommandType {
    DEFAULT(new DefaultCommand()),
    START_AUTHENTICATION(new StartAuthenticationCommand()),
    LOGIN(new LoginCommand()),
    LOGOUT(new LogoutCommand()),
    LIST_USERS(new ListUsersCommand()),
    LIST_SURVEYS(new ListSurveysCommand()),
//    LIST_USER_FINISHED_SURVEYS(new ListSurveysCommand()),
    LIST_USER_CREATED_SURVEYS(new ListUserCreatedSurveysCommand()),
    START_EDIT_SURVEY(new StartEditSurveyCommand());

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
