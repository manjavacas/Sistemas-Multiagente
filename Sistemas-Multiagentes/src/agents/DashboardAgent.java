package agents;

import jade.core.Agent;
import jade.core.AID;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;

import jade.proto.AchieveREResponder;
import jade.proto.AchieveREInitiator;

import jade.domain.FIPANames;
import jade.domain.FIPAAGentManagement.NotUnderstoodException;
import jade.domain.FIPAAGentManagement.RefuseException;
import jade.domain.FIPAAGentManagement.FailureException;

public class DashboardAgent extends Agent {

    final static String DASHBOARD = "Dashboard";
    final static String PROCESSER = "Processer";
    final static String WEB_AGENT = "WebAgent";

    protected void setup() {

        // TO-DO: URLs tokenizer
        String msgContent = "";

        ACLMessage msg = new ACLMessage(ACLMessage.REQUEST);
        msg.addReceiver(new AID(PROCESSER, AID.ISLOCALNAME));
        msg.setProtocol(FIPANames.InteractionProtocol.FIPA_REQUEST);
        msg.setSender(new AID(DASHBOARD, AID.ISLOCALNAME));
        msg.setContent(msgContent);

        addBehaviour(new RequestInitiator(this, msg));
    }

    protected void takeDown() {
        System.out.println("[DASHBOARD-AGENT] Taking down...");
    }

    public class RequestInitiator extends AchieveREInitiator {

        public RequestInitiator(Agent a, MessageTemplate temp) {
            super(a, temp);
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
            // TO-DO: get results from message
            // TO-DO: implement interface with results
        }

        protected void handleFailure(ACLMessage failure) {
            System.out.println("[DASHBOARD-AGENT] " + failure.getSender().getName() + " has failed!");
        }

    }
}