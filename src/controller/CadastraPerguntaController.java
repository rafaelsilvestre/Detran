package controller;

import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;

import dao.PerguntaDAO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import model.Pergunta;

public class CadastraPerguntaController implements Initializable{

	@FXML public ListView<String> listQuestion = new ListView<String>();
	@FXML public ObservableList<String> items = FXCollections.observableArrayList ();
	
	private PerguntaDAO perguntaDAO = new PerguntaDAO();
	private ArrayList<Pergunta> perguntas;
	
	public CadastraPerguntaController() throws SQLException{
		this.perguntas =  this.perguntaDAO.getPerguntas();
	}
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		for(int i = 0; i < this.perguntas.size(); i++){
			items.add(this.perguntas.get(i).getTitle().trim());
		}
		listQuestion.setItems(items);	
	}

}
