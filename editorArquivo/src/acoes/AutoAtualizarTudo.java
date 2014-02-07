package acoes;

import java.util.List;

import uitl.Util;

import main.ControleMain;
import model.Editor;

import javafx.scene.control.CheckBox;

public class AutoAtualizarTudo implements Runnable {
	private List<Editor> editores;
	private Util util;
	
	
	public AutoAtualizarTudo(List<Editor> editores, Util util){
		this.editores = editores;
		this.util = util;
	}
	
	@Override
	public void run() {
		for (Editor editor : editores) {
			try{
				if(editor.getFile() != null  && editor.isPodeCarregarAtualizar()){
					CarregarAtualizarArquivo atualizar = new CarregarAtualizarArquivo(editor, util);
					new Thread(atualizar).start();
				}
				Thread.sleep(500);
			}catch(Exception e){
				System.out.println("Erro ao executar Sleep na class AutoAtualizartudo linha: " + 32);
				editor.esconderProcesso();
			}
		}
	}

}
