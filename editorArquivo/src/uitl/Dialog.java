package uitl;

import javax.swing.JComponent;
import javax.swing.plaf.OptionPaneUI;

import javafx.application.Application;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class Dialog extends Application {
	private Label texto;
	private Button btnSim;
	private Button btnNao;
	
	@Override
	public void start(Stage palco) throws Exception {
		AnchorPane pane = new AnchorPane();
		pane.setPrefWidth(400);
		pane.setPrefHeight(100);
		
		texto = new Label();
		texto.setLayoutX(5);
		texto.setLayoutY(5);
		texto.setFont(new Font(18));
		
		btnSim = new Button("Sim");
		btnSim.setLayoutX(220);
		btnSim.setLayoutY(60);
		btnSim.setFont(new Font(14));
		btnSim.setMinHeight(35);
		btnSim.setMinWidth(80);
		
		btnNao = new Button("Não");
		btnNao.setLayoutX(310);
		btnNao.setLayoutY(60);
		btnNao.setFont(new Font(14));
		btnNao.setMinHeight(35);
		btnNao.setMinWidth(80);
		
		pane.getChildren().addAll(texto, btnSim, btnNao);
		
		Scene cena = new Scene(pane);
		palco.setScene(cena);
		
		btnSim.setOnMouseClicked(new EventHandler<Event>() {
			@Override
			public void handle(Event event) {
				
			}
		});
		
		btnNao.setOnMouseClicked(new EventHandler<Event>() {
			@Override
			public void handle(Event event) {
			}
		});
		

	}
	
	
	
	public Label getTexto() {
		return texto;
	}

	public void setTexto(Label texto) {
		this.texto = texto;
	}

	public Button getBtnSim() {
		return btnSim;
	}

	public void setBtnSim(Button btnSim) {
		this.btnSim = btnSim;
	}

	public Button getBtnNao() {
		return btnNao;
	}

	public void setBtnNao(Button btnNao) {
		this.btnNao = btnNao;
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		launch(args);

	}

}
