package com.QuizApp.Quiz.Controller;

import com.QuizApp.Quiz.Model.Questions;
import com.QuizApp.Quiz.Service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/question")
public class QuestionController {

    @Autowired
    QuestionService questionService;

    @GetMapping("/allQuestions")
    public ResponseEntity<List<Questions>> getAllQuestions(){
        return questionService.getAllQuestions();
    }

    @GetMapping("/category/{category}")
    public ResponseEntity<List<Questions>> getByCategory(@PathVariable String category){
        return questionService.getByCategory(category);
    }

    @PostMapping("add")
    public ResponseEntity<String> addQuestion(@RequestBody Questions questions){

        try{
            if(questionService.addQuestion(questions)){
                return new ResponseEntity<>("Successfully saved the question in database.", HttpStatus.CREATED);
            }
            else {
                return new ResponseEntity<>("Failed to save the data", HttpStatus.FORBIDDEN);
            }
        }catch (Exception e){
            return new ResponseEntity<>("Failed to save the data", HttpStatus.FORBIDDEN);
        }
    }

    @PutMapping("add")
    public ResponseEntity<String> updateQuestion(@RequestBody Questions question){
        return questionService.updateQuestion(question);
    }

    @DeleteMapping("remove/{id}")
    public ResponseEntity<String> deleteQuestion(@PathVariable int id){
        return questionService.deleteQuestionById(id);
    }

}
