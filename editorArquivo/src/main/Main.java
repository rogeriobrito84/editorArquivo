package main;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;


public class Main extends Application{
	
	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage palco) throws Exception {
			FXMLLoader loader = new FXMLLoader(ControleMain.class.getResource("principal.fxml"));
			try {
				//palco = new Stage(StageStyle.TRANSPARENT);
				ControleMain pane = (ControleMain) loader.load();
				Scene cena = new Scene(pane,Color.TRANSPARENT);
				System.out.println("Altura da cena: " + cena.heightProperty().doubleValue());
				System.out.println("Largura:  da cena" + cena.widthProperty().doubleValue());
				ControleMain.heightXProperty().bind(cena.heightProperty().subtract(0));
				ControleMain.widthXProperty().bind(cena.widthProperty().subtract(0)); 
				palco.setScene(cena);
			
				palco.show();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}
	
}

