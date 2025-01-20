package org.self.taskmaster;

import com.sun.tools.javac.Main;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.StackedBarChart;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.self.taskmaster.dao.DBConnect;
import org.self.taskmaster.dao.impl.TaskImpl;
import org.self.taskmaster.models.Task;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Time;

public class MainController {

    @FXML
    private Label welcomeText;

    @FXML
    private TableView<Task> taskTable;

    @FXML
    private ObservableList<Task> tableTasks = FXCollections.observableArrayList();

    @FXML
    private TableColumn<Task, Long> id_col;

    @FXML
    private TableColumn<Task, String> name_col;

    @FXML
    private TableColumn<Task, String> work_col;

    @FXML
    private TableColumn<Task, String> desc_col;

    @FXML
    private TableColumn<Task, String> start_col;

    @FXML
    private TableColumn<Task, String> end_col;


    @FXML
    private StackedBarChart<?, ?> stackedChart;



    @FXML
    public PieChart progressPie;
    // PieChart data slices
    @FXML
    private PieChart.Data appleSlice;
    @FXML
    private PieChart.Data bananaSlice;
    @FXML
    private PieChart.Data cherrySlice;
    @FXML
    public Canvas canvas;

    @FXML
    protected void addTask() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("task-window.fxml"));
            Parent root = (Parent) fxmlLoader.load();
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setScene(new Scene(root));
            stage.show();
            stage.setOnHidden(event -> {
                refresh();
            });
        } catch (NullPointerException | IOException e) {
            e.printStackTrace();
        }

    }
    @FXML
    protected void deleteTask() {
        Task task = taskTable.getSelectionModel().getSelectedItem();
        try (Connection connection = DBConnect.getConnection()) {
            new TaskImpl(connection).delete(task.getId());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        taskTable.getItems().remove(task);

    }
    @FXML
    protected void modifyTask() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("task-window.fxml"));
            Parent root = (Parent) fxmlLoader.load();
            TaskWindowController controller = fxmlLoader.getController();
            Task task = taskTable.getSelectionModel().getSelectedItem();
            controller.setWindowTask(task);
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setScene(new Scene(root));
            stage.setOnHidden(event -> {
                taskTable.refresh();
                refresh();
            });
            stage.show();
        } catch (NullPointerException | IOException e) {
            e.printStackTrace();
        }
    }


    @FXML
    public void initialize() {

       loadTasks();

    }
    private void loadTasks() {

        appleSlice = new PieChart.Data("Apples", 30);
        bananaSlice = new PieChart.Data("Bananas", 25);
        cherrySlice = new PieChart.Data("Cherries", 45);

        if (progressPie != null) {
            progressPie.getData().addAll(appleSlice, bananaSlice, cherrySlice);
        }
        try (Connection connection = DBConnect.getConnection()) {
            tableTasks = new TaskImpl(connection).findAll();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        id_col.setCellValueFactory(new PropertyValueFactory<>("id"));
        name_col.setCellValueFactory(new PropertyValueFactory<>("name"));
        work_col.setCellValueFactory(new PropertyValueFactory<>("workload"));
        desc_col.setCellValueFactory(new PropertyValueFactory<>("description"));
        start_col.setCellValueFactory(cellData -> {
            Time startTime = cellData.getValue().getStart_time();
            return new SimpleStringProperty(startTime != null ? startTime.toString() : "");
        });
        end_col.setCellValueFactory(cellData -> {
            Time endTime = cellData.getValue().getEnd_time();
            return new SimpleStringProperty(endTime != null ? endTime.toString() : "");
        });
        taskTable.setItems(tableTasks);
    }
    public void refresh() {
        loadTasks();
    }
}


//Software Engineering    C++, Java, Python, JS, C#
//Web Dev HTML, CSS, JS, NodeJs, Python
//App Dev Java, Kotlin, Swift, React Native, FLutter
//Game Dev C++, Java, C#, Unity, Unreal
//AI, ML, Data Science Python, Java, Julia, R, Haskell
//Cyber Linux, Networking, Social, Python, C++