package epam.zlatamigas.surveyplatform.controller.command;


import epam.zlatamigas.surveyplatform.controller.command.impl.*;
import epam.zlatamigas.surveyplatform.controller.command.impl.changepassword.*;
import epam.zlatamigas.surveyplatform.controller.command.impl.survey.attempt.FinishSurveyAttemptCommand;
import epam.zlatamigas.surveyplatform.controller.command.impl.survey.attempt.StartSurveyAttemptCommand;
import epam.zlatamigas.surveyplatform.controller.command.impl.survey.question.FinishEditQuestionCommand;
import epam.zlatamigas.surveyplatform.controller.command.impl.survey.question.RemoveQuestionCommand;
import epam.zlatamigas.surveyplatform.controller.command.impl.survey.question.StartEditQuestionCommand;
import epam.zlatamigas.surveyplatform.controller.command.impl.theme.*;
import epam.zlatamigas.surveyplatform.controller.command.impl.survey.*;
import epam.zlatamigas.surveyplatform.controller.command.impl.to.*;
import epam.zlatamigas.surveyplatform.controller.command.impl.user.*;

public enum CommandType {

    // Shared
    DEFAULT(new DefaultCommand()),
    HOME(new HomeCommand()),
    CHANGE_LOCALISATION(new ChangeLocalisationCommand()),

    // User authentication
    SIGN_IN(new StartSignInCommand()),
    FINISH_SIGN_IN(new FinishSignInCommand()),
    SIGN_UP(new StartSignUpCommand()),
    FINISH_SIGN_UP(new FinishSignUpCommand()),
    LOG_OUT(new LogOutCommand()),

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
    EDIT_QUESTION(new StartEditQuestionCommand()),
    FINISH_EDIT_QUESTION(new FinishEditQuestionCommand()),
    REMOVE_QUESTION(new RemoveQuestionCommand()),
    DELETE_SURVEY(new DeleteSurveyCommand()),
    CHANGE_SURVEY_STATUS_CLOSED(new ChangeSurveyStatusClosedCommand()),
    CHANGE_SURVEY_STATUS_STARTED(new ChangeSurveyStatusStartedCommand()),
    RESTART_SURVEY(new RestartSurveyCommand()),

    // Participate in survey
    SURVEY_ATTEMPT(new StartSurveyAttemptCommand()),
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
    EDIT_USER(new StartEditUserCommand()),
    FINISH_EDIT_USER(new FinishEditUserCommand()),
    DELETE_USER(new DeleteUserCommand()),
    CREATE_USER(new StartCreateUserCommand()),
    FINISH_CREATE_USER(new FinishCreateUserCommand()),
    ADMIN_DELETE_SURVEY(new AdminDeleteSurveyCommand()),

    // Change password
    CHANGE_PASSWORD(new StartChangePasswordCommand()),
    FINISH_CHANGE_PASSWORD(new FinishChangePasswordCommand()),
    SEND_KEY(new StartSendKeyToEmailCommand()),
    FINISH_SEND_KEY(new FinishSendKeyToEmailCommand()),
    CONFIRM_KEY(new StartConfirmKeyCommand()),
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
