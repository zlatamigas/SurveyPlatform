package epam.zlatamigas.surveyplatform.model.dao;

import epam.zlatamigas.surveyplatform.exception.DaoException;
import epam.zlatamigas.surveyplatform.model.entity.Theme;

import java.util.List;

public interface ThemeDao {
    List<Theme> findAllConfirmed() throws DaoException;

}
