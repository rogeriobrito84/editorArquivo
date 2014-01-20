package model;

import java.io.File;

import javax.annotation.processing.Processor;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.ReadOnlyDoubleProperty;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.Tab;
import javafx.scene.control.TextArea;
import javafx.scene.layout.AnchorPane;

public class Editor {
	private int id;
	private File file;
	private long ultimaModficacao;
	private boolean podeCarregarAtualizar = true;
	private Tab tab;
	private AnchorPane pane;
	private TextArea texto;
	private ProgressIndicator progresso;
	private DoubleProperty largura;
	private DoubleProperty altura;
	
	public Editor(int id, DoubleProperty largura, DoubleProperty altura){
		this.pane = new AnchorPane();
		this.tab = new Tab();
		tab.setText("novo "+ id);
		tab.setId(String.valueOf(id));
		this.id = id;
		this.texto = new TextArea();
		this.progresso = new ProgressIndicator(0);
		progresso.setVisible(false);
		this.largura = largura;
		this.altura = altura;
		pane.getChildren().addAll(texto,progresso);
		
		System.out.println("Largura do pane: " + largura.doubleValue() + "       Altura: " + altura.doubleValue());
		
		pane.maxHeightProperty().bind(altura.subtract(62));
		pane.minHeightProperty().bind(altura.subtract(62));
		pane.minWidthProperty().bind(largura);
		pane.maxWidthProperty().bind(largura);
		
		texto.maxHeightProperty().bind(pane.maxHeightProperty());
		texto.minHeightProperty().bind(pane.minHeightProperty());
		texto.maxWidthProperty().bind(pane.maxWidthProperty());
		texto.minWidthProperty().bind(pane.minWidthProperty());
		System.out.println("Largura do texto" + texto.getMinWidth() + "      Altura do texto: " + texto.getMinHeight());
		
		tab.setContent(pane);
		
	}
	
	public void centralizarMostrarProgresso(){
		progresso.setProgress(0);
		progresso.setVisible(true);
		double tamanho;
		if(altura.doubleValue() >= largura.doubleValue()){
			tamanho = largura.subtract(38).doubleValue();
		}else{
			tamanho = altura.subtract(100).doubleValue();
		}
		progresso.setPrefSize(tamanho, tamanho);
		double centroX = (largura.doubleValue() / 2)	- (progresso.getWidth() / 2);
		double centroY = (altura.subtract(63).doubleValue()  / 2)- (progresso.getHeight() / 2);
		progresso.setLayoutX(centroX);
		progresso.setLayoutY(centroY);
	}
	
	public void esconderProcesso(){
		progresso.setVisible(false);
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public File getFile() {
		return file;
	}
	public void setFile(File file) {
		this.file = file;
	}
	public long getUltimaModficacao() {
		return ultimaModficacao;
	}
	public void setUltimaModficacao(long ultimaModficacao) {
		this.ultimaModficacao = ultimaModficacao;
	}
	public boolean isPodeCarregarAtualizar() {
		return podeCarregarAtualizar;
	}
	public void setPodeCarregarAtualizar(boolean podeCarregarAtualizar) {
		this.podeCarregarAtualizar = podeCarregarAtualizar;
	}
	public Tab getTab() {
		return tab;
	}
	public void setTab(Tab tab) {
		this.tab = tab;
	}
	public AnchorPane getPane() {
		return pane;
	}
	public void setPane(AnchorPane pane) {
		this.pane = pane;
	}
	public TextArea getTexto() {
		return texto;
	}
	public void setTexto(TextArea texto) {
		this.texto = texto;
	}
	public ProgressIndicator getProgresso() {
		return progresso;
	}
	public void setProgresso(ProgressIndicator progresso) {
		this.progresso = progresso;
	}
	
	
	
	
	
	
	}
