package CalculatorTema;

import javax.swing.JOptionPane;

import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;
import proiect1.SendMessageAgent.AgentA_SendMessage;
import proiect1.SendMessageAgent.ResponseReceiver;

public class CalcAgent extends Agent {
	private static final long serialVersionUID = 1L;
	String result="";
	String replyOperands = "";
	protected void setup(){
	    System.out.println("Calculator is ready.");  
	    addBehaviour(new AgentCalcSendRequest());
	    addBehaviour(new ReceiveOperationAndNumber());
	    addBehaviour(new SendOperationResult());
	}
    public class AgentCalcSendRequest extends CyclicBehaviour{
		private static final long serialVersionUID = 1L;

		@Override
        public void action(){
        	try {
            ACLMessage msg = new ACLMessage(ACLMessage.REQUEST);
            msg.addReceiver(new AID(String.valueOf("Distribution"),AID.ISLOCALNAME));
            msg.setContent("Numbers and operation");
            send(msg);
        	Thread.sleep(10000);
        	}catch(Exception e) {}
        }
    }
    public class ReceiveOperationAndNumber extends CyclicBehaviour{
		private static final long serialVersionUID = 1L;

		@Override
        public void action(){
        ACLMessage remsg = myAgent.receive();
            if(remsg!=null){
                String reply = remsg.getContent();
                if(reply.equals("Close")) {
                myAgent.doDelete();
                }else {
                replyOperands = remsg.getContent();
                String senderAgent = remsg.getSender().getName();
                System.out.println(senderAgent);
               // JOptionPane.showMessageDialog(null, "Reply From "+senderAgent +":- "+reply, "Response from Agent: "+senderAgent , JOptionPane.INFORMATION_MESSAGE);
                String[] resolv = reply.split("[\\+\\-\\*\\/\\.]");
                if(reply.contains("+")) {
                	result = String.valueOf(Integer.valueOf(resolv[0]) + Integer.valueOf(resolv[1]));
                }
                if(reply.contains("-")) {
                	result = String.valueOf(Integer.valueOf(resolv[0]) - Integer.valueOf(resolv[1]));
                }
                if(reply.contains("*")) {
                	result = String.valueOf(Integer.valueOf(resolv[0]) * Integer.valueOf(resolv[1]));
                }
                if(reply.contains("/")) {
                	result = String.valueOf(Integer.valueOf(resolv[0]) / Integer.valueOf(resolv[1]));
                }
                }
            }
            else{
            block();
            }
        }
    }
    public class SendOperationResult extends CyclicBehaviour{
		private static final long serialVersionUID = 1L;

		@Override
        public void action(){
        	try {
                ACLMessage msg = new ACLMessage(ACLMessage.REQUEST);
                msg.addReceiver(new AID(String.valueOf("Distribution"),AID.ISLOCALNAME));
                msg.setContent(replyOperands +"="+ result);
                send(msg);
            	}catch(Exception e) {}
        }
    }

}
