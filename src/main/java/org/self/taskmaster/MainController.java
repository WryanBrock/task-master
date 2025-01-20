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
import javafx.scene.chart.*;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.self.taskmaster.dao.DBConnect;
import org.self.taskmaster.dao.impl.TaskImpl;
import org.self.taskmaster.models.Task;
import org.self.taskmaster.models.WorkLoad;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Time;
import java.util.HashMap;

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
    private StackedBarChart<String, Number> stackedChart;

    @FXML
    private CategoryAxis xAxis;

    @FXML
    private NumberAxis yAxis;

    @FXML
    public PieChart progressPie;
    // PieChart data slices
    @FXML
    private PieChart.Data liteSlice;
    @FXML
    private PieChart.Data modSlice;
    @FXML
    private PieChart.Data hardSlice;
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
                progressPie.getData().clear();
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
        progressPie.getData().clear();
        refresh();

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

        try (Connection connection = DBConnect.getConnection()) {
            tableTasks = new TaskImpl(connection).findAll();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        ObservableList<String> works = FXCollections.observableArrayList();

        for(Task task : tableTasks){
            works.add(task.getWorkload());
        }
        HashMap<String, Integer> map = new HashMap<>();

        for(String item : works){
            if(item.equals("LITE")){
                if(map.get("LITE")==null){
                    map.put("LITE", 0);
                }
                map.put("LITE", map.get("LITE")+1);

            }else if (item.equals("MOD")){
                if(map.get("MOD")==null){
                    map.put("MOD", 0);
                }
                map.put("MOD", map.get("MOD")+1);

            }else if (item.equals("HARD")){
                if(map.get("HARD")==null){
                    map.put("HARD", 0);
                }
                map.put("HARD", map.get("HARD")+1);

            }
        }
        map.putIfAbsent("LITE", 0);
        map.putIfAbsent("MOD", 0);
        map.putIfAbsent("HARD", 0);


        liteSlice = new PieChart.Data("LITE", map.get("LITE"));
        modSlice = new PieChart.Data("MOD", map.get("MOD"));
        hardSlice = new PieChart.Data("HARD", map.get("HARD"));

        if (progressPie != null) {
            progressPie.getData().addAll(liteSlice, modSlice, hardSlice);
        }

        xAxis.setLabel("Categories");
        yAxis.setLabel("Values");

        // Create data series
        XYChart.Series<String, Number> series1 = new XYChart.Series<>();
        series1.setName("2019");
        series1.getData().add(new XYChart.Data<>("A", 10));
        series1.getData().add(new XYChart.Data<>("B", 20));
        series1.getData().add(new XYChart.Data<>("C", 15));

        XYChart.Series<String, Number> series2 = new XYChart.Series<>();
        series2.setName("2020");
        series2.getData().add(new XYChart.Data<>("A", 25));
        series2.getData().add(new XYChart.Data<>("B", 30));
        series2.getData().add(new XYChart.Data<>("C", 20));

        // Add data to chart
        stackedChart.getData().addAll(series1, series2);

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