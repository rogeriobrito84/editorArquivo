package main;

import java.math.BigDecimal;
import java.math.RoundingMode;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class Teste {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String texto = " tudo mais demais tudo mas demais tudo";
//		texto.
	}

	
	public static BigDecimal calcularProgressoBig(int total, int atual){
		BigDecimal resultado = null;
		resultado = BigDecimal.valueOf(atual).divide(BigDecimal.valueOf(total), 16,RoundingMode.HALF_EVEN);
		
		return resultado;
	}

	


//	@Override
//	public void start(Stage palco) throws Exception {
//		palco.setHeight(300);
//		palco.setWidth(400);
//		AnchorPane pane = new AnchorPane();
//		Scene cena = new Scene(pane);
//		
//		TextArea texto = new TextArea();
//		texto.minHeightProperty().bind(cena.heightProperty());
//		texto.minWidthProperty().bind(cena.widthProperty());
//		pane.getChildren().add(texto);
//		
//		palco.setScene(cena);
//		palco.show();
//		Thread.sleep(3000);
//		carregarTexto(texto);
//		
//	}
//	
//	public void carregarTexto(TextArea area){
//		System.out.println("Inicializando carregamento StringBuilder --------------");
//		StringBuilder arquivo = new StringBuilder();
//		for(int i = 0; i < 40000;i++){
//			arquivo.append(i + " hhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhh\n");
//		}
//		System.out.println("Texto carregado no StringBuilder --------------");
//		System.out.println("Inicializando carregamento no TextArea --------------");
//		area.setText(arquivo.toString());
//		System.out.println("Texto carregado no TextArea --------------");
//	}
}
