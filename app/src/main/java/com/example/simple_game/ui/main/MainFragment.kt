package com.example.simple_game.ui.main

import android.annotation.SuppressLint
import android.app.Dialog
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.simple_game.R
import com.example.simple_game.databinding.FragmentMainBinding
import com.example.simple_game.util.AppPreference


class MainFragment : Fragment(R.layout.fragment_main) {

    private val viewModel: MainFragmentViewModel by viewModels()

    private lateinit var binding: FragmentMainBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentMainBinding.bind(view)

        observerLiveData()
    }

    @SuppressLint("SetTextI18n")
    private fun observerLiveData() {

        viewModel.getQuestion().observe(viewLifecycleOwner, { list ->

            binding.currentScore.text =
                "Score: ${viewModel.currentScore}/10"

            binding.question.text = "${viewModel.questionNumber + 1}. ${list[0]}"

            binding.radioButton1.text = list[1]
            binding.radioButton2.text = list[2]
            binding.radioButton3.text = list[3]
            binding.radioButton4.text = list[4]

            binding.buttonNext.setOnClickListener {
                when (binding.radioGroup.checkedRadioButtonId) {

                    binding.radioButton1.id ->
                        isCheckedRadioButtonTrue(list[1], list[5])

                    binding.radioButton2.id ->
                        isCheckedRadioButtonTrue(list[2], list[5])

                    binding.radioButton3.id ->
                        isCheckedRadioButtonTrue(list[3], list[5])

                    binding.radioButton4.id ->
                        isCheckedRadioButtonTrue(list[4], list[5])
                }

                nextQuestion()
            }
        })
    }

    private fun nextQuestion() {
        if (viewModel.questionNumber == 9) {
            endOfQuestions()
        } else {
            binding.radioButton1.isChecked = true
            viewModel.getNextQuestion()
        }

    }

    private fun endOfQuestions() {
        binding.buttonNext.isClickable = false
        viewModel.setScoreListToPreferences()

        showDialog()
    }

    private fun isCheckedRadioButtonTrue(s1: String, s2: String) {
        if (s1 == s2) viewModel.currentScore += 1
    }

    @SuppressLint("SetTextI18n")
    private fun showDialog() {
        val dialog = Dialog(requireContext())

        dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)
        dialog.window?.attributes?.windowAnimations = R.style.dialogAnimation

        dialog.let { dialog1 ->
            dialog1.setContentView(R.layout.dialog_layout)
            dialog1.findViewById<TextView>(R.id.score).text = "${viewModel.currentScore}/10 "

            //convert list of Int to list of String to correct display
            val top5ScoreList = AppPreference.getPreferencesScore()
            val top5ScoreListString = top5ScoreList
                .map { if (it == -1) " " else "$it/10" }

            //display results table
            dialog1.findViewById<TextView>(R.id.best_score).text =
                "1. ${top5ScoreListString[5]}\n" +
                        "2. ${top5ScoreListString[4]}\n" +
                        "3. ${top5ScoreListString[3]}\n" +
                        "4. ${top5ScoreListString[2]}\n" +
                        "5. ${top5ScoreListString[1]}"

            //button restart
            dialog1.findViewById<View>(R.id.button_restart).setOnClickListener {
                viewModel.fetchQuestions()
                dialog.onBackPressed()
            }

            //button home
            dialog1.findViewById<View>(R.id.button_home).setOnClickListener {
                dialog.onBackPressed()
                findNavController().navigate(R.id.action_mainFragment_to_startFragment)
            }
        }

        dialog.show()
    }
}