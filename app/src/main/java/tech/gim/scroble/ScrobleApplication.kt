package tech.gim.scroble

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.widget.Toast
import tech.gim.scroble.dagger.ApplicationComponent
import tech.gim.scroble.dagger.ApplicationModule
import tech.gim.scroble.dagger.DaggerApplicationComponent
import timber.log.Timber

/**
 * Application context,
 * Home to Dagger for dependency injection (that means singletons are true singletons troughout the app)
 * And exposes functions using system resources (check on internet connectivity)
 */
class ScrobleApplication() : Application() {
    var component: ApplicationComponent? = null
    var cm: ConnectivityManager? = null

    init {
        component = DaggerApplicationComponent.builder()
            .applicationModule(ApplicationModule(this))
            .build()
    }

    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())
        component?.inject(this)
    }

    fun checkInternetConnectivity() {
        if (cm == null) cm = this.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork: NetworkInfo? = cm!!.activeNetworkInfo
        if (activeNetwork?.isConnectedOrConnecting == true) {
            // OK
        } else {
            Toast.makeText(this, this.resources.getString(R.string.no_internet), Toast.LENGTH_LONG).show()
        }
    }
}
