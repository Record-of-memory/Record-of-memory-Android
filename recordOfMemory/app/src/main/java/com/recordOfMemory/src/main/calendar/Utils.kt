package com.recordOfMemory.src.main.calendar

import android.content.Context
import android.widget.TextView
import androidx.annotation.ColorRes
import androidx.core.content.ContextCompat
import java.time.DayOfWeek
import java.time.Month
import java.time.YearMonth
import java.time.format.TextStyle
import java.util.*

fun YearMonth.displayText(short: Boolean = false): String {
	return "${this.month.displayText(short = short)} ${this.year}"
}

fun Month.displayText(short: Boolean = true): String {
	val style = if (short) TextStyle.SHORT else TextStyle.FULL
	return getDisplayName(style, Locale.ENGLISH)
}

fun DayOfWeek.displayText(uppercase: Boolean = false): String {
	return getDisplayName(TextStyle.SHORT, Locale.ENGLISH).let { value ->
		if (uppercase) value.uppercase(Locale.ENGLISH) else value
	}
}

fun monthToInt(month:String):Int{
	return when(month){
		"Jan" -> 1
		"Feb" -> 2
		"Mar" -> 3
		"Apr" -> 4
		"May" -> 5
		"Jun" -> 6
		"Jul" -> 7
		"Aug" -> 8
		"Sep" -> 9
		"Oct" -> 10
		"Nov" -> 11
		"Dec" -> 12
		else -> 0
	}
}

internal fun Context.getColorCompat(@ColorRes color: Int) =
	ContextCompat.getColor(this, color)

internal fun TextView.setTextColorRes(@ColorRes color: Int) =
	setTextColor(context.getColorCompat(color))
