package com.romanuriel.core.preference

import android.content.SharedPreferences

interface ISharedPreference {
    interface PreferenceDataSource {
        fun save(key: PreferenceKey, value: Any)
        fun getString(key: PreferenceKey): String
        fun getInt(key: PreferenceKey): Int
        fun getBoolean(key: PreferenceKey): Boolean
        fun getLong(key: PreferenceKey): Long
    }

    fun save(key: PreferenceKey, value: Any)
    fun getString(key: PreferenceKey): String
    fun getInt(key: PreferenceKey): Int
    fun getBoolean(key: PreferenceKey): Boolean
    fun getLong(key: PreferenceKey): Long


    fun registerPreferenceChangeListener(listener: SharedPreferences.OnSharedPreferenceChangeListener)
    fun unregisterPreferenceChangeListener(listener: SharedPreferences.OnSharedPreferenceChangeListener)

}