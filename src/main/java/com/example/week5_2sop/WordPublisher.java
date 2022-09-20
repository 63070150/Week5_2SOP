package com.example.week5_2sop;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.lang.reflect.Array;
import java.util.ArrayList;

@RestController
public class WordPublisher {
    @Autowired
    private RabbitTemplate rabbitTemplate;
    public Word words = new Word();
    @RequestMapping(value = "/addBad/{word}", method = RequestMethod.GET)
    public ArrayList<String> addBadWord(@PathVariable("word") String s){
        words.badWords.add(s);
        return words.badWords;
    }
    @RequestMapping(value = "/delBad/{word}", method = RequestMethod.GET)
    public ArrayList<String> deleteBadWord(@PathVariable("word") String s){
        words.badWords.remove(s);
        return words.badWords;
    }
    @RequestMapping(value = "/addGood/{word}", method = RequestMethod.GET)
    public ArrayList<String> addGoodWord(@PathVariable("word") String s){
        words.goodWords.add(s);
        return words.goodWords;
    }
    @RequestMapping(value = "/delGood/{word}", method = RequestMethod.GET)
    public ArrayList<String> deleteGoodWord(@PathVariable("word") String s){
        words.goodWords.remove(s);
        return words.goodWords;
    }
    @RequestMapping(value = "proof/{sentence}", method = RequestMethod.GET)
    public String proofSentence(@PathVariable("sentence") String s) {
        boolean goodword = false;
        boolean badword = false;
        for (String Word : words.goodWords) {
            if (Word.contains(s)) {
                goodword = true;
            }
        }
        for (String Word : words.badWords) {
            if (Word.contains(s)) {
                badword = true;
            }
        }
        if (goodword && badword) {
            rabbitTemplate.convertAndSend("Fanout", "", s);
            return "Found Bad & Good Word";
        } else if (goodword) {
            rabbitTemplate.convertAndSend("GoodWordQueue", "good");
            return "Found Good Word";
        } else if (badword) {
            rabbitTemplate.convertAndSend("BadWordQueue", "bad");
            return "Found Bad Word";
        }
        return null;
    }
    @RequestMapping(value = "/getSentence", method = RequestMethod.GET)
    public Sentence getSentence(){
        Object laa = rabbitTemplate.convertSendAndReceive("GetQueue", "get", "");
        return (Sentence) laa;
    }

}
