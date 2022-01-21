package Trimestre2.Buscaminas;

import java.util.Random;

public class Tablero {

	private int dimensiones;
	private Casillas[] casillas;
	private int numeroMinas;
	private int banderasCorrectasColocadas;
	private int banderarMalColocadas;

	public Tablero(int dimensiones) {
		this.dimensiones = dimensiones;
		casillas = new Casillas[devolverDimensionTableroAlCuadrado()];
		iniciarTableroNuevo();

	}

	public int devolverDimensionTableroAlCuadrado() {

		return dimensiones * dimensiones;

	}

	private void imprimirTablero() {

		int numerosImprimidoLinea = 0;
		for (int i = 0; i < casillas.length; i++) {

			if (casillas[i].estaDescubierta()) {
				System.out.print(" " + casillas[i].devolverEmoteSegunEstado() + " ");
			} else {

				if (!casillas[i].esBandera()) {
					System.out.print(" " + "⬛" + " ");
				} else {
					System.out.print(" " + "❎" + " ");
				}
			}
			numerosImprimidoLinea++;
			if (numerosImprimidoLinea == dimensiones) {
				numerosImprimidoLinea = 0;
				System.out.println();
			}

		}
	}

	private void iniciarTableroNuevo() {
		System.out.println("\n ");

		for (int i = 0; i < casillas.length; i++) {
			Casillas casillaNueva = new Casillas();
			casillas[i] = casillaNueva;
		}
		int numeroDeMinasSegunDimension = (dimensiones * 15) / 10;
		System.out.println("Para un tablero de: " + dimensiones + "x" + dimensiones + " se colocaran:"
				+ numeroDeMinasSegunDimension + " minas");
		asignarMinas(numeroDeMinasSegunDimension);
		this.numeroMinas = numeroDeMinasSegunDimension;
		imprimirTablero();
	}

	private void asignarNumeroMinasAlrededor() {
		for (int i = 0; i < casillas.length; i++) {

			int[] posicionesAlrededor = { i + 1, i - 1, (i + 1 - dimensiones), i - dimensiones, i + dimensiones,
					i - 1 - dimensiones, i + dimensiones - 1, i + dimensiones + 1 };
			int contadorMina = 0;
			for (int j = 0; j < posicionesAlrededor.length; j++) {
				if (posicionesAlrededor[j] >= 0 && posicionesAlrededor[j] < devolverDimensionTableroAlCuadrado()) {

					// Estos dos if sirven para que no se cuenten las minas de al lado que seria la
					// inicial de la fila de abajo y al reves.
					if (esCasillaFinal(i) && (j == 0 || j == 7 || j == 2))
						continue;

					if (escasillaInicial(i) && (j == 1 || j == 5 || j == 6))
						continue;

					if (casillas[posicionesAlrededor[j]].devolverEstado().equals("mina")
							&& !casillas[i].devolverEstado().equals("mina")) {
						contadorMina++;
					}

				}
			}
			if (contadorMina > 0) {
				casillas[i].ponerEstado("numero");
				casillas[i].ponerNumeroMinas(contadorMina);
				contadorMina = 0;

			}
		}
	}

	private boolean esCasillaFinal(int posicion) {

		return ((posicion + 1) % dimensiones) == 0;

	}

	private boolean escasillaInicial(int posicion) {

		return esCasillaFinal(posicion - 1);

	}

	public void destaparCasillasAlrededor(int posicion) {


		int[] posicionesAlrededor = { posicion + 1, posicion - 1, (posicion + 1 - dimensiones), posicion - dimensiones, posicion + dimensiones,
				posicion - 1 - dimensiones, posicion + dimensiones - 1, posicion + dimensiones + 1 };

		for (int j = 0; j < posicionesAlrededor.length; j++) {

			if (esCasillaFinal(posicion) && (j == 0 || j == 7 || j == 2))
				continue;

			if (escasillaInicial(posicion) && (j == 1 || j == 5 || j == 6))
				continue;

			if (posicionesAlrededor[j] >= 0 && posicionesAlrededor[j] < devolverDimensionTableroAlCuadrado()) {
				Casillas casillaSeleciona = casillas[posicionesAlrededor[j]];

				if ((casillaSeleciona.devolverEstado().equals("vacio") || casillaSeleciona.devolverEstado().equals("numero"))
						&& (!casillaSeleciona.devolverEstado().equals("mina") && !casillaSeleciona.estaDescubierta())) {

					if (casillaSeleciona.devolverEstado().equals("vacio") && !casillaSeleciona.esBandera()) {

						casillaSeleciona.ponerEstado("descubierta");
						casillaSeleciona.descubrirCasilla();
						destaparCasillasAlrededor(posicionesAlrededor[j]);

					} else if (casillaSeleciona.devolverEstado().equals("numero") && !casillaSeleciona.esBandera()) {
						casillaSeleciona.descubrirCasilla();

					}
				}
			}

		}
	}

