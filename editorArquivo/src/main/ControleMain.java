package main;


import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import javafx.application.Platform;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.concurrent.Task;
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
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import model.Editor;
import uitl.Util;
import acoes.AutoAtualizarTudo;
import acoes.CarregarAtualizarArquivo;
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
	private MenuItem atualizarTudo;
	@FXML
	private MenuItem limpar;
	@FXML
	private MenuItem limparSalvar;
	@FXML
	private CheckBox checkAuto;
	
	
	
	// Atributos para aux
	@FXML
	private static DoubleProperty widthX = new SimpleDoubleProperty();
	@FXML
	private static DoubleProperty heightY = new SimpleDoubleProperty();
	
	// Atributos da classe

	private long tempoParaAtualizacao = 1000;
	private List<Editor> editores;
	private Util util;
	private int contTabs;
	private TarefaAutoAtualizar tarefaAutoAtualizar;
	private double posicaoX;
	private double posicaoY;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
	
		Novo.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				novoArquivo();
			}
		});

		abrir.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				abrirArquivo();
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
		
		salvarComo.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				try {
					salvarArquivoComo();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		
		atualizar.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				atualizarArquivo();
			}
		});
		
		atualizarTudo.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				AtualizarTodosArquivos();
			}
		});

		limpar.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				limpar();
			}
		});
		
		limparSalvar.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				limparSalvar();
			}
		});

		checkAuto.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				if (checkAuto.isSelected()) {
					iniciaTarefaAutoAtualizar();
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
	
	public Editor novoArquivo(){
		contTabs++;
		Editor editor = new Editor(contTabs, widthX, heightY); 
		tabPane.getTabs().add(editor.getTab());
		editores.add(editor);
		editor.getTab().setOnClosed(new EventHandler<Event>() {
			@Override 
			public void handle(Event event) {
				Tab tab = (Tab) event.getSource();
				int id = Integer.valueOf(tab.getId());
				removerEditor(id);
				abilitarDesabilitarMenu();
				
			}
		});
		abilitarDesabilitarMenu();
		return editor;
	}

	public void abrirArquivo() {
		Editor editor;
		Stage palco =  (Stage) menu.getScene().getWindow();
		File file = util.abrirNovoArquivo(palco);
		Tab tab;
		if(file != null){
			editor = getEditorPorNome(file.getAbsolutePath());
			if(editor != null){
				tab = getTab(editor.getId());
				tabPane.getSelectionModel().select(tab);
			}else{
				editor = novoArquivo();
				editor.getTab().setText(file.getName().replace(".txt", ""));
				editor.setFile(file);
				if(editor.isPodeCarregarAtualizar()){
					CarregarAtualizarArquivo carregar = new CarregarAtualizarArquivo(editor, util);
					new Thread(carregar).start();
					tab = getTab(editor.getId());
					tabPane.getSelectionModel().select(tab);
				}
			}
		}
	}

	
	private void salvarArquivo() {
		boolean temArquivo = false;
		int id = getIdTabSelecionado();
		Editor editor = getEditorPorId(id);
		if(editor.getFile() == null){
			if(util.salvarNovoArquivo(editor)){
				Editor editorAux = getEditorPorNome(editor.getFile().getAbsolutePath());
				if(editorAux != null){
					Tab tab = getTab(editorAux.getId());
					if(Integer.valueOf(tab.getId()) != getIdTabSelecionado()){
						removerEditor(editorAux.getId());
						tabPane.getTabs().remove(tab);
					}
					if(editor.isPodeCarregarAtualizar()){
						editor.setFile(editorAux.getFile());
						editor.getTab().setText(editor.getFile().getName().replace(".txt", ""));
						temArquivo = true;
					}
				}
			}
		}else{
			temArquivo = true;
		}
		if(temArquivo){
			SalvarArquivo salvar = new SalvarArquivo(editor, util);
			new Thread(salvar).start();
		}
	}
	
	public void salvarArquivoComo(){
		int id = getIdTabSelecionado();
		Editor editor = getEditorPorId(id);
		if(util.salvarNovoArquivo(editor)){
			Editor editorAux = getEditorPorNome(editor.getFile().getAbsolutePath());
			if(editorAux != null){
				Tab tab = getTab(editorAux.getId());
				if(Integer.valueOf(tab.getId()) != getIdTabSelecionado()){
					tabPane.getTabs().remove(tab);
					removerEditor(editorAux.getId());
				}
				editor.setFile(editorAux.getFile());
			}
			if(editor.isPodeCarregarAtualizar()){
				editor.getTab().setText(editor.getFile().getName().replace(".txt", ""));
				SalvarArquivo salvar = new SalvarArquivo(editor, util);
				new Thread(salvar).start();
			}
		}			
	}
	
	public void atualizarArquivo(){
		Editor editor;
		int id = getIdTabSelecionado();
		if(id > 0){
			editor = getEditorPorId(id);
			if(editor != null && editor.getFile() != null && editor.isPodeCarregarAtualizar()){
				CarregarAtualizarArquivo atualizar = new CarregarAtualizarArquivo(editor, util);
				new Thread(atualizar).start();
			}
		}
	}
	
	public void AtualizarTodosArquivos(){
		if(editores.size() > 0){
			AutoAtualizarTudo atualizarTudo = new AutoAtualizarTudo(editores, util);
			new Thread(atualizarTudo).start();
		}
	}
	
	public void limpar() {
		int id = getIdTabSelecionado();
		if(id > 0){
			Editor editor = getEditorPorId(id);
			editor.getTexto().clear();
		}
	}
	
	public void limparSalvar(){
		int id = getIdTabSelecionado();
		if(id > 0){
			Editor editor = getEditorPorId(id);
			editor.getTexto().clear();
			if(editor.getFile() != null && editor.isPodeCarregarAtualizar()){
				SalvarArquivo salvar = new SalvarArquivo(editor, util);
				new Thread(salvar).start();
			}
		}
	}
	

	/**
	 * Função que cria uma tarefa para ficar atualizando o arquivo
	 */
	public void iniciaTarefaAutoAtualizar() {
		if(editores.size() > 0){
			tarefaAutoAtualizar = new TarefaAutoAtualizar();
			new  Thread(tarefaAutoAtualizar).start();
		}
		Stage palco = (Stage) checkAuto.getScene().getWindow();
		Platform.setImplicitExit(false);

		palco.setOnCloseRequest(new EventHandler<WindowEvent>() {
			@Override
			public void handle(WindowEvent event) {
				if(tarefaAutoAtualizar.isRunning()){
					tarefaAutoAtualizar.cancel();
					Platform.exit();
					event.consume();
				}
			}
		});
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

//	public File recuperarArquivo(String urlArquivo) throws Exception {
//		File file = null;
//		if (!urlArquivo.equals("")) {
//			file = new File(urlArquivo);
//			if (!file.exists()) {
//				try {
//					file.createNewFile();
//				} catch (IOException e) {
//					throw new Exception("Erro ao criar arquivo");
//				}
//			}
//		}
//		return file;
//	}

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
	
	private void removerEditor(int id){
		for(int i = 0; i < editores.size();i++){
			if(editores.get(i).getId() == id){
				editores.remove(editores.get(i));
				break;
			}
		}
	}
	
	private Editor getEditorPorId(int id){
		Editor editor = null;
		for(int i = 0; i < editores.size();i++){
			if(editores.get(i).getId() == id){
				editor = editores.get(i);
				break;
			}
		}
		return editor;
	}
	
	private Editor getEditorPorNome(String nome){
		Editor editor = null;
		for(int i = 0; i < editores.size();i++){
			if(editores.get(i).getFile() != null){
				 if(editores.get(i).getFile().getAbsolutePath().equals(nome)){
					 editor = editores.get(i);
					 break;
				 }
			}
		}
		return editor;
	}
	
	public Tab getTab(int id){
		Tab tab = null;
		for(int i = 0; i < tabPane.getTabs().size(); i++){
			if(tabPane.getTabs().get(i).getId().equals(String.valueOf(id))){
				tab = tabPane.getTabs().get(i);
				break;
			}
		}
		return tab;
	}
	
	public int getIdTabSelecionado(){
		int id = -1;
		if(tabPane.getTabs().size() > 0){
			Tab tab =   tabPane.getSelectionModel().getSelectedItem();
			id = Integer.valueOf(tab.getId());
		}
		return id;
	}
	
	public void abilitarDesabilitarMenu(){
		if(tabPane.getTabs().size() > 0){
			salvar.setDisable(false);
			salvarComo.setDisable(false);
			atualizar.setDisable(false);
			atualizarTudo.setDisable(false);
			limpar.setDisable(false);
			checkAuto.setDisable(false);
		}else{
			salvar.setDisable(true);
			salvarComo.setDisable(true);
			atualizar.setDisable(true);
			limpar.setDisable(true);
			checkAuto.setDisable(true);
			atualizarTudo.setDisable(true);
		}
	}
	
	public class TarefaAutoAtualizar extends Task<Void>{
		private int id;
		private Editor editor;
		@Override
		protected Void call() throws Exception {
			while(checkAuto.isSelected()){
				id = getIdTabSelecionado();
				if(id > 0){
					editor = getEditorPorId(id);
					if(editor != null && editor.getFile() != null){
						if(editor.isPodeCarregarAtualizar()){
							CarregarAtualizarArquivo atualizar = new CarregarAtualizarArquivo(editor, util);
							new Thread(atualizar).start();
							System.out.println("Thread para :" + editor.getFile().getName() + " levantada!");
						}
					}
				}
				Thread.sleep(tempoParaAtualizacao);
			}
			return null;
		}
		
	}
	
	public static DoubleProperty heightXProperty() {
		return heightY;
	}

	public static DoubleProperty widthXProperty() {
		return widthX;
	}

}
