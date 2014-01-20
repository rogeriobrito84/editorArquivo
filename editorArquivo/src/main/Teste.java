package main;

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
import javafx.stage.Stage;

public class Teste {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Util util = new Util();
		System.out.println("rogerio " + util.getQuebralinha()+ " rogerio 2");
		
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
