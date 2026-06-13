package shared.data.common

import android.app.Application

object AppHolder {

    lateinit var app: Application

    fun init(app: Application) {
        AppHolder.app = app
    }
}