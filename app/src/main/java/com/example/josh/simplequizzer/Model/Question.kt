package com.example.josh.simplequizzer.Model

import java.util.*

fun Int.randomize(seed : Int = 0) : Int {
    val rand : Random = Random()
    if (seed == 0) return rand.nextInt()
    else return rand.nextInt(seed)

}

fun Double.randomize(seed : Int = 0) : Int {
    val rand : Random = Random()
    if (seed == 0) return rand.nextInt()
    else return rand.nextInt(seed)

}


class Quiz(difficulty : String , amountOfQuestions : Int) {
    var questions : Array<Question?> = arrayOfNulls(amountOfQuestions)
    var correct = 0
    var rounds = 0
    var currentQuestion : Question? = null
    var isLastQuestion : Boolean = false
    val diff = difficulty


    init {
        for (i in 0..amountOfQuestions - 1) {
            this.questions[i] = Question(difficulty.toLowerCase())
//            this.questions[i]!!.generateQuestion()
        }
        currentQuestion = questions[0]
    }

    fun progressRound() : Boolean {

        if (rounds == questions.size - 2) { //last round
            this.isLastQuestion = true
            this.rounds++
            this.currentQuestion = questions[rounds]
            return isLastQuestion
        } else {
            this.rounds++
            this.currentQuestion = questions[rounds]
            return isLastQuestion
        }

    }


    fun answerQuestion(r : String) : Boolean {
        if (r == currentQuestion!!.answer.toString()) {
            this.correct++
            return true
        }
        return false
    }


    fun endQuiz() : String {
        return "$correct/$rounds"
    }

}


data class Question(val difficulty : String = "normal") {


    var listOfChoices : MutableList<Double?> = mutableListOf()
    var answer : Double = 0.0
    var question : String? = ""

    init {
        generateQuestion()
    }

    private fun generateQuestion() {

        when (difficulty) {

            "hard"   -> {
                //populate list
                while (listOfChoices.size != 5) {
                    listOfChoices.add(answer.randomize(300) + 1.0)
                }
                setQuestion(listOfChoices)

            }
            "normal" -> {
                while (listOfChoices.size != 3) {
                    listOfChoices.add(answer.randomize(100) + 1.0)
                }
                setQuestion(listOfChoices)

            }
            "easy"   -> {
                while (listOfChoices.size != 3) {
                    listOfChoices.add(answer.randomize(50) + 1.0)
                }
                setQuestion(listOfChoices)

            }
        }


    }

    private fun setQuestion(ofChoices : MutableList<Double?>) {

        this.answer = ofChoices[0]!!
        this.question = "$answer"
        try {
            for (i in 1..ofChoices.size - 1) {
                if (i == ofChoices.lastIndex) {
                    this.question += "= ?"
                } else {
                    when (generateOperators()) {
                        "+" -> {
                            this.question = "(${this.question} + ${ofChoices[i]}) "
                            this.answer += ofChoices[i]!!
                        }
                        "-" -> {
                            this.question = "(${this.question} - ${ofChoices[i]}) "
                            this.answer -= ofChoices[i]!!
                        }
                        "*" -> {
                            this.question += " * ${ofChoices[i]} "
                            this.answer *= ofChoices[i]!!
                        }
                        "%" -> {
                            this.question += " % ${ofChoices[i]} "
                            this.answer %= ofChoices[i]!!
                        }
                        "/" -> {
                            this.question += " / ${ofChoices[i]} "
                            this.answer /= ofChoices[i]!!
                        }

                    }

                }
            }
        }
        catch (aoe : ArithmeticException) {
            this.answer = 0.0
        }

        this.answer
    }

    private fun generateOperators() : String {

        val operations : Array<String> = arrayOf("+" , "-" , "*" , "/" , "%")

        return operations[operations.size.randomize(operations.size)]

    }

    override fun toString() : String {
        return "What does $question"
    }


}




