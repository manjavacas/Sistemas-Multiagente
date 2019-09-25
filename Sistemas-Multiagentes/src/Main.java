import java.io.IOException;

/* Clase principal encargada de lanzar el agente */

public class Main {

	public static void main(String[] args) throws IOException {

		Agent myAgent = new Agent();
		myAgent.conectar();
	}

}
