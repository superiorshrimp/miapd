package gui;

import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import main.Phone;
import main.PhonesGenerator;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.LinkedList;

public class App extends javafx.application.Application {
    @Override
    public void start(Stage primaryStage) throws Exception{
        //
        String[] args = "python ../math/consistency_index.py 1 7 0.1666 0.5 0.25 0.1666 4 0.1428 1 0.3333 5 0.2 0.1428 5 6 3 1 6 3 2 8 2 0.2 0.1666 1 8 0.2 8 4 5 0.3333 0.125 1 0.1111 2 6 7 0.5 5 9 1 2 0.25 0.2 0.125 0.125 0.5 0.5 1".split(" ");
        Process proc = Runtime.getRuntime().exec(args);
        System.out.println("bbbbbbbb");
        proc.waitFor();
        BufferedReader stdInput = new BufferedReader(new InputStreamReader(proc.getInputStream()));
        String s = null;
        while ((s = stdInput.readLine()) != null) {
            System.out.println(s);
        }
        //

        PhonesGenerator phonesGenerator = new PhonesGenerator("../data/phones/");
        LinkedList<Phone> phones = phonesGenerator.getPhones();

        ArrayList<String> labels = Phone.getLabels();
        ExpertModule expertModule = new ExpertModule(labels, this);
        UserModule userModule = new UserModule(labels);

        FlowPane flowPane = new FlowPane(Orientation.VERTICAL);
        flowPane.setHgap(5);
        flowPane.setAlignment(Pos.CENTER);
        flowPane.setPadding(new Insets(0, 0, 0, 0)); // set top, right, bottom, left

        HBox hBox = new HBox();
        Button expertButton = new Button("I`m expert");
        Button userButton = new Button("I`m user");


        hBox.setAlignment(Pos.CENTER);
        Image image = new Image("xiaomi lepsze.jpg");
        ImageView imageView = new ImageView();
        imageView.setImage(image);
        imageView.setX(10);
        imageView.setY(10);
        imageView.setFitWidth(575);
        imageView.setPreserveRatio(true);
        hBox.getChildren().addAll(expertButton, userButton);

        VBox vBox = new VBox();
        vBox.setAlignment(Pos.CENTER);
        vBox.getChildren().addAll(imageView, hBox);

        BorderPane borderPane = new BorderPane();
        borderPane.setCenter(flowPane);
        borderPane.setBottom(vBox);
        BorderPane.setAlignment(vBox, Pos.CENTER);
        borderPane.setMinHeight(100);
        borderPane.setMaxHeight(480);
        borderPane.setMinWidth(500);

        Scene scene = new Scene(borderPane);

        primaryStage.setTitle("Getting Started");
        primaryStage.setScene(scene);
        primaryStage.show();
        primaryStage.setOnCloseRequest((WindowEvent we) -> {
            System.exit(0);
        });

        expertButton.setOnAction(event -> {
            primaryStage.close();
            expertModule.start(labels.get(0));
        });

        userButton.setOnAction(event -> {
            primaryStage.close();
            userModule.start();
        });
    }
}