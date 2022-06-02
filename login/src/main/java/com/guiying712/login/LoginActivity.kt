package com.guiying712.login

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.guiying712.annotations.Router
import com.guiying712.login.databinding.ActivityLoginBinding

@Router("/login")
class LoginActivity : AppCompatActivity() {
  private lateinit var binding: ActivityLoginBinding

  override fun onCreate(savedInstanceState: Bundle?) {
    // Switch to AppTheme for displaying the activity
    setTheme(R.style.AppTheme)
    super.onCreate(savedInstanceState)
    binding = ActivityLoginBinding.inflate(layoutInflater)
    setContentView(R.layout.activity_login)
    binding.recyclerView.apply {
      layoutManager = LinearLayoutManager(this@LoginActivity)
    }
  }
}
