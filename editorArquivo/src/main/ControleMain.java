package main;


import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.CheckBox;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TabPane.TabClosingPolicy;
import javafx.scene.control.ToolBar;
import javafx.scene.layout.AnchorPane;
import model.Editor;
import uitl.Util;
import acoes.SalvarArquivo;

public class ControleMain extends AnchorPane implements Initializable {
	@FXML
	private AnchorPane panePrincipal;
	@FXML 
	private TabPane tabPane;
	@FXML
	private ToolBar menu;
	@FXML
	private MenuButton menuArquivo;
	@FXML
	private MenuButton menuAcoes;
	@FXML
	private MenuItem Novo;
	@FXML
	private MenuItem abrir;
	@FXML
	private MenuItem salvar;
	@FXML
	private MenuItem salvarComo;
	@FXML
	private MenuItem atualizar;
	@FXML
	private MenuItem limpar;
	@FXML
	private CheckBox checkAuto;
	
	
	
	// Atributos para aux
	@FXML
	private static DoubleProperty widthX = new SimpleDoubleProperty();
	@FXML
	private static DoubleProperty heightY = new SimpleDoubleProperty();
	
	// Atributos da classe

	private long tempoParaAtualizacao = 2000;
	private List<Editor> editores;
	private Util util;
	private int contTabs;
	private double posicaoX;
	private double posicaoY;

	// constantes
	private static final String quebraLinha = System.getProperty("line.separator");

	@Override
	public void initialize(URL location, ResourceBundle resources) {
	
		Novo.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				novoArquivo();
			}
		});

		atualizar.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				try {
					carregarAtualizarArquivo();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});

		limpar.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				limpar();
			}
		});

		salvar.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				try {
					salvarArquivo();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});

		checkAuto.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				if (checkAuto.isSelected()) {
					iniciaAtualizador();
				}
			}
		});
		//Inicializando os editores
		editores = new ArrayList<Editor>();
		//Incializando a classe Util
		util = new Util();
		//Abilitando e desabilitando menus
		abilitarDesabilitarMenu();

		// Setando o tamnho do tabPane
		tabPane.maxHeightProperty().bind(heightY.subtract(35));
		tabPane.minHeightProperty().bind(heightY.subtract(35));
		tabPane.minWidthProperty().bind(widthX);
		tabPane.maxWidthProperty().bind(widthX);
		tabPane.setTabClosingPolicy(TabClosingPolicy.ALL_TABS);

		// Setando o tamanho do menu
		menu.minWidthProperty().bind(widthX);
		menu.maxWidthProperty().bind(widthX);
		
//		menu.setOnMousePressed(new EventHandler<MouseEvent>() {
//			@Override
//			public void handle(MouseEvent event) {
//				Stage palco = (Stage) menu.getScene().getWindow();
//				posicaoX = event.getScreenX() - palco.getX();
//				posicaoY = event.getScreenY() - palco.getY();
//				
//			}
//		} );
//
//		menu.setOnMouseDragged(new EventHandler<MouseEvent>() {
//			@Override
//			public void handle(MouseEvent event) {
//				Stage palco = (Stage) menu.getScene().getWindow();
//				palco.setX(event.getScreenX() - posicaoX);
//				palco.setY(event.getScreenY() - posicaoY);
//			}
//		});

	}

	public void abrirNovoArquivo() {
		
	}

	public void limpar() {
//		 if(texto != null){
//			 texto.clear();
//		 }
//		 ultimaModficacao = 0;
//		 arquivoAnterior = new StringBuilder();

	}

	/**
	 * Função que cria uma tarefa para ficar atualizando o arquivo
	 */
	public void iniciaAtualizador() {
		//AutoAtualizar auto = new AutoAtualizar(editores, checkAuto, tempoParaAtualizacao)
	}
	
	public void novoArquivo(){
		contTabs++;
		final Editor editor = new Editor(contTabs, widthX, heightY); 
		tabPane.getTabs().add(editor.getTab());
		editores.add(editor);
		editor.getTab().setOnClosed(new EventHandler<Event>() {
			@Override
			public void handle(Event event) {
				removerEditor(editores, editor.getId());
				abilitarDesabilitarMenu();
			}
		});
		abilitarDesabilitarMenu();
	}
	
	public void carregarAtualizarArquivo() {
		
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

//	class TarefaCarreggarAtualizarArquivo implements Runnable {
//		public TarefaCarreggarAtualizarArquivo(){
//			
//		}
//		@Override
//		public void run() {
//			podeCarregarAtualizar = false;
//			progresso.setVisible(true);
//			boolean recarregarArquivo = false;
//			File file;
//			try {
//				file = recuperarArquivo(urlArquivo);
//				StringBuilder novoArquivo = null;
//				String novoTexto = "";
//				if (file != null) {
//					
//				}
//				if (ultimaModficacao != file.lastModified()) {
//					br = new BufferedReader(new InputStreamReader(new FileInputStream(file), "UTF-8"));
//					int limiteProcesso = (int) (file.length() + 1);
//					int tamanhoArquivoAnterior = arquivoAnterior.length();
//					novoArquivo = new StringBuilder();
//					while (br.ready()) {
//						novoArquivo.append(br.readLine() + quebraLinha);
//						limiteProcesso++;
//						progresso.setProgress(calcularProgresso(limiteProcesso, novoArquivo.length()));
//					}
//					progresso.setProgress(1);
//					if (novoArquivo.length() >= tamanhoArquivoAnterior) {
//						novoTexto = novoArquivo.substring(0,
//								tamanhoArquivoAnterior);
//						if (novoTexto.equals(arquivoAnterior.toString())) {
//							novoTexto = novoArquivo.substring(tamanhoArquivoAnterior);
//						} else {
//							recarregarArquivo = true;
//						}
//					} else {
//						recarregarArquivo = true;
//					}
//					if (recarregarArquivo) {
//						arquivoAnterior = novoArquivo;
//						texto.setText(arquivoAnterior.toString());
//					} else {
//						arquivoAnterior.append(novoTexto);
//						texto.appendText(novoTexto);
//					}
//					ultimaModficacao = file.lastModified();
//				}
//			} catch (Exception e) {
//				e.printStackTrace();
//			} finally {
//				progresso.setProgress(0);
//				progresso.setVisible(false);
//				try{
//					br.close();
//				}catch(Exception e){}
//				podeCarregarAtualizar = true;
//			}
//			
//			
//		}
//		
//	}

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
		boolean temArquivo = false;
		int index =   tabPane.getSelectionModel().getSelectedIndex();
		Tab tab = tabPane.getTabs().get(index);
		Editor editor = getEditor(Integer.parseInt(tab.getId()));
		if(editor.getFile() == null){
			if(util.salvarNovoArquivo(editor)){
				temArquivo = true;
			}			
		}else{
			temArquivo = true;
		}
		if(temArquivo){
			SalvarArquivo salvar = new SalvarArquivo(editor, util);
			new Thread(salvar).start();
		}
	}

