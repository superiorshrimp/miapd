package gui;

import javafx.application.Application;
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
import main.Phone;

import java.util.ArrayList;

public class UserModule {
    ArrayList<Label> labels = new ArrayList<>();
    public UserModule(ArrayList<String> labels){
        for ( String label : labels){
            this.labels.add(new Label(label));
        }
    }
    public void start(){
        Stage userStage = new Stage();
        ArrayList<VBox> vBoxes = new ArrayList<>();
        for (int i = 0; i < labels.size(); i++){
            ChoiceBox<Integer> cb = new ChoiceBox<>(FXCollections.observableArrayList(
                    1, 2, 3, 4, 5, 6, 7, 8, 9
            ));
            VBox vBox = new VBox(labels.get(i), cb);
            vBox.setAlignment(Pos.CENTER);
            vBox.setPadding(new Insets(10, 10, 10, 10));
            vBoxes.add(vBox);
        }

        HBox hBox = new HBox();
        for (VBox vBox : vBoxes){
            hBox.getChildren().add(vBox);
        }

        Button nextButton = new Button("Next");
        Label infoLabel = new Label("How important to you are");
        FlowPane flowPane = new FlowPane(infoLabel, hBox, nextButton);

        flowPane.setAlignment(Pos.CENTER);
        Scene scene = new Scene(flowPane);


        userStage.setScene(scene);
        userStage.show();

        nextButton.setOnAction(event -> {
            userStage.close();
            showResults();
        });

        userStage.setOnCloseRequest((WindowEvent we) -> {
            System.exit(0);
        });
    }

    //Function to get results from BE
    private Phone getResults(){
        return new Phone("a", 1,1,1,1,1,1,1,1); //temporarily return basic phone
    }
    public void showResults(){
        Stage resultsStage = new Stage();
        resultsStage.setTitle("Results");
        Phone bestPhone = getResults();
        HBox hBox = new HBox(new Label(bestPhone.toString()));

        Scene scene = new Scene(hBox);
        resultsStage.setScene(scene);
        resultsStage.show();
    }
}
