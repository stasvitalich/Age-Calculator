package com.example.agecalculator

import android.app.DatePickerDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

import com.example.agecalculator.databinding.ActivityMainBinding
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding

    override fun onCreate(s: Bundle?) {
        super.onCreate(s)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.selectButton.setOnClickListener {
            clickDatePicker()
        }
    }

    private fun clickDatePicker() {

        val myCalendar = Calendar.getInstance()
        val year = myCalendar.get(Calendar.YEAR)
        val month = myCalendar.get(Calendar.MONTH)
        val day = myCalendar.get(Calendar.DAY_OF_MONTH)
        val dpd = DatePickerDialog(
            this, { _, selectedYear, selectedMonth, selectedDayOfMonth ->
                val myDate = "$selectedDayOfMonth/${selectedMonth + 1}/$selectedYear"
                binding.textDate.text = myDate

                val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.GERMAN)
                val newFormat = dateFormat.parse(myDate)

                // How much time has been passed between selected day and 01/01/1970
                val minBetween =
                    newFormat.time / 60000 // Divided by 60000 because we need to get a result in minutes

                // How much time has been passed between now and 01/01/1970
                val currentDate = dateFormat.parse(dateFormat.format(System.currentTimeMillis()))
                val currentDateInMinutes =
                    currentDate.time / 60000 // Divided by 60000 because we need to get a result in minutes

                val difference = currentDateInMinutes - minBetween
                binding.textDate2.text = difference.toString()
            },
            year,
            month,
            day
        )

        /*Through the following line, we limit our calendar.
        You won't be able to choose a future date, because it
        contradicts the logic of our application.*/
        dpd.datePicker.maxDate = System.currentTimeMillis() - 86400000 // Minus current date.
        dpd.show()
    }
}