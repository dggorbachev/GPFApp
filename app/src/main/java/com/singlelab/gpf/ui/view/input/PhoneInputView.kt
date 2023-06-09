package com.singlelab.gpf.ui.view.input

import android.content.Context
import android.graphics.drawable.Drawable
import android.text.Editable
import android.text.InputFilter
import android.text.InputFilter.LengthFilter
import android.text.TextWatcher
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import androidx.core.content.ContextCompat
import com.google.android.material.textfield.TextInputEditText
import com.singlelab.gpf.R
import com.singlelab.gpf.util.toShortPhone
import kotlinx.android.synthetic.main.view_phone_input.view.*


class PhoneInputView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr) {

    init {
        LayoutInflater.from(context).inflate(R.layout.view_phone_input, this, true)
        edit_text_phone.fixHintsForMeizu(edit_text_phone as TextInputEditText, edit_text_phone)
        addTextChangedListener()
    }

    private fun addTextChangedListener() {
        edit_text_phone.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                if (edit_text_phone.text.toString().toShortPhone().length == 12) {
                    edit_text_phone.setTextColor(ContextCompat.getColor(context, R.color.colorText))
                } else {
                    edit_text_phone.setTextColor(ContextCompat.getColor(context, R.color.colorGray))
                }
            }

            override fun afterTextChanged(s: Editable) {
            }
        })
    }

    fun isValid() = edit_text_phone.isValid

    fun setHint(hint: String) {
        edit_text_phone.setCustomHint(hint)
    }

    fun setMaxLength(length: Int) {
        edit_text_phone.filters = arrayOf<InputFilter>(LengthFilter(length))
    }

    fun setInputType(inputType: Int) {
        edit_text_phone.inputType = inputType
    }

    fun setStartDrawable(drawable: Drawable) {
        text_input_layout.startIconDrawable = drawable
    }

    fun setMaxLines(maxLines: Int) {
        edit_text_phone.maxLines = maxLines
    }

    fun setOnEditorListener(function: (Int) -> Boolean) {
        edit_text_phone.setOnEditorActionListener { _, actionId, _ ->
            function.invoke(actionId)
        }
    }

    fun getText() = edit_text_phone.text.toString()

    fun setText(text: String) {
        edit_text_phone.setText(text)
    }

    fun setError(error: String) {
        text_error.text = error
        text_error.visibility = View.VISIBLE
        text_warning.visibility = View.GONE
    }

    fun showError(isShow: Boolean) {
        text_error.visibility = if (isShow) View.VISIBLE else View.INVISIBLE
    }

    fun setWarning(warning: String) {
        text_warning.text = warning
        text_error.visibility = View.GONE
        text_warning.visibility = View.VISIBLE
    }
}