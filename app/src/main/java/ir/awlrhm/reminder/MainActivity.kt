package ir.awlrhm.reminder

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import ir.awlrhm.areminder.utility.Const.KEY_HOST_NAME
import ir.awlrhm.areminder.utility.Const.KEY_SSID
import ir.awlrhm.areminder.view.reminder.ReminderActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        findViewById<Button>(R.id.btnReminder).setOnClickListener {
            val intent = Intent(this@MainActivity, ReminderActivity::class.java)
            val bundle = Bundle()
            bundle.putString(KEY_HOST_NAME, "http://185.79.156.176:8013/api/")
            bundle.putString(KEY_SSID, "982")
            intent.putExtras(bundle)
            startActivity(intent)
        }
    }
}