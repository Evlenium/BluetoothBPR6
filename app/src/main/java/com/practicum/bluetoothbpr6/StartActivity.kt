package com.practicum.bluetoothbpr6

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.practicum.bluetoothbpr6.databinding.ContentStartBinding

class StartActivity : AppCompatActivity() {

    private lateinit var binding: ContentStartBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ContentStartBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}