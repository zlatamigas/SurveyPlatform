package epam.zlatamigas.surveyplatform.model.dao;

import epam.zlatamigas.surveyplatform.exception.DaoException;
import epam.zlatamigas.surveyplatform.model.entity.Theme;
import epam.zlatamigas.surveyplatform.model.entity.ThemeStatus;

import java.util.List;
import java.util.Optional;

public interface ThemeDao {
    Optional<Theme> findByName(String themeName) throws DaoException;

    List<Theme> findWithThemeStatus(ThemeStatus themeStatus) throws DaoException;
    boolean updateThemeStatus(int themeId, ThemeStatus themeStatus) throws DaoException;
}
