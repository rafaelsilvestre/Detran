package controller;

import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.TimerTask;

import dao.PerguntaDAO;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import model.Pergunta;
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
		Pergunta p1 = new Pergunta();
		p1.setTitle("O condutor que entrega a direção do seu veículo a pessoa não habilitada");
		
		PerguntaDAO perguntaDAO = new PerguntaDAO();
		try {
			ArrayList<Pergunta> perguntas =  perguntaDAO.getPerguntas();
			for(int i = 0; i < perguntas.size(); i++){
				System.out.println(perguntas.get(i).getTitle());
			}
			
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		
		option1.setToggleGroup(group);
		option2.setToggleGroup(group);
		option3.setToggleGroup(group);
		option4.setToggleGroup(group);
	}
	
	public void nextQuestion(){
		System.out.println("PÃ³ximo");
	}
	
	public void  prevQuestion(){
		System.out.println("Anterior");
	}
	
	public void examFinalize(){
		System.out.println("Finalizar");
	}
}
