package org.self.taskmaster;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.chart.PieChart;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.self.taskmaster.models.Task;

import java.io.IOException;

public class MainController {

    @FXML
    private Label welcomeText;
    @FXML
    public TableView tableView;
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
    public Label lab1;
    @FXML
    public Label lab3;
    @FXML
    public Label lab2;
    @FXML
    public Label lab5;
    @FXML
    public Label lab4;
    @FXML
    public Label lab6;
    @FXML
    public Label lab7;
    @FXML
    public Label lab8;
    @FXML
    public Label lab9;
    @FXML
    public Label lab10;
    @FXML
    public TextField text2;
    @FXML
    public TextField text1;
    @FXML
    public TextField text3;
    @FXML
    public TextField text4;
    @FXML
    public TextField text5;
    @FXML
    public TextField text6;
    @FXML
    public TextField text7;
    @FXML
    public TextField text8;
    @FXML
    public TextField text9;
    @FXML
    public TextField text10;

    @FXML
    protected void addTask() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("task-window.fxml"));
            Parent root = (Parent) fxmlLoader.load();
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setScene(new Scene(root));
            stage.show();
        } catch (NullPointerException | IOException e) {
            e.printStackTrace();
        }
    }
    @FXML
    protected void deleteTask() {
//        try {
//
//        } catch (NullPointerException | IOException e) {
//            e.printStackTrace();
//        }
    }
//    @FXML
    protected void modifyTask() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("task-window.fxml"));
            Parent root = (Parent) fxmlLoader.load();
//            TaskWindowController controller = fxmlLoader.getController();
//            controller.setWindowTask(task);
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setScene(new Scene(root));
            stage.show();
        } catch (NullPointerException | IOException e) {
            e.printStackTrace();
        }
    }

@FXML
public void initialize() {
    // Initialize PieChart slices
    appleSlice = new PieChart.Data("Apples", 30);
    bananaSlice = new PieChart.Data("Bananas", 25);
    cherrySlice = new PieChart.Data("Cherries", 45);

    // Add slices to PieChart
    progressPie.getData().addAll(appleSlice, bananaSlice, cherrySlice);


}


}


//Software Engineering    C++, Java, Python, JS, C#
//Web Dev HTML, CSS, JS, NodeJs, Python
//App Dev Java, Kotlin, Swift, React Native, FLutter
//Game Dev C++, Java, C#, Unity, Unreal
//AI, ML, Data Science Python, Java, Julia, R, Haskell
//Cyber Linux, Networking, Social, Python, C++