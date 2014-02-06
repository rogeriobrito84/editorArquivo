package uitl;


import java.io.File;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

import javafx.stage.FileChooser;
import javafx.stage.Stage;
import model.Editor;

public class Util {
	private static final String quebraLinha = System.getProperty("line.separator");
	private File file;
	private FileChooser chooser;
	private List<String> extensoes; 
	
	public Util(){
		chooser = new FileChooser();
		extensoes = new ArrayList<String>();
		extensoes.add("*.txt");
		extensoes.add("*.xml");
		extensoes.add("*.properties");
		extensoes.add("*.xsl");
		extensoes.add("*.java");
		FileChooser.ExtensionFilter filtro = new FileChooser.ExtensionFilter(
				"Arquivos .txt - xml - properties - xsl - java" , extensoes
		);
		chooser.getExtensionFilters().add(filtro);
	}
	
	public double calcularProgresso(int total, int atual) {
		double resultado = 0;
		
		if (total != 0) {
			resultado = BigDecimal.valueOf(atual).divide(BigDecimal.valueOf(total), 2,RoundingMode.HALF_EVEN).doubleValue();
		}
		
		return resultado;
	}
	
	public File abrirNovoArquivo(Stage palco) {
		file = chooser.showOpenDialog(palco);
		return file;
	}
	
	public boolean salvarNovoArquivo(Editor editor){
		boolean resultado = false;
		file = chooser.showSaveDialog((Stage) editor.getTexto().getScene().getWindow());
		if(file != null){
			String path = file.getAbsolutePath();
			if(!path.endsWith(".txt") && !path.endsWith(".xml") 
					&& !path.endsWith(".properties") &&  !path.endsWith(".xsl")){
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
