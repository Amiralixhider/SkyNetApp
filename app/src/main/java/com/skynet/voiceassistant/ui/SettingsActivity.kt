package com.skynet.voiceassistant.ui

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.skynet.voiceassistant.Prefs
import com.skynet.voiceassistant.R

class SettingsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)
        val et = findViewById<EditText>(R.id.et_openai)
        val btn = findViewById<Button>(R.id.btn_save_openai)
        val sp = Prefs.getSecurePrefs(this)
        et.setText(sp.getString("OPENAI_API_KEY",""))
        btn.setOnClickListener {
            sp.edit().putString("OPENAI_API_KEY", et.text.toString().trim()).apply()
            Toast.makeText(this,"Saved", Toast.LENGTH_SHORT).show()
        }
    }
}
