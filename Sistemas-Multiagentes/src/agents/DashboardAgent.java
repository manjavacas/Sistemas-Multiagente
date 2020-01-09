package agents;

import jade.core.Agent;

import java.io.IOException;
import java.util.ArrayList;

import jade.core.AID;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.UnreadableException;
import jade.proto.AchieveREInitiator;
import model.PopulationData;
import jade.domain.FIPANames;

public class DashboardAgent extends Agent {

    final static String DASHBOARD = "Dashboard";
    final static String PROCESSER = "Processer";
    final static String WEB_AGENT1 = "WebAgent1";
    final static String WEB_AGENT2 = "WebAgent2";
    final static String WEB_AGENT3 = "WebAgent3";
    final static String URL1 = "https://es.wikipedia.org/wiki/Boston";
    final static String URL2 = "https://es.wikipedia.org/wiki/Chicago";
    final static String URL3 = "https://es.wikipedia.org/wiki/Seattle";

    protected void setup() {

    	ArrayList<String> msgContent = new ArrayList<String>();
    	msgContent.add(WEB_AGENT1 + ":" + URL1);
    	msgContent.add(WEB_AGENT2 + ":" + URL2);
    	msgContent.add(WEB_AGENT3 + ":" + URL3);

        ACLMessage msg = new ACLMessage(ACLMessage.REQUEST);
        msg.addReceiver(new AID(PROCESSER, AID.ISLOCALNAME));
        msg.setProtocol(FIPANames.InteractionProtocol.FIPA_REQUEST);
        msg.setSender(new AID(DASHBOARD, AID.ISLOCALNAME));
        try {
			msg.setContentObject(msgContent);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println(e.getMessage());
		}

        addBehaviour(new RequestInitiator(this, msg));
    }

    protected void takeDown() {
        System.out.println("[DASHBOARD-AGENT] Taking down...");
    }

    public class RequestInitiator extends AchieveREInitiator {

        public RequestInitiator(Agent a, ACLMessage msg) {
            super(a, msg);
        }

        protected void handleAgree(ACLMessage agree) {
            System.out.println("[DASHBOARD-AGENT] " + agree.getSender().getName() + " has accepted the request.");
        }

        protected void handleRefuse(ACLMessage refuse) {
            System.out.println("[DASHBOARD-AGENT] " + refuse.getSender().getName() + " has rejected the request.");
        }

        protected void handleNotUnderstood(ACLMessage notUnderstood) {
            System.out.println(
                    "[DASHBOARD-AGENT] " + notUnderstood.getSender().getName() + " didn't understood the request.");
        }

        protected void handleInform(ACLMessage inform) {
            System.out.println("[DASHBOARD-AGENT] Received results from " + inform.getSender().getName());
            
            ArrayList<ArrayList<PopulationData>> info = null;
			try {
				info = (ArrayList<ArrayList<PopulationData>>)inform.getContentObject();
			} catch (UnreadableException e) {
				// TODO Auto-generated catch block
				System.out.println(e.getMessage());
			}
			for (ArrayList<PopulationData> table : info) {
				for (PopulationData line : table) {
					System.out.println(line);
				}
			}
			
			// TO-DO: implement interface with results
        }

        protected void handleFailure(ACLMessage failure) {
            System.out.println("[DASHBOARD-AGENT] " + failure.getSender().getName() + " has failed!");
        }

    }
}