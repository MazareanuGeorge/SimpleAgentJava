package proiect1;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;

public class ReceiverAgent extends Agent{
	private static final long serialVersionUID = 1L;

	protected void setup() {
	    System.out.println("Hello Agent : "+getAID().getName().substring(0,1) +" is ready.");
	    addBehaviour(new CockReader());
	}

    public class CockReader extends CyclicBehaviour{
		private static final long serialVersionUID = 1L;
		SimpleDateFormat dtf =new SimpleDateFormat("hh:mm:ss");
        @Override
        public void action(){
            ACLMessage remsg = myAgent.receive();
                if(remsg!=null){
                	System.out.println(remsg.getContent());
                    ACLMessage reply = remsg.createReply();
                    reply.setPerformative(ACLMessage.INFORM);
                    String price = remsg.getContent();
                    reply.setContent(dtf.format(Calendar.getInstance().getTimeInMillis()));
                    send(reply);     
                }else{
                    block();
                }
        }
}
    }
