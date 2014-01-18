package main;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;


public class Main extends Application{
	
	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(final Stage palco) throws Exception {
			FXMLLoader loader = new FXMLLoader(ControleMain.class.getResource("principal.fxml"));
			try {
				ControleMain pane = (ControleMain) loader.load();
				Scene cena = new Scene(pane);
				ControleMain.heightXProperty().bind(palco.heightProperty().subtract(27));
				ControleMain.widthXProperty().bind(palco.widthProperty().subtract(8)); 
				palco.setScene(cena);
				
				palco.show();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}
}

