package agents;

import java.io.Serializable;

public class PopulationData implements Serializable {

	private int year, population;

	public PopulationData(int year, int population) {
		this.year = year;
		this.population = population;
	}

	public int getYear() {
		return year;
	}

	public void setYear(int year) {
		this.year = year;
	}

	public int getPopulation() {
		return population;
	}

	public void setPopulation(int population) {
		this.population = population;
	}

	@Override
	public String toString() {
		return "[Year: " + year + " ----- " + population + "]";
	}

}