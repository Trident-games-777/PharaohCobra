package inc.trilokia.gfxtoo.utils.security_utils

import android.content.Context
import android.provider.Settings
import inc.trilokia.gfxtoo.data.CobraRepository
import java.io.File
import javax.inject.Inject

class SecurityManager @Inject constructor(
    private val context: Context,
    private val repository: CobraRepository
) {

    suspend fun isSecured(): Boolean = !root() && adb() != "1"

    private suspend fun root(): Boolean {
        val dirs = repository.getSnapshot().child("dirs").value as Map<String, String>
        try {
            for (dir in dirs.values) {
                if (File(dir + "su").exists()) return true
            }
        } catch (e: SecurityException) {
            e.printStackTrace()
        }
        return false
    }

    private fun adb(): String {
        return Settings.Global.getString(context.contentResolver, Settings.Global.ADB_ENABLED)
            ?: "null"
    }
}