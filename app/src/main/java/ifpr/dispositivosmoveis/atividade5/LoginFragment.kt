package ifpr.dispositivosmoveis.atividade5

import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.Navigation
import ifpr.dispositivosmoveis.atividade5.dao.UserDAO
import ifpr.dispositivosmoveis.atividade5.models.User
import ifpr.dispositivosmoveis.atividade5.util.UserSession

class LoginFragment : Fragment(), View.OnClickListener {
    private val dao: UserDAO = UserDAO()
    var navController: NavController? = null

    private lateinit var editTextUsername: EditText
    private lateinit var editTextPassword: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_login, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        navController = Navigation.findNavController(view)

        view.findViewById<Button>(R.id.btnLogin).setOnClickListener(this)
        view.findViewById<TextView>(R.id.textViewRegister).setOnClickListener(this)

        editTextUsername = view.findViewById<EditText>(R.id.editTextLoginUsername)
        editTextPassword = view.findViewById<EditText>(R.id.editTextLoginPassword)
    }


    override fun onClick(v: View?) {
        when (v!!.id) {
            R.id.btnLogin -> {
                if (userIsValid()) {
                    var user: User? = null

                    dao.authenticate(editTextUsername.text.toString(), editTextPassword.text.toString(), { userAPI ->
                        user = userAPI.first();
                        UserSession.setAuthenticatedUser(requireActivity(), user!!);
                        navController!!.navigate(R.id.action_loginFragment_to_mainFragment)
                    }, {
                        showMessage()
                    })
                } else {
                    showMessage()
                }
            }
            R.id.textViewRegister -> {
                navController!!.navigate(R.id.action_loginFragment_to_registerFragment)
            }
        }
    }

    private fun showMessage() {
        Toast.makeText(
            activity,
            resources.getText(R.string.login_error),
            Toast.LENGTH_SHORT
        ).show()
    }

    private fun userIsValid(): Boolean {
        try {
            if (isEmptyFields())
                return false;
            return true;
        } catch(e: Exception) {
            return false;
        }
    }

    fun isEmptyFields() : Boolean {
        return TextUtils.isEmpty(editTextUsername.text.toString()) || TextUtils.isEmpty(editTextPassword.text.toString())
    }
}