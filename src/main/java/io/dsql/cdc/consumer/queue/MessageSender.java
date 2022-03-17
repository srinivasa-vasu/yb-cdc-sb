//package io.dsql.cdc.consumer.queue;
//
//import java.util.Map;
//
//import javax.annotation.PostConstruct;
//
//import com.fasterxml.jackson.databind.ObjectMapper;
//import io.dsql.cdc.consumer.db.Todo;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.jms.connection.CachingConnectionFactory;
//import org.springframework.jms.core.JmsTemplate;
//import org.springframework.stereotype.Component;
//
//@Component
//public class MessageSender {
//
//	@Autowired
//	private JmsTemplate jmsTemplate;
//
//	@PostConstruct
//	private void customizeJmsTemplate() {
//		CachingConnectionFactory ccf = new CachingConnectionFactory();
//		ccf.setTargetConnectionFactory(jmsTemplate.getConnectionFactory());
//		jmsTemplate.setConnectionFactory(ccf);
//		jmsTemplate.setPubSubDomain(false);
//	}
//
//	public void sendEvent(Map<String, Object> todoEvent) {
//		final Todo todo = new ObjectMapper().convertValue(todoEvent, Todo.class);
//		jmsTemplate.convertAndSend("solace-test", todo);
//	}
//
//}
