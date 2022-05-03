package epam.zlatamigas.surveyplatform.service;

import epam.zlatamigas.surveyplatform.exception.ServiceException;
import epam.zlatamigas.surveyplatform.model.entity.Theme;

import java.util.List;

public interface ThemeService {
    List<Theme> findAll() throws ServiceException;

    List<Theme> findAllConfirmed() throws ServiceException;
}
