package acoes;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

import model.Editor;
import uitl.Util;

public class CarregarAtualizarArquivo implements Runnable {
	private Editor editor;
	private BufferedReader br;
	private int limiteProcesso;
	private StringBuilder novoArquivo = null;
	private double percentual;
	private Util util; 
	private int quantidadeLinhas;
	private int tempoSleep;
	int quantidadeLinhasSleep;
	
	public CarregarAtualizarArquivo(Editor editor, Util util){
		this.editor = editor;
		this.util = util;
	}
	
	@Override
	public void run() {
		editor.setPodeCarregarAtualizar(false);
		String linha;
		try {
			if (editor.getFile() != null) {
				if (editor.getUltimaModficacao() != editor.getFile().lastModified()) {
					editor.centralizarMostrarProgresso();
					novoArquivo = new StringBuilder();
					editor.mostrarProcesso();
					tempoSleep = editor.getTempoAtualizacaoSleep();
					quantidadeLinhasSleep = editor.getQuantidadeLinhasTempo();
					limiteProcesso = (int) editor.getFile().length();
					quantidadeLinhas =0;
					br = new BufferedReader(new InputStreamReader(new FileInputStream(editor.getFile()), "UTF-8"));
					editor.getTexto().clear();
					while (br.ready()) {
						quantidadeLinhas++;
						linha = br.readLine();
						if(linha.isEmpty()){
							novoArquivo.append(util.getQuebralinha());
						}else{
							novoArquivo.append(linha);
							if(br.ready()){
								novoArquivo.append(util.getQuebralinha());
							}
						}
						if(quantidadeLinhas == quantidadeLinhasSleep){
							editor.centralizarMostrarProgresso();
							percentual = util.calcularProgresso(limiteProcesso, novoArquivo.length());
							if(percentual > 0.99){
								percentual -= 0.01;
							}
							editor.getProgresso().setProgress(percentual);
							quantidadeLinhas = 0;
							Thread.sleep(tempoSleep);
						}
					}
					editor.getTexto().setText(novoArquivo.toString());
					editor.getTexto().setPrefColumnCount(editor.getQuantidadeLinhasArquivo());
					editor.setUltimaModficacao(editor.getFile().lastModified());
					editor.getProgresso().setProgress(1);
				}
			}
		} catch (Exception e) {
//			System.out.println("Estourou no Carregar!");
//			e.printStackTrace();
			editor.esconderProcesso();
		} finally {
			if(br != null){
				try {
					br.close();
				} catch (IOException e) {
//					System.out.println("Estourou no Carregar!");
//					e.printStackTrace();
				}
			}
			editor.esconderProcesso();
			editor.setPodeCarregarAtualizar(true);
		}

	}

}
