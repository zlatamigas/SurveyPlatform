package epam.zlatamigas.surveyplatform.model.dao;

import epam.zlatamigas.surveyplatform.exception.DaoException;
import epam.zlatamigas.surveyplatform.model.entity.AbstractEntity;

import java.util.List;

public interface BaseDao<T extends AbstractEntity> {
    boolean insert(T t) throws DaoException;
    boolean delete(int id) throws DaoException;
    T update(T t) throws DaoException;
    T findById(int id) throws DaoException;
    List<T> findAll() throws DaoException;
}