	private void asignarMinas(int numMinas) {

		for (int i = 0; i < numMinas; i++) {
			Random rand = new Random();
			int aleatorio = rand.nextInt(dimensiones * dimensiones);

			if (!estaRepetido(aleatorio)) {
				casillas[aleatorio].ponerEstado("mina");
				//System.out.println("Mina en:" + aleatorio);
			} else {
				i--;
			}
		}

		asignarNumeroMinasAlrededor();
	}

	private boolean estaRepetido(int posicion) {

		if (casillas[posicion].devolverEstado().equals("mina")) {
			return true;
		} else {
			return false;
		}

	}

	public void destaparCasilla(int posicion, boolean noImprimir) {
		Casillas casillaSelecionada = casillas[posicion];
		String estadoCasillaADestapar = casillaSelecionada.devolverEstado();
		if (casillaSelecionada.esBandera()) {
			System.out.println("No puedes destapar una casilla que tiene una bandera colocada.");
			imprimirTablero();
		} else {

			destaparCasillasAlrededor(posicion);
			switch (estadoCasillaADestapar) {

			case "vacio":
				casillaSelecionada.descubrirCasilla();
				casillaSelecionada.ponerEstado("descubierta");
				if (!noImprimir) {
					imprimirTablero();
				}
				break;
			case "mina":
				casillaSelecionada.descubrirCasilla();
				destaparTodasMinasGameOver();
				imprimirTablero();
				System.out.println("Has perdido");
				System.exit(1);
				break;
			case "descubierta":
				System.out.println("Esa casilla ya esta descubierta");
				break;
			case "numero":
				casillaSelecionada.descubrirCasilla();
				if (!noImprimir) {
					imprimirTablero();
				}
				break;
			}
		}
	}

	public void colocarBandera(int posicion) {

		Casillas casilla = casillas[posicion];

		if (!casilla.estaDescubierta()) {
			if (casilla.esBandera()) {
				casilla.actualizarBandera(false);
				if (casilla.devolverEstado().equals("mina")) {
					banderasCorrectasColocadas--;
				}else {
					banderarMalColocadas--;
				}
			} else if(devolverNumeroMinasRestandoBanderas() != 0){
				casilla.actualizarBandera(true);
				if (casilla.devolverEstado().equals("mina")) {
					banderasCorrectasColocadas++;
				}else {
					banderarMalColocadas++;
				}
			}else {
				System.out.println("Solo hay " + this.numeroMinas + " minas");
			}
		} else {
			System.out.println("Lo siento no puedes marca una casilla que esta descubierta.");
		}

		if (this.banderasCorrectasColocadas == this.numeroMinas) {
			System.out.println("Has Ganado FELICIDADES.");
			destaparTodoTablero();
			imprimirTablero();
			System.exit(1);

		}
		imprimirTablero();
	}

	public int devolverNumeroMinasRestandoBanderas() {
		return this.numeroMinas - (banderasCorrectasColocadas + banderarMalColocadas);
	}
	
	public int devolverNumeroMinasTotales() {
		return this.numeroMinas;
	}

	private void destaparTodoTablero() {

		for (int i = 0; i < casillas.length; i++) {

			if (!casillas[i].esBandera()) {
				casillas[i].descubrirCasilla();
			}
			if (casillas[i].devolverEstado().equals("vacio")) {
				casillas[i].ponerEstado("descubierta");
			}
		}

	}

	private void destaparTodasMinasGameOver() {

		for (int i = 0; i < casillas.length; i++) {
			Casillas casillaSelecionada = casillas[i];
			if (casillaSelecionada.devolverEstado().equals("mina") && !casillaSelecionada.estaDescubierta()) {
				casillaSelecionada.descubrirCasilla();

			}

		}

	}
}
