package epam.zlatamigas.surveyplatform.controller.command;


import epam.zlatamigas.surveyplatform.controller.command.impl.*;
import epam.zlatamigas.surveyplatform.controller.command.impl.changepassword.*;
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

    // List started surveys
    SURVEYS(new SurveysCommand()),

    // List created surveys of specified user (for survey creator)
    USER_SURVEYS(new UserSurveysCommand()),

    // List users and themes data (admin)
    USERS(new UsersCommand()),
    THEMES_CONFIRMED(new ThemesConfirmedCommand()),
    THEMES_WAITING(new ThemesWaitingCommand()),

    // CRUD survey and its parts (for survey creator)
    START_EDIT_SURVEY(new StartEditSurveyCommand()),
    EDIT_SURVEY(new EditSurveyCommand()),
    FINISH_EDIT_SURVEY(new FinishEditSurveyCommand()),
    START_EDIT_QUESTION(new StartEditQuestionCommand()),
    FINISH_EDIT_QUESTION(new FinishEditQuestionCommand()),
    REMOVE_QUESTION(new RemoveQuestionCommand()),
    DELETE_SURVEY(new DeleteSurveyCommand()),
    CHANGE_SURVEY_STATUS_CLOSED(new ChangeSurveyStatusClosedCommand()),
    CHANGE_SURVEY_STATUS_STARTED(new ChangeSurveyStatusStartedCommand()),
    RESTART_SURVEY(new RestartSurveyCommand()),

    // Participate in survey
    START_SURVEY_ATTEMPT(new StartSurveyAttemptCommand()),
    FINISH_SURVEY_ATTEMPT(new FinishSurveyAttemptCommand()),

    // View survey result (for survey creator)
    SURVEY_RESULT(new SurveyResultCommand()),

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
    DELETE_USER(new DeleteUserCommand()),
    START_CREATE_USER(new StartCreateUserCommand()),
    FINISH_CREATE_USER(new FinishCreateUserCommand()),
    ADMIN_DELETE_SURVEY(new AdminDeleteSurveyCommand()),

    // Change password
    START_CHANGE_PASSWORD(new StartChangePasswordCommand()),
    FINISH_CHANGE_PASSWORD(new FinishChangePasswordCommand()),
    START_SEND_KEY_ON_EMAIL(new StartSendKeyOnEmailCommand()),
    FINISH_SEND_KEY_ON_EMAIL(new FinishSendKeyOnEmailCommand()),
    START_CONFIRM_KEY(new StartConfirmKeyCommand()),
    FINISH_CONFIRM_KEY(new FinishConfirmKeyCommand())
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
