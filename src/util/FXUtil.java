package util;

import java.util.Optional;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.stage.Modality;
import javafx.stage.StageStyle;

public class FXUtil {
	public static void mensagem(String title, String header, String contentText) {
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.initModality(Modality.WINDOW_MODAL);  // default
		alert.initStyle(StageStyle.UTILITY);  // remove �cone
        alert.setTitle(title);
        alert.setHeaderText(header);  // header pode ser null
        alert.setContentText(contentText);
        alert.showAndWait();
        alert.close();
    }

	public static boolean confirma(String title, String header, String contentText) {
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.initModality(Modality.WINDOW_MODAL);
		alert.initStyle(StageStyle.UTILITY);
        alert.setTitle(title);
        alert.setHeaderText(header);  // header pode ser null
        alert.setContentText(contentText);
        Optional<ButtonType> result = alert.showAndWait();
        alert.close();
        return result.get() == ButtonType.OK;
    }

	public static void alerta(String title, String header, String contentText) {
		Alert alert = new Alert(AlertType.WARNING);
		alert.initModality(Modality.WINDOW_MODAL);  // default
		alert.initStyle(StageStyle.UTILITY);  // remove �cone
        alert.setTitle(title);
        alert.setHeaderText(header);  // header pode ser null
        alert.setContentText(contentText);
        alert.showAndWait();
        alert.close();
    }

	public static void erro(String title, String header, String contentText) {
		Alert alert = new Alert(AlertType.ERROR);
		alert.initModality(Modality.WINDOW_MODAL);  // default
		alert.initStyle(StageStyle.UTILITY);  // remove �cone
        alert.setTitle(title);
        alert.setHeaderText(header);  // header pode ser null
        alert.setContentText(contentText);
        alert.showAndWait();
        alert.close();
    }
	
	public static Integer stringToInteger(String numeroStr) throws Exception {
		Integer numeroInteger = 0;
		
    	try {
    		numeroInteger = Integer.valueOf(numeroStr.trim());
    	} catch (Exception e) {
    		throw new Exception("N�mero '" + numeroStr + "' inv�lido.");
    	}
    	return numeroInteger;
	}
}