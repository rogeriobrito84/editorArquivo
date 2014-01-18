package main;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.TextArea;
import javafx.scene.control.ToolBar;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class ControleMain extends AnchorPane implements Initializable {
	@FXML
	private TextArea texto;
	@FXML
	private AnchorPane panePrincipal;
	@FXML
	private ToolBar menu;
	@FXML
	private Button btnNovo;
	@FXML
	private Button btnAtualizar;
	@FXML
	private Button btnLimpar;
	@FXML
	private Button btnSalvar;
	@FXML
	private CheckBox ckAuto;
	@FXML
	public ProgressIndicator progresso;
	// Atributos para aux
	@FXML
	private static DoubleProperty widthX = new SimpleDoubleProperty(500);
	@FXML
	private static DoubleProperty heightX = new SimpleDoubleProperty(500);
	// Atributos da classe
	private FileReader fr;
	private BufferedReader br;
	private BufferedWriter bw;
	private StringBuilder arquivoAnterior;
	private String urlArquivo = "";
	private long ultimaModficacao = 0;
	private long tempoParaAtualizacao = 2000;
	private boolean podeCarregarAtualizar = true;

	// constantes
	private static final String quebraLinha = System
			.getProperty("line.separator");

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		btnNovo.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				try {
					if (abrirNovoArquivo() != null) {
						carregarAtualizarArquivo();
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});

		btnAtualizar.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				try {
					carregarAtualizarArquivo();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});

		btnLimpar.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				limpar();
			}
		});

		btnSalvar.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				try {
					salvarArquivo();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});

		ckAuto.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				if (ckAuto.isSelected()) {
					iniciaAtualizador();
				}
			}
		});

		// Setando o tamnho do textArea
		texto.maxHeightProperty().bind(heightX.subtract(24));
		texto.minHeightProperty().bind(heightX.subtract(24));
		texto.minWidthProperty().bind(widthX);
		texto.maxWidthProperty().bind(widthX);

		// Setando o tamanho do menu
		menu.minWidthProperty().bind(widthX);
		menu.maxWidthProperty().bind(widthX);
		// Criando o arquivoAnterior
		arquivoAnterior = new StringBuilder();
	}

	public File abrirNovoArquivo() {
		FileChooser chooser = new FileChooser();
		FileChooser.ExtensionFilter extension = new FileChooser.ExtensionFilter(
				"Adicione arquivos (TXT)", "*.txt");
		chooser.getExtensionFilters().add(extension);
		File file = chooser
				.showOpenDialog((Stage) texto.getScene().getWindow());
		if (file != null) {
			urlArquivo = file.getAbsolutePath();
		}
		return file;
	}

	public void limpar() {
		 if(texto != null){
			 texto.clear();
		 }
		 ultimaModficacao = 0;
		 arquivoAnterior = new StringBuilder();
	}

	/**
	 * Função que cria uma tarefa para ficar atualizando o arquivo
	 */
	public void iniciaAtualizador() {
		Task<Void> tarefa = new TarefaAutoAtualizar();
		new Thread(tarefa).start();
	}

	// public void carregar() throws Exception{
	// limpar();
	// Task<Void> tarefa = new TarefaCarregarArquivo();
	// configurarProgresso(tarefa);
	// new Thread(tarefa).start();
	// }

	public void carregarAtualizarArquivo() {
		if (podeCarregarAtualizar) {
			Task<Void> tarefa = new TarefaCarreggarAtualizarArquivo();
			configurarProgresso(tarefa);
			new Thread(tarefa).start();
		}
	}

	// public class TarefaAtualizarArquivo extends Task<Void>{
	// @Override
	// protected Void call() throws Exception {
	// File file = recuperarArquivo(urlArquivo);
	// boolean recarregar = false;
	// if(file != null && file.canExecute()){
	// Long antes = file.lastModified();
	// String novoTexto = "";
	// if(ultimaModficacao != antes && file != null){//Verifica se o arquiv foi
	// modificado
	// StringBuilder resultado = new StringBuilder();
	// try {
	// int tamanhoArquivo = (int) ( file.length() + 2);//Tamanho do arquivo mais
	// 2 de memória do StringBuilder
	// System.out.println("Arquivo: " + tamanhoArquivo);
	// fr = new FileReader(file);
	// br = new BufferedReader(fr);
	// while(br.ready()){
	// resultado.append(br.readLine() + quebraLinha);
	// //updateProgress(resultado.length(), tamanhoArquivo);
	// }
	// System.out.println("Arquivo: " + resultado.length());
	// ultimaModficacao = file.lastModified();
	// StringBuilder arquivoAtual = resultado;
	// int limite = (arquivoAnterior.toString().length());
	// if(arquivoAtual.toString().length() >= limite){//Verifica se o que tinha
	// antes foi alterado
	// novoTexto = arquivoAtual.substring(0, limite);//Separa só o que tinha
	// antes no arquivo
	// if(novoTexto.equals(arquivoAnterior.toString())){//Verifica se o que
	// tinha antes é igual o que tem agora no arquivo
	// novoTexto = arquivoAtual.substring(novoTexto.length());//Separa só que
	// foi modificado depois do que já tinha
	// recarregar = false;
	// }else{
	// recarregar = true;
	// }
	// }else{
	// recarregar = true;
	// }
	// if(recarregar){
	// limpar();
	// arquivoAnterior = arquivoAtual;
	// texto.setText(arquivoAtual.toString());
	// }else{
	// arquivoAnterior.append(novoTexto);
	// texto.appendText(novoTexto);
	// }
	// }catch(Exception e){
	// e.printStackTrace();
	// }
	// }
	// }
	// return null;
	// }
	//
	// }

	class TarefaCarreggarAtualizarArquivo extends Task<Void> {
		@Override
		protected Void call() throws IOException {
			podeCarregarAtualizar = false;
			progresso.setVisible(true);
			boolean recarregarArquivo = false;
			File file;
			try {
				file = recuperarArquivo(urlArquivo);
				StringBuilder novoArquivo = null;
				String novoTexto = "";
				if (file != null) {
					if (ultimaModficacao != file.lastModified()) {
						fr = new FileReader(file);
						br = new BufferedReader(new InputStreamReader(new FileInputStream(file), "UTF-8"));
						int limiteProcesso = (int) (file.length() + 1);
						int tamanhoArquivoAnterior = arquivoAnterior.length();
						novoArquivo = new StringBuilder();
						while (br.ready()) {
							novoArquivo.append(br.readLine() + quebraLinha);
							limiteProcesso++;
							progresso.setProgress(calcularProgresso(limiteProcesso, novoArquivo.length()));
						}
						progresso.setProgress(1);
						if (novoArquivo.length() >= tamanhoArquivoAnterior) {
							novoTexto = novoArquivo.substring(0,
									tamanhoArquivoAnterior);
							if (novoTexto.equals(arquivoAnterior.toString())) {
								novoTexto = novoArquivo.substring(tamanhoArquivoAnterior);
							} else {
								recarregarArquivo = true;
							}
						} else {
							recarregarArquivo = true;
						}
						if (recarregarArquivo) {
							arquivoAnterior = novoArquivo;
							texto.setText(arquivoAnterior.toString());
						} else {
							arquivoAnterior.append(novoTexto);
							texto.appendText(novoTexto);
						}
						ultimaModficacao = file.lastModified();
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				progresso.setProgress(0);
				progresso.setVisible(false);
				try{
					br.close();
					fr.close();
				}catch(Exception e){}
				podeCarregarAtualizar = true;
			}
			return null;
		}
	}

	// public void atualizar() throws Exception{
	// File file = recuperarArquivo(urlArquivo);
	// boolean recarregar = false;
	// if(file != null){
	// Long antes = file.lastModified();
	// String novoTexto = "";
	// if(ultimaModficacao != antes && file != null){//Verifica se o arquiv foi
	// modificado
	// StringBuilder resultado = new StringBuilder();
	// try {
	// int tamanhoArquivo = (int) ( file.length() + 2);//Tamanho do arquivo mais
	// 2 de memória do StringBuilder
	// System.out.println("Arquivo: " + tamanhoArquivo);
	// fr = new FileReader(file);
	// br = new BufferedReader(fr);
	// while(br.ready()){
	// resultado.append(br.readLine() + quebraLinha);
	// //updateProgress(resultado.length(), tamanhoArquivo);
	// }
	// System.out.println("Arquivo: " + resultado.length());
	// ultimaModficacao = file.lastModified();
	// StringBuilder arquivoAtual = resultado;
	// int limite = (arquivoAnterior.toString().length());
	// if(arquivoAtual.toString().length() >= limite){//Verifica se o que tinha
	// antes foi alterado
	// novoTexto = arquivoAtual.substring(0, limite);//Separa só o que tinha
	// antes no arquivo
	// if(novoTexto.equals(arquivoAnterior.toString())){//Verifica se o que
	// tinha antes é igual o que tem agora no arquivo
	// novoTexto = arquivoAtual.substring(novoTexto.length());//Separa só que
	// foi modificado depois do que já tinha
	// recarregar = false;
	// }else{
	// recarregar = true;
	// }
	// }else{
	// recarregar = true;
	// }
	// if(recarregar){
	// limpar();
	// arquivoAnterior = arquivoAtual;
	// texto.setText(arquivoAtual.toString());
	// }else{
	// arquivoAnterior.append(novoTexto);
	// texto.appendText(novoTexto);
	// }
	// }catch(Exception e){
	// e.printStackTrace();
	// }
	//
	// }
	// }
	// }
	//

	private void salvarArquivo() {
		if(podeCarregarAtualizar){
			Task<Void> tarefa = new TarefaSalvarArquivo();
			new Thread(tarefa).start();
		}
	}

	class TarefaSalvarArquivo extends Task<Void> {
		@Override
		protected Void call() throws Exception {
			podeCarregarAtualizar = false;
			progresso.setVisible(true);
			File file = recuperarArquivo(urlArquivo);
			try {
				if (file != null && arquivoAnterior != null) {
					FileWriter fw = new FileWriter(file);
					bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file), "UTF-8"));
					if(arquivoAnterior.toString().contains("\n")){
						String[] linhas = arquivoAnterior.toString().split("\n");
						StringBuilder auxContador = new StringBuilder();
						for (String linha : linhas) {
							if(linha.isEmpty()){
								auxContador.append(quebraLinha);
								bw.write(quebraLinha);
							}else{
								auxContador.append(linha);
								bw.write(linha);
							}
							progresso.setProgress(calcularProgresso(arquivoAnterior.length(), auxContador.length()));
						}
					}else{
						bw.write(arquivoAnterior.toString());
					}
					progresso.setProgress(1);
				}
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
					progresso.setVisible(false);
					progresso.setProgress(0);
					try{
						fr.close();
						bw.close();
					}catch(Exception e){}
					podeCarregarAtualizar = true;
			}
			return null;
		}

	}

	public File recuperarArquivo(String urlArquivo) throws Exception {
		File file = null;
		if (!urlArquivo.equals("")) {
			file = new File(urlArquivo);
			if (!file.exists()) {
				try {
					file.createNewFile();
				} catch (IOException e) {
					throw new Exception("Erro ao criar arquivo");
				}
			}
		}
		return file;
	}

	// public class TarefaCarregarArquivo extends Task<Void>{
	// @Override
	// protected Void call() throws Exception {
	// StringBuilder resultado = null;
	// File file = recuperarArquivo(urlArquivo);
	// if(file != null && file.canExecute()){
	// resultado = new StringBuilder();
	// try {
	// int tamanhoArquivo = (int) ( file.length() + 2);//Tamanho do arquivo mais
	// 2 de memória do StringBuilder
	// System.out.println("Arquivo: " + tamanhoArquivo);
	// fr = new FileReader(file);
	// br = new BufferedReader(fr);
	// while(br.ready()){
	// resultado.append(br.readLine() + quebraLinha);
	// updateProgress(resultado.length(), tamanhoArquivo);
	// }
	// System.out.println("Arquivo: " + resultado.length());
	// ultimaModficacao = file.lastModified();
	// arquivoAnterior = resultado;
	// texto.setText(resultado.toString());
	// } catch (FileNotFoundException e) {
	// e.printStackTrace();
	// } catch (IOException e) {
	// e.printStackTrace();
	// }finally {
	// try {
	// fr.close();
	// br.close();
	// } catch (IOException e) {
	// e.printStackTrace();
	// }
	// }
	// }
	// return null;
	// }
	// }

	public void configurarProgresso(Task<Void> tarefa) {
		double centroX = (widthX.doubleValue() / 2)	- (progresso.getWidth() / 2);
		double centroY = (heightX.doubleValue() / 2)- (progresso.getHeight() / 2);
		progresso.setLayoutX(centroX);
		progresso.setLayoutY(centroY);
	}

	public class TarefaAutoAtualizar extends Task<Void> {
		@Override
		protected Void call() throws Exception {
			int i = 0;
			while (ckAuto.isSelected()) {
				System.out.println("Vericação: " + i++);
				try {
					carregarAtualizarArquivo();
					Thread.sleep(tempoParaAtualizacao);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			return null;
		}
	}

	public double calcularProgresso(int total, int atual) {
		double resultado = 0;
		if (total != 0) {
			resultado = BigDecimal
					.valueOf(atual)
					.divide(BigDecimal.valueOf(total), 2,
							RoundingMode.HALF_EVEN).doubleValue();
		}
		return resultado;
	}

	public static DoubleProperty heightXProperty() {
		return heightX;
	}

	public static DoubleProperty widthXProperty() {
		return widthX;
	}

}
