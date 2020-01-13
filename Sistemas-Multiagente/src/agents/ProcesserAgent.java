package agents;

import jade.core.Agent;
import jade.core.behaviours.ParallelBehaviour;
import java.io.IOException;
import java.util.ArrayList;

import jade.core.AID;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import jade.lang.acl.UnreadableException;
import jade.proto.AchieveREResponder;
import jade.proto.AchieveREInitiator;

import jade.domain.FIPANames;
import jade.domain.FIPAAgentManagement.NotUnderstoodException;
import jade.domain.FIPAAgentManagement.RefuseException;

public class ProcesserAgent extends Agent {

	final static String DASHBOARD = "Dashboard";
	final static String PROCESSER = "Processer";

	final static int N_WEB_AGENTS = 3;

	private static RequestReceiver requestReceiver;
	private static ArrayList<PopulationData> maxPopulationsList = new ArrayList<PopulationData>();

	protected void setup() {

		MessageTemplate protocol = MessageTemplate.MatchProtocol(FIPANames.InteractionProtocol.FIPA_REQUEST);
		MessageTemplate performative = MessageTemplate.MatchPerformative(ACLMessage.REQUEST);
		MessageTemplate sender = MessageTemplate.MatchSender(new AID(DASHBOARD, AID.ISLOCALNAME));
		MessageTemplate temp = MessageTemplate.and(protocol, performative);
		temp = MessageTemplate.and(temp, sender);

		requestReceiver = new RequestReceiver(this, temp);
		addBehaviour(requestReceiver);
	}

	protected void takeDown() {
		System.out.println("[PROCESSER-AGENT] Taking down...");
	}

	public class RequestReceiver extends AchieveREResponder {

		public RequestReceiver(Agent a, MessageTemplate temp) {
			super(a, temp);
			System.out.println("[PROCESSER-AGENT] Waiting request...");
		}

		protected ACLMessage handleRequest(ACLMessage request) throws NotUnderstoodException, RefuseException {

			System.out.println("[PROCESSER-AGENT] " + request.getSender().getName() + " has sent a request.");
			ArrayList<String> msgContent = null;

			try {
				msgContent = (ArrayList<String>) request.getContentObject();
			} catch (UnreadableException e) {
				throw new NotUnderstoodException("[PROCESSER-AGENT] NotUnderstoodException: unreadable content of the message.");
			}

			ParallelBehaviour pb = new ParallelBehaviour();

			if (msgContent.size() == N_WEB_AGENTS) {

				String webAgentName, webAgentUrl;

				for (String content : msgContent) {

					String[] contentParts = content.split(">");
					webAgentName = contentParts[0];
					webAgentUrl = contentParts[1];

					System.out.println(
							"[PROCESSER-AGENT] URL: " + webAgentUrl + " will be processed by: " + webAgentName);

					ACLMessage msg = new ACLMessage(ACLMessage.REQUEST);
					msg.addReceiver(new AID((String) webAgentName, AID.ISLOCALNAME));
					msg.setProtocol(FIPANames.InteractionProtocol.FIPA_REQUEST);
					msg.setSender(new AID(PROCESSER, AID.ISLOCALNAME));
					msg.setContent(webAgentUrl);

					pb.addSubBehaviour(new RequestInitiator(myAgent, msg));
				}

			} else {
				throw new RefuseException("[PROCESSER-AGENT] RefuseException: the message content contains information for "
						+ msgContent.size() + " web agents but " + N_WEB_AGENTS + " web agents are needed!");
			}

			this.registerPrepareResultNotification(pb);

			ACLMessage agree = request.createReply();
			agree.setPerformative(ACLMessage.AGREE);
			return agree;
		}

	}

	public static class RequestInitiator extends AchieveREInitiator {

		private static int count = 0;

		public RequestInitiator(Agent a, ACLMessage msg) {
			super(a, msg);
		}

		protected void handleAgree(ACLMessage agree) {
			System.out.println("[PROCESSER-AGENT] " + agree.getSender().getName() + " has accepted the request.");
		}

		protected void handleRefuse(ACLMessage refuse) {
			System.out.println("[PROCESSER-AGENT] " + refuse.getSender().getName() + " has rejected the request.");
			System.out.println("[PROCESSER-AGENT] Reason - " + refuse.getContent());
		}

		protected void handleNotUnderstood(ACLMessage notUnderstood) {
			System.out.println(
					"[PROCESSER-AGENT] " + notUnderstood.getSender().getName() + " didn't understood the request.");
			System.out.println("[PROCESSER-AGENT] Reason - " + notUnderstood.getContent());
		}

		protected void handleInform(ACLMessage inform) {

			count++;

			System.out.println("[PROCESSER-AGENT] Received results from " + inform.getSender().getName());

			ArrayList<PopulationData> table = null;

			try {
				table = (ArrayList<PopulationData>) inform.getContentObject();
			} catch (UnreadableException e) {
				System.out.println(e.getMessage());
			}

			// Data processing: get greatest population
			PopulationData greatest = null;
			for (PopulationData pd : table) {
				if (greatest == null) {
					greatest = pd;
				}
				if (pd.getPopulation() > greatest.getPopulation()) {
					greatest = pd;
				}
			}

			maxPopulationsList.add(greatest);

			if (count >= N_WEB_AGENTS) {
				System.out.println("[PROCESSER-AGENT] Preparing result...");
				
				String incomingRequestkey = (String) requestReceiver.REQUEST_KEY;
				ACLMessage incomingRequest = (ACLMessage) requestReceiver.getDataStore().get(incomingRequestkey);
				// Prepare the notification to the request originator and store it in the
				// DataStore
				ACLMessage notification = incomingRequest.createReply();
				notification.setPerformative(ACLMessage.INFORM);
				try {
					notification.setContentObject(maxPopulationsList);
				} catch (IOException e) {
					System.out.println(e.getMessage());
				}
				String notificationkey = (String) requestReceiver.RESULT_NOTIFICATION_KEY;
				requestReceiver.getDataStore().put(notificationkey, notification);
			}

		}

		protected void handleFailure(ACLMessage failure) {
			System.out.println("[PROCESSER-AGENT] " + failure.getSender().getName() + " has failed!");
			System.out.println("[PROCESSER-AGENT] Reason - " + failure.getContent());
		}

	}

}
