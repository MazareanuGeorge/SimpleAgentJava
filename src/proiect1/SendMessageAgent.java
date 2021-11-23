package proiect1;

import java.util.Random;

import javax.swing.JOptionPane;

import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.core.behaviours.OneShotBehaviour;
import jade.lang.acl.ACLMessage;

public class SendMessageAgent extends Agent {
	private static final long serialVersionUID = 1L;
	protected void setup(){
	    System.out.println("Sender is ready.");  
	    addBehaviour(new AgentA_SendMessage());
	    addBehaviour(new ResponseReceiver()); }
    public class AgentA_SendMessage extends CyclicBehaviour{
		private static final long serialVersionUID = 1L;

		@Override
        public void action(){
        	try {
        	Thread.sleep(5000);
        	int agent =  (int) ((Math.random() * (4 - 1)) + 1);
            ACLMessage msg = new ACLMessage(ACLMessage.REQUEST);
            msg.addReceiver(new AID(String.valueOf(agent),AID.ISLOCALNAME));
            msg.setContent("Tell the time?");
            send(msg);
        	Thread.sleep(1000);
        	}catch(Exception e) {}
        }
    }
    public class ResponseReceiver extends CyclicBehaviour{
		private static final long serialVersionUID = 1L;

		@Override
        public void action(){
        ACLMessage remsg = myAgent.receive();
            if(remsg!=null){
                String reply = remsg.getContent();
                String senderAgent = remsg.getSender().getName().substring(0,1);
                System.out.println("Reply From "+String.valueOf(remsg.getSender().getName()).substring(0,1)+":- "+reply);
                JOptionPane.showMessageDialog(null, "Reply From "+senderAgent +":- "+reply, "Response from Agent: "+senderAgent , JOptionPane.INFORMATION_MESSAGE);
            }
            else{
            block();
            }
        }
    }
}
