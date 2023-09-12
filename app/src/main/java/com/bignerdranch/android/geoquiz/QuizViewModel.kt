package com.bignerdranch.android.geoquiz

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel

private const val TAG = "QuizViewModel"
const val CURRENT_INDEX_KEY = "CURRENT_INDEX_KEY"

class QuizViewModel(private val savedStateHandle: SavedStateHandle) : ViewModel() {

    private val questionBank = listOf(
        Question(R.string.question_australia, true, false),
        Question(R.string.question_oceans, true, false),
        Question(R.string.question_mideast, false, false),
        Question(R.string.question_africa, false, false),
        Question(R.string.question_americas, true, false),
        Question(R.string.question_asia, true, false))

    private var currentIndex: Int
        get() = savedStateHandle.get(CURRENT_INDEX_KEY) ?: 0
        set(value) = savedStateHandle.set(CURRENT_INDEX_KEY, value)
    private var questionsAnswered = 0
    private var numberCorrect = 0

    val currentQuestionAnswer: Boolean
        get() = questionBank[currentIndex].answer

    val currentQuestionText: Int
        get() = questionBank[currentIndex].textResId

    /*
    val currentIn: Int
        get() = currentIndex

    val questionBankView: List<Question>
            get() = questionBank

     */

    fun moveToNext() {
        currentIndex = (currentIndex + 1) % questionBank.size
    }

    fun moveToPrev() {
        currentIndex = (currentIndex - 1) % questionBank.size

        if (currentIndex < 0){
            currentIndex = questionBank.size - 1
        }
    }

    fun checkAnswered(): Boolean {
        return questionBank[currentIndex].isAnswered
    }

    fun changeAnswered() {
        questionBank[currentIndex].isAnswered = true
    }

    fun incrementCorrect() {
        numberCorrect++
    }

    fun incrementAnswered() {
        questionsAnswered++
    }

    fun getNumberCorrect(): Int {
        return numberCorrect
    }

    fun getNumberAnswered(): Int {
        return questionsAnswered
    }

    fun getBankSize(): Int {
        return questionBank.size
    }

}