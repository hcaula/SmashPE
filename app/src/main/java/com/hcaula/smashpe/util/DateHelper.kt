package com.hcaula.smashpe.util

import java.text.SimpleDateFormat
import java.util.*

class DateHelper {
    companion object {
        private const val DATE_FORMAT = "MMM dd yyyy, hh:mm a"

        fun format(date: Date): String =
            SimpleDateFormat(
                DATE_FORMAT,
                Locale.US
            ).format(date)
    }
}