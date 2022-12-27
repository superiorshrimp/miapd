package gui;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.geometry.HPos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class ExpertModule{
    ArrayList<Label> labels = new ArrayList<>();
    Application app;

    GridPane matrixGrid = new GridPane();
    ArrayList<ArrayList<Double>> matrix = new ArrayList<>(this.labels.size());
    ArrayList<ArrayList<Text>> matrixContent = new ArrayList<>(this.labels.size());
    public ExpertModule(ArrayList<String> labels, Application app){
        for(String label : labels){
            this.labels.add(new Label(label));
            this.app = app;
        }

        for(int row = 0; row<this.labels.size(); row++){
            matrix.add(new ArrayList<>(this.labels.size()));
            matrixContent.add(new ArrayList<>(this.labels.size()));
        }

        this.setUpGridPane();
    }

    public void start(){
        for(int row = 0; row<this.labels.size(); row++){
            for(int col = 0; col<this.labels.size(); col++){
                if(col == row){
                    Text content = new Text("1");
                    matrixGrid.add(content, col, row);
                    GridPane.setHalignment(content, HPos.CENTER);
                    matrixContent.get(row).add(content);
                    matrix.get(row).add((double)(1));
                }
                else if(col < row){
                    Text content = new Text("-");
                    matrixContent.get(row).add(content);
                    matrixGrid.add(content, col, row);
                    GridPane.setHalignment(content, HPos.CENTER);
                    matrix.get(row).add((double)(-1));
                }
                else{
                    ChoiceBox<String> choiceBox = new ChoiceBox<>(FXCollections.observableArrayList(
                        "1/9", "1/7", "1/5", "1/3", "1", "3", "5", "7", "9"
                    ));
                    this.choiceBoxObserve(choiceBox, row, col);
                    matrixContent.get(row).add(null);
                    matrixGrid.add(choiceBox, col, row);
                    GridPane.setHalignment(choiceBox, HPos.CENTER);
                    matrix.get(row).add((double)(-1));
                }
            }
        }
        Button previousButton = new Button("Previous");
        Button nextButton = new Button("Next");
        HBox buttons = new HBox();
        buttons.getChildren().addAll(previousButton, nextButton);

        VBox root = new VBox();
        root.setSpacing(20);

        root.getChildren().addAll(matrixGrid, buttons);

        Scene scene = new Scene(root);

        Stage expertStage = new Stage();
        expertStage.setScene(scene);
        expertStage.show();

        expertStage.setOnCloseRequest((WindowEvent we) -> {
            System.exit(0);
        });

        nextButton.setOnAction(event -> {
            if(this.isAllFilled()){
                expertStage.close();
                this.calculate();
            }
            else{
                System.out.println("fill all remaining fields");
            }
        });

        previousButton.setOnAction(event -> {
            expertStage.close();
            try {
                app.start(new Stage());
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
    }

    public void calculate(){
        double consistencyIndex = -1;
        try {
            consistencyIndex = this.getConsistencyIndex();
        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println("consistency index: " + consistencyIndex);

    }

    public double getConsistencyIndex() throws Exception{
        //example:
        //String[] args = "python ../math/consistency_index.py 1 7 0.1666 0.5 0.25 0.1666 4 0.1428 1 0.3333 5 0.2 0.1428 5 6 3 1 6 3 2 8 2 0.2 0.1666 1 8 0.2 8 4 5 0.3333 0.125 1 0.1111 2 6 7 0.5 5 9 1 2 0.25 0.2 0.125 0.125 0.5 0.5 1".split(" ");
        StringBuilder command = new StringBuilder("python ../math/consistency_index.py");
        for(int row = 0; row<this.labels.size(); row++){
            for(int col = 0; col<this.labels.size(); col++){
                command.append(" ").append(this.matrix.get(row).get(col));
            }
        }
        String[] args = command.toString().split(" ");
        Process proc = Runtime.getRuntime().exec(args);
        proc.waitFor();
        BufferedReader stdInput = new BufferedReader(new InputStreamReader(proc.getInputStream()));
        String s = stdInput.readLine();
        if(s != null){
//            int len = s.length();
//            s = s.substring(1, Math.min(len-4, 11));
            return Double.parseDouble(s);
        }
        throw new Exception();
    }

    private void setUpGridPane(){
        matrixGrid.setGridLinesVisible(true);

        int rowCount = this.labels.size();
        int columnCount = this.labels.size();

        RowConstraints rc = new RowConstraints();
        rc.setMinHeight(50);
        rc.setMaxHeight(50);

        for (int i = 0; i < rowCount; i++) {
            matrixGrid.getRowConstraints().add(rc);
        }

        ColumnConstraints cc = new ColumnConstraints();
        cc.setMinWidth(50);
        cc.setMaxWidth(50);

        for (int i = 0; i < columnCount; i++) {
            matrixGrid.getColumnConstraints().add(cc);
        }
    }

    private void choiceBoxObserve(ChoiceBox<String> choiceBox, int row, int col){
        choiceBox.setOnAction((event) -> {
            String val = choiceBox.getSelectionModel().getSelectedItem();
            if(val.equals("1")){
                matrix.get(row).set(col, (double)(1));
                matrix.get(col).set(row, (double)(1));
                Text content = matrixContent.get(col).get(row);
                content.setText("1");
            }
            else if(val.contains("/")){
                matrix.get(row).set(col, (double)(1 / Integer.parseInt(val.substring(2))));
                matrix.get(col).set(row, Double.parseDouble(val.substring(2)));
                Text content = matrixContent.get(col).get(row);
                content.setText(val.substring(2));
            }
            else{
                matrix.get(row).set(col, Double.parseDouble(val));
                matrix.get(col).set(row, (double)(1 / Integer.parseInt(val)));
                Text content = matrixContent.get(col).get(row);
                content.setText("1/" + val);
            }
        });
    }

    private boolean isAllFilled(){
        for(int row = 0; row<this.labels.size(); row++){
            for(int col = 0; col<this.labels.size(); col++){
                if(this.matrix.get(row).get(col) < 0){
                    return false;
                }
            }
        }
        return true;
    }
}
