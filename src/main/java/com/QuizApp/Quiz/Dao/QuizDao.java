package com.QuizApp.Quiz.Dao;

import com.QuizApp.Quiz.Model.Quizzes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface QuizDao extends JpaRepository<Quizzes, Integer> {
}
