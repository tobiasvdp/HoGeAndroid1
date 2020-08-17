package tech.gim.scroble

import android.app.SearchManager
import android.content.Context
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.appcompat.widget.SearchView.OnQueryTextListener
import androidx.appcompat.widget.Toolbar
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.navigation.NavigationView

/**
 * This App uses only one Activity, navigation and components are created using fragments
 */
class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        val drawerLayout: DrawerLayout = findViewById(R.id.drawer_layout)
        val navView: NavigationView = findViewById(R.id.nav_view)
        val navController = findNavController(R.id.nav_host_fragment)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.nav_home,
                R.id.nav_shows_popular,
                R.id.nav_shows_trending,
                R.id.nav_shows_most_recommended,
                R.id.nav_shows_most_viewed,
                R.id.nav_shows_anticipated
            ),
            drawerLayout
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val m = menuInflater.inflate(R.menu.main, menu)

        val searchItem: MenuItem? = menu.findItem(R.id.action_search)
        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        val searchView: SearchView? = searchItem?.actionView as SearchView
        searchView?.setSearchableInfo(searchManager.getSearchableInfo(componentName))
        searchView?.setOnQueryTextListener(OnSearchListener(this, searchView, searchItem))

        return true
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.action_settings) {
            val navController = findNavController(R.id.nav_host_fragment)
            navController.navigate(R.id.nav_settings)
            return true
        }
        return super.onOptionsItemSelected(item)
    }
}

class OnSearchListener(
    val mainActivity: MainActivity,
    val searchView: SearchView,
    val searchItem: MenuItem
) : OnQueryTextListener {

    override fun onQueryTextSubmit(query: String?): Boolean {
        if (! searchView.isIconified) {
            searchView.isIconified = true
        }
        searchItem.collapseActionView()

        val navController = mainActivity.findNavController(R.id.nav_host_fragment)
        val bundle = Bundle()
        bundle.putString("search", query)
        navController.navigate(R.id.nav_search_shows, bundle)

        return true
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        return true
    }
}
