package com.bignerdranch.android.geoquiz

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.Toast
import com.bignerdranch.android.geoquiz.databinding.ActivityMainBinding

private const val TAG = "MainActivity"

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    private val questionBank = listOf(
        Question(R.string.question_australia, true, false),
        Question(R.string.question_oceans, true, false),
        Question(R.string.question_mideast, false, false),
        Question(R.string.question_africa, false, false),
        Question(R.string.question_americas, true, false),
        Question(R.string.question_asia, true, false))

    private var currentIndex = 0
    private var questionsAnswered = 0
    private var numberCorrect = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG, "onCreate(Bundle?) called")
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.trueButton.setOnClickListener { view: View ->
            checkAnswer(true)
        }

        binding.falseButton.setOnClickListener {view: View ->
            checkAnswer(false)
        }

        binding.nextButton.setOnClickListener {
            currentIndex = (currentIndex + 1) % questionBank.size
            updateQuestion()

        }

        binding.previousButton.setOnClickListener {
            currentIndex = (currentIndex - 1) % questionBank.size
            if (currentIndex < 0){
                currentIndex = questionBank.size - 1
            }
            updateQuestion()

        }

        updateQuestion()
    }

    override fun onStart() {
        super.onStart()
        Log.d(TAG, "onStart() called")
    }

    override fun onResume() {
        super.onResume()
        Log.d(TAG, "onResume() called")
    }

    override fun onPause() {
        super.onPause()
        Log.d(TAG, "onPause() called")
    }

    override fun onStop() {
        super.onStop()
        Log.d(TAG, "onStop() called")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG, "onDestroy() called")
    }

    private fun updateQuestion() {
        val questionTextResId = questionBank[currentIndex].textResId
        binding.questionTextView.setText(questionTextResId)

        //check if button has been answered already and enable buttons accordingly
        if (questionBank[currentIndex].isAnswered == true && binding.trueButton.isEnabled == true){
            binding.trueButton.isEnabled = false
            binding.falseButton.isEnabled = false
        }
        else if (questionBank[currentIndex].isAnswered == false && binding.trueButton.isEnabled == false){
            binding.trueButton.isEnabled = true
            binding.falseButton.isEnabled = true
        }
    }

    private fun checkAnswer(userAnswer: Boolean){
        val correctAnswer = questionBank[currentIndex].answer

        val messageResId = if (userAnswer == correctAnswer) {
            R.string.correct_toast
        } else {
            R.string.incorrect_toast
        }

        if (userAnswer == correctAnswer) {
            numberCorrect++
        }

        Toast.makeText(this, messageResId, Toast.LENGTH_SHORT)
            .show()

        //disable buttons and set question to answered
        binding.trueButton.isEnabled = false
        binding.falseButton.isEnabled = false
        questionBank[currentIndex].isAnswered = true

        questionsAnswered++

        //check if all questions has been answered; if yes, display score
        if (questionsAnswered == questionBank.size){
            val score = (numberCorrect.toFloat() / questionsAnswered) * 100

            val scoreMessage = "Quiz Finished! Your Score: ${score.toInt()}%"
            Toast.makeText(this, scoreMessage, Toast.LENGTH_LONG)
                .show()
        }
    }
}
