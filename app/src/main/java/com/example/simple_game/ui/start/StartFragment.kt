package com.example.simple_game.ui.start

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.simple_game.R
import com.example.simple_game.databinding.FragmentStartBinding
import com.example.simple_game.util.AppPreference

class StartFragment : Fragment(R.layout.fragment_start) {

    private lateinit var binding: FragmentStartBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentStartBinding.bind(view)

        binding.buttonStartTest.setOnClickListener {
            findNavController().navigate(R.id.action_startFragment_to_mainFragment)
        }

        binding.buttonClearStatistic.setOnClickListener {
            AppPreference.clearPreferences()
            Toast.makeText(context, "Statistics cleared successfully", Toast.LENGTH_SHORT).show()
        }
    }

}