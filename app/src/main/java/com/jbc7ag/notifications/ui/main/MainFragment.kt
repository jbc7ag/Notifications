package com.jbc7ag.notifications.ui.main

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.graphics.Color
import android.os.Build
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import com.jbc7ag.notifications.MainActivity
import com.jbc7ag.notifications.R
import com.jbc7ag.notifications.databinding.MainFragmentBinding

class MainFragment : Fragment() {

    companion object {
        fun newInstance() = MainFragment()
    }

    private lateinit var viewModel: MainViewModel
    private lateinit var binding: MainFragmentBinding
    private val CHANNELID = "NOTIFY"
    private val CHANNELNAME = "NOTIFICATION"
    private val NOTIFICATIONID = 0


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {

        val binding = MainFragmentBinding.inflate(inflater)
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        binding.viewModel = viewModel

        val notificationManager = context?.let { ContextCompat.getSystemService(it, NotificationManager::class.java) } as NotificationManager

        viewModel.showTextNotification.observe(viewLifecycleOwner, Observer { show ->
            if(show)
            notificationManager.sendTextNotification(context?.getText(R.string.simple_text_description).toString(), context!!)
        })

        viewModel.showBigTextNotification.observe(viewLifecycleOwner, Observer { show ->
            if(show)
            notificationManager.sendBigTextNotification(context?.getText(R.string.simple_text_description).toString(), context!!)
        })

        viewModel.showBigPictureNotification.observe(viewLifecycleOwner, Observer { show ->
            if(show)
                notificationManager.sendBigTextNotification(context?.getText(R.string.simple_text_description).toString(), context!!)
        })

        viewModel.showMessagingNotification.observe(viewLifecycleOwner, Observer { show ->
            if(show)
                notificationManager.sendBigTextNotification(context?.getText(R.string.simple_text_description).toString(), context!!)
        })

        viewModel.showInboxNotification.observe(viewLifecycleOwner, Observer { show ->
            if(show)
                notificationManager.sendBigTextNotification(context?.getText(R.string.simple_text_description).toString(), context!!)
        })


        createChannel()
        binding.lifecycleOwner = this
        return binding.root
    }

    private fun createChannel() {

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val notificationChannel = NotificationChannel(CHANNELID, CHANNELNAME, NotificationManager.IMPORTANCE_HIGH)
                    .apply {
                        setShowBadge(false)
                    }

            notificationChannel.enableLights(true)
            notificationChannel.lightColor = Color.RED
            notificationChannel.enableVibration(true)
            notificationChannel.description = "Show Notifications"

            val notificationManager = requireActivity().getSystemService(NotificationManager::class.java)
            notificationManager.createNotificationChannel(notificationChannel)

        }

    }

    //Notifications

    // BASIC TEXT NOTIFICATION
    private fun NotificationManager.sendTextNotification(messageBody: String, applicationContext: Context) {

        cancelAll()
        val contentIntent = Intent(applicationContext, MainActivity::class.java)
        val contentPendingIntent  = PendingIntent.getActivity(applicationContext, NOTIFICATIONID, contentIntent, PendingIntent.FLAG_UPDATE_CURRENT)

        // Build the notification
        val builder = NotificationCompat
                .Builder(applicationContext,
                        CHANNELID)
                .setSmallIcon(R.drawable.rainbow)
                .setContentTitle(applicationContext.getString(R.string.simple_text))
                .setContentText(messageBody)
                .setContentIntent(contentPendingIntent)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
        notify(NOTIFICATIONID, builder.build())
    }

    // BIG TEXT NOTIFICATION
    private fun NotificationManager.sendBigTextNotification(messageBody: String, applicationContext: Context) {

        cancelAll()
        val contentIntent = Intent(applicationContext, MainActivity::class.java)
        val contentPendingIntent  = PendingIntent.getActivity(applicationContext, NOTIFICATIONID, contentIntent, PendingIntent.FLAG_UPDATE_CURRENT)

        val bigTextStyle = NotificationCompat.BigTextStyle()
                .bigText(applicationContext.getString(R.string.big_string))

        // Build the notification
        val builder = NotificationCompat
                .Builder(applicationContext,
                        CHANNELID)
                 .setStyle(bigTextStyle)
                .setSmallIcon(R.drawable.rainbow)
                .setContentTitle(applicationContext.getString(R.string.simple_text))
                .setContentText(messageBody)
                .setContentIntent(contentPendingIntent)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
        notify(NOTIFICATIONID, builder.build())
    }

}