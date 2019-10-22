package com.icomm.jms;

import javax.jms.Message;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;


@Component
public class TopicSub {

	@JmsListener(destination="video.topic",containerFactory="jmsListenerContainerTopic")
	public void receive1(String text){
		System.out.println("video.topic :receive1="+text);
	}
	
	
	@JmsListener(destination="video.topic",containerFactory="jmsListenerContainerTopic")
	public void receive2(String text){
		System.out.println("video.topic :receive2="+text);
	}
	
	
	@JmsListener(destination="video.topic")
	public void receive3(String text){
		System.out.println("video.topic :receive3="+text);
	}
	
	@JmsListener(destination = "video.topic", containerFactory = "jmsListenerContainerTopic")
	public void receive4(Message msg, Session session) {
		try {
			if(msg instanceof TextMessage) {
				TextMessage textMsg = (TextMessage)msg;
				System.out.println("video.topic :receive4="+textMsg.getText());
							
				msg.acknowledge();
			}else {
				System.out.println("video.topic :receive4=  no message");
			}
			
		}catch(Exception e) {
			
		}
	}

	
	
	
}