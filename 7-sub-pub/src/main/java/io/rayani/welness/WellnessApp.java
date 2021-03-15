package io.rayani.welness;

import io.rayani.model.Employee;
import org.apache.activemq.artemis.jms.client.ActiveMQConnectionFactory;

import javax.jms.*;
import javax.naming.InitialContext;
import javax.naming.NamingException;

public class WellnessApp {
    public static void main(String[] args) throws NamingException {
        InitialContext initialContext = new InitialContext();
        Topic topic = (Topic) initialContext.lookup("topic/myTopic");
        try (ActiveMQConnectionFactory cf = new ActiveMQConnectionFactory();
             JMSContext jmsContext = cf.createContext()) {
            JMSConsumer consumer = jmsContext.createConsumer(topic);
            Message message = consumer.receive();
            Employee employee = message.getBody(Employee.class);
            System.out.println(String.format("This is a employee in Wellness consumer: \\n %s",employee));
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }
}
