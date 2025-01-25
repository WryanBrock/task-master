package org.self.taskmaster.dao.impl;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.self.taskmaster.dao.DAO;
import org.self.taskmaster.models.Task;

import java.sql.*;
import java.time.ZoneId;

public class TaskImpl extends DAO<Task> {
    private static final String INSERT = "INSERT INTO task( name, start_time, start_date, end_time, end_date, workload, description, completed) values (?,?,?,?,?,?,?,?);";
    private static final String GET_ALL = "SELECT id, name, start_time, start_date, end_time, end_date, workload, description , completed FROM task";
    private static final String GET_ONE = "SELECT id, name, start_time, start_date, end_time, end_date, workload, description, completed FROM task where id = ?";
    private static final String DELETE = "DELETE FROM task where id = ?";
    private static final String UPDATE = "UPDATE task SET name = ?, start_time = ?, start_date = ?, end_time = ?, end_date = ?, workload = ?, description = ?, completed = ? WHERE id = ?";
    private static final String LAST_ONE = "SELECT id FROM task ORDER BY id DESC LIMIT 1";


    /**
     * Constructor used to initialize a DataAccessObject
     *
     * @param connection sets connection
     */
    public TaskImpl(Connection connection) throws SQLException {
        super(connection);
    }

    @Override
    public Task findById(long id) {
        Task task = new Task();
        try (PreparedStatement statement = this.connection.prepareStatement(GET_ONE)) {
            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                task.setId(resultSet.getLong("id"));
                task.setName(resultSet.getString("name"));
                task.setStart_time(resultSet.getTime("start_time"));
                task.setStart_date(resultSet.getDate("start_date"));
                task.setEnd_time(resultSet.getTime("end_time"));
                task.setEnd_date(resultSet.getDate("end_date"));
                task.setWorkload(resultSet.getString("workload").toUpperCase());
                task.setDescription(resultSet.getString("description"));
                task.setCompleted(resultSet.getBoolean("completed"));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return task;
    }

    @Override
    public ObservableList<Task> findAll() {
        ObservableList<Task> tasks = FXCollections.observableArrayList();
        try (PreparedStatement statement = this.connection.prepareStatement(GET_ALL)) {
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Task task = new Task();
                task.setId(resultSet.getLong("id"));
                task.setName(resultSet.getString("name"));
                task.setStart_time(resultSet.getTime("start_time"));
                task.setStart_date(resultSet.getDate("start_date"));
                task.setEnd_time(resultSet.getTime("end_time"));
                task.setEnd_date(resultSet.getDate("end_date"));
                task.setWorkload(resultSet.getString("workload").toUpperCase());
                task.setDescription(resultSet.getString("description"));
                task.setCompleted(resultSet.getBoolean("completed"));

                tasks.add(task);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return tasks;
    }

    @Override
    public Task update(Task data) {
        System.out.println(data);
        try (PreparedStatement statement = this.connection.prepareStatement(UPDATE)) {
            statement.setString(1, data.getName());
            statement.setTime(2, data.getStart_time());
            statement.setDate(3, data.getStart_date());
            statement.setTime(4, data.getEnd_time());
            statement.setDate(5, data.getEnd_date());
            statement.setString(6, data.getWorkload());
            statement.setString(7, data.getDescription());
            statement.setBoolean(8, data.getCompleted());
            statement.setLong(9, data.getId());
            statement.executeUpdate();
        }catch (Exception e) {
            e.printStackTrace();
        }
        return data;
    }

    @Override
    public Task create(Task data) {
        try {
            PreparedStatement statement = this.connection.prepareStatement(INSERT);
            statement.setString(1, data.getName());
            statement.setTime(2, data.getStart_time());
            statement.setDate(3, data.getStart_date());
            statement.setTime(4, data.getEnd_time());
            statement.setDate(5, data.getEnd_date());
            statement.setString(6, data.getWorkload());
            statement.setString(7, data.getDescription());
            statement.setBoolean(8, data.getCompleted());

            statement.execute();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return data;
    }

    @Override
    public void delete(long id) {
        try (PreparedStatement statement = this.connection.prepareStatement(DELETE)) {
            statement.setLong(1, id);
            int value = statement.executeUpdate();
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
