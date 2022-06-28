package epam.zlatamigas.surveyplatform.model.dao;

import epam.zlatamigas.surveyplatform.exception.DaoException;
import epam.zlatamigas.surveyplatform.model.entity.Theme;
import epam.zlatamigas.surveyplatform.model.entity.ThemeStatus;

import java.util.List;

public interface ThemeDao {
    List<Theme> findWithThemeStatus(ThemeStatus themeStatus) throws DaoException;
    boolean updateThemeStatus(int themeId, ThemeStatus themeStatus) throws DaoException;
}
