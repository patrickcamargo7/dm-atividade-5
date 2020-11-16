package ifpr.dispositivosmoveis.atividade5

import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.fragment_choose_recipient.*
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
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_login, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        navController = Navigation.findNavController(view)

        var bottomNav = requireActivity().findViewById<BottomNavigationView>(R.id.bottom_navigation)

        bottomNav.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.menuViewTransactions -> navController!!.navigate(R.id.action_mainFragment_to_viewTransactionFragment)
                R.id.menuSendMoney -> navController!!.navigate(R.id.action_mainFragment_to_chooseRecipientFragment)
                R.id.menuViewBalance -> navController!!.navigate(R.id.action_mainFragment_to_viewBalanceFragment)
            }
            true
        }

        hideBottomNav(bottomNav)

        navController!!.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                R.id.loginFragment -> {
                    hideBottomNav(bottomNav)
                }
                R.id.mainFragment -> {
                    showBottomNav(bottomNav)
                }
                else -> {
                    hideBottomNav(bottomNav)
                }
            }
        }

        view.findViewById<Button>(R.id.btnLogin).setOnClickListener(this)
    }

    private fun showBottomNav(bottomNav: BottomNavigationView) {
        bottomNav.visibility = View.VISIBLE
    }

    private fun hideBottomNav(bottomNav: BottomNavigationView) {
        bottomNav.visibility = View.GONE
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
        }
    }
}