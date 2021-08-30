package com.lvpf.mq;

import javax.jms.Queue;
import javax.jms.Connection;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.MessageProducer;
import javax.jms.ObjectMessage;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.swing.plaf.basic.BasicInternalFrameTitlePane.IconifyAction;

import org.apache.activemq.ActiveMQConnectionFactory;
/**
 * 消息接收
 * @author lpf18
 *
 */

public class ReceiverQueue {
	
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
		Queue queue = session.createQueue("user");
		
		//5、获取消息		
		MessageConsumer consumer = session.createConsumer(queue);
		
		consumer.setMessageListener(new MessageListener() {
			
			@Override
			public void onMessage(Message message) {
				// TODO Auto-generated method stub
				/* TextMessage message = (TextMessage) consumer.receive(); */
			if(message instanceof TextMessage) {
				try {
					System.out.println("message：" + ((TextMessage)message).getText());
				} catch (JMSException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}else
				if(message instanceof ObjectMessage) {
					ObjectMessage objectMessage = (ObjectMessage)message;
					try {
						Girl girl = (Girl)objectMessage.getObject();
						System.out.println(girl.getName());
						System.out.println(girl.getPrice());
						System.out.println(girl.getAge());
						
					}catch (Exception e) {
						// TODO: handle exception
					}
				}
			}
		});
		
		
		/*
		 * while (true) { TextMessage message = (TextMessage) consumer.receive();
		 * System.out.println("message：" + message.getText()); }
		 */
	}
}
