package com.j2bya.practice.Config;

import com.j2bya.practice.Saga.BookTicketSaga;
import com.j2bya.practice.Saga.RefundTicketSaga;
import com.rabbitmq.client.Channel;
import org.axonframework.amqp.eventhandling.spring.SpringAMQPMessageSource;
import org.axonframework.config.SagaConfiguration;
import org.axonframework.messaging.interceptors.TransactionManagingInterceptor;
import org.axonframework.serialization.Serializer;
import org.axonframework.spring.messaging.unitofwork.SpringTransactionManager;
import org.slf4j.Logger;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

import javax.transaction.Transactional;

import static org.slf4j.LoggerFactory.getLogger;

@Configuration
public class TicketAmqpConfig {

  private static final Logger LOGGER = getLogger(TicketAmqpConfig.class);

  @Value("${axon.amqp.exchange}")
  private String exchangeName;

  @Autowired
  private PlatformTransactionManager transactionManager;
  @Bean
  public Queue ticketQueue(){
    return new Queue("ticket", true);
  }

  @Bean
  public Exchange exchange(){
    return ExchangeBuilder.fanoutExchange(exchangeName).durable(true).build();
  }

  @Bean
  public Binding queueBinding(){
    return BindingBuilder.bind(ticketQueue()).to(exchange()).with("").noargs();
  }

  @Bean
  public SpringAMQPMessageSource queueMessageSource(Serializer serializer){
    return new SpringAMQPMessageSource(serializer){
      @RabbitListener(queues = "ticket")
      @Override
      @Transactional
      public void onMessage(Message message, Channel channel){
        LOGGER.debug("Message received: " +message.toString());
        super.onMessage(message, channel);
      }
    };
  }
  @Bean
  public SagaConfiguration<BookTicketSaga> bookTicketSagaConfiguration(Serializer serializer){
    return SagaConfiguration.subscribingSagaManager(BookTicketSaga.class, c-> queueMessageSource(serializer));
  }
  @Bean
  public SagaConfiguration<RefundTicketSaga> refundTicketSagaConfiguration(Serializer serializer){
    return SagaConfiguration.subscribingSagaManager(RefundTicketSaga.class, c-> queueMessageSource(serializer));
  }
  @Bean
  public TransactionManagingInterceptor transactionManagingInterceptor(){
    return new TransactionManagingInterceptor(new SpringTransactionManager(transactionManager));
  }
}
