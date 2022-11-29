package gui;

import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class App extends javafx.application.Application {
    @Override
    public void start(Stage primaryStage) throws Exception{
        primaryStage.show();
        primaryStage.setOnCloseRequest((WindowEvent we) -> {
            System.exit(0);
        });
    }
}