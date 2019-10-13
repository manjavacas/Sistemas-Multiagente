/* 
 * Agent to get the historical population of Chicago 
 * External libraries: jsoup-1.12.1, gson-2.8.6, RANDOM.ORG JSON-RPC API v1
 * Ruben Marquez, Antonio Manjavacas
 * */

package agents;

import java.io.IOException;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.random.api.RandomOrgClient;

public class Agent_Chicago {

	final static String URL = "https://es.wikipedia.org/wiki/Chicago";
	final static String REG_EXP = "\\d{4}((\\s\\d{4})|((\\s\\d{1,3}){2,3}))\\s[\\D&&\\W]";
	final static String API_KEY = "31c9d05e-9079-4778-9db0-7395dbdb9580";

	public ArrayList<Population_Data> getData() throws IOException {

		Document doc = Jsoup.connect(URL).get();

		// Body and pattern to be found
		String body = doc.body().text();
		String myPattern = REG_EXP;

		// Pattern object and matcher creation
		Pattern p = Pattern.compile(myPattern);
		Matcher m = p.matcher(body);

		// Find pattern and store data
		ArrayList<Population_Data> table = new ArrayList<Population_Data>();
		Population_Data reg = null;

		// Random number generation from www.random.org
		RandomOrgClient client = RandomOrgClient.getRandomOrgClient(API_KEY);
		int rand = client.generateIntegers(1, 0, 10)[0];

		while (m.find()) {
			if (rand % 2 == 0) { // random condition
				String cadena = m.group(0);
				String populationCad = cadena.substring(5, cadena.length() - 2);
				reg = new Population_Data(Integer.parseInt(cadena.substring(0, 4)),
						Integer.parseInt(populationCad.replace(" ", "")));
				table.add(reg);
			}
		}

		return table;
	}
}
