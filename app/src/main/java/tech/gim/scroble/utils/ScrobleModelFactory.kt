package tech.gim.scroble.utils

import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import tech.gim.scroble.ScrobleApplication
import java.lang.RuntimeException

class ScrobleModelFactory(private val app: ScrobleApplication): ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if( AndroidViewModel::class.java.isAssignableFrom(modelClass)){
            return modelClass.getDeclaredConstructor(ScrobleApplication::class.java).newInstance(app)
        }else{
            throw RuntimeException("Cannot construct viewmodel, is not an androidviewmodel")
        }
    }
}