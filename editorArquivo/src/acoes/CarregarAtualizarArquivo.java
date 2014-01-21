package acoes;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

import javafx.scene.control.TextArea;
import model.Editor;
import uitl.Util;

public class CarregarAtualizarArquivo implements Runnable {
	private Editor editor;
	private BufferedReader br;
	private String novoTexto = "";
	private int limiteProcesso;
	private StringBuilder novoArquivo = null;
	private int quantidadeCaracteresTexto;
	private TextArea areaAuxiliar;
	private Util util; 
	
	public CarregarAtualizarArquivo(Editor editor, Util util){
		this.editor = editor;
		this.util = util;
	}
	
	@Override
	public void run() {
		editor.setPodeCarregarAtualizar(false);
		editor.centralizarMostrarProgresso();
		boolean recarregarArquivo = false;
		String linha;
		try {
			if (editor.getFile() != null && editor.getFile().canRead()) {
				if (editor.getUltimaModficacao() != editor.getFile().lastModified()) {
					br = new BufferedReader(new InputStreamReader(new FileInputStream(editor.getFile()), "UTF-8"));
					limiteProcesso = (int) editor.getFile().length();
					quantidadeCaracteresTexto = editor.getTexto().getText().length();
					novoArquivo = new StringBuilder();
					
					while (br.ready()) {
						linha = br.readLine();
						if(linha.isEmpty()){
							novoArquivo.append(util.getQuebralinha());
							quantidadeCaracteresTexto++;
						}else{
							novoArquivo.append(linha);
							if(novoArquivo.length() < limiteProcesso){
								novoArquivo.append(util.getQuebralinha());
								quantidadeCaracteresTexto++;
							}
						}
						editor.getProgresso().setProgress(util.calcularProgresso(limiteProcesso, novoArquivo.length()));
					}
					
					if (novoArquivo.length() >= quantidadeCaracteresTexto) {
						novoTexto = novoArquivo.substring(0,quantidadeCaracteresTexto);
						areaAuxiliar = new TextArea();
						areaAuxiliar.appendText(novoTexto);
						if (areaAuxiliar.getText().equals(editor.getTexto().getText())) {
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
