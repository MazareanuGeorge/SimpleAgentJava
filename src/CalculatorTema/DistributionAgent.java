package CalculatorTema;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import CalculatorTema.CalcAgent.AgentCalcSendRequest;
import CalculatorTema.CalcAgent.ReceiveOperationAndNumber;
import jade.core.AID;
import jade.core.Agent;
import java.util.Queue;
import java.util.Set;
import java.util.UUID;

import jade.core.behaviours.CyclicBehaviour;
import jade.core.behaviours.SimpleBehaviour;
import jade.lang.acl.ACLMessage;

public class DistributionAgent extends Agent {
	Queue<String> agents = new LinkedList<String>();
	Set<String> listOfAgents = new HashSet<String>();
	UUID randomName = UUID.randomUUID();
	private int smallRange =  100;
	private int bigRange = 1000;
	protected void setup(){
	    System.out.println("Distributor is ready."); 
	    addBehaviour(new ReceiveOperationAndNumber());
	    addBehaviour(new SendNumberAndOperation());
	    addBehaviour(new SendKillOrder());
	}
    public class ReceiveOperationAndNumber extends CyclicBehaviour{
		private static final long serialVersionUID = 1L;

		@Override
        public void action(){
        ACLMessage remsg = myAgent.receive();
            if(remsg!=null){
                String reply = remsg.getContent();
                if(reply.equals("Numbers and operation"))
                {
                	System.out.println( "Received from" + remsg.getSender().getName());
                	agents.add(remsg.getSender().getName());
                	listOfAgents.add(remsg.getSender().getName());
                }else {
                	System.out.println("Result is " +remsg.getContent());
                    try {
						BufferedWriter fWriter = new BufferedWriter(new FileWriter(String.valueOf(randomName),true));
			            fWriter.write(remsg.getContent() + "(" + remsg.getSender().getName() + ")");
			            fWriter.newLine();
			            fWriter.close();
			            
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
                }

            }
            else{
            block();
            }
        }
    }
    
    public class SendNumberAndOperation extends CyclicBehaviour {

		@Override
		public void action() {
			// TODO Auto-generated method stub
			Integer firstNumber = (int) (Math.random() * (bigRange - smallRange + 1) + smallRange); 
			Integer secondNumber = (int) (Math.random() * (bigRange - smallRange + 1) + smallRange);
			String[] op =  {"+","-","*","/"};
			int locOperator = (int) (Math.random() * (3 - 0 + 1) + 0);
            if(!agents.isEmpty()) {
            	String[] agent = agents.poll().split("@");
                ACLMessage msg = new ACLMessage(ACLMessage.REQUEST);
                msg.addReceiver(new AID(agent[0],AID.ISLOCALNAME));
                msg.setContent(firstNumber.toString() + op[locOperator] + secondNumber.toString() );
                send(msg);
            }

		}
    	
    }
    
    public class SendKillOrder extends SimpleBehaviour {

		@Override
		public boolean done() {

			return true;
		}

		@Override
		public void action() {
		    JFrame f=new JFrame("Button to close all Calculator Agents");    
		    JButton b=new JButton("Click Here!");  
		    b.setBounds(50,100,95,30);    
		    f.add(b);
		    f.setSize(400,400);  
		    f.setLayout(null);  
		    f.setVisible(true);
		    System.out.println(listOfAgents.size());
    		    b.addActionListener(new ActionListener(){  
    				public void actionPerformed(ActionEvent e){
    					for(String agentAici : listOfAgents) {
    						ACLMessage msg = new ACLMessage(ACLMessage.INFORM);
    		                msg.addReceiver(new AID(agentAici,AID.ISGUID));
    		                msg.setContent("Close");
    		                send(msg);
    		                System.out.println("APASat");
    				        }
		                try {
							Thread.sleep(1000);
							myAgent.doDelete();
						} catch (InterruptedException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
    		    }
    		    });
		}
    }
    
}
