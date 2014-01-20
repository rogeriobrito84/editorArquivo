package uitl;

import java.io.File;
import java.math.BigDecimal;
import java.math.RoundingMode;

import model.Editor;

import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class Util {
	private static final String quebraLinha = System.getProperty("line.separator");
	private File file;
	FileChooser chooser;
	
	public double calcularProgresso(int total, int atual) {
		double resultado = 0;
		if (total != 0) {
			resultado = BigDecimal
					.valueOf(atual)
					.divide(BigDecimal.valueOf(total), 2,
							RoundingMode.HALF_EVEN).doubleValue();
		}
		return resultado;
	}
	
	public  boolean abrirNovoArquivo(Editor editor) {
		boolean resultado = false;
		chooser = new FileChooser();
		FileChooser.ExtensionFilter extension = new FileChooser.ExtensionFilter("Apenas arquivos (TXT)", "*.txt");
		chooser.getExtensionFilters().add(extension);
		file = chooser.showOpenDialog((Stage) editor.getTexto().getScene().getWindow());
		if(file != null){
			editor.setFile(file);
			resultado = true;
		}
		return resultado;
	}
	
	public boolean salvarNovoArquivo(Editor editor){
		boolean resultado = false;
		chooser = new FileChooser();
		FileChooser.ExtensionFilter extension = new FileChooser.ExtensionFilter("Apenas arquivos (TXT)", "*.txt");
		chooser.getExtensionFilters().add(extension);
		file = chooser.showSaveDialog((Stage) editor.getTexto().getScene().getWindow());
		if(file != null){
			editor.setFile(new File(file.getAbsolutePath()+".txt"));
			resultado = true;
		}
		return resultado;
	}

	public String getQuebralinha() {
		return quebraLinha;
	}
	
}
