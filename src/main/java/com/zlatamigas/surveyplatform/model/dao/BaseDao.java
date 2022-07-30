package com.zlatamigas.surveyplatform.model.dao;

import com.zlatamigas.surveyplatform.exception.DaoException;
import com.zlatamigas.surveyplatform.model.entity.AbstractEntity;

import java.util.Optional;

/**
 * Base DAO.
 */
public interface BaseDao<T extends AbstractEntity> {

    boolean insert(T t) throws DaoException;

    boolean delete(int id) throws DaoException;

    Optional<T> findById(int id) throws DaoException;

    boolean update(T t) throws DaoException;
}