//	class TarefaSalvarArquivo extends Task<Void> {
//		@Override
//		protected Void call() throws Exception {
//			podeCarregarAtualizar = false;
//			progresso.setVisible(true);
//			File file = recuperarArquivo(urlArquivo);
//			try {
//				StringBuilder contadorAux;
//				arquivoAnterior = new StringBuilder();
//				arquivoAnterior.append(texto.getText());
//				
//				bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file), "UTF-8"));
//				if(arquivoAnterior.toString().contains("\n")){
//					String[] linhas = arquivoAnterior.toString().split("\n");
//					StringBuilder auxContador = new StringBuilder();
//					for (String linha : linhas) {
//						if(linha.isEmpty()){
//							auxContador.append(quebraLinha);
//							bw.write(quebraLinha);
//						}else{
//							auxContador.append(linha);
//							bw.write(linha);
//						}
//						progresso.setProgress(calcularProgresso(arquivoAnterior.length(), auxContador.length()));
//					}
//				}else{
//					bw.write(arquivoAnterior.toString());
//				}
//				progresso.setProgress(1);
//			} catch (FileNotFoundException e) {
//				e.printStackTrace();
//			} catch (IOException e) {
//				e.printStackTrace();
//			} finally {
//					progresso.setVisible(false);
//					progresso.setProgress(0);
//					try{
//						bw.close();
//					}catch(Exception e){}
//					podeCarregarAtualizar = true;
//			}
//			return null;
//		}
//
//	}

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

//	public void configurarProgresso() {
//		double centroX = (widthX.doubleValue() / 2)	- (progresso.getWidth() / 2);
//		double centroY = (heightY.doubleValue() / 2)- (progresso.getHeight() / 2);
//		progresso.setLayoutX(centroX);
//		progresso.setLayoutY(centroY);
//	}

//	public class TarefaAutoAtualizar extends Task<Void> {
//		@Override
//		protected Void call() throws Exception {
//			int i = 0;
//			while (ckAuto.isSelected()) {
//				System.out.println("Vericação: " + i++);
//				try {
//					carregarAtualizarArquivo();
//					Thread.sleep(tempoParaAtualizacao);
//				} catch (Exception e) {
//					e.printStackTrace();
//				}
//			}
//			return null;
//		}
//	}

//	public double calcularProgresso(int total, int atual) {
//		double resultado = 0;
//		if (total != 0) {
//			resultado = BigDecimal
//					.valueOf(atual)
//					.divide(BigDecimal.valueOf(total), 2,
//							RoundingMode.HALF_EVEN).doubleValue();
//		}
//		return resultado;
//	}
	
	private void removerEditor(List<Editor> lista, int index){
		for(int i = index; i < lista.size();i++){
			if(lista.get(i).getId() == index){
				lista.remove(lista.get(i));
			}
		}
	}
	
	private Editor getEditor(int id){
		Editor editor = null;
		for(int i = 0; i < editores.size();i++){
			if(editores.get(i).getId() == id){
				editor = editores.get(i);
				break;
			}
		}
		return editor;
	}
	
	public void abilitarDesabilitarMenu(){
		if(tabPane.getTabs().size() > 0){
			salvar.setDisable(false);
			salvarComo.setDisable(false);
			atualizar.setDisable(false);
			limpar.setDisable(false);
			checkAuto.setDisable(false);
		}else{
			salvar.setDisable(true);
			salvarComo.setDisable(true);
			atualizar.setDisable(true);
			limpar.setDisable(true);
			checkAuto.setDisable(true);
		}
	}
	
	public static DoubleProperty heightXProperty() {
		return heightY;
	}

	public static DoubleProperty widthXProperty() {
		return widthX;
	}

}
