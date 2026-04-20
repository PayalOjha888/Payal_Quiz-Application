package com.QuizApp.Quiz.Service;

import com.QuizApp.Quiz.Dao.QuestionDao;
import com.QuizApp.Quiz.Model.Questions;
import org.assertj.core.util.Strings;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ArgumentsSource;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.verification.VerificationMode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.Assert;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

@ExtendWith(MockitoExtension.class)
public class QuestionServiceTest {

    @InjectMocks
    private QuestionService service;

    @Mock
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

    @Test
    public void testAddQuestions(){
        //1. Data preparation
        Questions questions = new Questions();
        questions.setId(1);
        questions.setCategory("java");
        questions.setQuestionTitle("Why is java platform independent?");
        questions.setOption1("Bytecode");
        questions.setOption2("jvm");
        questions.setOption3("JDK");
        questions.setOption4("None");
        questions.setDifficultyLevel("Easy");
        questions.setRightAnswer("Bytecode");

        //2. Mocking the calls
        Mockito.when(dao.save(questions)).thenReturn(questions);

        //3. calling the method

        Questions addedQuestion = service.addQuestion(questions);

        //4. Apply assertions

        Assertions.assertEquals(questions.getId(), addedQuestion.getId());

        Assertions.assertNotNull(addedQuestion);

        Assertions.assertEquals(questions.getQuestionTitle(), addedQuestion.getQuestionTitle());

    }

    @Test
    void testDeleteQuestions(){
        Mockito.doNothing().when(dao).deleteById(1);
        service.deleteQuestionById(1);
        Mockito.verify(dao, Mockito.times(1)).deleteById(1);
    }

    @Test
    void testUpdateQuestionsMethod() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Method validateName = QuestionService.class.getDeclaredMethod("validateName", String.class);

        validateName.setAccessible(true);

        Boolean result = (Boolean) validateName.invoke(service, "Default value of boolean in java?");

        Assertions.assertTrue(result);
    }
}
