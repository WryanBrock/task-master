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
import java.time.Month;
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
                stackedChart.getData().clear();
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
        stackedChart.getData().clear();
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
    protected void markComplete() {
        Task task = taskTable.getSelectionModel().getSelectedItem();
        task.setCompleted(true);
        try (Connection connection = DBConnect.getConnection()) {
            new TaskImpl(connection).update(task);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        taskTable.getItems().remove(task);
        progressPie.getData().clear();
        stackedChart.getData().clear();
        refresh();
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
        HashMap<String, Integer> pieMap = new HashMap<>();

        for(String item : works){
            if(item.equals("LITE")){
                if(pieMap.get("LITE")==null){
                    pieMap.put("LITE", 0);
                }
                pieMap.put("LITE", pieMap.get("LITE")+1);

            }else if (item.equals("MOD")){
                if(pieMap.get("MOD")==null){
                    pieMap.put("MOD", 0);
                }
                pieMap.put("MOD", pieMap.get("MOD")+1);

            }else if (item.equals("HARD")){
                if(pieMap.get("HARD")==null){
                    pieMap.put("HARD", 0);
                }
                pieMap.put("HARD", pieMap.get("HARD")+1);

            }
        }
        pieMap.putIfAbsent("LITE", 0);
        pieMap.putIfAbsent("MOD", 0);
        pieMap.putIfAbsent("HARD", 0);

        liteSlice = new PieChart.Data("LITE", pieMap.get("LITE"));
        modSlice = new PieChart.Data("MOD", pieMap.get("MOD"));
        hardSlice = new PieChart.Data("HARD", pieMap.get("HARD"));

        if (progressPie != null) {
            progressPie.getData().addAll(liteSlice, modSlice, hardSlice);
        }







        xAxis.setLabel("Time");
        yAxis.setLabel("Task Quantity");
        xAxis.setCategories(FXCollections.observableArrayList(
                "Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"
        ));
        ObservableList<Task> stackedStarted = FXCollections.observableArrayList();
        ObservableList<Task> stackedFinished = FXCollections.observableArrayList();

        for(Task task: tableTasks){
            if(task.getCompleted()){
                stackedFinished.add(task);
            }else{
                stackedStarted.add(task);
            }
        }
//        ObservableList<Task> finished = FXCollections.observableArrayList();
//        ObservableList<Task> started = FXCollections.observableArrayList();
        HashMap<String, Integer> finished = new HashMap<>();
        HashMap<String, Integer> started = new HashMap<>();

        finished.put("Jan",0);
        finished.put("Feb",0);
        finished.put("Mar",0);
        finished.put("Apr",0);
        finished.put("May",0);
        finished.put("Jun",0);
        finished.put("Jul",0);
        finished.put("Aug",0);
        finished.put("Sep",0);
        finished.put("Oct",0);
        finished.put("Nov",0);
        finished.put("Dec",0);



        started.put("Jan",0);
        started.put("Feb",0);
        started.put("Mar",0);
        started.put("Apr",0);
        started.put("May",0);
        started.put("Jun",0);
        started.put("Jul",0);
        started.put("Aug",0);
        started.put("Sep",0);
        started.put("Oct",0);
        started.put("Nov",0);
        started.put("Dec",0);

        for(Task task: stackedStarted){

            if(task.getStart_date().toLocalDate().getMonth()== Month.JANUARY){
                started.put("Jan", started.get("Jan")+1);
            }
            if(task.getStart_date().toLocalDate().getMonth()== Month.FEBRUARY){
                started.put("Feb", started.get("Feb")+1);
            }
            if(task.getStart_date().toLocalDate().getMonth()== Month.MARCH){
                started.put("Mar", started.get("Mar")+1);
            }
            if(task.getStart_date().toLocalDate().getMonth()== Month.APRIL){
                started.put("Apr", started.get("Apr")+1);
            }
            if(task.getStart_date().toLocalDate().getMonth()== Month.MAY){
                started.put("May", started.get("May")+1);
            }
            if(task.getStart_date().toLocalDate().getMonth()== Month.JUNE){
                started.put("Jun", started.get("Jun")+1);
            }
            if(task.getStart_date().toLocalDate().getMonth()== Month.JULY){
                started.put("Jul", started.get("Jul")+1);
            }
            if(task.getStart_date().toLocalDate().getMonth()== Month.AUGUST){
                started.put("Aug", started.get("Aug")+1);
            }
            if(task.getStart_date().toLocalDate().getMonth()== Month.SEPTEMBER){
                started.put("Sep", started.get("Sep")+1);
            }
            if(task.getStart_date().toLocalDate().getMonth()== Month.OCTOBER){
                started.put("Oct", started.get("Oct")+1);
            }
            if(task.getStart_date().toLocalDate().getMonth()== Month.NOVEMBER){
                started.put("Nov", started.get("Nov")+1);
            }
            if(task.getStart_date().toLocalDate().getMonth()== Month.DECEMBER){
                started.put("Dec", started.get("Dec")+1);
            }

        }
        for(Task task: stackedFinished){
            if(task.getStart_date().toLocalDate().getMonth()== Month.JANUARY){
                finished.put("Jan", finished.get("Jan")+1);
            }
            if(task.getStart_date().toLocalDate().getMonth()== Month.FEBRUARY){
                finished.put("Feb", finished.get("Feb")+1);
            }
            if(task.getStart_date().toLocalDate().getMonth()== Month.MARCH){
                finished.put("Mar", finished.get("Mar")+1);
            }
            if(task.getStart_date().toLocalDate().getMonth()== Month.APRIL){
                finished.put("Apr", finished.get("Apr")+1);
            }
            if(task.getStart_date().toLocalDate().getMonth()== Month.MAY){
                finished.put("May", finished.get("May")+1);
            }
            if(task.getStart_date().toLocalDate().getMonth()== Month.JUNE){
                finished.put("Jun", finished.get("Jun")+1);
            }
            if(task.getStart_date().toLocalDate().getMonth()== Month.JULY){
                finished.put("Jul", finished.get("Jul")+1);
            }
            if(task.getStart_date().toLocalDate().getMonth()== Month.AUGUST){
                finished.put("Aug", finished.get("Aug")+1);
            }
            if(task.getStart_date().toLocalDate().getMonth()== Month.SEPTEMBER){
                finished.put("Sep", finished.get("Sep")+1);
            }
            if(task.getStart_date().toLocalDate().getMonth()== Month.OCTOBER){
                finished.put("Oct", finished.get("Oct")+1);
            }
            if(task.getStart_date().toLocalDate().getMonth()== Month.NOVEMBER){
                finished.put("Nov", finished.get("Nov")+1);
            }
            if(task.getStart_date().toLocalDate().getMonth()== Month.DECEMBER){
                finished.put("Dec", finished.get("Dec")+1);
            }
        }

        XYChart.Series<String, Number> series1 = new XYChart.Series<>();
        series1.setName("Started");


        for (String key : started.keySet()) {
            series1.getData().add(new XYChart.Data<>(key, started.get(key))); // Add data point to series
        }


        XYChart.Series<String, Number> series2 = new XYChart.Series<>();
        series2.setName("Finished");

        for (String key : finished.keySet()) {
            series2.getData().add(new XYChart.Data<>(key, finished.get(key))); // Add data point to series
        }
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


        taskTable.setRowFactory(tv -> new TableRow<Task>() {
            @Override
            protected void updateItem(Task item, boolean empty) {
                super.updateItem(item, empty);
                if (item == null || empty) {
                    setStyle("");
                } else if (item.getCompleted()) {
                    setStyle("-fx-background-color: lightgreen;");
                } else {
                    setStyle("");
                }
            }
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