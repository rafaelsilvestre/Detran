package controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

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
import javafx.stage.Stage;

public class RelatorioController implements Initializable{
	@FXML private LineChart graficoLinha;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		graficoLinha = new LineChart<>(
			new CategoryAxis(), new NumberAxis());
			
			final String T1 = "T1";
			final String T2 = "T2";
			final String T3 = "T3";
			final String T4 = "T4";

			XYChart.Series prod1 = new XYChart.Series();
			prod1.setName("Produto 1");

			prod1.getData().add(new XYChart.Data(T1, 5));
			prod1.getData().add(new XYChart.Data(T2, -2));
			prod1.getData().add(new XYChart.Data(T3, 3));
			prod1.getData().add(new XYChart.Data(T4, 15));

			XYChart.Series prod2 = new XYChart.Series();
			prod2.setName("Produto 2");

			prod2.getData().add(new XYChart.Data(T1, -5));
			prod2.getData().add(new XYChart.Data(T2, -1));
			prod2.getData().add(new XYChart.Data(T3, 12));
			prod2.getData().add(new XYChart.Data(T4, 8));

			XYChart.Series prod3 = new XYChart.Series();
			prod3.setName("Produto 3");

			prod3.getData().add(new XYChart.Data(T1, 12));
			prod3.getData().add(new XYChart.Data(T2, 15));
			prod3.getData().add(new XYChart.Data(T3, 12));
			prod3.getData().add(new XYChart.Data(T4, 20));
			graficoLinha.getData().addAll(prod1, prod2, prod3);
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
