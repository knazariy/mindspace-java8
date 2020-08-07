package com.nklymok.mindspace.controller;

import com.nklymok.mindspace.model.TaskModel;
import com.nklymok.mindspace.view.effect.BlurEffect;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.time.format.DateTimeFormatter;

public class RecentItemPaneController {
    private final String DUE_PREFIX = "Due: ";
    private TaskModel model;

    @FXML
    private Button removeButton;
    @FXML
    private Button editButton;
    @FXML
    private Text headerText;
    @FXML
    private Text dueText;
    @FXML
    private PriorityPaneController priorityPane;

    EventHandler<ActionEvent> removeButtonHandler = event -> {
        TaskManagerController.getInstance().removeByModel(model);
    };

    EventHandler<ActionEvent> editButtonHandler = event -> edit();

    public RecentItemPaneController(TaskModel model) {
        this.model = model;
    }

    public void initialize() {
        updateFields();
        removeButton.setOnAction(removeButtonHandler);
        editButton.setOnAction(editButtonHandler);
    }

    public void edit() {
        FXMLLoader editStageFXMLLoader = new FXMLLoader(getClass().getResource("/fxmls/edit-stage.fxml"));
        EditStageController editStageController = new EditStageController(model);
        editStageFXMLLoader.setController(editStageController);

        Stage stage = new Stage(StageStyle.TRANSPARENT);
        try {
            Scene editScene = new Scene(editStageFXMLLoader.load());
            editScene.setFill(Color.TRANSPARENT);
            stage.setScene(editScene);
            stage.setOnHidden(event -> {

            });
            stage.setOnShown(event -> BlurEffect.getInstance().blur());
            stage.setOnHidden(event -> BlurEffect.getInstance().unblur());
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void updateFields() {
        updateHeaderText();
        updateDueText();
        updatePriority();
    }

    private void updateHeaderText() {
        headerText.setText(model.getHeader());
    }

    private void updateDueText() {
        dueText.setText(DUE_PREFIX + model.getDueDate().format(DateTimeFormatter.ofPattern("dd MMM uuuu HH:mm")));
    }

    private void updatePriority() {
        priorityPane.setPriority(model.getPriority());
    }

    public TaskModel getModel() {
        return model;
    }

    //TODO actionlisteners for buttons, removing task and recent from map
}
