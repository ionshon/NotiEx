package com.example.notiex

import android.app.*
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.example.notiex.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }

    private val CHANNEL_ID = "MP_CHANNEL"
    private var notificationManager: NotificationManager? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        //노티피케이션 채널 생성
        createNotificationChannel() // CHANNEL_ID, "DemoChannel") //, "this is a demo")

        binding.button.setOnClickListener {
            //
            displayNotification()
        }
    }

   fun displayNotification() {

        val NOTI_ID = 153
        val tapResultIntent = Intent(this, SecondActivity::class.java) /*.apply {
            //현재 액티비티에서 새로운 액티비티를 실행한다면 현재 액티비티를 새로운 액티비티로 교체하는 플래그
            //flags = Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT
            //이전에 실행된 액티비티들을 모두 없엔 후 새로운 액티비티 실행 플래그
            flags = Intent.FLAG_ACTIVITY_CLEAR_TASK
        } */
        val pendingIntent: PendingIntent? = TaskStackBuilder.create(this).run {
            // Add the intent, which inflates the back stack
            addNextIntentWithParentStack(tapResultIntent)
            // Get the PendingIntent containing the entire back stack
            getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT)
        }
            /*
            PendingIntent.getActivity(
            this,
            0,
            tapResultIntent,
            PendingIntent.FLAG_UPDATE_CURRENT )*/

        val notification: Notification = NotificationCompat.Builder(this@MainActivity, CHANNEL_ID)
            .setContentTitle("Foreground Service - Title")
            .setContentText("This is a demo notification") // 노티 내용
            .setSmallIcon(R.mipmap.ic_launcher_round)
            .setAutoCancel(true) // 사용자가 알림을 탭하면 자동으로 알림을 삭제합니다.
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setContentIntent(pendingIntent) //노티클릭시 인텐트작업
            .build()
        with(NotificationManagerCompat.from(this)) {
           notify(NOTI_ID, notification)
       }
        //notificationManager?.notify(NOTI_ID, notification) //노티실행
    }

    private fun createNotificationChannel() { //id: String, name: String, channelDescription: String) {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val serviceChannel = NotificationChannel(CHANNEL_ID,
                "FOREGROUND_CHANNEL",
                NotificationManager.IMPORTANCE_HIGH).apply {  }
           // val manager = getSystemService(NotificationManager::class.java)
            notificationManager?.createNotificationChannel(serviceChannel)


        }
    }
/*
    fun createNotificationChannel(id: String, name: String, channelDescription: String) {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            //중요도
            val importance = NotificationManager.IMPORTANCE_HIGH
            //채널 생성
            val channel = NotificationChannel(id, name, importance).apply {
                // description = channelDescription
            }
            notificationManager?.createNotificationChannel(channel)
        } else {

        }
        val notification = NotificationCompat.Builder(this, CHANNEL_ID)
            .setContentTitle("Foreground Service")
            .setSmallIcon(R.mipmap.ic_launcher_round)
            .build()

    } */

}