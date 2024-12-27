package org.self.taskmaster;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import org.self.taskmaster.dao.DBConnect;
import org.self.taskmaster.dao.impl.TaskImpl;
import org.self.taskmaster.models.Task;
import org.self.taskmaster.models.WorkLoad;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;

import static org.self.taskmaster.functions.Processing.*;

public class TaskWindowController {

    @FXML
    private TextArea definition_field;

    @FXML
    private TextField end_field;

    @FXML
    private CheckBox heavy_check;

    @FXML
    private CheckBox low_check;

    @FXML
    private CheckBox med_check;

    @FXML
    private DatePicker start_date;

    @FXML
    private DatePicker end_date;

    @FXML
    private TextField name_field;

    @FXML
    private TextField start_field;

    @FXML
    private CheckBox start_am_check;

    @FXML
    private CheckBox start_pm_check;

    @FXML
    private CheckBox end_am_check;

    @FXML
    private CheckBox end_pm_check;

    @FXML
    private Button done;

    @FXML
    private Button cancel;

    private Task task;

    @FXML
    void cancel_action(ActionEvent event) {
        Stage stage = (Stage) cancel.getScene().getWindow();
        stage.close();
    }


    @FXML
    void done_action(ActionEvent event) {
        Task task = new Task();
        if (low_check.isSelected() && !med_check.isSelected() && !heavy_check.isSelected()) {
            task.setWorkload(WorkLoad.LITE);
        } else if (!low_check.isSelected() && med_check.isSelected() && !heavy_check.isSelected()) {
            task.setWorkload(WorkLoad.MOD);
        } else if (!low_check.isSelected() && !med_check.isSelected() && heavy_check.isSelected()) {
            task.setWorkload(WorkLoad.HARD);
        }
        task.setName(name_field.getText().trim());
        task.setStart_time(TimeProcessing(start_field.getText()));
        task.setStart_date(DateProcessing(start_date.getValue()));
        task.setEnd_time(TimeProcessing(end_field.getText()));
        task.setEnd_date(DateProcessing(end_date.getValue()));
        task.setDescription(definition_field.getText().trim());
        try {
            Task taskReturn = new TaskImpl(DBConnect.getConnection()).create(task);
        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }
        Stage stage = (Stage) done.getScene().getWindow();
        stage.close();
    }

    @FXML
    void initialize() {
        // Initialize CheckBoxes (checked by default)
        low_check.setSelected(false);
        med_check.setSelected(false);
        heavy_check.setSelected(false);

        start_am_check.setSelected(true);
        start_pm_check.setSelected(false);
        end_am_check.setSelected(true);
        end_pm_check.setSelected(false);

        // Add listeners to each checkbox
        start_am_check.selectedProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue) {
                uncheckOtherStart(start_am_check);
            }
        });
        // Add listeners to each checkbox
        start_pm_check.selectedProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue) {
                uncheckOtherStart(start_pm_check);
            }
        });


        // Add listeners to each checkbox
        end_am_check.selectedProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue) {
                uncheckOtherEnd(end_am_check);
            }
        });
        // Add listeners to each checkbox
        end_pm_check.selectedProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue) {
                uncheckOtherEnd(end_pm_check);
            }
        });


        // Add listeners to each checkbox
        low_check.selectedProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue) {
                uncheckOtherEnums(low_check);
            }
        });

        med_check.selectedProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue) {
                uncheckOtherEnums(med_check);
            }
        });

        heavy_check.selectedProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue) {
                uncheckOtherEnums(heavy_check);
            }
        });

    }

    private void uncheckOtherEnums(CheckBox selectedCheckBox) {
        if (selectedCheckBox != low_check) {
            low_check.setSelected(false);
        }
        if (selectedCheckBox != med_check) {
            med_check.setSelected(false);
        }
        if (selectedCheckBox != heavy_check) {
            heavy_check.setSelected(false);
        }
    }


    private void uncheckOtherStart(CheckBox selectedCheckBox) {
        if (selectedCheckBox != start_am_check) {
            start_am_check.setSelected(false);
        }
        if (selectedCheckBox != start_pm_check) {
            start_pm_check.setSelected(false);
        }

    }


    private void uncheckOtherEnd(CheckBox selectedCheckBox) {
        if (selectedCheckBox != end_am_check) {
            end_am_check.setSelected(false);
        }
        if (selectedCheckBox != end_pm_check) {
            end_pm_check.setSelected(false);
        }

    }
//    public void setWindowTask(Task task) {
//        this.task = task;
//        updateUI();
//    }
//
//    // Update the UI with task details
//    private void updateUI() {
//        if (task != null) {
//            name_field.setText(task.getName());
//
//            if (low_check.isSelected() && !med_check.isSelected() && !heavy_check.isSelected()) {
//                task.setWorkload(WorkLoad.LITE);
//            } else if (!low_check.isSelected() && med_check.isSelected() && !heavy_check.isSelected()) {
//                task.setWorkload(WorkLoad.MOD);
//            } else if (!low_check.isSelected() && !med_check.isSelected() && heavy_check.isSelected()) {
//                task.setWorkload(WorkLoad.HARD);
//            }
//
//            String start_time = "", start_da = "", end_time = "", end_da = "";
//
//            String[] newStartDate = start_date.getValue().toString().split("-");
//            String[] newEndDate = end_date.getValue().toString().split("-");
//
//            int x, y, z , a, b, c= 0;
//
//            x = Integer.parseInt(newStartDate[0]);
//            y = Integer.parseInt(newStartDate[1]);
//            z = Integer.parseInt(newStartDate[2]);
//
//            start_field.setText(start_time);
//            start_date.setValue(LocalDate.of(x,y,z));
//
//            a = Integer.parseInt(newEndDate[0]);
//            b = Integer.parseInt(newEndDate[1]);
//            c = Integer.parseInt(newEndDate[2]);
//
//            end_field.setText(end_time);
//            end_date.setValue(LocalDate.of(a,b,c));
//
//
//            definition_field.setText(task.getDescription());
//        }
//    }

}
