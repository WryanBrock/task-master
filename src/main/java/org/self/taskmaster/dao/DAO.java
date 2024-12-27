package org.self.taskmaster.dao;

import javafx.collections.ObservableList;

import java.sql.*;
import java.sql.Connection;
import java.sql.SQLException;


public abstract class DAO<T> {
    protected final Connection connection;

    /**
     * Constructor used to initialize a DataAccessObject
     *
     * @param connection sets connection
     */
    public DAO(Connection connection) throws SQLException {
        super();
        this.connection = connection;
    }

    /**
     * This abstract method is used to find a unique object by an id
     *
     * @param id sets id
     * @return a generic object
     */
    public abstract T findById(long id);

    /**
     * This abstract method is used retrieve a list of a generic object
     *
     * @return a generic list objects
     */
    public abstract ObservableList<T> findAll();

    /**
     * This abstract method is used to update a generic object
     *
     * @param data sets data
     * @return a generic object
     */
    public abstract T update(T data);

    /**
     * This abstract method is used to create a generic object
     *
     * @param data sets data
     * @return a generic object
     */
    public abstract T create(T data);

    /**
     * This abstract method is used to delete a generic object
     *
     * @param id sets id
     */
    public abstract void delete(long id);


}
