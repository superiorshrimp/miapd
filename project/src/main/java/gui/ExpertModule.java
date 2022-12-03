package gui;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.util.ArrayList;

public class ExpertModule {

    ArrayList<Label> labels = new ArrayList<>();
    Application app;
    int stageNumber = 0;
    public ExpertModule(ArrayList<String> labels, Application app){
        for ( String label : labels){
            this.labels.add(new Label(label));
            this.app = app;
        }
    }

        public void start(String label){

        ArrayList<VBox> vBoxes = new ArrayList<>();

        for (int i = 0; i < labels.size(); i++){
            if (!labels.get(i).getText().equals(label)){
                ChoiceBox<Integer> cb = new ChoiceBox<>(FXCollections.observableArrayList(
                        1, 2, 3, 4, 5, 6, 7, 8, 9
                ));
                VBox vBox = new VBox(labels.get(i), cb);
                vBox.setAlignment(Pos.CENTER);
                vBox.setPadding(new Insets(10, 10, 10, 10));
                vBoxes.add(vBox);
            }
        }

        HBox hBox = new HBox();
        for (VBox vBox : vBoxes){
            hBox.getChildren().add(vBox);
        }
        Button nextButton = new Button("Next");
        Button previousButton = new Button("Previous");
        Label comparisonValue = new Label("You are comparing to " + label);

        FlowPane flowPane = new FlowPane(comparisonValue, hBox, nextButton, previousButton);

        flowPane.setAlignment(Pos.CENTER);
        Scene scene = new Scene(flowPane);

        Stage expertStage = new Stage();
        expertStage.setScene(scene);
        expertStage.show();

        expertStage.setOnCloseRequest((WindowEvent we) -> {
            System.exit(0);
        });

        nextButton.setOnAction(event -> {
            expertStage.close();
            this.stageNumber += 1;
            if (this.stageNumber >= labels.size()){
                try {
                    app.start(new Stage());
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
            else
                this.start(labels.get(stageNumber).getText());
        });

        previousButton.setOnAction(event -> {
            this.stageNumber -= 1;
            if (this.stageNumber >= 0) {
                expertStage.close();
                this.start(labels.get(stageNumber).getText());
            }
            else this.stageNumber += 1;
        });
    }
}
