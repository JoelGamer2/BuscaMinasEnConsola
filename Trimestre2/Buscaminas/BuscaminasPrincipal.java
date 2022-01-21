package Trimestre2.Buscaminas;

import java.util.Scanner;

public class BuscaminasPrincipal {

	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);
		System.out.println("Bienvenido a buscaminas! Creado por JoelGamer2\nDe que dimensiones quieres el tablero");
		
		Tablero juego = new Tablero(in.nextInt());
		
		while (true) {
			System.out.println("1.Poner/Quitar Bandera, 2.Destapar" + "Quedan:" + juego.devolverNumeroMinasRestandoBanderas() + " minas de: " + juego.devolverNumeroMinasTotales());
			switch(in.nextInt()) {
			
			case 1:
				preguntarCoordenadaBandera(in, juego);
				break;
			case 2:
				preguntarCoordenadaADestapar(in, juego);
				break;
			
			}
		}

	}

	public static void preguntarCoordenadaBandera(Scanner in, Tablero juego) {
		
		String posicionValida = "(0-" + (juego.devolverDimensionTableroAlCuadrado()-1) + ")";
		System.out.println("En que coordenada quieres poner la Bandera? " + posicionValida);
		
		int posicion = in.nextInt();
		if (posicion < juego.devolverDimensionTableroAlCuadrado()) {
			juego.colocarBandera(posicion);
		}else {
			System.out.println("Introduzca una posicion valida " + posicionValida);
		}

	}
	
	public static void preguntarCoordenadaADestapar(Scanner in, Tablero juego) {
		String posicionValida = "(0-" + (juego.devolverDimensionTableroAlCuadrado()-1) + ")";
		System.out.println("Que coordenada quieres destapar? " + posicionValida);
		
		int posicion = in.nextInt();
		if (posicion < juego.devolverDimensionTableroAlCuadrado()) {
			juego.destaparCasilla(posicion, false);
		}else {
			System.out.println("Introduzca una posicion valida " + posicionValida);
		}

	}

}
