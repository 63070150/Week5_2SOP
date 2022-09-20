package com.example.week5_2sop;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SentenceConsumer {
    public RabbitTemplate rabbitTemplate;
    protected Sentence sentences = new Sentence();
    @RabbitListener(queues = "BadWordQueue")
    public void addBadSentence(String s){
        sentences.badSentence.add(s);
    }
    @RabbitListener(queues = "GoodWordQueue")
    public void addGoodSentence(String s){
        sentences.goodSentence.add(s);
    }

    @RabbitListener(queues = "GetQueue")
    public Sentence getSentencs() {
        return sentences;
    }
}
