
/* Agent to get the historical population in Boston */

import java.io.IOException;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

public class Agent_Boston {

	ArrayList<Population_Data> getData() throws IOException {

		Document doc = Jsoup.connect("https://es.wikipedia.org/wiki/Boston").get();

		// Body and pattern to be found
		String body = doc.body().text();
		String myPattern = "\\d{4}((\\s\\d{4})|((\\s\\d{1,3}){2,3}))\\s[\\D&&\\W]";

		// Pattern object and matcher creation
		Pattern p = Pattern.compile(myPattern);
		Matcher m = p.matcher(body);

		// Random number generation from www.random.org
		int rand = -1;
		//*** RANDOM GENERATION HERE ***//
		
		// Find pattern and store data
		ArrayList<Population_Data> table = new ArrayList<Population_Data>();
		Population_Data reg = null;

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
