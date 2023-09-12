package com.bignerdranch.android.geoquiz

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.activity.viewModels
import com.bignerdranch.android.geoquiz.databinding.ActivityMainBinding

private const val TAG = "MainActivity"

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    private val quizViewModel: QuizViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG, "onCreate(Bundle?) called")
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        Log.d(TAG, "Got a QuizViewModel: $quizViewModel")


        binding.trueButton.setOnClickListener { view: View ->
            checkAnswer(true)
        }

        binding.falseButton.setOnClickListener {view: View ->
            checkAnswer(false)
        }

        binding.nextButton.setOnClickListener {
            quizViewModel.moveToNext()
            updateQuestion()

        }

        binding.previousButton.setOnClickListener {
            quizViewModel.moveToPrev()
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
        val questionTextResId = quizViewModel.currentQuestionText
        binding.questionTextView.setText(questionTextResId)

        //check if button has been answered already and enable buttons accordingly
        if (quizViewModel.checkAnswered() == true && binding.trueButton.isEnabled == true){
            binding.trueButton.isEnabled = false
            binding.falseButton.isEnabled = false
        }
        else if (quizViewModel.checkAnswered() == false && binding.trueButton.isEnabled == false){
            binding.trueButton.isEnabled = true
            binding.falseButton.isEnabled = true
        }
    }

    private fun checkAnswer(userAnswer: Boolean){
        val correctAnswer = quizViewModel.currentQuestionAnswer

        val messageResId = if (userAnswer == correctAnswer) {
            R.string.correct_toast
        } else {
            R.string.incorrect_toast
        }

        if (userAnswer == correctAnswer) {
            quizViewModel.incrementCorrect()
        }

        Toast.makeText(this, messageResId, Toast.LENGTH_SHORT)
            .show()

        //disable buttons and set question to answered
        binding.trueButton.isEnabled = false
        binding.falseButton.isEnabled = false
        quizViewModel.changeAnswered()

        quizViewModel.incrementAnswered()

        //check if all questions has been answered; if yes, display score
        if (quizViewModel.getNumberAnswered() == quizViewModel.getBankSize()){
            val score = (quizViewModel.getNumberCorrect().toFloat() / quizViewModel.getNumberAnswered()) * 100

            val scoreMessage = "Quiz Finished! Your Score: ${score.toInt()}%"
            Toast.makeText(this, scoreMessage, Toast.LENGTH_LONG)
                .show()
        }
    }
}
