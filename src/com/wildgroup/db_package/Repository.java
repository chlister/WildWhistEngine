package com.wildgroup.db_package;

import com.wildgroup.user_package.models.User;

/**
 * @author Marc Rohwedder Kær
 * @date 29-01-2019
 */
public interface Repository<T extends User> {
    void add(T entity);
    void update(T entity);
    void delete(T entity);
}
