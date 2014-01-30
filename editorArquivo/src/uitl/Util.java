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
			resultado = BigDecimal.valueOf(atual).divide(BigDecimal.valueOf(total), 2,RoundingMode.HALF_EVEN).doubleValue();
		}
		
		return resultado;
	}
	
	public File abrirNovoArquivo(Stage palco) {
		chooser = new FileChooser();
		FileChooser.ExtensionFilter extension = new FileChooser.ExtensionFilter("Apenas arquivos (TXT)", "*.txt");
		chooser.getExtensionFilters().add(extension);
		file = chooser.showOpenDialog(palco);
		return file;
	}
	
	public boolean salvarNovoArquivo(Editor editor){
		boolean resultado = false;
		chooser = new FileChooser();
		FileChooser.ExtensionFilter extension = new FileChooser.ExtensionFilter("Apenas arquivos (TXT)", "*.txt");
		chooser.getExtensionFilters().add(extension);
		file = chooser.showSaveDialog((Stage) editor.getTexto().getScene().getWindow());
		if(file != null){
			String path = file.getAbsolutePath();
			if(!path.endsWith(".txt")){
				path = path + ".txt";
			}
			editor.setFile(new File(path));
			resultado = true;
		}
		return resultado;
	}
	
	
	
	public String getQuebralinha() {
		return quebraLinha;
	}
	
}
