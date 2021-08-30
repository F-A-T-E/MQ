package com.lvpf.mq;

import javax.jms.Queue;
import javax.jms.Connection;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;
import org.apache.activemq.ActiveMQConnectionFactory;

public class SenderTopic {
	
	public static void main(String[] args) throws Exception {
		//1、获取连接工厂
		
		ActiveMQConnectionFactory activeMQConnectionFactory = new ActiveMQConnectionFactory(
				ActiveMQConnectionFactory.DEFAULT_USER,
				ActiveMQConnectionFactory.DEFAULT_PASSWORD,
				"tcp://localhost:61616"
				);
		//2、获取一个向ActiveMQ的连接		
		Connection connection = activeMQConnectionFactory.createConnection();
		
		//3、获取session
		Session session = connection.createSession(true, Session.AUTO_ACKNOWLEDGE);
		
		
		//4、找目的地，获取destination，消费端，也会从这个目的地获取消息
		Destination topic = session.createTopic("user");
		
		//5、向目的地写入消息
		
		MessageProducer messageProducer = session.createProducer(topic);
		//consumer -> 消费者
		//producer -> 创建者
		for (int i = 0; i < 100; i++) {
			TextMessage textMessage = session.createTextMessage("hi: "+i);
			//向目的地写入消息
			
			messageProducer.send(textMessage);
			
		//	Thread.sleep(1000);
		}
				
		
		//6、关闭连接
		connection.close();
		System.out.println("system exit...");
	}
}
