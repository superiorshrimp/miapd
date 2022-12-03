package gui;

import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.util.ArrayList;

public class ExpertModule {
    public void start(){

        ArrayList history = new ArrayList();

        Stage expertStage = new Stage();

        expertStage.show();

        expertStage.setOnCloseRequest((WindowEvent we) -> {
            System.exit(0);
        });
    }
}
