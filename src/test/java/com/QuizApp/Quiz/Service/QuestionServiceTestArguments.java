package com.QuizApp.Quiz.Service;

import com.QuizApp.Quiz.Model.QuestionWrapper;
import com.QuizApp.Quiz.Model.Questions;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;
import org.junit.jupiter.params.support.ParameterDeclarations;

import java.util.stream.Stream;


public class QuestionServiceTestArguments implements ArgumentsProvider {
    @Override
    public Stream<? extends Arguments> provideArguments(ParameterDeclarations parameters, ExtensionContext context) throws Exception {
        return Stream.of(
                Arguments.of(Questions.builder().questionTitle("What is the range of data type int in java?").option1("-2^31 to 2^31-1").option2("-2^8 to 2^8-1").option3("-2^64 to 2^64-1").option4("All of these").category("java").difficultyLevel("easy").rightAnswer("-2^31 to 2^31-1").build()),
                Arguments.of(Questions.builder().questionTitle("What is scope of private modifier in java?").option1("Method level").option2("Class level").option3("Package level").option4("None of these").category("java").difficultyLevel("easy").rightAnswer("Class level").build())
        );
    }
}
