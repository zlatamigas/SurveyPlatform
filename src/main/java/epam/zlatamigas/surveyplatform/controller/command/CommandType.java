package epam.zlatamigas.surveyplatform.controller.command;


import epam.zlatamigas.surveyplatform.controller.command.impl.*;
import epam.zlatamigas.surveyplatform.controller.command.impl.finish.FinishEditQuestionCommand;
import epam.zlatamigas.surveyplatform.controller.command.impl.finish.FinishEditSurveyCommand;
import epam.zlatamigas.surveyplatform.controller.command.impl.samepage.*;
import epam.zlatamigas.surveyplatform.controller.command.impl.to.*;
import epam.zlatamigas.surveyplatform.controller.command.impl.start.*;

public enum CommandType {
    // Shared
    DEFAULT(new DefaultCommand()),
    HOME(new HomeCommand()),
    CHANGE_LOCALISATION(new ChangeLocalisationCommand()),

    // User confirmation
    LOGIN(new LoginCommand()),
    LOGOUT(new LogoutCommand()),
    START_AUTHENTICATION(new StartAuthenticationCommand()),
    // TODO
    // LOGUP(new LogupCommand()),
    // CONFIRM_LOGUP(new ConfirmLogupCommand()),

    // List data
    LIST_USERS(new ListUsersCommand()),
    LIST_SURVEYS(new ListSurveysCommand()),
    LIST_USER_CREATED_SURVEYS(new ListUserCreatedSurveysCommand()),
    // TODO
    // LIST_USER_FINISHED_SURVEYS(new ListUserFinishedSurveysCommand()),

    // CRUD survey and its parts
    START_EDIT_SURVEY(new StartEditSurveyCommand()),
    FINISH_EDIT_SURVEY(new FinishEditSurveyCommand()),
    START_EDIT_QUESTION(new StartEditQuestionCommand()),
    // TODO
    FINISH_EDIT_QUESTION(new FinishEditQuestionCommand()),
    REMOVE_QUESTION(new RemoveQuestionCommand()),
    ADD_ANSWER(new AddAnswerCommand()),
    REMOVE_ANSWER(new RemoveAnswerCommand()),
    // CANCEL_EDIT_SURVEY(new CancelEditSurveyCommand()),
    // CANCEL_EDIT_QUESTION(new CancelEditQuestionCommand()),
    // CANCEL_EDIT_ANSWER(new CancelEditAnswerCommand()),
    DELETE_SURVEY(new DeleteSurveyCommand()),
    // SHOW_DELETE_SURVEY(new ShowDeleteSurveyCommand()),
    // SHOW_DELETE_QUESTION(new ShowDeleteQuestionCommand()),
    // SHOW_DELETE_ANSWER(new ShowDeleteAnswerCommand()),
    // CONFIRM_DELETE_SURVEY(new ConfirmDeleteSurveyCommand()),
    // CONFIRM_DELETE_QUESTION(new ConfirmDeleteQuestionCommand()),
    // CONFIRM_DELETE_ANSWER(new ConfirmDeleteAnswerCommand()),
    STOP_SURVEY(new StopSurveyCommand())

    // Participate in survey
    // TODO
    // START_TRY_SURVEY(new StartTrySurveyCommand()),
    // FINISH_TRY_SURVEY(new FinishTrySurveyCommand()),
    // CANCEL_TRY_SURVEY(new CancelTrySurveyCommand()),
    ;

    private Command command;

    CommandType(Command command){
        this.command = command;
    }

    public static Command define(String commandStr){

        CommandType commandType;
        try {
            commandType = commandStr != null ? CommandType.valueOf(commandStr.toUpperCase()) : DEFAULT;
        } catch (IllegalArgumentException e){
            commandType = DEFAULT;
        }
        return commandType.command;
    }

    public static CommandType defineCommandType(String commandStr) {

        CommandType commandType;
        try {
            commandType = commandStr != null ? CommandType.valueOf(commandStr.toUpperCase()) : DEFAULT;
        } catch (IllegalArgumentException e){
            commandType = DEFAULT;
        }

        return commandType;
    }
}
