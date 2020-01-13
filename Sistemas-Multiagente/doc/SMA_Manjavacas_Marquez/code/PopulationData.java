package agents;

import java.io.Serializable;

public class PopulationData implements Serializable {

	private int year, population;
	private String url;

	public PopulationData(int year, int population, String url) {
		this.year = year;
		this.population = population;
		this.url = url;
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

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	@Override
	public String toString() {
		return "YEAR: " + year + ", POPULATION: " + population + ", URL: " + url;
	}

}
