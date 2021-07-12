package sample;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        primaryStage.setTitle("Snake");
        MainCanvas mainCanvas = new MainCanvas(600,20);
        GridPane gridPane = new GridPane();
        gridPane.add(mainCanvas,0,0);
        primaryStage.setScene(new Scene(gridPane));
        primaryStage.setResizable(false);
        mainCanvas.initialize();
        mainCanvas.draw(Color.BLACK);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}

//Java 16
//--module-path lib --add-modules javafx.controls,javafx.fxml