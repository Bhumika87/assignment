package com.example.Practice.controller;
import com.example.Practice.pojo.QueAndAnswer;
import com.example.Practice.pojo.ResponseNext;
import com.example.Practice.service.ServiceLayer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {
    private final ServiceLayer serviceLayer;
     @Autowired
    public HomeController(ServiceLayer serviceLayer)
    {
        this.serviceLayer = serviceLayer;
    }

    @GetMapping("/")
    public ResponseEntity<?> getData() {

        return ResponseEntity.ok(serviceLayer.consumeAPI());

    }
    @GetMapping("/play")
    public ResponseEntity<?> getQuest() {
        return ResponseEntity.ok(serviceLayer.consumeQuestion());
    }
    @PostMapping("/next")
    public ResponseEntity<ResponseNext> checkAnsNextQue(@RequestBody QueAndAnswer queAndAnswer) {
        String queFromJson = queAndAnswer.getQuestion();
        String ansFromJson = queAndAnswer.getAnswer();
        int catId = serviceLayer.consumecatId(queFromJson);
        String ans = serviceLayer.consumeAns(catId);
        ResponseNext responseNext = new ResponseNext();
       if(ans.equals(ansFromJson)){
                 //if answer is matched
           responseNext.setCorrectAns("Correct");

        }else
        {
               //if ans is not matched
            responseNext.setCorrectAns("InCorrect Ans");
        }
            //get question
        responseNext.setNextQuestion(serviceLayer.consumeQuestion());

        return ResponseEntity.ok(responseNext);


    }
}
