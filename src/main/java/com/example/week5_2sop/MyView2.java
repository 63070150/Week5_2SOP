package com.example.week5_2sop;

import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;
import org.springframework.http.MediaType;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

import java.lang.reflect.Array;
import java.util.ArrayList;


@Route(value = "index2")
public class MyView2 extends HorizontalLayout {
    public MyView2() {
        TextField word = new TextField("Add Word");
        Button addgood = new Button("Add Good Word");
        Button addbad = new Button("Add Bad Word");
        VerticalLayout h1 = new VerticalLayout();
        VerticalLayout h2 = new VerticalLayout();
        Select<String> good = new Select<>();
        good.setLabel("Good Words");
        Select<String> bad = new Select<>();
        bad.setLabel("Bad Words");
        h1.add(word, addgood, addbad, good, bad);

        TextField sentence = new TextField("Add Sentence");
        Button addsen = new Button("Add Sentence");
        TextField goodsen = new TextField("Good Sentences");
        TextField badsen = new TextField("Bad Sentences");
        Button show = new Button("Show Sentence");
        h2.add(sentence, addsen, goodsen, badsen, show);

        add(h1, h2);

        addgood.addClickListener(event ->{
            MultiValueMap<String, String>formData = new LinkedMultiValueMap<>();
            formData.add("word", word.getValue());
            ArrayList<String> out = WebClient.create()
                    .post()
                    .uri("http://127.0.0.1:8080/addGood")
                    .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                    .body(BodyInserters.fromFormData(formData))
                    .retrieve()
                    .bodyToMono(ArrayList.class)
                    .block();
            good.setItems(out);
            new Notification("Insert"+word.getValue()+"in good word list", 2000).open();
        });
        addbad.addClickListener(event ->{
            MultiValueMap<String, String>formData = new LinkedMultiValueMap<>();
            formData.add("word", word.getValue());
            ArrayList<String> out = WebClient.create()
                    .post()
                    .uri("http://127.0.0.1:8080/addBad")
                    .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                    .body(BodyInserters.fromFormData(formData))
                    .retrieve()
                    .bodyToMono(ArrayList.class)
                    .block();
            bad.setItems(out);
            new Notification("Insert"+word.getValue()+"in bad word list", 2000).open();
        });
        addsen.addClickListener(event ->{
            MultiValueMap<String, String>formData = new LinkedMultiValueMap<>();
            formData.add("sentence", sentence.getValue());
            String out = WebClient.create()
                    .post()
                    .uri("http://127.0.0.1:8080/proof")
                    .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                    .body(BodyInserters.fromFormData(formData))
                    .retrieve()
                    .bodyToMono(String.class)
                    .block();
            new Notification(out, 2000).open();
        });
        show.addClickListener(event ->{
            Sentence out = WebClient.create()
                    .get()
                    .uri("http://127.0.0.1:8080/getSentence")
                    .retrieve()
                    .bodyToMono(Sentence.class)
                    .block();
            goodsen.setValue(out.goodSentence.toString());
            badsen.setValue(out.badSentence.toString());
        });
    }
}
