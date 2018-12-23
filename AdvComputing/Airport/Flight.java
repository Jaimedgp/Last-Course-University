/**
 * Flight which arrive to the airport
 * 
 * @author Jaime 
 * @version feb-2018
 */
public class Flight {
	private final String flightId;
	private final String originAirport;

	/**
	 * It build a flight with the data.
	 * @param flightId identificador del vuelo
	 * @param originAirport aeropuerto de origen del vuelo
	 */
	public Flight(String flightId, String originAirport) {
		this.flightId = flightId;
		this.originAirport = originAirport;
	}

	/**
	 * Retorna el identificador del vuelo.
	 * 
	 * @return el identificador del vuelo.
	 */
	public String flightId() {
		return flightId;
	}
	
	/**
	 * Retorna el aeropuerto de origen del vuelo.
	 * 
	 * @return el aeropuerto de origen del vuelo
	 */
	public String originAirport() {
		return originAirport;
	}
}
