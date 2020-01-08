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

public class ProcesserAgent extends Agents {

    final static String DASHBOARD = "Dashboard";
    final static String PROCESSER = "Processer";
    final static String WEB_AGENT = "WebAgent";

    protected void setup() {

        MessageTemplate protocol = MessageTemplate.MatchProtocol(FIPANames.InteractionProtocol.FIPA_REQUEST);
        MessageTemplate performative = MessageTemplate.MatchPerformative(ACLMessage.REQUEST);
        MessageTemplate sender = MessageTemplate.MatchSender(new AID(DASHBOARD, AID.ISLOCALNAME));

        MessageTemplate temp = MessageTemplate.and(protocol, performative);
        temp = MessageTemplate.and(temp, sender);

        addBehaviour(new RequestReceiver(this, temp));

    }

    protected void takeDown() {
        System.out.println("[PROCESSER] Taking down...");
    }

    public class RequestReceiver extends AchieveREResponder {

        public RequestReceiver(Agent a, MessageTemplate temp) {
            super(a, temp);
        }

        protected ACLMessage handleRequest(ACLMessage request) throws NotUnderstoodException, RefuseException {
            // TO-DO
        }

        protected ACLMessage prepareResultNotification(ACLMessage request, ACLMessage response)
                throws FailureException {
            // TO-DO
        }

    }

    public class RequestInitiator extends AchieveREInitiator {

        public RequestInitiator(Agent a, MessageTemplate temp) {
            super(a, temp);
        }

        protected void handleAgree(ACLMessage agree) {
            System.out.println("[PROCESSER] " + agree.getSender().getName() + " has accepted the request.");
        }

        protected void handleRefuse(ACLMessage refuese) {
            System.out.println("[PROCESSER] " + refuse.getSender().getName() + " has rejected the request.");
        }

        protected void handleNotUnderstood(ACLMessage notUnderstood) {
            System.out
                    .println("[PROCESSER] " + notUnderstood.getSender().getName() + " didn't understood the request.");
        }

        protected void handleInform(ACLMessage inform) {
            System.out.println("[PROCESSER] Received results from " + inform.getSender().getName());
            // TO-DO: get results from message and send them to dashboard
        }

        protected void handleFailure(ACLMessage failure) {
            System.out.println("[PROCESSER] " + failure.getSender().getName() + " has failed!");
        }

    }

}