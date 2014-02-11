package model;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.LineNumberReader;

import javafx.animation.FadeTransition;
import javafx.beans.property.DoubleProperty;
import javafx.scene.Cursor;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.Tab;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextInputControl;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.util.Duration;

public class Editor {
	private int id;
	private File file;
	private long ultimaModficacao;
	private boolean podeCarregarAtualizar = true;
	private Tab tab;
	private AnchorPane pane;
	private Pane lightBack;
	private TextArea texto;
	private ProgressIndicator progresso;
	private DoubleProperty largura;
	private DoubleProperty altura;
	private int ultimaPosicaoCursor;
	
	public Editor(int id, DoubleProperty largura, DoubleProperty altura, String tamanhoFonte) throws InterruptedException{
		this.pane = new AnchorPane();
		this.tab = new Tab();
		this.lightBack = new Pane();
		this.largura = largura;
		this.altura = altura;
		this.texto = new TextArea();
		this.id = id;
		this.progresso = new ProgressIndicator(0);
		progresso.setVisible(false);
		tab.setText("novo "+ id);
		tab.setId(String.valueOf(id));
		pane.getChildren().addAll(texto, lightBack, progresso);
		//Configurando o tamanho da �rea da Tab
		pane.maxHeightProperty().bind(altura.subtract(54));
		pane.minHeightProperty().bind(altura.subtract(54));
		pane.minWidthProperty().bind(largura);
		pane.maxWidthProperty().bind(largura);
		//Configurando o tamanho do TextArea
		texto.maxHeightProperty().bind(pane.maxHeightProperty());
		texto.minHeightProperty().bind(pane.minHeightProperty());
		texto.maxWidthProperty().bind(pane.maxWidthProperty());
		texto.minWidthProperty().bind(pane.minWidthProperty());
		texto.setStyle("-fx-font-size: " + tamanhoFonte + ";");
		texto.setWrapText(true);
		
		
		
		//Configurando o pane para o lightBack
		lightBack.maxHeightProperty().bind(pane.maxHeightProperty());
		lightBack.minHeightProperty().bind(pane.minHeightProperty());
		lightBack.maxWidthProperty().bind(pane.maxWidthProperty());
		lightBack.minWidthProperty().bind(pane.minWidthProperty());
		lightBack.setVisible(false);
		
		tab.setContent(pane);
		
	}
	
	public void centralizarMostrarProgresso(){
		double tamanho;
		if(altura.doubleValue() >= largura.doubleValue()){
			tamanho = largura.subtract(58).doubleValue();
		}else{
			tamanho = altura.subtract(120).doubleValue();
		}
		progresso.setPrefSize(tamanho, tamanho);
		double centroX = (largura.doubleValue() / 2)	- (progresso.getWidth() / 2);
		double centroY = (altura.subtract(63).doubleValue()  / 2)- (progresso.getHeight() / 2);
		progresso.setLayoutX(centroX);
		progresso.setLayoutY(centroY);
	}
	
	public int getQuantidadeLinhasArquivo() throws Exception{
		int quantidade = 0;
		if(file != null && !file.getAbsolutePath().isEmpty()){
			LineNumberReader linhaLeitura = new LineNumberReader(new FileReader(file));  
			linhaLeitura.skip(file.length());  
			quantidade = (linhaLeitura.getLineNumber() +1);  
		}
		return quantidade;
	}
	
	public int getTempoAtualizacaoSleep() throws Exception{
		int tempo = 0;
		int linhas = 0;
		linhas = getQuantidadeLinhasArquivo();
		if(linhas >= 10 && linhas <= 100){
			tempo =  Math.round(linhas / 2);
		}else if(linhas > 100 && linhas <= 300){
			tempo = Math.round(linhas / 10);
		}else if(linhas > 300 && linhas <= 500){
			tempo = Math.round(linhas / 30);
		}else if(linhas > 500 && linhas <= 1000){
			tempo = Math.round(linhas / 50);
		}else if(linhas > 1000 && linhas <= 5000){
			tempo = Math.round(linhas / 200);
		}else if(linhas > 5000 && linhas <= 80000){
			tempo = Math.round(linhas / 250);
		}else if(linhas > 8000 && linhas <= 10000){
			tempo = Math.round(linhas / 300);
		}else if(linhas > 10000 && linhas <= 20000){
			tempo = Math.round(linhas / 9000);
		}else if(linhas > 20000 && linhas <= 50000){
			tempo = Math.round(linhas / 1000);
		}else if(linhas > 50000){
			tempo = Math.round(linhas / 1500);
		}else{
			tempo = linhas;
		}
		System.out.println(tempo);
		return tempo;
	}
	
