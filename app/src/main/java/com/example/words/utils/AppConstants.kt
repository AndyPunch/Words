package com.example.words.utils

import androidx.datastore.preferences.core.booleanPreferencesKey

object AppConstants {
    //DataStore
    val IS_DB_CREATED_KEY = booleanPreferencesKey("IS_DB_CREATED_KEY")

    //db
    const val DB_NAME = "wordsDb"

    //PreferenceDataStore
    const val PREF_NAME = "PreferenceDataStore"
}