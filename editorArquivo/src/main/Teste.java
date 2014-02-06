package main;

import java.math.BigDecimal;
import java.math.RoundingMode;

import javafx.application.Application;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.SplitPane;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextArea;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class Teste extends Application{

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		launch();
	
	}

	
	public static BigDecimal calcularProgressoBig(int total, int atual){
		BigDecimal resultado = null;
		resultado = BigDecimal.valueOf(atual).divide(BigDecimal.valueOf(total), 16,RoundingMode.HALF_EVEN);
		
		return resultado;
	}

	


	@Override
	public void start(Stage palco) throws Exception {
		palco.setHeight(300);
		palco.setWidth(400);
		AnchorPane pane = new AnchorPane();
		Scene cena = new Scene(pane);
		
		final SplitPane split = new SplitPane();
		split.minHeightProperty().bind(cena.heightProperty().subtract(200));
		split.minWidthProperty().bind(cena.widthProperty());
		pane.getChildren().add(split);
		
		final TabPane tabPane1 = new TabPane();
		
		final TabPane tabPane2 = new TabPane();
		
		
		
		Tab tab1 = new Tab();
		tab1.setText("Tab 1");
		AnchorPane cont1 = new AnchorPane();
		TextArea area1 = new TextArea();
		cont1.getChildren().add(area1);
		tab1.setContent(cont1);
		
		Tab tab2 = new Tab();
		tab2.setText("Tab 2");
		tab2.setContent(new AnchorPane());
		AnchorPane cont2 = new AnchorPane();
		TextArea area2 = new TextArea();
		cont2.getChildren().add(area2);
		tab2.setContent(cont2);
		
		tabPane1.getTabs().add(tab1);
		tabPane2.getTabs().add(tab2);
		
		split.getItems().addAll(tabPane1,tabPane2);
		cena.setOnMouseClicked(new EventHandler<Event>() {
			@Override
			public void handle(Event event) {
				if(tabPane2.isFocusTraversable()){
					
				}
			}
		});
		
		
		
		palco.setScene(cena);
		palco.show();
	}
	
	
	
	public void carregarTexto(TextArea area){
		System.out.println("Inicializando carregamento StringBuilder --------------");
		StringBuilder arquivo = new StringBuilder();
		for(int i = 0; i < 40000;i++){
			arquivo.append(i + " hhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhh\n");
		}
		System.out.println("Texto carregado no StringBuilder --------------");
		System.out.println("Inicializando carregamento no TextArea --------------");
		area.setText(arquivo.toString());
		System.out.println("Texto carregado no TextArea --------------");
	}
}
