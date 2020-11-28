package ifpr.dispositivosmoveis.atividade5.util

import android.app.Activity
import android.content.Context
import ifpr.dispositivosmoveis.atividade5.models.User
import kotlin.Exception

class UserSession {
    companion object {
        const val USER_AUTH_ID = "_auth_user_id";
        const val USERNAME_AUTH = "_auth_username";

        public fun setAuthenticatedUser(activity: Activity, user: User) {
            val sharedPref = activity?.getPreferences(Context.MODE_PRIVATE) ?: return;
            with(sharedPref.edit()) {
                user.id?.let { putLong(USER_AUTH_ID, it) }
                putString(USERNAME_AUTH, user.username)
                commit()
            }
        }

        public fun isAuthenticated(activity: Activity): Boolean {
            try {
                UserSession.getUserAuthId(activity)
                return true
            } catch (e: Exception) {
                return false
            }
        }

        public fun getUserAuthId(activity: Activity): Long {
            val sharedPref = activity?.getPreferences(Context.MODE_PRIVATE)
            val userAuthId = sharedPref.getLong(USER_AUTH_ID, -1)

            if (userAuthId.compareTo(-1) == 0)
                throw Exception();
            return userAuthId
        }

        public fun logout(activity: Activity)
        {
            val sharedPref = activity?.getPreferences(Context.MODE_PRIVATE)
            sharedPref.edit().remove(USER_AUTH_ID).commit()
            sharedPref.edit().remove(USERNAME_AUTH).commit()
        }
    }
}