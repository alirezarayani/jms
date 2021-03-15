package io.rayani;

import io.rayani.model.Employee;
import org.apache.activemq.artemis.jms.client.ActiveMQConnectionFactory;

import javax.jms.JMSContext;
import javax.jms.JMSProducer;
import javax.jms.Topic;
import javax.naming.InitialContext;
import javax.naming.NamingException;

public class HRApp {
    public static void main(String[] args) throws NamingException {
        InitialContext initialContext = new InitialContext();
        Topic topic = (Topic) initialContext.lookup("topic/myTopic");
        try (ActiveMQConnectionFactory cf = new ActiveMQConnectionFactory();
             JMSContext jmsContext = cf.createContext()) {
            JMSProducer producer = jmsContext.createProducer();
            //initial employee
            Employee employee = new Employee();
            employee.setId(123);
            employee.setFirstName("Alireza");
            employee.setLastName("Rayani");
            employee.setEmail("Alireza.rayani@gmail.com");
            employee.setDesignation("Software Architect");
            employee.setPhone("09124060764");

            producer.send(topic,employee);
            System.out.println("Message Send");
        }
    }
}
