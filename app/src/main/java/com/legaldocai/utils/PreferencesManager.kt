package com.legaldocai.utils

import android.content.Context
import android.content.SharedPreferences
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PreferencesManager @Inject constructor(context: Context) {

    private val prefs: SharedPreferences = context.getSharedPreferences("legal_doc_prefs", Context.MODE_PRIVATE)

    companion object {
        private const val KEY_API_KEY = "groq_api_key"
        private const val KEY_LANGUAGE = "preferred_language"
        private const val KEY_VOICE_FEEDBACK = "voice_feedback"
        private const val KEY_DARK_MODE = "dark_mode"
    }

    fun saveApiKey(key: String) = prefs.edit().putString(KEY_API_KEY, key).apply()
    fun getApiKey(): String = prefs.getString(KEY_API_KEY, "") ?: ""

    fun saveLanguage(lang: String) = prefs.edit().putString(KEY_LANGUAGE, lang).apply()
    fun getLanguage(): String = prefs.getString(KEY_LANGUAGE, "English") ?: "English"

    fun setVoiceFeedback(enabled: Boolean) = prefs.edit().putBoolean(KEY_VOICE_FEEDBACK, enabled).apply()
    fun isVoiceFeedbackEnabled(): Boolean = prefs.getBoolean(KEY_VOICE_FEEDBACK, true)

    fun setDarkMode(enabled: Boolean) = prefs.edit().putBoolean(KEY_DARK_MODE, enabled).apply()
    fun isDarkModeEnabled(): Boolean = prefs.getBoolean(KEY_DARK_MODE, false)
}
