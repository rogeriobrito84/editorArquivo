package main;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class Teste {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		System.out.println("resultado: \n".contains("\n"));

	}
	public static double calcuProgresso(int total, int atual){
		double resultado = 0;
		if(total != 0){
			 resultado = BigDecimal.valueOf(atual).divide(BigDecimal.valueOf(total),2,RoundingMode.HALF_EVEN).doubleValue();
		}
		return resultado;
	}
}
