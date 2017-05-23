  package view;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application{

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent parent = FXMLLoader.load(getClass().getResource("Main.fxml"));
    	//Parent parent = FXMLLoader.load(getClass().getResource("CadastraPerguntas.fxml"));
        Scene scene = new Scene(parent);
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.centerOnScreen();
        primaryStage.show();
        
    } 

    public static void main(String[] args) {
        launch(args);
    }
}

