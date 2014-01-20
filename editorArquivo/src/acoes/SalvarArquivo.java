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
	
	public SalvarArquivo(Editor editor, Util util){
		this.editor = editor;
		this.util = util;
	}
	
	@Override
	public void run() {
		editor.setPodeCarregarAtualizar(false);
		editor.centralizarProgresso();
		editor.getProgresso().setVisible(true);
		try {
			if(editor.getFile() != null){
				editor.getTab().setText(editor.getFile().getName());
				bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(editor.getFile()), "UTF-8"));
				if(editor.getTexto().getText().contains("\n")){
					String[] linhas = editor.getTexto().getText().toString().split("\n");
					contadorAux = new StringBuilder();
					for (String linha : linhas) {
						if(linha.isEmpty()){
							contadorAux.append(util.getQuebralinha());
							bw.write(util.getQuebralinha());
						}else{
							contadorAux.append(linha + util.getQuebralinha());
							bw.write(linha + util.getQuebralinha());
						}
						editor.getProgresso().setProgress(util.calcularProgresso(editor.getTexto().getText().length(), contadorAux.length()));
					}
				}else{
					bw.write(editor.getTexto().getText());
					editor.getProgresso().setProgress(util.calcularProgresso(editor.getTexto().getText().length(), contadorAux.length()));
				}
				
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
			editor.getProgresso().setVisible(true);
			editor.getProgresso().setProgress(0);
			editor.setPodeCarregarAtualizar(false);
		}

	}

}
