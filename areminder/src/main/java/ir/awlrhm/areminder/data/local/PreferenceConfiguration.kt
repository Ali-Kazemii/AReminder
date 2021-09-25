package ir.awlrhm.areminder.data.local

import android.content.Context
import android.content.SharedPreferences
import ir.awlrhm.areminder.utils.Const
import ir.awlrhm.areminder.utils.Const.KEY_ACCESS_TOKEN
import ir.awlrhm.areminder.utils.Const.KEY_PREFERENCE_NAME

class PreferenceConfiguration(
   private val context: Context
) {
    private var pref: SharedPreferences =
        context.getSharedPreferences(KEY_PREFERENCE_NAME, Context.MODE_PRIVATE)

    var accessToken: String
        get() = pref.getString(KEY_ACCESS_TOKEN, "")!!
        set(value) = pref.edit().putString(KEY_ACCESS_TOKEN, value).apply()


    var imei: String
        get() = pref.getString(Const.KEY_IMEI, "")!!
        set(value) {
            pref.edit().putString(Const.KEY_IMEI, value).apply()
        }

    var osVersion: String
        get() = pref.getString(Const.KEY_OS_VERSION, "")!!
        set(value) {
            pref.edit().putString(Const.KEY_OS_VERSION, value).apply()
        }

    var deviceModel: String
        get() = pref.getString(Const.KEY_DEVICE_MODEL, "")!!
        set(value) {
            pref.edit().putString(Const.KEY_DEVICE_MODEL, value).apply()
        }

    var appVersion: String
    get() = pref.getString(Const.KEY_APP_VERSION, "")!!
    set(value) {
        pref.edit().putString(Const.KEY_APP_VERSION, value).apply()
    }

    var ssId: Int
        get() = pref.getInt(Const.KEY_SSID, 0)
        set(value) {
            pref.edit().putInt(Const.KEY_SSID, value).apply()
        }

    var userId: Long
        get() = pref.getLong(Const.KEY_USER_ID, 0)
        set(value) {
            pref.edit().putLong(Const.KEY_USER_ID, value).apply()
        }

    var hostName: String
        get() = pref.getString(Const.KEY_HOST_NAME, "")!!
        set(value) {
            pref.edit().putString(Const.KEY_HOST_NAME, value).apply()
        }
}