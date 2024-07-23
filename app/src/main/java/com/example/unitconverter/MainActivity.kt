package com.example.unitconverter

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar

class MainActivity : AppCompatActivity() {

    private val conversionRates = mapOf(
        "Kilometers" to 1000.0,
        "Meters" to 1.0,
        "Centimeters" to 0.01,
        "Millimeters" to 0.001
        // Add more conversion rates as needed
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Set up the toolbar
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        // Initialize Spinners
        val spinnerFrom: Spinner = findViewById(R.id.spinnerFrom)
        val spinnerTo: Spinner = findViewById(R.id.spinnerTo)
        val inputValue: EditText = findViewById(R.id.inputValue)
        val convertButton: Button = findViewById(R.id.convertButton)
        val resultTextView: TextView = findViewById(R.id.resultTextView)

        val units = resources.getStringArray(R.array.unit_array)
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, units)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerFrom.adapter = adapter
        spinnerTo.adapter = adapter

        convertButton.setOnClickListener {
            val fromUnit = spinnerFrom.selectedItem.toString()
            val toUnit = spinnerTo.selectedItem.toString()
            val value = inputValue.text.toString()

            if (value.isNotEmpty()) {
                val input = value.toDoubleOrNull()
                if (input != null) {
                    val result = convertUnits(input, fromUnit, toUnit)
                    resultTextView.text = "Result: $result $toUnit"
                } else {
                    Toast.makeText(this, "Invalid input value", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(this, "Please enter a value", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun convertUnits(value: Double, fromUnit: String, toUnit: String): Double {
        val fromRate = conversionRates[fromUnit] ?: 1.0
        val toRate = conversionRates[toUnit] ?: 1.0
        return value * fromRate / toRate
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_settings -> {
                Toast.makeText(this, "Settings selected", Toast.LENGTH_SHORT).show()
                true
            }
            R.id.action_about -> {
                Toast.makeText(this, "About selected", Toast.LENGTH_SHORT).show()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}
