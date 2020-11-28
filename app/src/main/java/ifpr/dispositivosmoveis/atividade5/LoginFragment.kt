package ifpr.dispositivosmoveis.atividade5

import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_login.*

class LoginFragment : Fragment(), View.OnClickListener {
    var navController: NavController? = null

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

        view.findViewById<Button>(R.id.btnLogin).setOnClickListener(this)
        view.findViewById<TextView>(R.id.textViewRegister).setOnClickListener(this)
    }


    override fun onClick(v: View?) {
        when (v!!.id) {
            R.id.btnLogin -> {
                if (!TextUtils.isEmpty(editTextPassword.text.toString())
                    && !TextUtils.isEmpty(editTextPersonName.text.toString())
                ) {
                    navController!!.navigate(R.id.action_loginFragment_to_mainFragment)
                } else {
                    Toast.makeText(activity, resources.getText(R.string.login_error), Toast.LENGTH_SHORT).show()
                }
            }
            R.id.textViewRegister -> {
                navController!!.navigate(R.id.action_loginFragment_to_registerFragment)
            }
        }
    }
}