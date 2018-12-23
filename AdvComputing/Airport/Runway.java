import java.util.ArrayList;
/**
 * Pista de aterrizaje de un aeropuerto.
 * 
 * @author Metodos de Programacion (UC) y <TODO: nombre alumno>
 * @version feb-2018
 */
public class Runway{
	private final int runwayId;
	ArrayList<Flight> listOfFlights = new ArrayList<Flight>();
	// TODO: atributo con los vuelos pendientes de aterrizar.

	/**
	 * Construye una pista con el identificador indicado.
	 * 
	 * @param idPista identificador de la pista
	 */
	public Runway(int runwayId) {
		this.runwayId = runwayId;
	}
	
	public void flightInQueue(Flight flight) {
		listOfFlights.add(flight);
	}
	
	public Flight deleteNextFlight() {
		if (listOfFlights.size() == 0) {
			return null;
		}
		Flight flightLanded = listOfFlights.remove(0);
		return flightLanded;
	}
	
	public int numPendingFlights() {
		return listOfFlights.size();
	}

	/**
	 * Retorna el identificador de la pista.
	 * @return identificador de la pista.
	 */
	public int runwayId() {
		return runwayId;
	}
	
	// TODO: otros metodos

}