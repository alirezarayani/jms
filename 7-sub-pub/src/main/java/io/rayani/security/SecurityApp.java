package io.rayani.security;

import io.rayani.model.Employee;
import org.apache.activemq.artemis.jms.client.ActiveMQConnectionFactory;

import javax.jms.*;
import javax.naming.InitialContext;
import javax.naming.NamingException;

public class SecurityApp {
    public static void main(String[] args) throws NamingException {
        InitialContext initialContext = new InitialContext();
        Topic topic = (Topic) initialContext.lookup("topic/myTopic");
        try (ActiveMQConnectionFactory cf = new ActiveMQConnectionFactory();
             JMSContext jmsContext = cf.createContext()) {
            jmsContext.setClientID("securityApp");
            JMSConsumer consumer = jmsContext.createDurableConsumer(topic, "subscription1");
            consumer.close();
//            JMSConsumer consumer = jmsContext.createConsumer(topic);
            Thread.sleep(10000);
            consumer = jmsContext.createDurableConsumer(topic, "subscription1");
            Message message = consumer.receive();
            Employee employee = message.getBody(Employee.class);
            System.out.println(String.format("This is a employee in security consumer: \\n %s", employee));
            jmsContext.unsubscribe("subscription1");
            consumer.close();
            cf.close();
        } catch (JMSException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}
