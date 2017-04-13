package com.example.josh.simplequizzer.UI


import android.content.Intent
import android.os.Bundle
import android.support.constraint.ConstraintLayout
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.TextView
import com.example.josh.simplequizzer.R
import org.jetbrains.anko.include
import org.jetbrains.anko.onClick
import org.jetbrains.anko.toast

class MainActivity : AppCompatActivity() {


    var choices : Int? = null
    var selectText : TextView? = null
    var mainImage : ImageView? = null
    var choicesText : EditText? = null
    var difficultyGroup : RadioGroup? = null
    var startButton : Button? = null
    var easyDifficultyChoice : RadioButton? = null
    var normalDifficultyChoice : RadioButton? = null
    var hardDifficultyChoice : RadioButton? = null
    var cLayout : ConstraintLayout? = null
    override fun onCreate(savedInstanceState : Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        cLayout = include<View>(R.layout.activity_main) {} as ConstraintLayout
        choicesText = findViewById(R.id.choices_text) as EditText
        selectText = findViewById(R.id.textView) as TextView
        mainImage = findViewById(R.id.title_view) as ImageView
        easyDifficultyChoice = findViewById(R.id.difficulty_easy) as RadioButton
        normalDifficultyChoice = findViewById(R.id.difficulty_normal) as RadioButton
        hardDifficultyChoice = findViewById(R.id.difficulty_hard) as RadioButton
        difficultyGroup = findViewById(R.id.difficulty_group) as RadioGroup
        startButton = findViewById(R.id.start_button) as Button
        startButton !!.onClick {
            if (choicesText !!.text.toString() != "" && difficultyGroup !!.checkedRadioButtonId != - 1) {
                choices = choicesText !!.text.toString().toInt()
                val selectedButton = findViewById(difficultyGroup !!.checkedRadioButtonId) as RadioButton
                val difficultyChoice = selectedButton.text.toString()

                var i : Intent = Intent(this , QuizActivity::class.java)
                i.putExtra("choices" , choices !!)
                i.putExtra("difficulty" , difficultyChoice)
                startActivity(i)
            } else {
                toast("Please fill in all inputs")
            }

        }

    }
}
