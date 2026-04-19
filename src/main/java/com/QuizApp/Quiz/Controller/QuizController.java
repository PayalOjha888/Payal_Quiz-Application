package com.QuizApp.Quiz.Controller;

import com.QuizApp.Quiz.Model.QuestionWrapper;
import com.QuizApp.Quiz.Model.Responses;
import com.QuizApp.Quiz.Service.QuizService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("quiz")
public class QuizController {

    @Autowired
    QuizService quizService;

    //Function to create a quiz which is taking the category,
    // number of questions and title or name that a user want to assign to the quiz
    @PostMapping("create")
    public ResponseEntity<String> createQuiz(@RequestParam String category, @RequestParam int numQ,
                                             @RequestParam String title){
            return quizService.createQuiz(category, numQ, title);
    }

    @GetMapping("get/{id}")
    public ResponseEntity<List<QuestionWrapper>> getQuizQuestions(@PathVariable Integer id){
        return quizService.getQuizQuestions(id);
    }

    @PostMapping("/submit/{id}")
    public ResponseEntity<Integer> submitQuiz(@PathVariable Integer id, @RequestBody List<Responses> responses){
             return quizService.calculateResult(id, responses);
    }

}
