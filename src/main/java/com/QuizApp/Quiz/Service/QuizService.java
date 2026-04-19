package com.QuizApp.Quiz.Service;

import com.QuizApp.Quiz.Dao.QuestionDao;
import com.QuizApp.Quiz.Dao.QuizDao;
import com.QuizApp.Quiz.Model.QuestionWrapper;
import com.QuizApp.Quiz.Model.Questions;
import com.QuizApp.Quiz.Model.Quizzes;
import com.QuizApp.Quiz.Model.Responses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class QuizService {

    @Autowired
    QuizDao quizDao;

    @Autowired
    QuestionDao questionDao;

    public ResponseEntity<String> createQuiz(String category, int numQ, String title) {

        List<Questions> questions = questionDao.findRandomQuestionsByCategory(category, numQ);

        Quizzes quizzes = new Quizzes();
        quizzes.setTitle(title);
        quizzes.setQuestions(questions);

        quizDao.save(quizzes);
        return new ResponseEntity<>("Success!", HttpStatus.CREATED);
    }

    public ResponseEntity<List<QuestionWrapper>> getQuizQuestions(Integer id) {
        Optional<Quizzes> quizzes = quizDao.findById(id);

        List<Questions> quesFromDB= quizzes.get().getQuestions();

        List<QuestionWrapper> questionsForUsers = new ArrayList<>();

        for(Questions q : quesFromDB){
            QuestionWrapper qw = new QuestionWrapper(q.getId(), q.getQuestionTitle(),
                    q.getOption1(), q.getOption2(), q.getOption3(), q.getOption4());
            questionsForUsers.add(qw);
        }

        return new ResponseEntity<>(questionsForUsers, HttpStatus.OK);
    }

    public ResponseEntity<Integer> calculateResult(Integer id, List<Responses> responses) {
             Optional<Quizzes> quizzes = quizDao.findById(id);
             List<Questions> list = quizzes.get().getQuestions();
             int right = 0;
             int i=0;
             for(Responses response : responses){

                   if(response.getResponse().equals(list.get(i).getRightAnswer()))
                         right+=1;

                 i+=1;
             }
             return new ResponseEntity<>(right, HttpStatus.OK);
    }
}
