package dog.abcd.fliqlo

import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import dog.abcd.fliqlo.databinding.ActivityMainBinding
import java.util.*


class MainActivity : AppCompatActivity() {

    companion object {
        lateinit var instance: MainActivity
    }

    lateinit var bind: ActivityMainBinding

    val handler = Handler(Looper.getMainLooper())

    val runnable = Runnable {
        changeTime()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O_MR1) {
            setShowWhenLocked(true)
        }
        instance = this
        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
        bind = ActivityMainBinding.inflate(layoutInflater)
        setContentView(bind.root)
        initView()
    }

    fun initView() {
        val calendar = Calendar.getInstance()
        val hour = calendar.get(Calendar.HOUR_OF_DAY)
        val minute = calendar.get(Calendar.MINUTE)
        bind.hour.setNewText(hour.toString().addZero())
        bind.hour.setAnimationDuration(2000)
        bind.minute.setNewText(minute.toString().addZero())
        bind.minute.setAnimationDuration(1500)
        handler.postDelayed(runnable, (60 - calendar.get(Calendar.SECOND)) * 1000L)
    }

    fun changeTime() {
        val calendar = Calendar.getInstance()
        val hour = calendar.get(Calendar.HOUR_OF_DAY)
        val minute = calendar.get(Calendar.MINUTE)
        bind.hour.animateTextChange(hour.toString().addZero())
        bind.minute.animateTextChange(minute.toString().addZero())
        handler.postDelayed(runnable, (60 - calendar.get(Calendar.SECOND)) * 1000L)
    }

    override fun onDestroy() {
        super.onDestroy()
        handler.removeCallbacks(runnable)
    }

    fun String.addZero(): String {
        return if (length == 1) {
            "0$this"
        } else {
            this
        }
    }
}