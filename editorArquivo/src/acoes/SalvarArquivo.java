package acoes;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;

import uitl.Util;

import model.Editor;

public class SalvarArquivo implements Runnable {
	private Editor editor;
	Util util;
	private StringBuilder contadorAux;
	private BufferedWriter bw;
	private int tamanhoArquivo;
	
	public SalvarArquivo(Editor editor, Util util){
		this.editor = editor;
		this.util = util;
	}
	
	@Override
	public void run() {
		editor.setPodeCarregarAtualizar(false);
		editor.centralizarMostrarProgresso();
		try {
			if(editor.getFile() != null && editor.getFile().canWrite()){
				bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(editor.getFile()), "UTF-8"));
				tamanhoArquivo = (editor.getTexto().getText().length());
				if(editor.getTexto().getText().contains("\n")){
					String[] linhas = editor.getTexto().getText().toString().split("\\n");
					contadorAux = new StringBuilder();
					
					for (String linha : linhas) {
						if(linha.isEmpty()){
							contadorAux.append(util.getQuebralinha());
							bw.write(util.getQuebralinha());
							tamanhoArquivo++;
						}else{
							contadorAux.append(linha);
							if(contadorAux.length() < tamanhoArquivo){
								contadorAux.append(util.getQuebralinha());
								bw.write(linha + util.getQuebralinha());
								tamanhoArquivo++;
							}else{
								bw.write(linha);
							}
						}
						editor.getProgresso().setProgress(util.calcularProgresso(tamanhoArquivo, contadorAux.length()));
					}
					if(contadorAux.length() < tamanhoArquivo){
						int inicio = (tamanhoArquivo - linhas.length) - (tamanhoArquivo - contadorAux.length());
						String enters = editor.getTexto().getText().substring(inicio);
						bw.write(enters);
					}
					
				}else{
					bw.write(editor.getTexto().getText());
				}
				editor.setUltimaModficacao(editor.getFile().lastModified());
				editor.getProgresso().setProgress(1);
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try{
				if(bw != null){
					bw.close();
				}
			}catch(Exception e){}
			editor.esconderProcesso();
			editor.setPodeCarregarAtualizar(true);
		}

	}

}
