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
		try {
			if(editor.getFile() != null){
				editor.centralizarMostrarProgresso();
				editor.mostrarProcesso();
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
						editor.centralizarMostrarProgresso();
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
				editor.getProgresso().setProgress(1);
			}
		} catch (Exception e) {
			System.out.println("Erro na class SalvarArquivo linha: " + 69);
			e.printStackTrace();
		} finally {
			try{
				if(bw != null){
					bw.close();
				}
			}catch(Exception e){
				System.out.println("Erro ao fechar BufferedWrite na class SalvarArquivo linha: " + 77);
			}
			editor.esconderProcesso();
			editor.setUltimaModficacao(editor.getFile().lastModified());
			editor.setPodeCarregarAtualizar(true);
		}

	}

}
