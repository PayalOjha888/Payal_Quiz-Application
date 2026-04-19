package com.QuizApp.Quiz.Service;

import com.QuizApp.Quiz.Dao.QuestionDao;
import com.QuizApp.Quiz.Model.Questions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class QuestionService {

    @Autowired
    QuestionDao questionDao;

    public ResponseEntity<List<Questions>> getAllQuestions() {
        try {
            return new ResponseEntity<>(questionDao.findAll(), HttpStatus.OK);
        }catch(Exception e){
            e.printStackTrace();
        }
        return new ResponseEntity<>(new ArrayList<>(), HttpStatus.BAD_REQUEST);
    }

    public ResponseEntity<List<Questions>> getByCategory(String category) {
        try {
            return new ResponseEntity<>(questionDao.findAllByCategory(category), HttpStatus.OK);
        }catch(Exception e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>(new ArrayList<>(), HttpStatus.BAD_REQUEST);
    }

    public boolean addQuestion(Questions questions) {
        Questions saved = questionDao.save(questions);
        if(saved!=null){
            return true;
        }
        return false;
    }

    public ResponseEntity<String> updateQuestion(Questions question) {
        questionDao.save(question);
        return new ResponseEntity<>("Success", HttpStatus.CREATED);
    }

    public ResponseEntity<String> deleteQuestionById(int id) {
       try{
           questionDao.deleteById(id);
           return new ResponseEntity<>("Question with id "+id+" has been deleted!", HttpStatus.OK);
       }
       catch(Exception e){
           e.printStackTrace();
       }
        return new ResponseEntity<>("Failed to delete", HttpStatus.BAD_REQUEST);
    }
}
