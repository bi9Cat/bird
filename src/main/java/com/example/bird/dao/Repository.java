package com.example.bird.dao;

import java.util.List;
import java.util.Optional;

public interface Repository<T, ID> {
    T insert(T entity);

    void update(T entity);

    Optional<T> findById(ID id);

    List<T> findAll();

    List<T> findAllById(List<ID> ids);

    long count();

    int deleteById(ID id);

    void deleteAllById(Iterable<? extends ID> ids);
}
