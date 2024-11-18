package com.example.zzcalculatorv2

import android.icu.util.Output
import android.os.Bundle
import android.renderscript.ScriptGroup.Input
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import android.util.Log
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.text.isDigitsOnly
import net.objecthunter.exp4j.Expression
import net.objecthunter.exp4j.ExpressionBuilder

class MainActivity : AppCompatActivity() {

    private var isDark: Boolean = false

    private val ADDITION = '+'
    private val SUBTRACTION = '-'
    private val MULTIPLICATION = '*'
    private val DIVISION = '/'
    private val PERCENTAGE = '%'

    private lateinit var InputDisplay: TextView
    private lateinit var OutputDisplay: TextView

    private lateinit var button_c: Button
    private lateinit var button_delete: Button
    private lateinit var button_divide: Button
    private lateinit var button_multiply: Button
    private lateinit var button_minus: Button
    private lateinit var button_plus: Button
    private lateinit var button_percentage: Button
    private lateinit var button_equal: Button
    private lateinit var button_dot: Button
    private lateinit var button_theme: Button
    private lateinit var button_0: Button
    private lateinit var button_1: Button
    private lateinit var button_2: Button
    private lateinit var button_3: Button
    private lateinit var button_4: Button
    private lateinit var button_5: Button
    private lateinit var button_6: Button
    private lateinit var button_7: Button
    private lateinit var button_8: Button
    private lateinit var button_9: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        InputDisplay = findViewById(R.id.textView_input)
        OutputDisplay = findViewById(R.id.textView_output)

        button_c = findViewById(R.id.button_c)
        button_delete = findViewById(R.id.button_delete)
        button_divide = findViewById(R.id.button_divide)
        button_multiply = findViewById(R.id.button_multiply)
        button_minus = findViewById(R.id.button_minus)
        button_plus = findViewById(R.id.button_plus)
        button_percentage = findViewById(R.id.button_percentage)
        button_equal = findViewById(R.id.button_equal)
        button_dot = findViewById(R.id.button_dot)
        button_theme = findViewById(R.id.button_theme)


        button_0 = findViewById(R.id.button_0)
        button_1 = findViewById(R.id.button_1)
        button_2 = findViewById(R.id.button_2)
        button_3 = findViewById(R.id.button_3)
        button_4 = findViewById(R.id.button_4)
        button_5 = findViewById(R.id.button_5)
        button_6 = findViewById(R.id.button_6)
        button_7 = findViewById(R.id.button_7)
        button_8 = findViewById(R.id.button_8)
        button_9 = findViewById(R.id.button_9)

        button_c.setOnClickListener {
            InputDisplay.text = ""
            OutputDisplay.text = ""
        }

        button_delete.setOnClickListener {
            val string = InputDisplay.text.toString()
            if (string.isNotEmpty()) {
                InputDisplay.text = string.dropLast(1)
                appendOnExpression("")
            }
        }

        button_theme.setOnClickListener {
            Log.d("theme", "button clicked | $isDark")
            changeTheme()
        }

        button_0.setOnClickListener { appendOnExpression("0") }
        button_1.setOnClickListener { appendOnExpression("1") }
        button_2.setOnClickListener { appendOnExpression("2") }
        button_3.setOnClickListener { appendOnExpression("3") }
        button_4.setOnClickListener { appendOnExpression("4") }
        button_5.setOnClickListener { appendOnExpression("5") }
        button_6.setOnClickListener { appendOnExpression("6") }
        button_7.setOnClickListener { appendOnExpression("7") }
        button_8.setOnClickListener { appendOnExpression("8") }
        button_9.setOnClickListener { appendOnExpression("9") }

        button_plus.setOnClickListener { appendOnExpression("+") }
        button_minus.setOnClickListener { appendOnExpression("-") }
        button_multiply.setOnClickListener { appendOnExpression("*") }
        button_divide.setOnClickListener { appendOnExpression("/") }
        button_percentage.setOnClickListener { appendOnExpression("%") }
        button_dot.setOnClickListener { appendOnExpression(".") }

        button_equal.setOnClickListener(){
            var result = OutputDisplay.text.toString()
            if(result.isNotEmpty()){
                InputDisplay.text = ""
            }
        }
    }

    private fun appendOnExpression(string: String?) {
        val old = InputDisplay.text.toString()
        if (old.isEmpty() && OutputDisplay.text.isNotEmpty()){
            InputDisplay.text = OutputDisplay.text.toString() + string
            OutputDisplay.text = ""
        } else {
            InputDisplay.text = old + string
        }
        if (string != null) {
            if(string.isDigitsOnly()){
                try {
                    val expression = ExpressionBuilder(InputDisplay.text.toString()).build()
                    val result = expression.evaluate()
                    if (result % 1 == 0.0) {
                        OutputDisplay.text = result.toInt().toString()
                    } else {
                        OutputDisplay.text = result.toString()
                    }
                } catch (e: Exception) {
                    Log.e("Error", "message: ${e.message}")
                    OutputDisplay.text = "Error"
                }
            }
        }
    }

    private fun changeTheme() {
        if (isDark) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            isDark = isDark.not()
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            isDark = isDark.not()
        }
        Log.d("theme", "State Now | $isDark")
    }
}

//        var test = "2+5"
//        var evaluation = ExpressionBuilder(test).build()
//        Log.d("testing", evaluation.evaluate().toInt().toString())