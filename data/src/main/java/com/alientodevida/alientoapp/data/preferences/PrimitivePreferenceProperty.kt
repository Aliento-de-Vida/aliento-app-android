@file:Suppress("UNCHECKED_CAST")

package com.alientodevida.alientoapp.data.preferences

import android.content.SharedPreferences
import androidx.core.content.edit
import com.alientodevida.alientoapp.domain.preferences.Preferences
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

enum class Type {
	BOOLEAN,
	INT,
	LONG,
	FLOAT,
	STRING,
}

class PrimitivePreferenceProperty<T>(
	private val type: Type,
	private val name: String,
	private val preferences: SharedPreferences,
) : ReadWriteProperty<Any, T> {

	override fun getValue(
		thisRef: Any,
		property: KProperty<*>,
	): T =
		preferences.getValue(name, type) as T

	override fun setValue(
		thisRef: Any,
		property: KProperty<*>,
		value: T,
	) =
		preferences.setValue(name, type, value)

	fun clear() = preferences.edit { remove(name) }

}

private fun SharedPreferences.getValue(
	name: String,
	type: Type,
): Any = when (type) {
	Type.BOOLEAN -> getBoolean(name, Preferences.DEFAULT_BOOLEAN)
	Type.INT -> getInt(name, Preferences.DEFAULT_INT)
	Type.LONG -> getLong(name, Preferences.DEFAULT_LONG)
	Type.FLOAT -> getFloat(name, Preferences.DEFAULT_FLOAT)
	Type.STRING -> getString(name, Preferences.DEFAULT_STRING) as Any
}

private fun <T> SharedPreferences.setValue(
	name: String,
	type: Type,
	value: T,
) {
	when (type) {
		Type.BOOLEAN -> edit { putBoolean(name, value as Boolean) }
		Type.INT -> edit { putInt(name, value as Int) }
		Type.LONG -> edit { putLong(name, value as Long) }
		Type.FLOAT -> edit { putFloat(name, value as Float) }
		Type.STRING -> edit { putString(name, value as String) }
	}
}
