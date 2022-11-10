package org.example.demo3;


import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;


public class HelloController {
    @FXML
    VBox leftPanel,rightPanel;
    public void btnExitAction() {
        Platform.exit();
    }

    public void copyBtnAction() {
        PanelController leftP = (PanelController) leftPanel.getProperties().get("ctrl");
        PanelController rightP = (PanelController) rightPanel.getProperties().get("ctrl");

        if (leftP.getSelectedFileName()==null&&rightP.getSelectedFileName()==null){
            Alert alert = new Alert(Alert.AlertType.ERROR,"None file been checked", ButtonType.OK);
            alert.showAndWait();
            return;
        }

        PanelController source = null,destination =null;
        if (leftP.getSelectedFileName()!=null){
            source  = leftP;
            destination = rightP;
        }
        if (rightP.getSelectedFileName()!=null){
            source = rightP;
            destination = leftP;
        }

        Path sourcePath = Paths.get(source.getCurrentPath(),source.getSelectedFileName());
        Path destinationPath = Paths.get(destination.getCurrentPath()).resolve(sourcePath.getFileName().toString());

        try {
            Files.copy(sourcePath,destinationPath);
            destination.updateList(Paths.get(destination.getCurrentPath()));
        } catch (IOException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR,"Can`t copy checked file", ButtonType.OK);
            alert.showAndWait();
        }
    }

    public void moveBtnAction() {
        PanelController leftP = (PanelController) leftPanel.getProperties().get("ctrl");
        PanelController rightP = (PanelController) rightPanel.getProperties().get("ctrl");

        if (leftP.getSelectedFileName()==null&&rightP.getSelectedFileName()==null){
            Alert alert = new Alert(Alert.AlertType.ERROR,"None file been checked", ButtonType.OK);
            alert.showAndWait();
            return;
        }

        PanelController source = null,destination =null;
        if (leftP.getSelectedFileName()!=null){
            source  = leftP;
            destination = rightP;
        }
        if (rightP.getSelectedFileName()!=null){
            source = rightP;
            destination = leftP;
        }

        Path sourcePath = Paths.get(source.getCurrentPath(),source.getSelectedFileName());
        Path destinationPath = Paths.get(destination.getCurrentPath()).resolve(sourcePath.getFileName().toString());

        try {
            Files.move(sourcePath,destinationPath);
            destination.updateList(Paths.get(destination.getCurrentPath()));
        } catch (IOException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR,"Can`t move checked file", ButtonType.OK);
            alert.showAndWait();
        }
    }
    public void deleteBtnAction(){
        PanelController leftP = (PanelController) leftPanel.getProperties().get("ctrl");
        PanelController rightP = (PanelController) rightPanel.getProperties().get("ctrl");

        if (leftP.getSelectedFileName()==null&&rightP.getSelectedFileName()==null){
            Alert alert = new Alert(Alert.AlertType.ERROR,"None file been checked", ButtonType.OK);
            alert.showAndWait();
            return;
        }

        PanelController source;
        if (leftP.getSelectedFileName()!=null){
            source  = leftP;
        }else{
            source=rightP;
        }

        Path sourcePath = Paths.get(source.getCurrentPath(),source.getSelectedFileName());

        try {
            Files.delete(sourcePath);
        } catch (IOException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR,"Can`t delete checked file", ButtonType.OK);
            alert.showAndWait();
        }
    }
}