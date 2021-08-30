package com.lvpf.mq;

import javax.jms.Queue;
import javax.jms.Connection;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;
import org.apache.activemq.ActiveMQConnectionFactory;
/**
 * 消息接收
 * @author lpf18
 *
 */

public class ReceiverTopic {
	
	public static void main(String[] args) throws Exception {
		//1、获取连接工厂
		
		ActiveMQConnectionFactory activeMQConnectionFactory = new ActiveMQConnectionFactory(
				ActiveMQConnectionFactory.DEFAULT_USER,
				ActiveMQConnectionFactory.DEFAULT_PASSWORD,
				"tcp://localhost:61616"
				);
		//2、获取一个向ActiveMQ的连接		
		Connection connection = activeMQConnectionFactory.createConnection();
		
		connection.start();
		//3、获取session
		Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);		
		
		//4、找目的地，获取destination，消费端，也会从这个目的地获取消息
		Destination topic = session.createTopic("user");
		
		//5、获取消息		
		MessageConsumer consumer = session.createConsumer(topic);
		
		while (true) {
			TextMessage message = (TextMessage) consumer.receive();	
			System.out.println("message：" + message.getText());
		}
	}
}
