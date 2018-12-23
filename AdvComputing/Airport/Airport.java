
import java.util.ArrayList;

/**
 * Airport con varias pistas de aterrizaje.
 * 
 * @author Metodos de Programacion (UC) y <TODO: nombre alumno>
 * @version feb-2018
 */

public class Airport {
	
	private int numRunways;
	Runway[] listOfRunways;
	ArrayList<Flight> flightsHistory = new ArrayList<Flight>();
	// TODO: atributos
	
	/**
	 * Construye un Airport con el numero de pistas indicadas.
	 * @param numRunways numero de pistas del Airport.
	 */
	public Airport(int numRunways) {
		this.numRunways = numRunways;
		listOfRunways = new Runway[numRunways]; 
		for (int i=0; i<numRunways; i++) {
			listOfRunways[i] = new Runway(i);
		}
	}
	
	/**
	 * Asigna el flight a la pista con menos flights pendientes para aterrizar.
	 * 
	 * @param flight que solicita pista para aterrizar.
	 * @return la pista a la que ha sido asignado el flight.
	 */
	public Runway flightRequiresRunway(Flight flight) {
		if (flightsHistory.size() != 0) {
			
			for (int i = 0; i<flightsHistory.size(); i++) {
				Flight thisFlight = flightsHistory.get(i);

				if (flight.flightId().equals(thisFlight.flightId())) {
					return null;
				}
			}
		}
				
		Runway runwayWithLessFlights = searchRunwayWithLessFlights();
		runwayWithLessFlights.flightInQueue(flight);
		
		flightsHistory.add(flight);
		
		return runwayWithLessFlights;
	}
	
	/**
	 * Registra que ha aterrizado el flight que lleva mas tiempo esperando
	 * para aterrizar en la pista indicada.
	 * 
	 * @param runwayId identificador de la pista en la que ha aterrizado
	 * el flight.
	 * @return el flight si habia al menos un flight pendiente de aterrizar
	 * en la pista indicada o null en caso contrario.
	 */
	public Flight flightLanded(int runwayId) {
		// TODO		
		return listOfRunways[runwayId].deleteNextFlight();
	}
	
	private Runway searchRunwayWithLessFlights() {
		
		int minPendingFlights = listOfRunways[0].numPendingFlights();
		int runWayWithMinQueue = 0;
		
		for (int i=1; i<numRunways; i++) {
			
			int m = listOfRunways[i].numPendingFlights();
			
			if (m<minPendingFlights) {
				
				minPendingFlights=m;
				runWayWithMinQueue = i;
			}
		}
		
		return listOfRunways[runWayWithMinQueue];
	}
	
	
	// TODO: otros metodos
	

}
