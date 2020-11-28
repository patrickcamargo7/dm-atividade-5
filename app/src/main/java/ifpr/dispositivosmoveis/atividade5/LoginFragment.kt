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
import ifpr.dispositivosmoveis.atividade5.database.AppDatabase
import ifpr.dispositivosmoveis.atividade5.database.dao.UserDAO
import ifpr.dispositivosmoveis.atividade5.models.User

class LoginFragment : Fragment(), View.OnClickListener {
    private var dao: UserDAO? = null
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

        dao = context?.let { AppDatabase.getInstance(it).userDAO() }
    }


    override fun onClick(v: View?) {
        when (v!!.id) {
            R.id.btnLogin -> {
                if (userIsValid()) {
                    navController!!.navigate(R.id.action_loginFragment_to_mainFragment)
                } else {
                    Toast.makeText(
                        activity,
                        resources.getText(R.string.login_error),
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
            R.id.textViewRegister -> {
                navController!!.navigate(R.id.action_loginFragment_to_registerFragment)
            }
        }
    }

    fun userIsValid(): Boolean {
        try {
            if (isEmptyFields())
                return false;

            var users: List<User> = dao!!.where(editTextUsername.text.toString(), editTextPassword.text.toString());
            if (users.isNotEmpty())
                return true;

            return false;
        } catch(e: Exception) {
            return false;
        }
    }

    fun isEmptyFields() : Boolean {
        return TextUtils.isEmpty(editTextUsername.text.toString()) || TextUtils.isEmpty(editTextPassword.text.toString())
    }
}