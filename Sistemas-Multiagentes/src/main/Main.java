package main;

import java.io.IOException;
import java.util.ArrayList;

import agents.Agent_Boston;
import agents.Agent_Chicago;
import agents.Agent_Seattle;
import agents.Population_Data;

/* 
 * Main class 
 * External libraries: jsoup-1.12.1, gson-2.8.6, RANDOM.ORG JSON-RPC API v1
 * Ruben Marquez, Antonio Manjavacas
 * */

public class Main {

	public static void main(String[] args) throws IOException {

		Agent_Boston myAgentBoston = new Agent_Boston();
		ArrayList<Population_Data> pdBoston = myAgentBoston.getData();
		System.out.println("___________________________");
		System.out.println("BOSTON POPULATION PER YEAR");
		for (Population_Data p : pdBoston) {
			System.out.println(p);
		}

		Agent_Chicago myAgentChicago = new Agent_Chicago();
		ArrayList<Population_Data> pdChicago = myAgentChicago.getData();
		System.out.println("___________________________");
		System.out.println("CHICAGO POPULATION PER YEAR");
		for (Population_Data p : pdChicago) {
			System.out.println(p);
		}

		Agent_Seattle myAgentSeattle = new Agent_Seattle();
		ArrayList<Population_Data> pdSeattle = myAgentSeattle.getData();
		System.out.println("___________________________");
		System.out.println("SEATTLE POPULATION PER YEAR");
		for (Population_Data p : pdSeattle) {
			System.out.println(p);
		}

	}

}
