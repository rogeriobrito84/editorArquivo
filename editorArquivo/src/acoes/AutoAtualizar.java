package acoes;

import java.util.List;

import uitl.Util;

import javafx.scene.control.CheckBox;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import model.Editor;

public class AutoAtualizar implements Runnable {
	private int id;
	private List<Editor> editores;
	private CheckBox checkAuto;
	private Util util;
	private TabPane tabPane;
	private long tempoParaAtualizacao;
	private Editor editor;
	
	public AutoAtualizar(List<Editor> editores, CheckBox checkAuto, Util util,TabPane tabPane, long tempoParaAtualizacao){
		this.editores = editores;
		this.checkAuto = checkAuto;
		this.util = util;
		this.tabPane = tabPane;
		this.tempoParaAtualizacao = tempoParaAtualizacao;
	}
	
	@Override
	public void run() {
		while(checkAuto.isSelected() && checkAuto != null){
			id = getIdTabSelecionado();
			try{ 
				if(id > 0){
					editor = getEditorPorId(id);
					if(editor != null & editor.getFile() != null & !editor.getFile().getAbsolutePath().isEmpty()){
						if(editor.isPodeCarregarAtualizar()){
							CarregarAtualizarArquivo atualizar = new CarregarAtualizarArquivo(editor, util);
							new Thread(atualizar).start();
						}
					}
				}
				Thread.sleep(tempoParaAtualizacao); 
			}catch(Exception e){}
		}

	}
	
	public int getIdTabSelecionado(){
		int id = -1;
		if(tabPane.getTabs().size() > 0){
			Tab tab =   tabPane.getSelectionModel().getSelectedItem();
			id = Integer.valueOf(tab.getId());
		}
		return id;
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
}
