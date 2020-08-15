package tech.gim.scroble

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.widget.Toast
import tech.gim.scroble.dagger.ApplicationComponent
import tech.gim.scroble.dagger.ApplicationModule
import tech.gim.scroble.dagger.DaggerApplicationComponent
import tech.gim.scroble.service.ShowService
import timber.log.Timber
import javax.inject.Inject

class ScrobleApplication : Application() {
    val component: ApplicationComponent by lazy {
        DaggerApplicationComponent.builder()
            .applicationModule(ApplicationModule(this))
            .build()
    }
    var cm: ConnectivityManager? = null

    @Inject
    lateinit var showService: ShowService

    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())
        component.inject(this)
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
