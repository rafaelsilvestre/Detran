package controller;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.TimerTask;

import dao.ExameDAO;
import dao.OpcaoDAO;
import dao.PerguntaDAO;
import javafx.application.Platform;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import model.Exame;
import model.Opcao;
import model.Pergunta;
import model.Usuario;
import util.Cronometro;
import util.FXUtil;

public class ResponderProvaController extends Cronometro implements Initializable{
	@FXML private ToggleGroup group = new ToggleGroup();
	@FXML private RadioButton option1;
	@FXML private RadioButton option2;
	@FXML private RadioButton option3;
	@FXML private RadioButton option4;
	@FXML public Label labelTime;
	@FXML public Label labelPergunta;
	@FXML public Label indexQuestion;
	@FXML public ImageView imageQuestion;
	@FXML public Button btnFinalizarProva;
	@FXML public Button btnAvancar;
	@FXML public Button btnVoltar;
	
	private int positionQuestion = 0;
	private PerguntaDAO perguntaDAO = new PerguntaDAO();
	private ArrayList<Pergunta> perguntas;
	
	public ResponderProvaController() throws SQLException{
		super(0, 0, 0, 0, 20, 0, REGRESSIVA);
		this.perguntas =  this.perguntaDAO.getPerguntas();
	}
 
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		this.reloadQuestion();
		
		this.cronometro();
		
		Timer timer = new Timer();
		timer.scheduleAtFixedRate(new TimerTask() {
			@Override
			public void run() {
				Platform.runLater(() -> {
					labelTime.setText(getTime());
					if(getTime().equals("00:00")){
						//labelTime.setText("00:00");
						System.out.println("Acabou");
						cancel();
						FXUtil.alerta("Atencao", "Tempo disponivel para resolver a prova excedido", "Retorne novamente para resolver uma nova prova");
						btnFinalizarProva.setDisable(true);
						btnAvancar.setDisable(true);
						btnVoltar.setDisable(true);
					}
                });
            }
        }, 1000, 1000);
		
		option1.setToggleGroup(group);
		option2.setToggleGroup(group);
		option3.setToggleGroup(group);
		option4.setToggleGroup(group);
	}
	
	public void reloadQuestion(){
		Pergunta pergunta = this.perguntas.get(this.positionQuestion);
		ArrayList<Opcao> opcoes = pergunta.getOpcoes();
		
		this.indexQuestion.setText("Pergunta " + (this.positionQuestion + 1));
		this.labelPergunta.setText(pergunta.getTitle().trim());
		
		Thread thread = new Thread() {
			@Override
			public void run() {
				if(pergunta.getImage() == null)
					imageQuestion.setVisible(false);
				else{
					imageQuestion.setVisible(true);
					Image image = new Image(pergunta.getImage());
					imageQuestion.setImage(image);
				} 
			}
		};
		thread.start();
		
		for(int i = 0; i < opcoes.size(); i++){
			switch(i){
				case 0:
					if(pergunta.getOpcoes().get(0) != null)
						this.option1.setText(pergunta.getOpcoes().get(0).getTitle().trim());
					
					if(opcoes.get(i).getId() == pergunta.getSelected())
						this.option1.setSelected(true);
					else
						this.option1.setSelected(false);
				break;
				
				case 1:
					if(pergunta.getOpcoes().get(1) != null)
						this.option2.setText(pergunta.getOpcoes().get(1).getTitle().trim());
					
					if(opcoes.get(i).getId() == pergunta.getSelected())
						this.option2.setSelected(true);
					else
						this.option2.setSelected(false);
				break;
				
				case 2:
					if(pergunta.getOpcoes().get(2) != null)
						this.option3.setText(pergunta.getOpcoes().get(2).getTitle().trim());
					
					if(opcoes.get(i).getId() == pergunta.getSelected())
						this.option3.setSelected(true);
					else
						this.option3.setSelected(false);
				break;
					
				case 3:
					if(pergunta.getOpcoes().get(3) != null)
						this.option4.setText(pergunta.getOpcoes().get(3).getTitle().trim());
					
					if(opcoes.get(i).getId() == pergunta.getSelected())
						this.option4.setSelected(true);
					else
						this.option4.setSelected(false);
				break;
			}
		}
	}
	
	public boolean nextQuestion(){		
		if(this.positionQuestion == (this.perguntas.size() - 1))
			return false;
		this.positionQuestion++;
		this.reloadQuestion();
		return true;
	}
	
	public boolean prevQuestion(){
		if(this.positionQuestion == 0)
			return false;
		this.positionQuestion--;
		this.reloadQuestion();
		return true;
	}
	
	public void examFinalize(Event event) throws IOException, SQLException{		
		if(this.isComplete()){
			ArrayList<Pergunta> perguntasRespondidas = this.perguntas;	
			
			Exame exame = new Exame();
			exame.setTempo(labelTime.getText());
			
			ExameDAO exameDAO = new ExameDAO();
			Exame exameRetorno = exameDAO.save(perguntasRespondidas, new Usuario(1), exame);
			
			GabaritoController exameControl = new GabaritoController();
			exameControl.exame = exameRetorno;
			
			this.backToGabarito(event);
		}else{
			FXUtil.alerta("Prova Incompleta!", "Existem perguntas sem alternativa selecionada!", "Retorne para concluír sua prova");
		}
	}
	
	public void backToHome(Event event) throws IOException{
		Node node = (Node) event.getSource();
		Stage stage = (Stage) node.getScene().getWindow();
		Parent root = FXMLLoader.load(getClass().getResource("/view/Main.fxml"));
		Scene scene = new Scene(root, 734, 307);
		
		stage.setScene(scene);
		stage.setResizable(false);
		stage.centerOnScreen();
		stage.show();  
	}
	
	public void backToGabarito(Event event) throws IOException{
		Node node = (Node) event.getSource();
		Stage stage = (Stage) node.getScene().getWindow();
		Parent root = FXMLLoader.load(getClass().getResource("/view/Gabarito.fxml"));
		Scene scene = new Scene(root, 734, 307);
		
		stage.setScene(scene);
		stage.setResizable(false);
		stage.centerOnScreen();
		stage.show();  
	}
	
	public void desistirProva(Event event) throws IOException{
		this.backToHome(event);
	}
	
	public Opcao getOptionSelected(){
		Opcao option = null;
		Pergunta pergunta = this.perguntas.get(this.positionQuestion);
		RadioButton selectedRadioButton = (RadioButton) this.group.getSelectedToggle();
		
		if(selectedRadioButton != null){
			for(int i = 0; i < pergunta.getOpcoes().size(); i++){
				if(pergunta.getOpcoes().get(i).getTitle().trim().equals(selectedRadioButton.getText().trim())){
					option = pergunta.getOpcoes().get(i);
				}
			}
		}
		return option;
	}
	
	public void reloadAnswer(Event event){
		Opcao option = this.getOptionSelected();
		this.perguntas.get(this.positionQuestion).setSelected(option.getId());
	}
	
	public boolean isComplete(){
		Boolean returnMethod = true;
		ArrayList<Pergunta> perguntas = this.perguntas;
		for(int i = 0; i < perguntas.size(); i++){
			Integer id = perguntas.get(i).getSelected();
			if(id == null || id == 0){				
				returnMethod = false;
				break;
			}
		}
		return returnMethod;
	}
}
