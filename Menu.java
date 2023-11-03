package com.example.weatherapp;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.File;

public class Menu extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        Pane pane = FXMLLoader.load(new File("C:\\Users\\User\\IdeaProjects\\WeatherApp\\src\\main\\java\\com\\example\\weatherapp\\weather.fxml").toURI().toURL());
        Scene scene = new Scene(pane);
        stage.setScene(scene);
        stage.show();
    }
}
