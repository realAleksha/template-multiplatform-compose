package kotli

import shared.data.common.AppHolder
import android.app.Application as AndroidApplication

class Application : AndroidApplication() {

    override fun onCreate() {
        ref = this
        AppHolder.init(this)
        super.onCreate()
    }

    companion object {
        lateinit var ref: Application
            private set
    }
}