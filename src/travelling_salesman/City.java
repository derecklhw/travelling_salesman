package travelling_salesman;

/**
 * Represents a city identified by x and y coordinates.
 */
class City {
	private int number;
	private double xCoordinate;
	private double yCoordinate;

	/**
	 * Constructs a new City instance.
	 *
	 * @param number      The unique identifier of the city.
	 * @param xCoordinate The x-coordinate of the city's location.
	 * @param yCoordinate The y-coordinate of the city's location.
	 */
	public City(int number, double xCoordinate, double yCoordinate) {
		this.number = number;
		this.xCoordinate = xCoordinate;
		this.yCoordinate = yCoordinate;
	}

	/**
	 * Returns the unique identifier of the city.
	 * 
	 * @return The unique identifier of the city.
	 */
	public int getNumber() {
		return number;
	}

	/**
	 * Sets the unique identifier of the city.
	 * 
	 * @param number The unique identifier of the city.
	 */
	public void setNumber(int number) {
		this.number = number;
	}

	/**
	 * Returns the x-coordinate of the city's location.
	 * 
	 * @return The x-coordinate of the city's location.
	 */
	public double getXCoordinate() {
		return xCoordinate;
	}

	/**
	 * Sets the x-coordinate of the city's location.
	 * 
	 * @param xCoordinate The x-coordinate of the city's location.
	 */
	public void setXCoordinate(double xCoordinate) {
		this.xCoordinate = xCoordinate;
	}

	/**
	 * Returns the y-coordinate of the city's location.
	 * 
	 * @return The y-coordinate of the city's location.
	 */
	public double getYCoordinate() {
		return yCoordinate;
	}

	/**
	 * Sets the y-coordinate of the city's location.
	 * 
	 * @param yCoordinate The y-coordinate of the city's location.
	 */
	public void setYCoordinate(double yCoordinate) {
		this.yCoordinate = yCoordinate;
	}

	/**
	 * Calculates the Euclidean distance from this city to another city.
	 *
	 * @param other The other city to which distance is to be calculated.
	 * @return The Euclidean distance to the other city.
	 */
	public double distanceTo(City other) {
		double dx = this.xCoordinate - other.xCoordinate;
		double dy = this.yCoordinate - other.yCoordinate;
		return Math.sqrt(dx * dx + dy * dy);
	}

	/**
	 * Returns a string representation of this city.
	 */
	@Override
	public String toString() {
		return "City [number=" + number + ", x=" + xCoordinate + ", y=" + yCoordinate + "]";
	}
}
