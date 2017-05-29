package controller;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;

import dao.ExameDAO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.Exame;
import util.FXUtil;

public class ExamesController  implements Initializable{
	
	@FXML private TableView<Exame> tableExame;
	@FXML private TableColumn<Exame, String> idExame;
	@FXML private TableColumn<Exame, String> dataExame;
	private ArrayList<Exame> exames = new ArrayList<Exame>();
	private ExameDAO exameDAO;
	
	public ExamesController(){
		this.exameDAO = new ExameDAO();
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		idExame.setCellValueFactory(new PropertyValueFactory<Exame, String>("id"));          
		dataExame.setCellValueFactory(new PropertyValueFactory<Exame, String>("date"));          
        
		try {
			this.reloadTable();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void reloadTable() throws SQLException{
		ObservableList<Exame> listExames = FXCollections.observableArrayList();
		this.exames = exameDAO.getExames();
		
		tableExame.setItems(listExames);
		listExames.addAll(this.exames);
		tableExame.setItems(listExames);		
	}
	
	public void verGabarito(){
		Exame exame = tableExame.getSelectionModel().getSelectedItem();
		if(exame == null)
			FXUtil.alerta("Opps!", "Não existe um exame selecionado!", "Seleciona uma exame na tabela");
		else{
			
		}
	}
	
	public void excluirExame() throws SQLException{
		System.out.println("Excluir Exame");
		Exame exame = tableExame.getSelectionModel().getSelectedItem();
		if(exame == null)
			FXUtil.alerta("Opps!", "Não existe um exame selecionado!", "Seleciona uma exame na tabela");
		else{
			this.exameDAO.deleteExame(exame);
			this.reloadTable();
			FXUtil.alerta("Opps!", "Exame excluido com sucesso", null);
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

}
