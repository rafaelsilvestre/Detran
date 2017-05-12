package controller;

import java.net.URL;
import java.util.Calendar;
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.TimerTask;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import util.Cronometro;

public class ResponderProvaController implements Initializable{
	@FXML private ToggleGroup group = new ToggleGroup();
	@FXML private RadioButton option1;
	@FXML private RadioButton option2;
	@FXML private RadioButton option3;
	@FXML private RadioButton option4;
	@FXML public Label time;
 
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		Cronometro contagem = new Cronometro(0, 0, 0, 0, 20, 0);
        contagem.cronometro();
        
        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                Platform.runLater(() -> {
                	time.setText(contagem.getTime());
                });
            }
        }, 1000, 1000);
		
		option1.setToggleGroup(group);
		option2.setToggleGroup(group);
		option3.setToggleGroup(group);
		option4.setToggleGroup(group);
	}
	
	public void nextQuestion(){
		System.out.println("Póximo");
	}
	
	public void  prevQuestion(){
		System.out.println("Anterior");
	}
	
	public void examFinalize(){
		System.out.println("Finalizar");
	}
}
