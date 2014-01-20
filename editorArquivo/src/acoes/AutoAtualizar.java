package acoes;

import java.util.List;

import uitl.Util;

import main.ControleMain;
import model.Editor;

import javafx.scene.control.CheckBox;

public class AutoAtualizar implements Runnable {
	private CheckBox checkAuto;
	private List<Editor> editores;
	private long tempoParaAtualizacao;
	private Util util;
	
	public AutoAtualizar(List<Editor> editores, CheckBox checkAuto, long tempoParaAtualizacao){
		this.tempoParaAtualizacao = tempoParaAtualizacao;
		this.checkAuto = checkAuto;
		this.editores = editores;
		this.util = new Util();
	}
	
	@Override
	public void run() {
		int i = 0;
		while (checkAuto.isSelected()) {
			System.out.println("Vericação: " + i++);
			try {
				for (Editor editor : editores) {
					CarregarAtualizarArquivo caa = new CarregarAtualizarArquivo(editor, util);
					new Thread(caa).start();
				}
				Thread.sleep(tempoParaAtualizacao);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

	}

}
