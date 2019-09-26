
/* Agent to get the historical population in Boston */

import java.io.IOException;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

public class Agent_Boston {

	void conectar() throws IOException {
		
		Document doc = Jsoup.connect("https://es.wikipedia.org/wiki/Boston").get();
		
		// Body and pattern to be found
		String body = doc.body().text();
		String myPattern = "\\d{4}((\\s\\d{4})|((\\s\\d{1,3}){2,3}))\\s[\\D&&\\W]";
		
		// Pattern object and matcher creation
		Pattern p = Pattern.compile(myPattern);
		Matcher m = p.matcher(body);
		
		ArrayList<Integer> years = new ArrayList<Integer>();
		ArrayList<Integer> population = new ArrayList<Integer>();
		
		// Find pattern and store the data
		while(m.find()) {
			String cadena = m.group(0);
			years.add(Integer.parseInt(cadena.substring(0, 4)));
			String populationCad = cadena.substring(5, cadena.length()-2);
			population.add(Integer.parseInt(populationCad.replace(" ", "")));
		}
		
		// Show the historical population table of Boston
		System.out.println("HISTORICAL POPULATION OF BOSTON");
		System.out.println("--------------------------------");
		for(int i = 0; i < years.size(); i++) {
			System.out.println(years.get(i) + " -------- " + population.get(i));
		}
		System.out.println("--------------------------------");
	}
}
