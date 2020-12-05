package ifpr.dispositivosmoveis.atividade5

import android.os.Bundle
import android.text.TextUtils
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.navigation.NavController
import androidx.navigation.Navigation
import ifpr.dispositivosmoveis.atividade5.dao.UserDAO
import ifpr.dispositivosmoveis.atividade5.models.User
import ifpr.dispositivosmoveis.atividade5.util.UserSession
import kotlinx.android.synthetic.main.fragment_register.*

class RegisterFragment : Fragment(), View.OnClickListener {
    var navController: NavController? = null
    private var dao: UserDAO = UserDAO()

    private lateinit var usernameEditText: EditText
    private lateinit var passwordEditText: EditText
    private lateinit var passwordConfirmationEditText: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_register, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        navController = Navigation.findNavController(view)

        view.findViewById<Button>(R.id.btnCancelRegister).setOnClickListener(this)
        view.findViewById<Button>(R.id.btnRegister).setOnClickListener(this)

        usernameEditText = view.findViewById<EditText>(R.id.editTextUsername)
        passwordEditText = view.findViewById<EditText>(R.id.editTextRegisterPassword)
        passwordConfirmationEditText = view.findViewById<EditText>(R.id.editTextRegisterPasswordConfirmation)
    }

    override fun onClick(v: View?) {
        when (v!!.id) {
            R.id.btnCancelRegister -> navController!!.navigate(R.id.action_registerFragment_to_loginFragment)
            R.id.btnRegister -> {
                if (!TextUtils.isEmpty(editTextUsername.text.toString()) && this.isValidPasswordConfirmation()) {
                    var userCreated : User = this.createUser()!!;
                    navController!!.navigate(R.id.action_registerFragment_to_mainFragment)
                } else {
                    Toast.makeText(activity, resources.getText(R.string.invalid_data), Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    fun isValidPasswordConfirmation() =
        !TextUtils.isEmpty(passwordEditText.text.toString()) &&
                passwordEditText.text.toString() == passwordConfirmationEditText.text.toString()

    fun createUser() : User?
    {
        val user = User(usernameEditText.text.toString(), passwordConfirmationEditText.text.toString())

        try {
            dao.insert(user, { userAPI ->
                UserSession.setAuthenticatedUser(requireActivity(), userAPI);
            }, { error ->
                throw error;
            })

            return user;
        } catch (e: Exception) {
            return null;
        }
    }

}