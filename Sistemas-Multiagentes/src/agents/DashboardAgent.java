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

    protected void setup() {

    }

    protected void takeDown() {
   
    }

    public class DemandBehaviour extends AchieveREInitiator {

        public DemandBehaviour(Agent a, MessageTemplate temp) {
            super(a, temp);
        }

        protected void handleAgree(ACLMessage agree) {
            // TO-DO
        }

        protected void handleRefuse(ACLMessage refuese) {
            // TO-DO
        }

        protected void handleNotUnderstood(ACLMessage notUnderstood) {
            // TO-DO
        }

        protected void handleInform(ACLMessage inform) {
            // TO-DO
        }

        protected void handleFailure(ACLMessage failure) {
            // TO-DO
        }

    }
}