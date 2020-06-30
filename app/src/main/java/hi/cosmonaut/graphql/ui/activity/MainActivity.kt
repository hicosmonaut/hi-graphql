package hi.cosmonaut.graphql.ui.activity

import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.Window
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import hi.cosmonaut.graphql.R
import hi.cosmonaut.graphql.util.Constants

class MainActivity: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        configureUI()
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if(Constants.LOGS_ENABLED) Log.d(TAG, "onCreate(): start")
    }

    private fun configureUI() {
        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
        window.addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN)

        requestWindowFeature(Window.FEATURE_NO_TITLE)
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_FULLSCREEN
        actionBar?.hide()
    }

    companion object{
        private val TAG: String = MainActivity::class.java.simpleName


    }
}