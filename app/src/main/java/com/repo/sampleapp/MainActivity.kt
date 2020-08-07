package com.repo.sampleapp

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private var textArrayAfter = ArrayList<String>()

    private var textArrayBefore = ArrayList<String>()

    private var buttonCount = 0

    private val number = MutableLiveData<Int>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val textWatcher = object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                textArrayAfter.add(s.toString())
            }
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                textArrayBefore.add(s.toString())
            }
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        }

        inputText.addTextChangedListener(textWatcher)

        buttonA.setOnClickListener {
            buttonCount++
            number.value = buttonCount
        }

        number.observe(this, Observer {
                when (it) {
                    1 -> {
                        buttonA.text = "Redo"
                        inputText.setText(textArrayBefore[textArrayBefore.size.minus(1)])
                    }
                    2 -> inputText.setText(textArrayAfter[textArrayAfter.size.minus(3)]).also { buttonCount = 0 }
                }
        })

        buttonB.setOnClickListener {
            label.text = textRevert(inputText.text.toString())

        }
    }

    private fun textRevert(text: String) : StringBuffer {
        val buffer = StringBuffer(text)
        return buffer.reverse()
    }

}