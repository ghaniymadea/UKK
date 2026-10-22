package com.pemula.ukk

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.pemula.ukk.adapter.LogAdapter
import com.pemula.ukk.controller.LogAktivitasController
import com.pemula.ukk.databinding.ActivityManageLogsBinding
import kotlinx.coroutines.launch

class ManageLogsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityManageLogsBinding
    private lateinit var adapter: LogAdapter
    private val logController = LogAktivitasController()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityManageLogsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.ivBack.setOnClickListener { finish() }

        adapter = LogAdapter(emptyList())
        binding.rvLogs.layoutManager = LinearLayoutManager(this)
        binding.rvLogs.adapter = adapter

        loadLogs()
    }

    private fun loadLogs() {
        lifecycleScope.launch {
            val paged = logController.fetchLogAktivitasList(1, 50)
            if (paged != null) {
                adapter.updateData(paged.items)
            }
        }
    }
}
