package acoes;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

import javax.security.auth.callback.TextOutputCallback;

import uitl.Util;

import model.Editor;

public class CarregarAtualizarArquivo implements Runnable {
	private Editor editor;
	private BufferedReader br;
	private String novoTexto = "";
	private int limiteProcesso;
	private StringBuilder novoArquivo = null;
	private int quantidadeCaracteresTexto;
	private Util util; 
	
	public CarregarAtualizarArquivo(Editor editor, Util util){
		this.editor = editor;
		this.quantidadeCaracteresTexto = editor.getTexto().getText().length();
		this.util = util;
	}
	
	@Override
	public void run() {
		editor.setPodeCarregarAtualizar(false);
		editor.centralizarMostrarProgresso();
		boolean recarregarArquivo = false;
		try {
			if (editor.getFile() != null) {
				if (editor.getUltimaModficacao() != editor.getFile().lastModified()) {
					br = new BufferedReader(new InputStreamReader(new FileInputStream(editor.getFile()), "UTF-8"));
					limiteProcesso = (int) editor.getFile().length();
					novoArquivo = new StringBuilder();
					
					while (br.ready()) {
						novoArquivo.append(br.readLine() + util.getQuebralinha());
						editor.getProgresso().setProgress(util.calcularProgresso(limiteProcesso, novoArquivo.length()));
					}
					
					editor.getProgresso().setProgress(1);
					
					if (novoArquivo.length() >= editor.getTexto().getText().length()) {
						novoTexto = novoArquivo.substring(0,quantidadeCaracteresTexto);
						if (novoTexto.equals(editor.getTexto().getText())) {
							novoTexto = novoArquivo.substring(quantidadeCaracteresTexto);
						} else {
							recarregarArquivo = true;
						}
					} else {
						recarregarArquivo = true;
					}
					if (recarregarArquivo) {
						editor.getTexto().setText(novoArquivo.toString());
					} else {
						editor.getTexto().appendText(novoTexto);
					}
					editor.setUltimaModficacao(editor.getFile().lastModified());
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if(br != null){
				try {
					br.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			editor.esconderProcesso();
			editor.setPodeCarregarAtualizar(true);
		}

	}

}
