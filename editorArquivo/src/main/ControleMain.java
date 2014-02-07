package main;


import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import javax.swing.text.TabableView;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
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
import javafx.scene.control.SplitPane;
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
	private SplitPane split;
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
	private MenuItem dividir;
	@FXML
	private MenuItem comparar;
	@FXML
	private MenuItem pesquisar;
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

	private long tempoParaAtualizacao = 500;
	private List<Editor> editores;
	private Util util;
	private int contTabs;
	private Thread threadAtualizar;
	private TabPane tabPane1;
	private TabPane tabPane2;
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
	private KeyCodeCombination ctrlShiftCompararArquivos;
	private KeyCodeCombination ctrlDividirTela;
	private KeyCodeCombination ctrlPesquisar;
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
				try {
					atualizarArquivo();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		
		atualizarTudo.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				try {
					AtualizarTodosArquivos();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		
		dividir.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				try {
					dividirTela();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		comparar.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				try {
					compararArquivos();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		
		atualizarTudo.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				try {
					AtualizarTodosArquivos();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		
		pesquisar.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				try {
					pesquisar();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});

		limpar.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				try {
					limpar();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		
		limparSalvar.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				try {
					limparSalvar();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		
		textoPesquisa.addEventHandler(KeyEvent.KEY_RELEASED, new EventHandler<KeyEvent>() {
			@Override
			public void handle(KeyEvent event) {
				try {
					abilitarPesquisa();
					if(event.getCode() == KeyCode.ENTER){
						pesquisar();
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		
		textoPesquisa.textProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> observable,
					String TextoAntigo, String TextNovo) {
				try {
					aumentarTextoPesquisa(TextNovo);
				} catch (Exception e) {
					e.printStackTrace();
				} 
			}
		});
		
		
		
		btnAnterior.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				try {
					if(anterior(textoPesquisa.getText())){
						textoPesquisa.setStyle("-fx-text-fill: black;fx-background-color:white;");
					}else{
						textoPesquisa.setStyle("-fx-text-fill: white;" +
								"-fx-background-color: linear-gradient(#ff5400, #be1d00);");
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		
		btnProximo.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				try {
					if(proximo(textoPesquisa.getText())){
						textoPesquisa.setStyle("-fx-text-fill: black;fx-background-color:white;");
					}else{
						textoPesquisa.setStyle("-fx-text-fill: white;" +
								"-fx-background-color: linear-gradient(#ff5400, #be1d00);");
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		
		fonteCombo.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> observable,
					String antigoTamanho, String novoTamnanho) {
				try {
					mudarTamanhoFonte(novoTamnanho);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		
		checkAuto.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				try {
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
				} catch (Exception e) {
					e.printStackTrace();
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
		
		//instanciando tabPanes
		tabPane1 = new TabPane();
		tabPane2 = new TabPane();
		//Inicializando os editores
		editores = new ArrayList<Editor>();
		//Incializando a classe Util
		util = new Util();
		//Abilitando e desabilitando menus
		abilitarDesabilitarMenu();

		// Setando o tamnho do split
		split.maxHeightProperty().bind(heightY.subtract(27));
		split.minHeightProperty().bind(heightY.subtract(27));
		split.minWidthProperty().bind(widthX);
		split.maxWidthProperty().bind(widthX);
		split.getItems().add(tabPane1);
		
		
		
		// Setando o tamanho do menu
		menu.minWidthProperty().bind(widthX);
		menu.maxWidthProperty().bind(widthX);
		//Setando texto dos Butões próximo e anterior
		btnAnterior.setText("<");
		btnProximo.setText(">");
		//tirando os focus
		menuArquivo.setFocusTraversable(false);
		menuAcoes.setFocusTraversable(false);
		textoPesquisa.setFocusTraversable(false);
		fonteCombo.setFocusTraversable(false);
		
		//Inicializando as teclas de atalho
		ctrlNovo = new KeyCodeCombination(KeyCode.N, KeyCombination.CONTROL_DOWN);
		ctrlAbrir = new KeyCodeCombination(KeyCode.O, KeyCombination.CONTROL_DOWN);
		ctrlSalvar = new KeyCodeCombination(KeyCode.S, KeyCombination.CONTROL_DOWN);
		ctrlShifttSalvarComo = new KeyCodeCombination(KeyCode.S,KeyCombination.CONTROL_DOWN, KeyCombination.SHIFT_DOWN);
		altFechar = new KeyCodeCombination(KeyCode.F, KeyCombination.ALT_DOWN);
		ctrlAtualizar = new KeyCodeCombination(KeyCode.T, KeyCombination.CONTROL_DOWN);
		ctrlShiftAtualizarTudo = new KeyCodeCombination(KeyCode.T, KeyCombination.CONTROL_DOWN, KeyCombination.SHIFT_DOWN);
		ctrlDividirTela = new KeyCodeCombination(KeyCode.D, KeyCombination.CONTROL_DOWN);
		ctrlShiftCompararArquivos = new KeyCodeCombination(KeyCode.C,KeyCombination.CONTROL_DOWN, KeyCombination.SHIFT_DOWN);
		ctrlPesquisar = new KeyCodeCombination(KeyCode.P, KeyCombination.CONTROL_DOWN);
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
		getTabPane().getTabs().add(editor.getTab());
		editores.add(editor);
		adicionarEventoAoFechar(editor.getTab());
		
		tab = getTab(editor.getId());
		getTabPane().getSelectionModel().select(tab);
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
				getTabPane().getSelectionModel().select(tab);
			}else{
				editor = novoArquivo();
				editor.getTab().setText(file.getName());
				editor.setFile(file);
				if(editor.isPodeCarregarAtualizar()){
					CarregarAtualizarArquivo carregar = new CarregarAtualizarArquivo(editor, util);
					new Thread(carregar).start();
					tab = getTab(editor.getId());
					getTabPane().getSelectionModel().select(tab);
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
						getTabPane().getTabs().remove(tab);
					}
					editor.setFile(editorAux.getFile());
				}
				editor.getTab().setText(editor.getFile().getName());
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
					getTabPane().getTabs().remove(tab);
					removerEditor(editorAux.getId());
				}
				editor.setFile(editorAux.getFile());
			}
			if(editor.isPodeCarregarAtualizar()){
				editor.getTab().setText(editor.getFile().getName());
				SalvarArquivo salvar = new SalvarArquivo(editor, util);
				new Thread(salvar).start();
			}
		}			
	}
	
	public void fecharTab(){
		if(getTabPane().getTabs().size() > 0){
			int id = getIdTabSelecionado();
			Tab tab = getTab(id);
			getTabPane().getTabs().remove(tab);
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
	
	public void dividirTela(){
		Editor editor;
		int id;
		if(split.getItems().size() == 1 && tabPane1.getTabs().size() > 1){
			tabPane2 = new TabPane();
			 id = getIdTabSelecionado();
			 if(id > -1){
				 editor = getEditorPorId(id);
				 if(editor != null){
					 tabPane2.getTabs().add(editor.getTab());
					 tabPane1.getTabs().remove(editor.getTab());
					 split.getItems().add(tabPane2);
					 tabPane2.getSelectionModel().select(editor.getTab());
					 abilitarDesabilitarMenu();
				 }
			 }
		}
	}
	
	public void compararArquivos(){
		Editor editorSelecionado;
		Editor EditorNaoSelecionado;
		if(split.getItems().size() > 1){
			editorSelecionado = getEditorPorId(getIdTabSelecionado());
			EditorNaoSelecionado = getEditorPorId(getIdTabTabNaoSelecionado());
			editorSelecionado.compararArquivos(EditorNaoSelecionado);
		}
	}
	
	public void pesquisar(){
		if(proximo(textoPesquisa.getText())){
			textoPesquisa.setStyle("-fx-text-fill: black;fx-background-color:white;");
		}else{
			textoPesquisa.setStyle("-fx-text-fill: white;" +
					"-fx-background-color: linear-gradient(#ff5400, #be1d00);");
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
		int id= getIdTabSelecionado();
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
			localizou =  editor.localizarAnterior(pesquisa);
		}
		return localizou;
	}
	

	/**
	 * Função que cria uma tarefa para ficar atualizando o arquivo
	 */
	public void iniciaTarefaAutoAtualizar() {
		if(editores.size() > 0){
			if(threadAtualizar == null){
				AutoAtualizar tarefaAutoAtualizar = new AutoAtualizar(editores, checkAuto, util, tabPane1, tabPane2, tempoParaAtualizacao);
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
		 double larguraMax = (menu.getWidth() - 395);
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
		TabPane tabPane = getTabPane();
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
			
			if(ctrlPesquisar.match(key)){
				pesquisar();
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
			if(!dividir.isDisable()){
				if(ctrlDividirTela.match(key)){
					dividirTela();
				}
			}
			if(!comparar.isDisable()){
				if(ctrlShiftCompararArquivos.match(key)){
					compararArquivos();
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
		TabPane tabPane = getTabPane();
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
		TabPane tabPane = getTabPane();
		if(tabPane.getTabs().size() > 0){
			Tab tab =   tabPane.getSelectionModel().getSelectedItem();
			id = Integer.valueOf(tab.getId());
		}
		return id;
	}
	
	public int getIdTabTabNaoSelecionado(){
		TabPane tabPane = getTabPane();
		if(tabPane.equals(tabPane1)){
			tabPane = tabPane2;
		}else{
			tabPane = tabPane1;
		}
		int id = -1;
		if(tabPane.getTabs().size() > 0){
			Tab tab =   tabPane.getSelectionModel().getSelectedItem();
			id = Integer.valueOf(tab.getId());
		}
		return id;
	}
	
	public TabPane getTabPane(){
		TabPane tabPane = tabPane1;
		Editor editor;
		int id;
		if(tabPane2.getTabs().size() > 0){
			id = Integer.valueOf(tabPane2.getSelectionModel().getSelectedItem().getId());
			editor = getEditorPorId(id);
			if(editor != null){
				if(editor.getTexto().isFocused()){
					tabPane = tabPane2;
				}
			}
		}
		return tabPane;
	}
	
	public void adicionarEventoAoFechar(Tab tab){
		tab.setOnClosed(new EventHandler<Event>() {
			@Override 
			public void handle(Event event) {
				Tab tab = (Tab) event.getSource();
				int id = Integer.valueOf(tab.getId());
				removerEditor(id);
				abilitarDesabilitarMenu();
			}
		});
	}
	
	/**
	 * Abilita as opções do menu apenas se tiver algum editor aberto.
	 */
	public void abilitarDesabilitarMenu(){
		TabPane tabPane = getTabPane();
		if(tabPane.getTabs().size() > 0){
			salvar.setDisable(false);
			salvarComo.setDisable(false);
			fechar.setDisable(false);
			atualizar.setDisable(false);
			atualizarTudo.setDisable(false);
			pesquisar.setDisable(false);
			limpar.setDisable(false);
			textoPesquisa.setDisable(false);
			checkAuto.setDisable(false);
			limparSalvar.setDisable(false);
		}else{
			salvar.setDisable(true);
			salvarComo.setDisable(true);
			fechar.setDisable(true);
			atualizar.setDisable(true);
			pesquisar.setDisable(true);
			limpar.setDisable(true);
			textoPesquisa.setDisable(true);
			checkAuto.setDisable(true);
			atualizarTudo.setDisable(true);
			limparSalvar.setDisable(true);
			contTabs = 0;
		}
		if(tabPane1.getTabs().size() > 1 && split.getItems().size() < 2){
			dividir.setDisable(false);
		}else{
			dividir.setDisable(true);
		}
		if(split.getItems().size() > 1){
			comparar.setDisable(false);
			if(tabPane2.getTabs().size() < 1 || tabPane1.getTabs().size() < 1){
				removerDivisao();
				dividir.setDisable(true);
				comparar.setDisable(true);
			}
		}else{
			comparar.setDisable(true);
		}
		abilitarPesquisa();
	}
	
	public void removerDivisao(){
		for (Tab t : tabPane2.getTabs()) {
			removerEditor(Integer.valueOf(t.getId()));
		}
		split.getItems().remove(tabPane2);
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
