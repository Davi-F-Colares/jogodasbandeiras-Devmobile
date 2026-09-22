package com.example.flagquiz.controller

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.flagquiz.R

class ResultActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_result)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.resultMain)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val playerName = intent.getStringExtra("PLAYER_NAME") ?: "Jogador"
        val score = intent.getIntExtra("SCORE", 0)

        val playerNameTextView = findViewById<TextView>(R.id.playerNameTextView)
        playerNameTextView.text = "Parabéns, $playerName!"

        val scoreTextView = findViewById<TextView>(R.id.scoreTextView)
        scoreTextView.text = "Pontuação Final: $score / 100 pontos"

        val restartButton = findViewById<Button>(R.id.restartButton)
        restartButton.setOnClickListener {
            finish()
        }
    }
}