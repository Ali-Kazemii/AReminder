package ir.awlrhm.reminder.network

import android.content.Context
import android.content.SharedPreferences
import ir.awlrhm.reminder.login.Keys.KEY_ACCESS_TOKEN
import ir.awlrhm.reminder.login.Keys.KEY_APP_VERSION
import ir.awlrhm.reminder.login.Keys.KEY_DEVICE_MODEL
import ir.awlrhm.reminder.login.Keys.KEY_HOST_NAME
import ir.awlrhm.reminder.login.Keys.KEY_IMEI
import ir.awlrhm.reminder.login.Keys.KEY_OS_VERSION
import ir.awlrhm.reminder.login.Keys.KEY_PREFERENCE_NAME
import ir.awlrhm.reminder.login.Keys.KEY_USER_ID

class PreferenceConfig(
   private val context: Context
) {
    private var pref: SharedPreferences =
        context.getSharedPreferences(KEY_PREFERENCE_NAME, Context.MODE_PRIVATE)

    var accessToken: String
        get() = pref.getString(KEY_ACCESS_TOKEN, "")!!
        set(value) = pref.edit().putString(KEY_ACCESS_TOKEN, value).apply()


    var imei: String
        get() = pref.getString(KEY_IMEI, "")!!
        set(value) {
            pref.edit().putString(KEY_IMEI, value).apply()
        }

    var osVersion: String
        get() = pref.getString(KEY_OS_VERSION, "")!!
        set(value) {
            pref.edit().putString(KEY_OS_VERSION, value).apply()
        }

    var deviceModel: String
        get() = pref.getString(KEY_DEVICE_MODEL, "")!!
        set(value) {
            pref.edit().putString(KEY_DEVICE_MODEL, value).apply()
        }

    var appVersion: String
    get() = pref.getString(KEY_APP_VERSION, "")!!
    set(value) {
        pref.edit().putString(KEY_APP_VERSION, value).apply()
    }


    var userId: Long
        get() = pref.getLong(KEY_USER_ID, 0)
        set(value) {
            pref.edit().putLong(KEY_USER_ID, value).apply()
        }
}