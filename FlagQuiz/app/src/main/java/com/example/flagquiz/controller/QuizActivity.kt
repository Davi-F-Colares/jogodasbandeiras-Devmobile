package com.example.flagquiz.controller

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.flagquiz.R
import com.example.flagquiz.model.FlagQuestion

class QuizActivity : AppCompatActivity() {

    private lateinit var selectedQuestions: List<FlagQuestion>
    private var currentQuestionIndex = 0
    private var score = 0
    private var isAnswerSubmitted = false
    private var playerName = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_quiz)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.quizMain)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        playerName = intent.getStringExtra("PLAYER_NAME") ?: "Jogador"

        val allFlags = getFlagBank()
        selectedQuestions = allFlags.shuffled().take(5)

        loadQuestion()

        val actionButton = findViewById<Button>(R.id.actionButton)
        actionButton.setOnClickListener {
            if (!isAnswerSubmitted) {
                checkAnswer()
            } else {
                nextQuestion()
            }
        }
    }

    private fun loadQuestion() {
        isAnswerSubmitted = false
        val actionButton = findViewById<Button>(R.id.actionButton)
        actionButton.text = "Responder"

        val answerEditText = findViewById<EditText>(R.id.answerEditText)
        answerEditText.text.clear()
        answerEditText.isEnabled = true

        val feedbackTextView = findViewById<TextView>(R.id.feedbackTextView)
        feedbackTextView.text = ""

        val currentQuestion = selectedQuestions[currentQuestionIndex]

        val counterTextView = findViewById<TextView>(R.id.counterTextView)
        counterTextView.text = "${currentQuestionIndex + 1} de 5"

        val imgFlag = findViewById<ImageView>(R.id.imgFlag)
        imgFlag.setImageResource(currentQuestion.imageResId)
    }

    private fun checkAnswer() {
        val answerEditText = findViewById<EditText>(R.id.answerEditText)
        val userAnswer = answerEditText.text.toString().trim()

        if (userAnswer.isEmpty()) {
            Toast.makeText(this, "Digite o nome do país!", Toast.LENGTH_SHORT).show()
            return
        }

        val currentQuestion = selectedQuestions[currentQuestionIndex]
        val feedbackTextView = findViewById<TextView>(R.id.feedbackTextView)

        if (userAnswer.equals(currentQuestion.countryName, ignoreCase = true)) {
            score += 20
            feedbackTextView.text = "Correto!"
            feedbackTextView.setTextColor(Color.GREEN)
        } else {
            feedbackTextView.text = "Incorreto! Resposta: ${currentQuestion.countryName}"
            feedbackTextView.setTextColor(Color.RED)
        }

        answerEditText.isEnabled = false
        isAnswerSubmitted = true

        val actionButton = findViewById<Button>(R.id.actionButton)
        if (currentQuestionIndex == selectedQuestions.size - 1) {
            actionButton.text = "Ver Resultado"
        } else {
            actionButton.text = "Próxima Pergunta"
        }
    }

    private fun nextQuestion() {
        if (currentQuestionIndex < selectedQuestions.size - 1) {
            currentQuestionIndex++
            loadQuestion()
        } else {
            val intent = Intent(this, ResultActivity::class.java)
            intent.putExtra("PLAYER_NAME", playerName)
            intent.putExtra("SCORE", score)
            startActivity(intent)
            finish()
        }
    }

    private fun getFlagBank(): List<FlagQuestion> {
        return listOf(
            FlagQuestion("Brasil", R.drawable.ic_flag_br),
            FlagQuestion("Argentina", R.drawable.ic_flag_ar),
            FlagQuestion("Cuba", R.drawable.ic_flag_cu),
            FlagQuestion("China", R.drawable.ic_flag_cn),
            FlagQuestion("Alemanha", R.drawable.ic_flag_de),
            FlagQuestion("Inglaterra", R.drawable.ic_flag_en),
            FlagQuestion("Peru", R.drawable.ic_flag_pe),
            FlagQuestion("Espanha", R.drawable.ic_flag_es),
            FlagQuestion("Portugal", R.drawable.ic_flag_pt),
            FlagQuestion("Paraguai", R.drawable.ic_flag_py),
            FlagQuestion("Marrocos", R.drawable.ic_flag_ma),
            FlagQuestion("Egito", R.drawable.ic_flag_eg),
            FlagQuestion("Irlanda", R.drawable.ic_flag_ie),
            FlagQuestion("Uruguai", R.drawable.ic_flag_uy),
            FlagQuestion("Chile", R.drawable.ic_flag_cl)
        )
    }
}