package com.krp.whoknows.Utils

import android.app.DatePickerDialog
import android.content.Context
import com.krp.whoknows.R
import java.time.LocalDate
import java.util.Locale

/**
 * Created by KUSHAL RAJ PAREEK on 07,February,2025
 */
class Dialogs {

    fun openDatePicker(
        context: Context,
        onDateSetListener: DatePickerDialog.OnDateSetListener,
        date: LocalDate
    ) {
        Locale.setDefault(Locale.ENGLISH)
        val datePickerDialog = DatePickerDialog(
            context,
            R.style.DatePickerTheme,
            onDateSetListener,
            date.year,
            date.monthValue,
            date.dayOfMonth
        )

        datePickerDialog.show()
    }
}