package com.j2bya.practice.Configs;

import com.rabbitmq.client.Channel;
import org.axonframework.amqp.eventhandling.spring.SpringAMQPMessageSource;
import org.axonframework.serialization.Serializer;
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

@RefreshScope
@Configuration
public class TrainShiftAmqpConfig {

  private static final Logger LOGGER = getLogger(TrainShiftAmqpConfig.class);

  @Value("${axon.amqp.exchange}")
  private String exchangeName;

  @Autowired
  private PlatformTransactionManager transactionManager;
  @Bean
  public Queue ticketQueue(){
    return new Queue("trainShift", true);
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
      @RabbitListener(queues = "trainShift")
      @Override
      @Transactional
      public void onMessage(Message message, Channel channel){
        LOGGER.debug("Message received: " +message.toString());
        super.onMessage(message, channel);
      }
    };
  }
//  @Bean
//  public SagaConfiguration<TicketSaga> ticketSagaConfiguration(Serializer serializer){
//    SagaConfiguration<TicketSaga> sagaConfiguration = SagaConfiguration.subscribingSagaManager(TicketSaga.class, c-> queueMessageSource(serializer));
//    return sagaConfiguration;
//  }

//  @Bean
//  public TransactionManagingInterceptor transactionManagingInterceptor(){
//    return new TransactionManagingInterceptor(new SpringTransactionManager(transactionManager));
//  }
}
