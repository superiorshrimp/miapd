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

import java.io.FileInputStream;
import java.io.InputStream;

public class App extends javafx.application.Application {
    @Override
    public void start(Stage primaryStage) throws Exception{

        ExpertModule expertModule = new ExpertModule();
        UserModule userModule = new UserModule(Phone.getLabels());

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
            expertModule.start();
        });

        userButton.setOnAction(event -> {
            primaryStage.close();
            userModule.start();
        });
    }
}