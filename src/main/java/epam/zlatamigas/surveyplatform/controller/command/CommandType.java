package epam.zlatamigas.surveyplatform.controller.command;


import epam.zlatamigas.surveyplatform.controller.command.impl.*;
import epam.zlatamigas.surveyplatform.controller.command.impl.cancel.*;
import epam.zlatamigas.surveyplatform.controller.command.impl.finish.*;
import epam.zlatamigas.surveyplatform.controller.command.impl.samepage.*;
import epam.zlatamigas.surveyplatform.controller.command.impl.to.*;
import epam.zlatamigas.surveyplatform.controller.command.impl.start.*;

public enum CommandType {

    // Shared
    DEFAULT(new DefaultCommand()),
    HOME(new HomeCommand()),
    CHANGE_LOCALISATION(new ChangeLocalisationCommand()),

    // User authentication
    START_SIGN_IN(new StartSignInCommand()),
    FINISH_SIGN_IN(new FinishSignInCommand()),
    START_SIGN_UP(new StartSignUpCommand()),
    FINISH_SIGN_UP(new FinishSignUpCommand()),
    LOGOUT(new LogOutCommand()),

    // Change password
    TO_FORGOT_PASSWORD(new ToForgotPasswordCommand()),
    SEND_FORGOTTEN_PASSWORD_KEY(new SendForgottenPasswordKeyCommand()),
    CONFIRM_CHANGE_PASSWORD_KEY(new ConfirmPasswordKeyCommand()),
    CHANGE_PASSWORD(new ChangePasswordCommand()),

    // List started surveys
    LIST_SURVEYS(new ListSurveysCommand()),
    SEARCH_SURVEYS(new SearchSurveysCommand()),

    // List created surveys of specified user (for survey creator)
    LIST_USER_CREATED_SURVEYS(new ListUserCreatedSurveysCommand()),
    SEARCH_USER_CREATED_SURVEYS(new SearchUserCreatedSurveysCommand()),

    // List users and themes data (admin)
    LIST_USERS(new ListUsersCommand()),
    SEARCH_USERS(new SearchUsersCommand()),
    LIST_THEMES_CONFIRMED(new ListThemesConfirmedCommand()),
    SEARCH_THEMES_CONFIRMED(new SearchThemesConfirmedCommand()),
    LIST_THEMES_WAITING(new ListThemesWaitingCommand()),

    // CRUD survey and its parts (for survey creator)
    START_EDIT_SURVEY(new StartEditSurveyCommand()),
    FINISH_EDIT_SURVEY(new FinishEditSurveyCommand()),
    START_EDIT_QUESTION(new StartEditQuestionCommand()),
    FINISH_EDIT_QUESTION(new FinishEditQuestionCommand()),
    REMOVE_QUESTION(new RemoveQuestionCommand()),
    CANCEL_EDIT_SURVEY(new CancelEditSurveyCommand()),
    CANCEL_EDIT_QUESTION(new CancelEditQuestionCommand()),
    DELETE_SURVEY(new DeleteSurveyCommand()),
    CHANGE_SURVEY_STATUS_CLOSED(new ChangeSurveyStatusClosedCommand()),
    CHANGE_SURVEY_STATUS_STARTED(new ChangeSurveyStatusStartedCommand()),

    // Participate in survey
    START_SURVEY_ATTEMPT(new StartSurveyAttemptCommand()),
    FINISH_SURVEY_ATTEMPT(new FinishSurveyAttemptCommand()),
    CANCEL_SURVEY_ATTEMPT(new CancelSurveyAttemptCommand()),

    // View survey result (for survey creator)
    VIEW_SURVEY_RESULT(new ViewSurveyResultCommand()),

    // CRUD themes (for admin)
    CONFIRM_THEME(new ConfirmThemeCommand()),
    REJECT_THEME(new RejectThemeCommand()),
    DELETE_THEME(new DeleteThemeCommand()),
    ADD_THEME(new AddThemeCommand()),

    // Go to user account page with common user info (for registered user)
    USER_ACCOUNT(new UserAccountCommand()),

    // CRUD user (for admin)
    START_EDIT_USER(new StartEditUserCommand()),
    FINISH_EDIT_USER(new FinishEditUserCommand()),
    CANCEL_EDIT_USER(new CancelEditUserCommand()),
    START_CREATE_USER(new StartCreateUserCommand()),
    FINISH_CREATE_USER(new FinishCreateUserCommand()),
    CANCEL_CREATE_USER(new CancelCreateUserCommand())
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
