package main;


import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TabPane.TabClosingPolicy;
import javafx.scene.control.TextField;
import javafx.scene.control.ToolBar;
import javafx.scene.control.Tooltip;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import model.Editor;
import uitl.Util;
import acoes.AutoAtualizar;
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
	private MenuItem fechar;
	@FXML
	private MenuItem atualizar;
	@FXML
	private MenuItem atualizarTudo;
	@FXML
	private MenuItem limpar;
	@FXML
	private MenuItem limparSalvar;
	@FXML
	private TextField textoPesquisa;
	@FXML
	private Button btnAnterior;
	@FXML
	private Button btnProximo;
	@FXML 
	private ComboBox<String> fonteCombo;
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
	private Thread threadAtualizar;
	private double posicaoX;
	private double posicaoY;
	
	//Teclas de atalho
	private KeyCodeCombination ctrlNovo;
	private KeyCodeCombination ctrlAbrir;
	private KeyCodeCombination ctrlSalvar;
	private KeyCodeCombination ctrlShifttSalvarComo;
	private KeyCodeCombination altFechar;
	private KeyCodeCombination ctrlAtualizar;
	private KeyCodeCombination ctrlShiftAtualizarTudo;
	private KeyCodeCombination ctrlLimpar;
	private KeyCodeCombination ctrlShiftLimparSalvar;
	private KeyCodeCombination ctrlShiftAltAutoAtualizar;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
	
		Novo.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				try {
					novoArquivo();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		});

		abrir.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				try {
					abrirArquivo();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
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
		
		fechar.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				try {
					fecharTab();
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
		
		textoPesquisa.addEventHandler(KeyEvent.KEY_RELEASED, new EventHandler<KeyEvent>() {
			@Override
			public void handle(KeyEvent event) {
				abilitarPesquisa();
				if(event.getCode() == KeyCode.ENTER){
					if(proximo(textoPesquisa.getText())){
						textoPesquisa.setStyle("-fx-text-fill: black;fx-background-color:white;");
					}else{
						textoPesquisa.setStyle("-fx-text-fill: white;" +
								"-fx-background-color: linear-gradient(#ff5400, #be1d00);");
					}
				}
			}
		});
		
		textoPesquisa.textProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> observable,
					String TextoAntigo, String TextNovo) {
				 aumentarTextoPesquisa(TextNovo);
			}
		});
		
		
		
		btnAnterior.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				if(anterior(textoPesquisa.getText())){
					textoPesquisa.setStyle("-fx-text-fill: black;fx-background-color:white;");
				}else{
					textoPesquisa.setStyle("-fx-text-fill: white;" +
							"-fx-background-color: linear-gradient(#ff5400, #be1d00);");
				}
			}
		});
		
		btnProximo.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				if(proximo(textoPesquisa.getText())){
					textoPesquisa.setStyle("-fx-text-fill: black;fx-background-color:white;");
				}else{
					textoPesquisa.setStyle("-fx-text-fill: white;" +
							"-fx-background-color: linear-gradient(#ff5400, #be1d00);");
				}
			}
		});
		
		fonteCombo.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> observable,
					String antigoTamanho, String novoTamnanho) {
				System.out.println("Tamanho: " + novoTamnanho);
				mudarTamanhoFonte(novoTamnanho);
			}
		});
		
		checkAuto.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				if (checkAuto.isSelected()) {
					iniciaTarefaAutoAtualizar();
				}else{
					if(threadAtualizar != null){
						try{
							if(threadAtualizar.isAlive()){
								threadAtualizar.interrupt();
								threadAtualizar = null;
							}
						}catch(Exception e){
							
						}
					}
				}
			}
		});
		
		//Capturar eventos do teclado
		panePrincipal.setOnKeyPressed(new EventHandler<KeyEvent>() {
			@Override
			public void handle(KeyEvent event) {
				try {
					acessarAtalhos(event);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				
			}
		});
		
		checkAuto.setTooltip(new Tooltip("Auto Atualizar --> CTRL + SHIFT + ALT + T"));
		
		
		
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
		//Setando texto dos Butões próximo e anterior
		btnAnterior.setText("<");
		btnProximo.setText(">");
		//Setando o combobox do tamanho da fonte
//		ObservableList<String> tamnanhosFonte = FXCollections.observableArrayList(
//			"10","12","14","16","18","20","24","28","32"
//		);
//		fonteCombo.setItems(tamnanhosFonte);
		
		
		
		//Inicializando as teclas de atalho
		ctrlNovo = new KeyCodeCombination(KeyCode.N, KeyCombination.CONTROL_DOWN);
		ctrlAbrir = new KeyCodeCombination(KeyCode.O, KeyCombination.CONTROL_DOWN);
		ctrlSalvar = new KeyCodeCombination(KeyCode.S, KeyCombination.CONTROL_DOWN);
		ctrlShifttSalvarComo = new KeyCodeCombination(KeyCode.S,KeyCombination.CONTROL_DOWN, KeyCombination.SHIFT_DOWN);
		altFechar = new KeyCodeCombination(KeyCode.F, KeyCombination.ALT_DOWN);
		ctrlAtualizar = new KeyCodeCombination(KeyCode.T, KeyCombination.CONTROL_DOWN);
		ctrlShiftAtualizarTudo = new KeyCodeCombination(KeyCode.T, KeyCombination.CONTROL_DOWN, KeyCombination.SHIFT_DOWN);
		ctrlLimpar = new KeyCodeCombination(KeyCode.L, KeyCombination.CONTROL_DOWN);
		ctrlShiftLimparSalvar = new KeyCodeCombination(KeyCode.L, KeyCombination.CONTROL_DOWN, KeyCombination.SHIFT_DOWN);
		ctrlShiftAltAutoAtualizar = new KeyCodeCombination(KeyCode.T, KeyCombination.CONTROL_DOWN, KeyCombination.SHIFT_DOWN, KeyCombination.ALT_DOWN);
		
		
		
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
	
	public Editor novoArquivo() throws InterruptedException{
		contTabs++;
		Tab tab;
		Editor editor = new Editor(contTabs, widthX, heightY, fonteCombo.getSelectionModel().getSelectedItem()); 
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
		tab = getTab(editor.getId());
		tabPane.getSelectionModel().select(tab);
		abilitarDesabilitarMenu();
		return editor;
	}

	public void abrirArquivo() throws InterruptedException {
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
				Editor editorAux = getEditorEmOutraTab(id, editor.getFile().getAbsolutePath());
				if(editorAux != null){
					Tab tab = getTab(editorAux.getId());
					if(Integer.valueOf(tab.getId()) != getIdTabSelecionado()){
						removerEditor(editorAux.getId());
						tabPane.getTabs().remove(tab);
					}
					editor.setFile(editorAux.getFile());
				}
				editor.getTab().setText(editor.getFile().getName().replace(".txt", ""));
				temArquivo = true;
			}
		}else{
			temArquivo = true;
		}
		if(temArquivo){
			if(editor.isPodeCarregarAtualizar()){
				SalvarArquivo salvar = new SalvarArquivo(editor, util);
				new Thread(salvar).start();
			}
		}
	}
	
	public void salvarArquivoComo(){
		int id = getIdTabSelecionado();
		Editor editor = getEditorPorId(id);
		if(util.salvarNovoArquivo(editor)){
			Editor editorAux = getEditorEmOutraTab(id, editor.getFile().getAbsolutePath());
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
	
	public void fecharTab(){
		if(tabPane.getTabs().size() > 0){
			int id = getIdTabSelecionado();
			Tab tab = getTab(id);
			tabPane.getTabs().remove(tab);
			removerEditor(id);
			abilitarDesabilitarMenu();
		}
	}
	
	public void atualizarArquivo(){
		Editor editor;
		int id = getIdTabSelecionado();
		if(id > 0){
			editor = getEditorPorId(id);
			if(editor != null && editor.getFile() != null && editor.isPodeCarregarAtualizar()){
				try{
				CarregarAtualizarArquivo atualizar = new CarregarAtualizarArquivo(editor, util);
				new Thread(atualizar).start();
				}catch(Exception e){}
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
	
	public boolean proximo(String pesquisa){
		boolean localizou = false;
		int id = getIdTabSelecionado();
		if(id > 0){
			Editor editor = getEditorPorId(id);
			localizou =  editor.localizarProximo(pesquisa);
		}
		return localizou;
	}
	
	public boolean anterior(String pesquisa){
		boolean localizou = false;
		int id = getIdTabSelecionado();
		if(id > 0){
			Editor editor = getEditorPorId(id);
			localizou = editor.localizarAnterior(pesquisa);
		}
		return localizou;
	}
	

	/**
	 * Função que cria uma tarefa para ficar atualizando o arquivo
	 */
	public void iniciaTarefaAutoAtualizar() {
		if(editores.size() > 0){
			if(threadAtualizar == null){
				AutoAtualizar tarefaAutoAtualizar = new AutoAtualizar(editores, checkAuto, util, tabPane, tempoParaAtualizacao);
				threadAtualizar = new  Thread(tarefaAutoAtualizar);
				threadAtualizar.start();
			}
		}
		final Stage palco = (Stage) checkAuto.getScene().getWindow();
		palco.setOnCloseRequest(new EventHandler<WindowEvent>() {
			@Override
			public void handle(WindowEvent event) {
				try{
					if(threadAtualizar.isAlive()){
							checkAuto.setSelected(false);
							threadAtualizar.interrupt();
							threadAtualizar = null;
					}
				palco.close();
				}catch(Exception e){}
			}
		});
	}
	
	private void aumentarTextoPesquisa(String TextoNovo) {
		double larguraMin = 120;
		 double larguraAtual = textoPesquisa.getPrefWidth();
		 double LarguraNova = (TextoNovo.length() * 9);
		 double larguraMax = (tabPane.getWidth() - 390);
		 System.out.println("Scala: " + larguraAtual+ "Ltotal: " + tabPane.getWidth()+ "texto: " + TextoNovo.length());
		 if(LarguraNova >= larguraAtual && LarguraNova <= larguraMax){
			 textoPesquisa.setPrefWidth(LarguraNova);
		 }else if(LarguraNova <= larguraAtual && larguraAtual > larguraMin){
			 textoPesquisa.setPrefWidth(LarguraNova);
		 }else if(LarguraNova > larguraMax){
			 textoPesquisa.setPrefWidth(larguraMax);
		 }
	}
	
	public void mudarTamanhoFonte(String tamanho){
		for (Editor e : editores) {
			e.getTexto().setStyle("-fx-font-size: " + tamanho + ";");
		}
	}
	
	
	private void acessarAtalhos(KeyEvent key) throws InterruptedException{
		if(ctrlNovo.match(key)){
			novoArquivo();
		}
		if(ctrlAbrir.match(key)){
			abrirArquivo();
		}
		if(tabPane.getTabs().size() > 0){
			if(ctrlSalvar.match(key)){
				salvarArquivo();
			}
			
			if(ctrlShifttSalvarComo.match(key)){
				salvarArquivoComo();
			}
			
			if(altFechar.match(key)){
				fecharTab();
			}
			
			if(ctrlAtualizar.match(key)){
				atualizarArquivo();
			}
			
			if(ctrlShiftAtualizarTudo.match(key)){
				AtualizarTodosArquivos();
			}
			
			if(ctrlLimpar.match(key)){
				limpar();
			}
			
			if(ctrlShiftLimparSalvar.match(key)){
				limparSalvar();
			}
			
			if(ctrlShiftAltAutoAtualizar.match(key)){
				if(!checkAuto.isSelected()){
					checkAuto.setSelected(true);
					iniciaTarefaAutoAtualizar();
				}else{
					checkAuto.setSelected(false);
				}
			}
		}
		
	}
	
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
	
	private Editor getEditorEmOutraTab(int id, String nome){
		Editor editor = null;
		for(int i = 0; i < editores.size();i++){
			if(editores.get(i).getFile() != null){
				 if(editores.get(i).getFile().getAbsolutePath().equals(nome) && editores.get(i).getId() != id){
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
	/**
	 * Abilita as opções do menu apenas se tiver algum editor aberto.
	 */
	public void abilitarDesabilitarMenu(){
		if(tabPane.getTabs().size() > 0){
			salvar.setDisable(false);
			salvarComo.setDisable(false);
			fechar.setDisable(false);
			atualizar.setDisable(false);
			atualizarTudo.setDisable(false);
			limpar.setDisable(false);
			textoPesquisa.setDisable(false);
			checkAuto.setDisable(false);
			limparSalvar.setDisable(false);
		}else{
			salvar.setDisable(true);
			salvarComo.setDisable(true);
			fechar.setDisable(true);
			atualizar.setDisable(true);
			limpar.setDisable(true);
			textoPesquisa.setDisable(true);
			checkAuto.setDisable(true);
			atualizarTudo.setDisable(true);
			limparSalvar.setDisable(true);
			contTabs = 0;
		}
		abilitarPesquisa();
	}
	
	public void abilitarPesquisa(){
		if(textoPesquisa.getText().isEmpty()){
			btnAnterior.setDisable(true);
			btnProximo.setDisable(true);
		}else{
			btnAnterior.setDisable(false);
			btnProximo.setDisable(false);
		}
		textoPesquisa.setStyle("-fx-text-fill: black;fx-background-color:white;");
	}
	
	public static DoubleProperty heightXProperty() {
		return heightY;
	}

	public static DoubleProperty widthXProperty() {
		return widthX;
	}

}
