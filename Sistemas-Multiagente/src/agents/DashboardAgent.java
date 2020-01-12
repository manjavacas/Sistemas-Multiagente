package agents;

import java.io.IOException;
import java.util.ArrayList;

// Agent imports
import jade.core.Agent;

import jade.core.AID;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.UnreadableException;
import jade.proto.AchieveREInitiator;
import jade.domain.FIPANames;

// Interface imports
import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.GridBagLayout;
import javax.swing.JTextField;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JButton;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class DashboardAgent extends Agent {

	final static String DASHBOARD = "Dashboard";
	final static String PROCESSER = "Processer";

	final static String WEB_AGENT_1 = "WebAgent1";
	final static String WEB_AGENT_2 = "WebAgent2";
	final static String WEB_AGENT_3 = "WebAgent3";

	protected void setup() {

		Form frame = null;

		try {
			frame = new Form();
			frame.setVisible(true);
		} catch (Exception e) {
			System.out.println("[DASHBOARD-AGENT] Form view failed: " + e.getMessage());
		}

		while (!frame.ready) { // wait until button event
			try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

		System.out.println("[DASHBOARD-AGENT] Received URLs! Sending to processer...");

		ArrayList<String> msgContent = new ArrayList<String>();
		msgContent.add(WEB_AGENT_1 + ">" + frame.url_1);
		msgContent.add(WEB_AGENT_2 + ">" + frame.url_2);
		msgContent.add(WEB_AGENT_3 + ">" + frame.url_3);

		ACLMessage msg = new ACLMessage(ACLMessage.REQUEST);
		msg.addReceiver(new AID(PROCESSER, AID.ISLOCALNAME));
		msg.setProtocol(FIPANames.InteractionProtocol.FIPA_REQUEST);
		msg.setSender(new AID(DASHBOARD, AID.ISLOCALNAME));

		try {
			msg.setContentObject(msgContent);
		} catch (IOException e) {
			System.out.println("[DASHBOARD-AGENT] " + e.getMessage());
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

			

		protected void handleInform(ACLMessage inform) {
			System.out.println("[DASHBOARD-AGENT] Received results from " + inform.getSender().getName());

			ArrayList<PopulationData> info = null;

			try {
				info = (ArrayList<PopulationData>) inform.getContentObject();
			} catch (UnreadableException e) {
				Syst

				
			System.out.println("[DASHBOARD-AGENT] Showing results...");

			Summary sum = null;

			try {
				sum = new Summary();
				sum.textPane.setText(info.get(0));
				sum.textPane_1.setText(info.get(1));
				sum.textPane_2.setText(info.get(2));
				sum.setVisible(true);
			} catch (Exception e) {
				System.out.println("[DASHBOARD-AGENT] Summary view failed: " + e.getMessage());
			}

		}

		protected void handleFailure(ACLMessage failure) {
			System.out.println("[DASHBOARD-AGENT] " + failure.getSender().getName() + " failed during execution!");
		}

	}

	/** Input information form */
	class Form extends JFrame {

		private static final long serialVersionUID = 1L;
		private JPanel contentPane;
		private JTextField textField1;
		private JTextField textField2;
		private JTextField textField3;

		public String url_1 = "";
		public String url_2 = "";
		public String url_3 = "";

		private String default_url_1 = "https://es.wikipedia.org/wiki/Boston";
		private String default_url_2 = "https://es.wikipedia.org/wiki/Chicago";
		private String default_url_3 = "https://es.wikipedia.org/wiki/Seattle";

		public boolean ready = false;

		/**
		 * Create the frame
		 */
		public Form() {
			setForeground(new Color(173, 216, 230));
			setTitle("Population data multi-agent system");
			setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			setBounds(100, 100, 695, 476);
			contentPane = new JPanel();
			contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
			contentPane.setLayout(new BorderLayout(0, 0));
			setContentPane(contentPane);

			JPanel panel = new JPanel();
			contentPane.add(panel, BorderLayout.CENTER);
			GridBagLayout gbl_panel = new GridBagLayout();
			gbl_panel.columnWidths = new int[] { 64, 0, 0, 0, 0 };
			gbl_panel.rowHeights = new int[] { 0, 0, 0, 0, 0, 0, 0 };
			gbl_panel.columnWeights = new double[] { 0.0, 0.0, 1.0, 1.0, Double.MIN_VALUE };
			gbl_panel.rowWeights = new double[] { 1.0, 0.0, 1.0, 1.0, 1.0, 1.0, Double.MIN_VALUE };
			panel.setLayout(gbl_panel);

			JLabel lblTitle = new JLabel("Enter URLs for each agent");
			lblTitle.setFont(new Font("Tahoma", Font.BOLD, 14));
			GridBagConstraints gbc_lblTitle = new GridBagConstraints();
			gbc_lblTitle.gridwidth = 2;
			gbc_lblTitle.insets = new Insets(0, 0, 5, 5);
			gbc_lblTitle.gridx = 1;
			gbc_lblTitle.gridy = 1;
			panel.add(lblTitle, gbc_lblTitle);

			JLabel lblUrl1 = new JLabel("URL 1");
			GridBagConstraints gbc_lblUrl1 = new GridBagConstraints();
			gbc_lblUrl1.insets = new Insets(0, 0, 5, 5);
			gbc_lblUrl1.gridx = 1;
			gbc_lblUrl1.gridy = 2;
			panel.add(lblUrl1, gbc_lblUrl1);

			textField1 = new JTextField(default_url_1);
			GridBagConstraints gbc_textField1 = new GridBagConstraints();
			gbc_textField1.fill = GridBagConstraints.HORIZONTAL;
			gbc_textField1.insets = new Insets(0, 0, 5, 5);
			gbc_textField1.gridx = 2;
			gbc_textField1.gridy = 2;
			panel.add(textField1, gbc_textField1);
			textField1.setColumns(10);

			JLabel lblUrl2 = new JLabel("URL 2");
			GridBagConstraints gbc_lblUrl2 = new GridBagConstraints();
			gbc_lblUrl2.insets = new Insets(0, 0, 5, 5);
			gbc_lblUrl2.gridx = 1;
			gbc_lblUrl2.gridy = 3;
			panel.add(lblUrl2, gbc_lblUrl2);

			textField2 = new JTextField(default_url_2);
			GridBagConstraints gbc_textField2 = new GridBagConstraints();
			gbc_textField2.fill = GridBagConstraints.HORIZONTAL;
			gbc_textField2.insets = new Insets(0, 0, 5, 5);
			gbc_textField2.gridx = 2;
			gbc_textField2.gridy = 3;
			panel.add(textField2, gbc_textField2);
			textField2.setColumns(10);

			JLabel lblUrl3 = new JLabel("URL 3");
			GridBagConstraints gbc_lblUrl3 = new GridBagConstraints();
			gbc_lblUrl3.insets = new Insets(0, 0, 5, 5);
			gbc_lblUrl3.gridx = 1;
			gbc_lblUrl3.gridy = 4;
			panel.add(lblUrl3, gbc_lblUrl3);

			textField3 = new JTextField(default_url_3);
			GridBagConstraints gbc_textField3 = new GridBagConstraints();
			gbc_textField3.insets = new Insets(0, 0, 5, 5);
			gbc_textField3.fill = GridBagConstraints.HORIZONTAL;
			gbc_textField3.gridx = 2;
			gbc_textField3.gridy = 4;
			panel.add(textField3, gbc_textField3);
			textField3.setColumns(10);

			JButton btnProcess = new JButton("Process");
			btnProcess.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					url_1 = textField1.getText();
					url_2 = textField2.getText();
					url_3 = textField3.getText();
					ready = true;
					dispose();
				}
			});

			btnProcess.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
			btnProcess.setBackground(new Color(135, 206, 235));
			btnProcess.setForeground(new Color(0, 0, 0));
			GridBagConstraints gbc_btnProcess = new GridBagConstraints();
			gbc_btnProcess.gridx = 3;
			gbc_btnProcess.gridy = 5;
			panel.add(btnProcess, gbc_btnProcess);
		}

		/* Output information form */
		class Summary extends JFrame {

			private static final long serialVersionUID = 1L;
			private JPanel contentPane;

			public JTextPane textPane;
			public JTextPane textPane_1;
			public JTextPane textPane_2;

			/**
			 * Create the frame.
			 */
			public Summary() {
				setTitle("Summary");
				setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				setBounds(100, 100, 783, 528);
				contentPane = new JPanel();
				contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
				setContentPane(contentPane);
				GridBagLayout gbl_contentPane = new GridBagLayout();
				gbl_contentPane.columnWidths = new int[] { 0, 0, 0, 0, 0 };
				gbl_contentPane.rowHeights = new int[] { 0, 0, 0, 0, 0, 0, 0 };
				gbl_contentPane.columnWeights = new double[] { 1.0, 0.0, 1.0, 1.0, Double.MIN_VALUE };
				gbl_contentPane.rowWeights = new double[] { 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, Double.MIN_VALUE };
				contentPane.setLayout(gbl_contentPane);

				JLabel lblTitle = new JLabel("Most populated years obtained...");
				lblTitle.setFont(new Font("Tahoma", Font.BOLD, 15));
				GridBagConstraints gbc_lblTitle = new GridBagConstraints();
				gbc_lblTitle.gridwidth = 2;
				gbc_lblTitle.insets = new Insets(0, 0, 5, 5);
				gbc_lblTitle.gridx = 1;
				gbc_lblTitle.gridy = 1;
				contentPane.add(lblTitle, gbc_lblTitle);

				JLabel lblResult1 = new JLabel("Result 1:");
				GridBagConstraints gbc_lblResult1 = new GridBagConstraints();
				gbc_lblResult1.insets = new Insets(0, 0, 5, 5);
				gbc_lblResult1.gridx = 1;
				gbc_lblResult1.gridy = 2;
				contentPane.add(lblResult1, gbc_lblResult1);

				textPane = new JTextPane();
				GridBagConstraints gbc_textPane = new GridBagConstraints();
				gbc_textPane.insets = new Insets(0, 0, 5, 5);
				gbc_textPane.fill = GridBagConstraints.HORIZONTAL;
				gbc_textPane.gridx = 2;
				gbc_textPane.gridy = 2;
				contentPane.add(textPane, gbc_textPane);

				JLabel lblResult2 = new JLabel("Result 2:");
				GridBagConstraints gbc_lblResult2 = new GridBagConstraints();
				gbc_lblResult2.insets = new Insets(0, 0, 5, 5);
				gbc_lblResult2.gridx = 1;
				gbc_lblResult2.gridy = 3;
				contentPane.add(lblResult2, gbc_lblResult2);

				textPane_1 = new JTextPane();
				GridBagConstraints gbc_textPane_1 = new GridBagConstraints();
				gbc_textPane_1.insets = new Insets(0, 0, 5, 5);
				gbc_textPane_1.fill = GridBagConstraints.HORIZONTAL;
				gbc_textPane_1.gridx = 2;
				gbc_textPane_1.gridy = 3;
				contentPane.add(textPane_1, gbc_textPane_1);

				JLabel lblResult3 = new JLabel("Result 3:");
				GridBagConstraints gbc_lblResult3 = new GridBagConstraints();
				gbc_lblResult3.insets = new Insets(0, 0, 5, 5);
				gbc_lblResult3.gridx = 1;
				gbc_lblResult3.gridy = 4;
				contentPane.add(lblResult3, gbc_lblResult3);

				textPane_2 = new JTextPane();
				GridBagConstraints gbc_textPane_2 = new GridBagConstraints();
				gbc_textPane_2.insets = new Insets(0, 0, 5, 5);
				gbc_textPane_2.fill = GridBagConstraints.HORIZONTAL;
				gbc_textPane_2.gridx = 2;
				gbc_textPane_2.gridy = 4;
				contentPane.add(textPane_2, gbc_textPane_2);
			}

		}

	}
}