	public int getQuantidadeLinhasTempo() throws Exception{
		int tempo = 0;
		int linhas = 0;
		linhas = getQuantidadeLinhasArquivo();
		if(linhas >= 10 && linhas < 4000){
			tempo =  Math.round(linhas / 10);
		}else if(linhas > 4000 && linhas <= 1000){
			tempo =  Math.round(linhas / 20);
		}else if(linhas > 10000){
			tempo = Math.round(linhas / 20);
		}else{
			tempo = linhas;
		}
		return tempo;
	}
	
	public boolean localizarAnterior(String pesquisa){
		setPodeCarregarAtualizar(false);
		mostrarProcesso();
		progresso.setProgress(2);
		boolean localizou = true;
		if(texto.getSelection().getEnd() == ultimaPosicaoCursor){
			texto.positionCaret(texto.getSelection().getStart()); 
		}
		int posicaoTexto = texto.getText(0, texto.getAnchor()).lastIndexOf(pesquisa);
		if(posicaoTexto > -1){
			texto.positionCaret(posicaoTexto);
			texto.selectRange(posicaoTexto, (posicaoTexto+pesquisa.length()));
            ultimaPosicaoCursor = (posicaoTexto + texto.getSelectedText().length());
		}else{
			if(texto.getText().contains(pesquisa)){
				texto.end();
				localizarAnterior(pesquisa);
			}else{
				localizou = false;
			}
		}
		esconderProcesso();
		setPodeCarregarAtualizar(true);
		return localizou;
	}
	
	public boolean localizarProximo(String pesquisa){
		setPodeCarregarAtualizar(false);
		mostrarProcesso();
		progresso.setProgress(2);
		boolean localizou = true;
		if(texto.getSelection().getEnd() == ultimaPosicaoCursor){
			texto.positionCaret(ultimaPosicaoCursor);
		}
		int posicaoTexto = texto.getText().indexOf(pesquisa, texto.getAnchor());
		if(posicaoTexto > -1){
			texto.positionCaret(posicaoTexto);
            texto.selectRange(posicaoTexto, (posicaoTexto + pesquisa.length()));
            ultimaPosicaoCursor = (posicaoTexto + texto.getSelectedText().length());
		}else{
			if(texto.getText().contains(pesquisa)){
				texto.positionCaret(0);
				ultimaPosicaoCursor = 0;
				localizarProximo(pesquisa);
			}else{
				localizou = false;
			}
		}
		esconderProcesso();
		setPodeCarregarAtualizar(true);
		return localizou;
	}
	
	public boolean compararArquivos(Editor editor){
		setPodeCarregarAtualizar(false);
		mostrarProcesso();
		boolean igual = true;
		String textoAuxiliar = "";
		TextArea pesquisa = editor.getTexto();
		int tamanhoTexto;
			if(texto.getLength() < pesquisa.getLength()){
				tamanhoTexto = texto.getLength();
				textoAuxiliar = pesquisa.getText(0, texto.getLength());
			}else{
				tamanhoTexto = pesquisa.getLength();
				textoAuxiliar = pesquisa.getText();
			}
			
			for(int i = 0; i <= tamanhoTexto; i++){
				if(!texto.getText().substring(0, i).equals(textoAuxiliar.substring(0, i))){
					texto.selectPositionCaret((i-1));
					texto.selectRange((i-1), texto.getLength());
					break;
				}
			}
		esconderProcesso();
		setPodeCarregarAtualizar(true);
		return igual;
	}
	
	
	
	
	public void mostrarProcesso(){
		progresso.setProgress(0);
		progresso.setVisible(true);
		lightBack.setVisible(true);
	}
	public void esconderProcesso(){
		progresso.setVisible(false);
		lightBack.setVisible(false);
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
