import javax.jms.Connection;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;


public class MessageReciever implements Runnable{

	
	  private static final String BROKER_URL = ActiveMQConnection.DEFAULT_BROKER_URL;
	  private static final String SUBJECT = "MESSAGE";
	  
	  
	  public static void main(String args[]) throws JMSException, InterruptedException {			
		  
				Runnable r1 = new MessageReciever();
				Runnable r2 = new MessageReciever();
				
				Thread t1 = new Thread(r1);
				Thread t2 = new Thread(r2);
				
			     t1.start();
			     
			     Thread.sleep(5000);
			     
			     t2.start();			     
			  
	  }


	@Override
	public void run() {	
		Connection connection = null;
		try {
			
			ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory(BROKER_URL);
		
			connection = connectionFactory.createConnection();
			
			connection.start();
			
			Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
			
			Destination destination = session.createQueue("SARFRAZ");
			
			
			MessageConsumer messageConsumer = session.createConsumer(destination);
			Message message =  messageConsumer.receive();
			
			if(message instanceof TextMessage) {			
				TextMessage messageResponse = (TextMessage) message;
				System.out.println("RECIEVE MESSAGE " + messageResponse.getText());
				
			}
			
			
		}
		catch(JMSException ex) {
			 System.out.println(ex.getMessage());	
		}
		
		try {
			connection.close();
		} catch (JMSException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
}
