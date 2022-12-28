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
import javafx.util.Pair;
import main.Phone;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;

public class UserModule {
    private static int stageNumber = 0;
    ArrayList<Label> labels = new ArrayList<>();
    ArrayList<Pair<VBox, Integer>> vBoxes = new ArrayList<>();
    float[][] userPreferences;
    public UserModule(ArrayList<String> labels){
        for ( String label : labels){
            this.labels.add(new Label(label));
        }
        int arraySize = this.labels.size();
        this.userPreferences = new float[arraySize][arraySize];
        for (int i = 0; i < arraySize; i++){
            this.userPreferences[i][i] = 1;
        }
        for (int i = 0; i < labels.size()-1; i++) {
            Label label = this.labels.get(i);
            VBox vBox = new VBox(label);
            vBox.setAlignment(Pos.CENTER);
            vBox.setPadding(new Insets(10, 10, 10, 10));
            vBoxes.add(new Pair<>(vBox, i*7+i+1));
            System.out.println(i*7+i+1);
            System.out.println((i*7+i+1) / labels.size());
            System.out.println((i*7+i+1) % labels.size());
            System.out.println();
        }
        Collections.shuffle(vBoxes);
        Label label = this.labels.get(labels.size()-1);
        VBox vBox = new VBox(label);
        vBox.setAlignment(Pos.CENTER);
        vBox.setPadding(new Insets(10, 10, 10, 10));
        vBoxes.add(new Pair<>(vBox, (labels.size()-1)*7+labels.size()-1+1));
    }
    public void start(){
        Stage userStage = new Stage();


        Button nextButton = new Button("Next");
        Label infoLabel = new Label("Compare importance of first feature to second");
        HBox hBox = new HBox(vBoxes.get(UserModule.stageNumber).getKey(), vBoxes.get(UserModule.stageNumber + 1).getKey());
        System.out.println(UserModule.stageNumber);
        int xIdx = vBoxes.get(UserModule.stageNumber).getValue() / labels.size();
        int yIdx = vBoxes.get(UserModule.stageNumber).getValue() % labels.size();

        ChoiceBox<Integer> cb = new ChoiceBox<>(FXCollections.observableArrayList(
                1, 2, 3, 4, 5, 6, 7, 8, 9
        ));

        hBox.setAlignment(Pos.CENTER);
        VBox vBox = new VBox(infoLabel, hBox, cb, nextButton);
        vBox.setAlignment(Pos.CENTER);
        FlowPane flowPane = new FlowPane(vBox);

        flowPane.setAlignment(Pos.CENTER);
        Scene scene = new Scene(flowPane);


        userStage.setScene(scene);
        userStage.show();

        nextButton.setOnAction(event -> {
            userStage.close();
            UserModule.stageNumber += 1;
            if (UserModule.stageNumber > labels.size() - 2) calculateResults();
            else start();
        });

        cb.setOnAction(event -> {
            userPreferences[xIdx][yIdx] = cb.getValue();
            for (var line : this.userPreferences){
                System.out.println(Arrays.toString(line));
            }
            System.out.println();
        });

        userStage.setOnCloseRequest((WindowEvent we) -> {
            System.exit(0);
        });
    }

    //Function to get results from BE
    private Phone getResults(){
        return new Phone("a", 1,1,1,1,1,1,1,1); //temporarily return basic phone
    }

    private void calculateResults(){
        float size = this.userPreferences.length;
        for ( int x = 0; x < size - 2; x ++ ){
            for ( int y = 2; y < size; y++ ){
                if (x + y > 6) break;
                System.out.println("x " + (y-2) + " y " + (x + y));
                System.out.println("x " + (y-2) + " y " + (x + y -1));
                System.out.println("x " + (x + y -1) + " y " + (x + y));
                System.out.println();
                userPreferences[y - 2][x + y] = Math.min((float) Math.sqrt(userPreferences[y - 2][x + y - 1] * userPreferences[x + y - 1][x + y]), 9);
            }
        }

        for ( int x = 0; x < size - 1; x++ ){
            for ( int y = 1; y < size; y++ ){
                if (x + y > 6) break;
                System.out.println("x " + (x + y) + " y " + (y - 1));
                System.out.println();
                userPreferences[x + y][y - 1] = 1/userPreferences[y - 1][x + y];
            }
        }

        System.out.println();
        for (var line : this.userPreferences){
            System.out.println(Arrays.toString(line));
        }
        showResults();
    }
    private void showResults(){
        Stage resultsStage = new Stage();
        resultsStage.setTitle("Results");
        Phone bestPhone = getResults();
        HBox hBox = new HBox(new Label(bestPhone.toString()));
        Scene scene = new Scene(hBox);
        resultsStage.setScene(scene);
        resultsStage.show();
    }
}
