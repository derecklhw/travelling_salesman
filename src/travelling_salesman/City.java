package travelling_salesman;

class City {
    private int number;
    private double x;
    private double y;

    public City(int number, double x, double y) {
        this.number = number;
        this.x = x;
        this.y = y;
    }

	public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		this.number = number;
	}

	public double getX() {
		return x;
	}

	public void setX(double x) {
		this.x = x;
	}

	public double getY() {
		return y;
	}

	public void setY(double y) {
		this.y = y;
	}

	@Override
	public String toString() {
		return "City [number=" + number + ", x=" + x + ", y=" + y + "]";
	}    
}
