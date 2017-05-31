package controller;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;

import dao.ExameDAO;
import dao.OpcaoDAO;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Control;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import model.Exame;
import model.Opcao;
import model.Pergunta;

public class GabaritoController implements Initializable {
	
	@FXML private Label idExame;
	@FXML private Label labelTempo;
	@FXML private Label labelNota;
	@FXML private VBox vBoxPrimary;
	public static Exame exame;
	private ExameDAO exameDAO;
	private double nota = 0.0;
	String[] alternativas = { "A", "B", "C", "D"};
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		System.out.println(this.exame.getId());
		
		try {
			this.reloadQuestion();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void reloadQuestion() throws SQLException{
		exameDAO = new ExameDAO();
		ArrayList<Pergunta> perguntas = exameDAO.getPerguntasExame(this.exame);
		Exame exame = exameDAO.getExame(this.exame);
		
		// Detalhes Exame
		idExame.setText("#" + Integer.toString(exame.getId()));
		labelTempo.setText(exame.getTempo());
		labelNota.setText(Double.toString(exame.getNota()));
		
		for(int i = 0; i < perguntas.size(); i++){	
			Label label = new Label();
			label.setMinSize(Control.USE_COMPUTED_SIZE, Control.USE_COMPUTED_SIZE);
			label.setMaxSize(732, 100);
			label.setPrefWidth(Control.USE_COMPUTED_SIZE);
			label.setPadding(new Insets(0, 10, 10, 10));
			label.setWrapText(true);
			label.setText((i+1) + " - " + perguntas.get(i).getTitle().trim());
			
			vBoxPrimary.getChildren().add(label);
			
			for(int x = 0; x < perguntas.get(i).getOpcoes().size(); x++){
				Opcao opcao = perguntas.get(i).getOpcoes().get(x);
				Label opcaoLabel = new Label();
				opcaoLabel.setMinSize(732, Control.USE_COMPUTED_SIZE);
				opcaoLabel.setMaxSize(732, 100);
				opcaoLabel.setPrefWidth(Control.USE_COMPUTED_SIZE);
				opcaoLabel.setPadding(new Insets(0, 10, 10, 30));
				opcaoLabel.setWrapText(false);
				
				
				if(opcao.isVerdadeiro())
					opcaoLabel.setTextFill(Color.web("#87c037"));
				
				if(perguntas.get(i).getSelected() == opcao.getId()){
					if(opcao.isVerdadeiro()){
						opcaoLabel.setTextFill(Color.web("#87c037"));
						++nota;
					}else
						opcaoLabel.setTextFill(Color.web("#ff0000"));
				}
				
				opcaoLabel.setText(alternativas[x]+ ") " + opcao.getTitle().trim());
				vBoxPrimary.getChildren().add(opcaoLabel);
			}
		}
	}
	
	public void backToExames(Event event) throws IOException{
		Node node = (Node) event.getSource();
		Stage stage = (Stage) node.getScene().getWindow();
		Parent root = FXMLLoader.load(getClass().getResource("/view/Exames.fxml"));
		Scene scene = new Scene(root, 734, 307);
		
		stage.setScene(scene);
		stage.setResizable(false);
		stage.centerOnScreen();
		stage.show();  
	}
}
