package com.QuizApp.Quiz.Service;

import com.QuizApp.Quiz.Dao.QuestionDao;
import com.QuizApp.Quiz.Dao.QuizDao;
import com.QuizApp.Quiz.Model.QuestionWrapper;
import com.QuizApp.Quiz.Model.Questions;
import static org.junit.jupiter.api.Assertions.*;

import com.QuizApp.Quiz.Model.Quizzes;
import com.QuizApp.Quiz.Model.Responses;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class QuizServiceTest {

    @InjectMocks
    QuizService quizService;

    @Mock
    QuizDao quizDao;

    @Mock
    QuestionDao questionDao;

    @Test
    public void testingCreateQuiz(){
        //1. Arrange
        String category = "java";
        int numQ = 5;
        String title = "Quiz: 1";

        Questions questions = new Questions();
        questions.setCategory(category);
        List<Questions> list = List.of(questions);

        when(questionDao.findRandomQuestionsByCategory(category, numQ)).thenReturn(list);

        //2. Act

        ResponseEntity<String> response = quizService.createQuiz(category, numQ, title);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals("Success!", response.getBody());

        //3. Assert
        verify(quizDao, times(1)).save(any(Quizzes.class));

    }

    @Test
    public void testGetQuizQuestions(){
        Questions question = Questions.builder().id(1).category("java").
                questionTitle("What is the parent of all classes in Java?").
                option1("String[]").option2("Iterable").option3("Object").
                option4("None of these").difficultyLevel("easy").rightAnswer("Object").build();
        int id = 2;
        String title = "Quiz: 2";

        List<Questions> list = List.of(question);

        Quizzes quiz = Quizzes.builder().id(id).title(title).questions(list).build();

        when(quizDao.findById(quiz.getId())).thenReturn(Optional.of(quiz));

        ResponseEntity<List<QuestionWrapper>> quizQuestions = quizService.getQuizQuestions(id);

        assertEquals(HttpStatus.OK, quizQuestions.getStatusCode());
        assertNotNull(quizQuestions.getBody());

        assertEquals(1, quizQuestions.getBody().size());
        assertEquals("What is the parent of all classes in Java?", quizQuestions.getBody().getFirst().getQuestionTitle());
        assertEquals("Object", quizQuestions.getBody().getFirst().getOption3());

        verify(quizDao, times(1)).findById(id);
    }

    @Test
    public void testCalculateQuestions(){

        //Arrange

        Questions question1 = Questions.builder().id(1).rightAnswer("Object").build();
        Questions question2 = Questions.builder().id(2).rightAnswer("JVM").build();

        int quizId = 1;
        String title = "Quiz:1";
        Quizzes quiz = Quizzes.builder().id(quizId).title(title).questions(List.of(question1, question2)).build();

        when(quizDao.findById(quizId)).thenReturn(Optional.of(quiz));

        Responses res1 = Responses.builder().response("Object").build();
        Responses res2 = Responses.builder().response("JRE").build();

        //Act

        ResponseEntity<Integer> response = quizService.calculateResult(quizId, List.of(res1, res2));

        //Assert

        assertEquals(HttpStatus.OK, response.getStatusCode());

        assertEquals(1, response.getBody());

        verify(quizDao, times(1)).findById(quizId);
    }


}
