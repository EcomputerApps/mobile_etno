package com.example.mobile_etno.internalization

import android.annotation.TargetApi
import android.content.Context
import android.content.SharedPreferences
import android.content.res.Configuration
import android.content.res.Resources
import android.os.Build
import androidx.preference.PreferenceManager
import java.util.*

object LocaleHelper {
    private final val SELECTED_LANGUAGE: String = "Locale.Helper.Selected.Language"

    fun setLocale(context: Context, language: String): Context {
        persist(context, language)

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.N){
            return updateResources(context, language)
        }
        return updateResourcesLegacy(context, language)
    }

    private fun persist(context: Context, language: String){
        val preferences: SharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)
        val editor: SharedPreferences.Editor = preferences.edit()
        editor.putString(SELECTED_LANGUAGE, language)
        editor.apply()
    }
    private fun updateResources(context: Context, language: String): Context{
        val locale: Locale = Locale(language)
        Locale.setDefault(locale)

        val configuration: Configuration = context.resources.configuration
        configuration.setLocale(locale)
        configuration.setLayoutDirection(locale)

        return context.createConfigurationContext(configuration)
    }
    @SuppressWarnings("deprecation")
    private fun updateResourcesLegacy(context: Context, language: String): Context{
        val locale: Locale = Locale(language)
        Locale.setDefault(locale)

        val resources: Resources = context.resources

        val configuration: Configuration = context.resources.configuration
        configuration.setLocale(locale)

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1){
            configuration.setLayoutDirection(locale)
        }
        resources.updateConfiguration(configuration, resources.displayMetrics)

        return context
    }

}