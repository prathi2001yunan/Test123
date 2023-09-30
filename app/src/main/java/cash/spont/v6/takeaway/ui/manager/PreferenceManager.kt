package cash.spont.v6.takeaway.ui.manager

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import cash.spont.v6.takeawayrepo.repository.AppConfig

@SuppressLint("CommitPrefEdits")
object PreferenceManager {
    private lateinit var prefs: SharedPreferences
    fun setup(context: Context) {
        prefs =
            context.getSharedPreferences("prefs", Context.MODE_PRIVATE)
    }

    fun setValues(map: Map<String, String>) {
        val editor = prefs.edit()
        for ((key, value) in map) {
            editor.putString(key, value)
            editor.apply()
        }
    }

    fun setValue(key: String, value: String) {
        val editor = prefs.edit()
        editor.apply {
            editor.putString(key, value)
            apply()
        }

    }

    fun getValues(key: List<String>): List<String?> {
        val list = mutableListOf<String>()
        for (i in key) {
            prefs.getString(i, null)?.let { list.add(it) }
        }
        return list
    }

    fun getValue(key: String): String? {
        return prefs.getString(key, null)
    }

    fun deleteValues(list: List<String>) {
        for (key in list) {
            prefs.edit().remove(key)
        }
    }

    fun deleteValue(key: String) {
        prefs.edit().remove(key)
    }
}

