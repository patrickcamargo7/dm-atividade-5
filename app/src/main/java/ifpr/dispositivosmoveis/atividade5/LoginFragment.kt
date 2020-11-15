package ifpr.dispositivosmoveis.atividade5

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.core.view.isVisible
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.google.android.material.bottomnavigation.BottomNavigationView

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
        hideBottomNav(bottomNav)

        navController!!.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                R.id.loginFragment -> {
                    hideBottomNav(bottomNav)
                }
                else -> {
                    showBottomNav(bottomNav)
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
                navController!!.navigate(R.id.action_loginFragment_to_mainFragment)
            }
        }
    }
}