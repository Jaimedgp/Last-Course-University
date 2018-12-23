import fundamentos.*;

/**
 * Interfaz Gráfica de Usuario (GUI) de la aplicación de asignacion
 * de vuelos a las pistas de aterrizaje de un aeropuerto.
 * 
 * @author Metodos de Programacion (UC) y <TODO: nombre alumno>
 * @version feb-2018
 */
public class GUIAirportManagement {

	/**
	 * Programa principal basado en menu.
	 */
	public static void main(String[] args) {
		// opciones del menu
		final int VUELO_SOLICITA_PISTA = 0, VUELO_ATERRIZADO = 1, MUESTRA_REGISTRO_VUELOS = 2;

		// variables auxiliares
		String codVuelo;
		String origen;
		int idPista;
		Lectura lect;

		// crea el aeropuerto con tres pistas de aterrizaje
		Airport airport = new Airport(3);

		// crea la ventana de menu
		Menu menu = new Menu("Aeropuerto");
		menu.insertaOpcion("Vuelo solicita pista", VUELO_SOLICITA_PISTA);
		menu.insertaOpcion("Vuelo aterrizado", VUELO_ATERRIZADO);
		menu.insertaOpcion("Muesra registro vuelos", MUESTRA_REGISTRO_VUELOS);
		int opcion;

		// lazo de espera de comandos del usuario
		while(true) {
			opcion = menu.leeOpcion();

			// realiza las acciones dependiendo de la opción elegida
			switch (opcion) {
			case VUELO_SOLICITA_PISTA:
				// lee los datos del vuelo
				lect = new Lectura("Datos vuelo");
				lect.creaEntrada("Codigo", "IBE8874");
				lect.creaEntrada("Origen", "Madrid");
				lect.esperaYCierra();
				codVuelo = lect.leeString("Codigo");
				origen = lect.leeString("Origen");

				// asigna el vuelo a la pista con menos vuelos pendientes
				Runway p =airport.flightRequiresRunway(new Flight(codVuelo, origen));
				if (p==null) {
					mensaje("ERROR", "Ya exite otro vuelo con ese identificador");
				} else {
					mensaje("Pista asignada", "Pista:" +  p.runwayId());
				}
				break;

			case VUELO_ATERRIZADO:
				// lee el identificador de la pista
				lect = new Lectura("Aterriza vuelo");
				lect.creaEntrada("Id pista", 0);
				lect.esperaYCierra();
				idPista = lect.leeInt("Id pista");

				// elimina el siguiente vuelo de la pista			
				Flight elVuelo = airport.flightLanded(idPista);
				if (elVuelo != null){
					mensaje ("Vuelo aterrizado", "Identificador del vuelo: " + elVuelo.flightId());
				}
				else {
					mensaje("ERROR", "No hay ningun vuelo pendiente de " +
							"aterrizar en la pista " + idPista);					
				}
				break;

			case MUESTRA_REGISTRO_VUELOS:
				// muestra todos los vuelos
				// TODO
				break;
			}
		}
	}

	/**
	 * Metodo auxiliar que muestra un ventana de mensaje
	 * @param titulo titulo de la ventana
	 * @param txt texto contenido en la ventana
	 */
	private static void mensaje(String titulo, String txt) {
		Mensaje msj = new Mensaje(titulo);
		msj.escribe(txt);
	}
}
