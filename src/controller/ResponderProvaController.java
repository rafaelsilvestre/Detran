package controller;

import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.TimerTask;

import dao.OpcaoDAO;
import dao.PerguntaDAO;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import model.Opcao;
import model.Pergunta;
import util.Cronometro;

public class ResponderProvaController implements Initializable{
	@FXML private ToggleGroup group = new ToggleGroup();
	@FXML private RadioButton option1;
	@FXML private RadioButton option2;
	@FXML private RadioButton option3;
	@FXML private RadioButton option4;
	@FXML public Label labelTime;
	@FXML public Label labelPergunta;
	@FXML public Label indexQuestion;
	@FXML public ImageView imageQuestion;
	
	private int positionQuestion = 0;
	
	private PerguntaDAO perguntaDAO = new PerguntaDAO();
	private ArrayList<Pergunta> perguntas;
	
	public ResponderProvaController() throws SQLException{
		this.perguntas =  this.perguntaDAO.getPerguntas();
	}
 
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		this.reloadQuestion();
		
		option1.setToggleGroup(group);
		option2.setToggleGroup(group);
		option3.setToggleGroup(group);
		option4.setToggleGroup(group);
	}
	
	public void reloadQuestion(){
		Pergunta pergunta = this.perguntas.get(this.positionQuestion);
		Image image = new Image("http://www.detran.se.gov.br/images/sinalizacao_transito/regulamentacao/R_26.jpg");
		
		this.indexQuestion.setText("Pergunta " + (this.positionQuestion + 1));
		this.labelPergunta.setText(pergunta.getTitle().trim());
		this.imageQuestion.setImage(image);
		
		for(int i = 0; i < pergunta.getOpcoes().size(); i++){
			switch(i){
				case 0:
					this.option1.setText(pergunta.getOpcoes().get(0).getTitle().trim());
					break;
				case 1:
					this.option2.setText(pergunta.getOpcoes().get(1).getTitle().trim());
					break;
				case 2:
					this.option3.setText(pergunta.getOpcoes().get(2).getTitle().trim());
					break;
				case 3:
					this.option4.setText(pergunta.getOpcoes().get(3).getTitle().trim());
					break;
			}
		}
	}
	
	public boolean nextQuestion(){
		//Pergunta pergunta = this.perguntas.get(this.positionQuestion);
//		RadioButton selectedRadioButton = (RadioButton) group.getSelectedToggle();
//		String toogleGroupValue = selectedRadioButton.getText();
//		System.out.println(toogleGroupValue);

//		for(int i = 0; i < pergunta.getOpcoes().size(); i++){
//			if(pergunta.getOpcoes().get(i).getTitle().equals(toogleGroupValue)){
//				System.out.println("Pergunta Correta -" + pergunta.getOpcoes().get(i).getTitle());
//			}
//		}
		
		if(this.positionQuestion == (this.perguntas.size() - 1))
			return false;
		this.positionQuestion++;
		this.reloadQuestion();
		return true;
	}
	
	public boolean  prevQuestion(){
		if(this.positionQuestion == 0)
			return false;
		this.positionQuestion--;
		this.reloadQuestion();
		return false;
	}
	
	public void examFinalize(){
		System.out.println("Finalizar");
	}
}
