package com.ucelebi.automobile.dao;

import com.ucelebi.automobile.model.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.io.Serializable;

@Repository("abstractDao")
public interface AbstractItemDao<T extends Item, ID extends Serializable> extends JpaRepository<T, ID> {
}
