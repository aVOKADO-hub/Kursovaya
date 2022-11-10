package org.example.demo3;


import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
public class HelloApplication extends Application {
    private static final int sceneHeight = 1280;
    private static final int sceneWidth = 720;
    @Override
    public void start(Stage stage) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/login.fxml"));
        stage.setTitle("Java File Manager");
        stage.setScene(new Scene(root));
        stage.show();
        if (Login.getIsLoginWindow()==false){
            stage.close();
            Stage stageMain = new Stage();
            Parent rootMain = FXMLLoader.load(getClass().getResource("/main.fxml"));
            stageMain.setTitle("Java File Manager");
            stageMain.setScene(new Scene(rootMain,sceneHeight,sceneWidth));
            stageMain.show();
        }

    }

    public static void main(String[] args)   {
        launch();
    }
    public static int getSceneHeight() {
        return sceneHeight;
    }

    public static int getSceneWidth() {
        return sceneWidth;
    }
}