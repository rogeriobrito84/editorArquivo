package main;

import java.awt.TextField;
import java.io.File;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.nio.file.attribute.FileAttribute;
import java.nio.file.attribute.FileTime;
import java.util.List;

import javax.swing.JOptionPane;

import model.Editor;

import uitl.Dialog;
import uitl.Util;

import javafx.application.Application;
import javafx.beans.InvalidationListener;
import javafx.beans.property.DoubleProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;

public class Teste {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		TextArea area = new TextArea();
		File file = new File("");
		Thread th = new Thread();
		
		
	}

	
	public static BigDecimal calcularProgressoBig(int total, int atual){
		BigDecimal resultado = null;
		resultado = BigDecimal.valueOf(atual).divide(BigDecimal.valueOf(total), 16,RoundingMode.HALF_EVEN);
		
		return resultado;
	}

	
	private void listarTodos(List<Integer> lista){
		for (int i = 0; i < lista.size();i++){
			System.out.println("Nome: " + lista.get(i)+ "            index: " + i);
			System.out.println("--------------------------------------------- ");
		}
	}
	
	private void remover(List<Integer> lista, int index){
		for(int i = 0; i < lista.size();i++){
			if(lista.get(i) == index){
				lista.remove(lista.get(i));
				break;
			}
		}
	}
}
