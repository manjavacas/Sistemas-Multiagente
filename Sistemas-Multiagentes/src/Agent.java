
/* Agent to get web data */

import java.io.IOException;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

public class Agent {

	void conectar() throws IOException {
		
		Document doc = Jsoup.connect("http://en.wikipedia.org/").get();
		
		// Body and pattern to be found
		String body = doc.body().text();
		String myPattern = "[A-Z]\\w*";
		
		// Pattern object and matcher creation
		Pattern p = Pattern.compile(myPattern);
		Matcher m = p.matcher(body);
		
		// Find pattern and count occurrences
		int c = 0;
		while(m.find()) {
			System.out.println(m.group(0));
			c++;
		}
		
		System.out.println("\n** Palabras que empiezan por mayuscula: " + c + " **");	
	}

}
