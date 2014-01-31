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
	private int quantidadeCaracteresTexto;
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
					quantidadeCaracteresTexto = 0;
					br = new BufferedReader(new InputStreamReader(new FileInputStream(editor.getFile()), "UTF-8"));
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
							quantidadeCaracteresTexto += novoArquivo.length();
							editor.getProgresso().setProgress(util.calcularProgresso(limiteProcesso, quantidadeCaracteresTexto));
							//editor.getTexto().appendText(novoArquivo.toString());
							quantidadeLinhas = 0;
							//novoArquivo = new StringBuilder();
							Thread.sleep(tempoSleep);
						}
					}
					
					editor.getTexto().setText(novoArquivo.toString());
					editor.getProgresso().setProgress(1);
					editor.setUltimaModficacao(editor.getFile().lastModified());
					//Após passar de 4000 linhas o TextArea perde o foco.
					int numeroLinhas = editor.getQuantidadeLinhasArquivo();
					if(editor.getQuantidadeLinhasArquivo() > 5000){
						numeroLinhas = Math.round(numeroLinhas/ 1000);
							for(int i = 0; i < numeroLinhas;i++){
								editor.getTexto().appendText(util.getQuebralinha());
								if(numeroLinhas == 6){break;}
								if(i > 8){break;}
							}
					}
					
				}
			}
		} catch (Exception e) {
			System.out.println("Estourou no Carregar!");
			editor.esconderProcesso();
		} finally {
			if(br != null){
				try {
					br.close();
				} catch (IOException e) {
					System.out.println("Estourou no Carregar!");
					e.printStackTrace();
				}
			}
			editor.esconderProcesso();
			editor.setPodeCarregarAtualizar(true);
		}

	}

}
