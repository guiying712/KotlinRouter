package com.guiying712.demo

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.guiying712.annotations.Router
import com.guiying712.demo.databinding.ActivityMainBinding
import com.guiying712.router.ARouter

@Router("/main")
class MainActivity : AppCompatActivity() {

  private lateinit var binding: ActivityMainBinding

  override fun onCreate(savedInstanceState: Bundle?) {
    // Switch to AppTheme for displaying the activity
    setTheme(R.style.AppTheme)
    super.onCreate(savedInstanceState)
    val binding = ActivityMainBinding.inflate(layoutInflater)
    setContentView(binding.root)
    ARouter.startUrl(this,"/login")
    binding.recyclerView.apply {
      layoutManager = LinearLayoutManager(this@MainActivity)
    }
  }
}
