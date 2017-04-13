package com.example.josh.simplequizzer

import com.example.josh.simplequizzer.Model.Question
import io.kotlintest.specs.BehaviorSpec
import org.amshove.kluent.shouldEqual
import org.junit.Test

class MockitoKotlin : BehaviorSpec() {

    init {
        Given("a question with 200 responses") {
            val q = Question("easy")
//  val test = Question("Does this question contain unique responses" , 2450 , 200)
            When("They are generated") {

                //                test.generateResponses()
                Then("Each possible choice should be unique") {
                    q.question shouldEqual q.answer
//                    val choices = test.arrayOfResponses.map { it !!.choice }
//                    choices shouldContain test.answer
                }
            }

        }

    }

    @Test
    fun testQuestionsResponsesAreUnique() {

        val q = Question()

        q.question shouldEqual q.answer
    }

}