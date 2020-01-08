package agents;

import model.PopulationData;

import jade.core.Agent;
import jade.core.AID;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import jade.proto.AchieveREResponder;

import jade.domain.FIPANames;
import jade.domain.FIPAAGentManagement.NotUnderstoodException;
import jade.domain.FIPAAGentManagement.RefuseException;
import jade.domain.FIPAAGentManagement.FailureException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.random.api.RandomOrgClient;

public class WebAgent extends Agent {

    final static String REG_EXP = "\\d{4}((\\s\\d{4})|((\\s\\d{1,3}){2,3}))\\s[\\D&&\\W]";
    final static String REG_EXP_URL = "^(https?|ftp|file)://[-a-zA-Z0-9+&@#/%?=~_|!:,.;]*[-a-zA-Z0-9+&@#/%=~_|]";
    final static String API_KEY = "31c9d05e-9079-4778-9db0-7395dbdb9580";

    final static String DASHBOARD = "Dashboard";
    final static String PROCESSER = "Processer";
    final static String WEB_AGENT = "WebAgent";

    final static int MAX = 10;
    final static int MIN = 0;
    final static int NUMS = 10;

    protected void setup() {

        MessageTemplate sender = MessageTemplate.MatchSender(new AID(PROCESSER, AID.ISLOCALNAME));
        MessageTemplate protocol = MessageTemplate.MatchProtocol(FIPANames.InteractionProtocol.FIPA_REQUEST);
        MessageTemplate performative = MessageTemplate.MatchPerformative(ACLMessage.REQUEST);

        MessageTemplate temp = MessageTemplate.and(sender, protocol);
        temp = MessageTemplate.and(temp, performative);

        addBehaviour(new RequestReceiver(this, temp));
    }

    protected void takeDown() {
        System.out.println("[WEB-AGENT] Taking down...");
    }

    public class RequestReceiver extends AchieveREResponder {

        public RequestReceiver(Agent a, MessageTemplate temp) {
            super(a, temp);
        }

        protected ACLMessage handleRequest(ACLMessage request) throws NotUnderstoodException, RefuseException {

            String url = request.getContent();

            if (isValidUrl(url)) {
                try {
                    ArrayList<PopulationData> data = getData(url);
                } catch (IOException exception) {
                    throw new RefuseException("[WEB-AGENT] Refuse exception: error getting information.");
                }
            } else
                throw new NotUnderstoodException("[WEB-AGENT] NotUnderstoodException: Invalid URL received.");

            ACLMessage agree = request.createReply();
            agree.setPerformative(ACLMessage.AGREE);

            // TO-DO: serialize ArrayList<PopulationData>
            // agree.setContentObject(()data);
            // TO-DO: add failure exception
            // TO-DO: prepareResultNotification() method

            return agree;
        }

        protected ACLMessage prepareResultNotification(ACLMessage request, ACLMessage response) {
            // TO-DO: tasks here!
        }

        /** Checks if and URL is valid **/
        private boolean isValidUrl(String url) {
            Pattern p = Pattern.compile(REG_EXP_URL);
            Matcher m = p.matcher(url);
            return m.matches();
        }

        /** Get population and date registers **/
        private ArrayList<Population_Data> getData(String url) throws IOException {

            Document doc = Jsoup.connect(url).get();

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
            int[] rands = client.generateIntegers(NUMS, MIN, MAX);
            int n = 0;

            while (m.find()) {
                if (rands[n++ % NUMS] % 2 == 0) { // random condition
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

}
