
/* Clase agente encargada de recopilar informacion de la web */

import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

public class Agent {

	void conectar() throws IOException {

		Document doc = Jsoup.connect("http://en.wikipedia.org/").get();

	}

}
