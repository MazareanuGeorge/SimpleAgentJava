package CalculatorProject;

import jade.core.Agent;
import jade.core.behaviours.*;
import jade.lang.acl.*;


public class CalcAgent3 extends Agent
{

    protected void setup()
    {
		addBehaviour(new CyclicBehaviour(this)
		{
			 public void action() {
				 ACLMessage msg = myAgent.receive();
					//Create an ACLMessage and check message queue
					 String content = msg.getContent();
					//Perform the appropriate check on the response from the message queue
					if (content != "")
					{
						System.out.println("Content");
						//Retrieve content from message and convert to integer

						//Perform some computation to return the result

						//Create and send reply message back calling agent
						//NOTE: Convert integer to string for content of reply


					}
				block();
			 }
		});
	}
}
