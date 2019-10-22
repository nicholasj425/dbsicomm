package com.icomm;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

import javax.jms.ConnectionFactory;
import javax.jms.Queue;
import javax.jms.Session;
import javax.jms.Topic;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.command.ActiveMQQueue;
import org.apache.activemq.command.ActiveMQTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.jms.config.DefaultJmsListenerContainerFactory;
import org.springframework.jms.config.JmsListenerContainerFactory;
import org.springframework.jms.core.JmsTemplate;

import tk.mybatis.spring.annotation.MapperScan;

@SpringBootApplication
//扫描 mybatis mapper 包路径
@MapperScan(basePackages = "com.icomm.mapper")
//扫描 所有需要的包, 包含一些自用的工具类包 所在的路径
@ComponentScan(basePackages= {"com.icomm", "org.n3r.idworker"})
//开启定时任务
@EnableScheduling
//开启异步调用方法
@EnableAsync
@EnableEurekaClient
@EnableHystrix
public class IcommApplication {

	public static void main(String[] args) {
		SpringApplication.run(IcommApplication.class, args);
	}

	@Bean
	public Queue queue(){
		return new ActiveMQQueue("first.queue");
	}

	//新建一个topic队列
	@Bean
	public Topic topic(){
		return new ActiveMQTopic("video.topic");
	}
	
	@Bean
    public JmsTemplate jmsTemplate(ConnectionFactory activeMQConnectionFactory,Queue queue){
    	JmsTemplate jmsTemplate=new JmsTemplate();
    	jmsTemplate.setConnectionFactory(activeMQConnectionFactory);
    	jmsTemplate.setDefaultDestination(queue); //此处可不设置默认，在发送消息时也可设置队列
    	jmsTemplate.setSessionAcknowledgeMode(4);//客户端签收模式
    	return jmsTemplate;
    }

	@Bean
	public JmsListenerContainerFactory<?> jmsListenerContainerTopic(ConnectionFactory activeMQConnectionFactory) {
		DefaultJmsListenerContainerFactory bean = new DefaultJmsListenerContainerFactory();
		bean.setPubSubDomain(true);
		bean.setConnectionFactory(activeMQConnectionFactory);
		bean.setSessionAcknowledgeMode(4);
		//bean.setSessionAcknowledgeMode(Session.CLIENT_ACKNOWLEDGE);
		//bean.setSessionAcknowledgeMode(ActiveMQJMSConstants.INDIVIDUAL_ACKNOWLEDGE);
		return bean;
	}

}
