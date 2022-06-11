package epam.zlatamigas.surveyplatform.controller.command;


import epam.zlatamigas.surveyplatform.controller.command.impl.*;
import epam.zlatamigas.surveyplatform.controller.command.impl.cancel.CancelEditQuestionCommand;
import epam.zlatamigas.surveyplatform.controller.command.impl.cancel.CancelEditSurveyCommand;
import epam.zlatamigas.surveyplatform.controller.command.impl.cancel.CancelSurveyAttemptCommand;
import epam.zlatamigas.surveyplatform.controller.command.impl.finish.FinishSurveyAttemptCommand;
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
    CANCEL_EDIT_SURVEY(new CancelEditSurveyCommand()),
    CANCEL_EDIT_QUESTION(new CancelEditQuestionCommand()),
    DELETE_SURVEY(new DeleteSurveyCommand()),
    // SHOW_DELETE_SURVEY(new ShowDeleteSurveyCommand()),
    // SHOW_DELETE_QUESTION(new ShowDeleteQuestionCommand()),
    // SHOW_DELETE_ANSWER(new ShowDeleteAnswerCommand()),
    // CONFIRM_DELETE_SURVEY(new ConfirmDeleteSurveyCommand()),
    // CONFIRM_DELETE_QUESTION(new ConfirmDeleteQuestionCommand()),
    // CONFIRM_DELETE_ANSWER(new ConfirmDeleteAnswerCommand()),
    CHANGE_SURVEY_STATUS_CLOSED(new ChangeSurveyStatusClosedCommand()),
    CHANGE_SURVEY_STATUS_STARTED(new ChangeSurveyStatusStartedCommand()),

    // Participate in survey
    // TODO
    START_SURVEY_ATTEMPT(new StartSurveyAttemptCommand()),
    FINISH_SURVEY_ATTEMPT(new FinishSurveyAttemptCommand()),
    CANCEL_SURVEY_ATTEMPT(new CancelSurveyAttemptCommand())
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
