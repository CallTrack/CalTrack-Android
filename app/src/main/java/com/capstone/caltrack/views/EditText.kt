package com.capstone.caltrack.views

import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatEditText

open class EditText : AppCompatEditText {
    constructor(context: Context) : super(context) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        init()
    }

    private fun init() {
        addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable?) {
                validateInput()
            }
        })
    }

    fun validateInput(): Boolean {
        val input = text.toString()
        val isValid = inputValidation?.validate(input) ?: true

        error = if (isValid) {
            null
        } else {
            inputValidation?.errorMessage ?: ""
        }

        return isValid
    }

    private var inputValidation: InputValidation? = null

    interface InputValidation {
        val errorMessage: String
        fun validate(input: String): Boolean
    }

    fun setValidationCallback(inputValidation: InputValidation) {
        this.inputValidation = inputValidation
    }
}