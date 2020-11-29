package ifpr.dispositivosmoveis.atividade5

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.AttributeSet
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.FragmentContainerView
import androidx.navigation.*
import androidx.navigation.fragment.NavHostFragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import ifpr.dispositivosmoveis.atividade5.util.UserSession
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), BottomNavigationView.OnNavigationItemSelectedListener,
    NavController.OnDestinationChangedListener {
    var navController: NavController? = null
    var graph: NavGraph? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(findViewById(R.id.toolbar))

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHostFragment.navController

        graph = navController!!.navInflater.inflate(R.navigation.nav_graph)
        graph!!.startDestination = this.getInitialFragment()
        if (this.getInitialFragment() == R.id.loginFragment) {
            hideBottomNav()
            hideToolbarItems()
        }
        navController!!.graph = graph as NavGraph

        navController!!.addOnDestinationChangedListener(this)

        var bottomNav = findViewById<BottomNavigationView>(R.id.bottom_navigation)
        bottomNav.setOnNavigationItemSelectedListener(this);
    }

    fun getInitialFragment(): Int {
        if (UserSession.isAuthenticated(this)) {
            return R.id.mainFragment
        }
        return R.id.loginFragment
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.toolbar_items, menu)
        return true
    }

    private fun showBottomNav() {
        var bottomNav = findViewById<BottomNavigationView>(R.id.bottom_navigation)
        bottomNav.visibility = View.VISIBLE
    }

    private fun hideBottomNav() {
        var bottomNav = findViewById<BottomNavigationView>(R.id.bottom_navigation)
        bottomNav.visibility = View.GONE
    }

    private fun hideToolbarItems() {
        toolbar.menu.findItem(R.id.menuViewLogout).isVisible = false
    }

    private fun showToolbarSearch()
    {
        toolbar.menu.findItem(R.id.action_search).isVisible = true
    }

    private fun hideToolbarSearch()
    {
        toolbar.menu.findItem(R.id.action_search).isVisible = false
    }

    private fun showToolbarItems() {
        toolbar.menu.findItem(R.id.menuViewLogout).isVisible = true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.getItemId()

        if (id == R.id.menuViewLogout) {
            UserSession.logout(this);
            navController!!.navigate(R.id.action_mainFragment_to_loginFragment);
            return true
        }

        return super.onOptionsItemSelected(item)
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menuViewTransactions -> {
                navController!!.navigate(R.id.action_mainFragment_to_viewTransactionFragment)
            }
            R.id.menuSendMoney -> {
                val bundle = bundleOf("type" to ChooseRecipientFragment.TYPE_SEND)
                navController!!.navigate(R.id.action_mainFragment_to_chooseRecipientFragment, bundle)
            }
            R.id.menuReceiveMoney -> {
                val bundle = bundleOf("type" to ChooseRecipientFragment.TYPE_RECEIVE)
                navController!!.navigate(R.id.action_mainFragment_to_chooseRecipientFragment, bundle)
            }
            R.id.menuViewBalance -> navController!!.navigate(R.id.action_mainFragment_to_viewBalanceFragment)
        }
        return true
    }

    override fun onDestinationChanged(
        controller: NavController,
        destination: NavDestination,
        arguments: Bundle?
    ) {
        destination.id
        when (destination.id) {
            R.id.loginFragment -> {
                hideBottomNav()
                hideToolbarItems()
                hideToolbarSearch()
            }
            R.id.mainFragment -> {
                showBottomNav()
                showToolbarItems()
                hideToolbarSearch()
            }
            R.id.registerFragment -> {
                hideToolbarItems()
                hideToolbarSearch()
            }
            /*
            R.id.viewTransactionFragment -> {
                showToolbarSearch()
            }*/
            else -> {
                hideBottomNav()
                hideToolbarItems()
                hideToolbarSearch()
            }
        }
    }
}