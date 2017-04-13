package com.example.josh.simplequizzer.UI


import android.graphics.Color
import android.os.Bundle
import android.support.constraint.ConstraintLayout
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.Button
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.TextView
import com.example.josh.simplequizzer.Model.Quiz
import com.example.josh.simplequizzer.Model.randomize
import com.example.josh.simplequizzer.R
import org.jetbrains.anko.include
import org.jetbrains.anko.onClick
import org.jetbrains.anko.textColor
import org.jetbrains.anko.toast

class QuizActivity : AppCompatActivity() {

    val difficulty : String? = null
    var question : TextView? = null
    var radio_button_easy_1 : RadioButton? = null
    var radio_button_easy_2 : RadioButton? = null
    var radio_button_easy_3 : RadioButton? = null
    var radio_button_normal : RadioButton? = null
    var radio_button_hard : RadioButton? = null
    var submit_butn : Button? = null
    var radio_group : RadioGroup? = null
    var results : String? = null
    var constraint_layout : ConstraintLayout? = null
    var quiz : Quiz? = null
    var incorrect : Int = 0
    var round = 0


    override fun onCreate(savedInstanceState : Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_quiz)
        quiz = Quiz(intent.getStringExtra("difficulty") , intent.getIntExtra("choices" , 10))
        constraint_layout = include<View>(R.layout.activity_quiz) as ConstraintLayout
        question = findViewById(R.id.question_view) as TextView
        question!!.textColor = Color.WHITE
        radio_button_easy_1 = findViewById(R.id.easy_difficulty_radio_1) as RadioButton
        radio_button_easy_2 = findViewById(R.id.easy_difficulty_radio_2) as RadioButton
        radio_button_easy_3 = findViewById(R.id.easy_difficulty_radio_3) as RadioButton
        radio_button_normal = findViewById(R.id.normal_difficulty_radio) as RadioButton
        radio_button_hard = findViewById(R.id.hard_difficulty_radio) as RadioButton
        radio_group = findViewById(R.id.user_choices_group) as RadioGroup

        submit_butn = findViewById(R.id.submit_button) as Button
        submit_butn!!.onClick {
            var selectedId : Int?
            var selectedButton : RadioButton?
            if (radio_group!!.checkedRadioButtonId == -1) {
                toast("Please make a selection")
            } else {
                selectedId = radio_group!!.checkedRadioButtonId
                selectedButton = findViewById(selectedId) as RadioButton
                submitAnswer(selectedButton)

            }
        }
    }

    private fun submitAnswer(selectedButton : RadioButton) {
        if (quiz!!.isLastQuestion || quiz!!.questions.size == 1) {
            results = quiz!!.endQuiz()
            radio_group!!.visibility = View.GONE
            round++
            question!!.text = "You + Math = ? \n\n A M☆thster! \n\n You made: $incorrect mistakes, out of $round questions"
            submit_butn!!.text = "Play Again"
            submit_butn!!.onClick {
                finish()

            }
        } else if (selectedButton.text == quiz!!.currentQuestion!!.answer.toString()) {
            quiz!!.answerQuestion(selectedButton.text.toString())
            quiz!!.progressRound()
            setChoices()
            round++
            toast("☆ great job! ")
        } else {
            toast("✘, try again")
            incorrect++

        }
    }

    override fun onResume() {
        super.onResume()
        setChoices()
    }

    private fun setChoices() {
        question!!.text = quiz!!.currentQuestion.toString()
        var arrayOfChoices : Array<RadioButton?>
        var indexOfAns : Int?
        when (quiz!!.diff) {
            "normal" -> {
                radio_button_hard!!.visibility = View.GONE
                arrayOfChoices = arrayOf(radio_button_easy_1 , radio_button_easy_2 , radio_button_easy_3 , radio_button_normal)

            }
            "hard"   -> {
                radio_button_hard!!.visibility = View.VISIBLE
                arrayOfChoices = arrayOf(radio_button_easy_1 , radio_button_easy_2 , radio_button_easy_3 , radio_button_normal , radio_button_hard)


            }
            "easy"   -> {
                radio_button_hard!!.visibility = View.GONE
                radio_button_normal!!.visibility = View.GONE
                arrayOfChoices = arrayOf(radio_button_easy_1 , radio_button_easy_2 , radio_button_easy_3)

            }
            else     -> {
                arrayOfChoices = arrayOfNulls(4)
            }
        }

        //TODO ensure that bound always stays positive
        indexOfAns = 0.randomize(arrayOfChoices.size)
        for (i in 0..arrayOfChoices.size - 1) {
            if (i == indexOfAns) {
                arrayOfChoices[i]!!.text = quiz!!.currentQuestion!!.answer.toString()
            } else {
                val x = 0.randomize(quiz!!.currentQuestion!!.answer.toInt() + 1) + 1.0
                arrayOfChoices[i]!!.text = x.toString()
            }
        }


    }
}


