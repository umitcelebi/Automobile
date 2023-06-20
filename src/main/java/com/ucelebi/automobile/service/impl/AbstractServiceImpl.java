package com.ucelebi.automobile.service.impl;

import com.ucelebi.automobile.dao.AbstractItemDao;
import com.ucelebi.automobile.model.Item;
import com.ucelebi.automobile.service.AbstractService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

@Service
public class AbstractServiceImpl<T extends Item, ID extends Serializable> implements AbstractService<T, ID> {

    public static Logger log = Logger.getLogger(AbstractServiceImpl.class);

    @Qualifier("abstractDao")
    private final AbstractItemDao<T, ID> abstractDao;

    @Autowired
    public AbstractServiceImpl(AbstractItemDao<T, ID> abstractDao) {
        this.abstractDao = abstractDao;
    }

    @Override
    public T save(T entity) {
        return abstractDao.save(entity);
    }

    @Override
    public List<T> findAll() {
        return abstractDao.findAll();
    }

    @Override
    public Page<T> findAll(Pageable pageable) {
        return abstractDao.findAll(pageable);
    }

    @Override
    public Optional<T> findById(ID entityId) {
        return abstractDao.findById(entityId);
    }

    @Override
    public T update(T entity) {
        return abstractDao.save(entity);
    }

    @Override
    public T updateById(T entity, ID entityId) {
        Optional<T> getById = abstractDao.findById(entityId);
        if (getById.isEmpty()) {
            return entity;
        }
        return null;
    }

    @Transactional
    @Override
    public void deleteAll() {
        try {
            abstractDao.deleteAll();
        } catch (Exception e) {
            log.error("Error while deleting all entities.",e);
        }
    }

    @Transactional
    @Override
    public void delete(T entity) {
        try {
            abstractDao.delete(entity);
        }catch (Exception e) {
            log.error("Error while deleting entity. {}",e);
        }
    }

    @Transactional
    @Override
    public void deleteById(ID entityId) {
        try {
            abstractDao.deleteById(entityId);
        } catch (Exception e) {
            log.error("Error while deleting entity by id. {}",e);
        }
    }
}
