package controller;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

import dao.GraficoDAO;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class RelatorioController implements Initializable{
	@FXML private Label provasRealizadas;
	@FXML private Label aprovados;
	@FXML private Label reprovados;
	@FXML private Label horario;
	
	private StringBuilder labelProvasRealizadas;
	private StringBuilder labelAprovados;
	private StringBuilder labelReprovados;
	private StringBuilder labelHorario;
	private GraficoDAO exameDAO;
	
	public RelatorioController(){
		exameDAO = new GraficoDAO();
	}
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		labelProvasRealizadas = new StringBuilder();
		labelAprovados = new StringBuilder();
		labelReprovados = new StringBuilder();
		labelHorario = new StringBuilder();
		
		labelProvasRealizadas.append("Provas Realizadas - ");
		labelAprovados.append("Aprovados - ");
		labelReprovados.append("Reprovados - ");
		labelHorario.append("Media de horario - ");
		
		try {
			labelProvasRealizadas.append(exameDAO.getProvasRealizadas());
			labelAprovados.append(exameDAO.getAprovados());
			labelReprovados.append(exameDAO.getReprovados());
			labelHorario.append("02:05");
			
			this.reloadInfo();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void reloadInfo(){
		provasRealizadas.setText(labelProvasRealizadas.toString());
		aprovados.setText(labelAprovados.toString());
		reprovados.setText(labelReprovados.toString());
		horario.setText(labelHorario.toString());
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
