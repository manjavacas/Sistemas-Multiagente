
/* Agent to get the historical population in Boston */

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.net.ssl.HttpsURLConnection;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class Agent_Boston {

	ArrayList<Population_Data> getData() throws IOException {

		Document doc = Jsoup.connect("https://es.wikipedia.org/wiki/Boston").get();

		// Body and pattern to be found
		String body = doc.body().text();
		String myPattern = "\\d{4}((\\s\\d{4})|((\\s\\d{1,3}){2,3}))\\s[\\D&&\\W]";

		// Pattern object and matcher creation
		Pattern p = Pattern.compile(myPattern);
		Matcher m = p.matcher(body);
		
		// Find pattern and store data
		ArrayList<Population_Data> table = new ArrayList<Population_Data>();
		Population_Data reg = null;

		while (m.find()) {
			// Random number generation from www.random.org
			int rand = getRandom();
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
	
	//*** RANDOM GENERATION HERE ***//
	public int getRandom() throws IOException {

		JsonObject params = new JsonObject();

		params.addProperty("n", 1);
		params.addProperty("min", 1);
		params.addProperty("max", 100);
		params.addProperty("replacement", true);
		params.addProperty("apiKey", "1c681598-6977-45b4-bd2d-32fcdaaf1758");

		JsonObject request = new JsonObject();
		request.addProperty("jsonrpc", "2.0");
		request.addProperty("method", "generateIntegers");
		request.add("params", params);
		request.addProperty("id", 22481);

		HttpsURLConnection con = (HttpsURLConnection) new URL("https://api.random.org/json-rpc/2/invoke")
				.openConnection();
		con.setRequestMethod("POST");
		con.setRequestProperty("Content-Type", "application/json");
		con.setDoOutput(true);
		DataOutputStream dos = new DataOutputStream(con.getOutputStream());
		dos.writeBytes(request.toString());
		dos.flush();
		dos.close();

		int responseCode = con.getResponseCode();
		if (responseCode == HttpsURLConnection.HTTP_OK) {
			BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
			String inputLine;
			StringBuffer response = new StringBuffer();

			while ((inputLine = in.readLine()) != null) {
				response.append(inputLine);
			}
			in.close();

			JsonObject json = new JsonParser().parse(response.toString()).getAsJsonObject();
			JsonObject result = json.get("result").getAsJsonObject();
			JsonArray array = result.get("random").getAsJsonObject().get("data").getAsJsonArray();

			return array.get(0).getAsInt();

		}
		
		return 0;
		
	}

}
