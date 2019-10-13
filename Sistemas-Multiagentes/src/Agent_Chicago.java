
/* Agent to get the historical population in Chicago */

import java.io.IOException;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

public class Agent_Chicago {

	ArrayList<Population_Data> getData() throws IOException {

		Document doc = Jsoup.connect("https://es.wikipedia.org/wiki/Chicago").get();

		// Body and pattern to be found
		String body = doc.body().text();
		String myPattern = "\\d{4}((\\s\\d{4})|((\\s\\d{1,3}){2,3}))\\s[\\D&&\\W]";

		// Pattern object and matcher creation
		Pattern p = Pattern.compile(myPattern);
		Matcher m = p.matcher(body);

		ArrayList<Population_Data> table = new ArrayList<Population_Data>();
		Population_Data reg = null;

		// Find pattern and store the data
		while (m.find()) {
			String cadena = m.group(0);
			String populationCad = cadena.substring(5, cadena.length() - 2);
			reg = new Population_Data(Integer.parseInt(cadena.substring(0, 4)),
					Integer.parseInt(populationCad.replace(" ", "")));
			table.add(reg);
		}
	
		return table;
	}

}
