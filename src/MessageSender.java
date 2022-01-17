
import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.Queue;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;

public class MessageSender {
	
	private static final String BROKER_URL = ActiveMQConnection.DEFAULT_BROKER_URL;
	private static final String SUBJECT = "MESSAGE";
	private static final String MESSAGE = "HELLO BROTHER, YOU HAVE A NEW MESSAGE TO READ";
	
	
	public static void main(String args[]) throws JMSException {
		
		String [] topics = {"SAEED", "SARFRAZ"};
		
		ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(BROKER_URL);
		
		Connection connection = connectionFactory.createConnection();
		connection.start();
		
		Session session = connection.createSession(false, Session.DUPS_OK_ACKNOWLEDGE);
		
//		Destination destination = session.createQueue(null);

		
		MessageProducer producer = session.createProducer(null);
		
		Queue [] destinationQueues = new Queue[2];
		
		destinationQueues[0] = session.createQueue(topics[0]);
		destinationQueues[1] = session.createQueue(topics[1]);
		
		
		TextMessage message = session.createTextMessage(MESSAGE);
		
		for(Queue queue: destinationQueues) {
			producer.send(queue, message);			
		}
		
		System.out.println("MESSAGE SENT TO QUEUE " + message.getText());
		producer.close();
		connection.close();
		session.close();
	}
	
}
