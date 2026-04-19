package com.QuizApp.Quiz.Service;

import com.QuizApp.Quiz.Dao.QuestionDao;
import com.QuizApp.Quiz.Model.Questions;
import org.assertj.core.util.Strings;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ArgumentsSource;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

@SpringBootTest
public class QuestionServiceTest {

    @Autowired
    private QuestionService service;

    @Autowired
    private QuestionDao dao;

    @Test
    public void testGetAllQuestions(){
        Assertions.assertNotNull(service.getAllQuestions());
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "java",
            "python"
    })
    public void testGetQuestionsByCategory(String category){
        Assertions.assertNotNull(service.getByCategory(category));
    }

    @ParameterizedTest
    @ArgumentsSource(QuestionServiceTestArguments.class)
    public void testAddQuestions(Questions questions){
        boolean b = service.addQuestion(questions);
        Assertions.assertTrue(b, "failed : " + questions);
    }

    @ParameterizedTest
    @ValueSource(ints = {
            31, 32, 33, 34, 35, 36, 37, 38, 39, 39
    })
    void testDeleteQuestions(int id){
        HttpStatus ok = HttpStatus.OK;
        ResponseEntity<String> stringResponseEntity = service.deleteQuestionById(id);
        Assertions.assertEquals(ok, stringResponseEntity.getStatusCode());
    }

    @ParameterizedTest
    @ArgumentsSource(QuestionServiceTestArguments.class)
    void testUpdateQuestionsMethod(Questions questions){
        ResponseEntity<String> stringResponseEntity = service.updateQuestion(questions);
        Assertions.assertEquals(HttpStatus.CREATED, stringResponseEntity.getStatusCode());
    }


}
