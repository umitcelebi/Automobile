package com.ucelebi.automobile.service;

import com.ucelebi.automobile.model.Item;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

public interface AbstractService<T extends Item, ID extends Serializable> {
    T save(T entity);
    List<T> findAll();
    Page<T> findAll(Pageable pageable);
    Optional<T> findById(ID entityId);
    T update(T entity);
    T updateById(T entity, ID entityId);
    void deleteAll();
    void delete(T entity);
    void deleteById(ID entityId);
}
