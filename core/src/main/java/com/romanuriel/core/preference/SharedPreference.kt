package com.romanuriel.core.preference

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit

enum class PreferenceKey(val code: String) {
    ACTIVE_NOTIFICATION("NewNotification"),
    MODE_THEME("ModeTheme"),
    LANGUAGE("Language"),
    SAVE_DRAFT("Save_draft"),
    USER_ID("user_id"),
    FULL_NAME_USER("Full name user"),
    ORDER("Order")
}
class SharedPreference(
    context: Context,
    name: String
): ISharedPreference {
    private val sharedPreference = context.getSharedPreferences(name, Context.MODE_PRIVATE)
    override fun save(key: PreferenceKey, value: Any) {
        sharedPreference.edit {
            when (value) {
                is String -> this.putString(key.code, value)
                is Long -> this.putLong(key.code, value)
                is Boolean -> this.putBoolean(key.code, value)
                is Int -> this.putInt(key.code, value)
            }
            this.commit()
        }
    }

    override fun getString(key: PreferenceKey): String {
        return sharedPreference.getString(key.code, "") ?: ""
    }

    override fun getInt(key: PreferenceKey): Int {
        return sharedPreference.getInt(key.code, -1)
    }

    override fun getBoolean(key: PreferenceKey): Boolean {
        return sharedPreference.getBoolean(key.code, false)
    }

    override fun getLong(key: PreferenceKey): Long {
        return sharedPreference.getLong(key.code, -1L)
    }

    override fun registerPreferenceChangeListener(listener: SharedPreferences.OnSharedPreferenceChangeListener) {
        sharedPreference.registerOnSharedPreferenceChangeListener(listener)
    }

    override fun unregisterPreferenceChangeListener(listener: SharedPreferences.OnSharedPreferenceChangeListener) {
        sharedPreference.unregisterOnSharedPreferenceChangeListener(listener)
    }

}