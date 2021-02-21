package com.jbc7ag.notifications.ui.main

import android.app.*
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
            notificationManager.sendBigTextNotification(context?.getText(R.string.big_text_description).toString(), context!!)
        })

        viewModel.showBigPictureNotification.observe(viewLifecycleOwner, Observer { show ->
            if(show)
                notificationManager.sendBigPictureNotification(context?.getText(R.string.big_picture_description).toString(), context!!)
        })

        viewModel.showMessagingNotification.observe(viewLifecycleOwner, Observer { show ->
            if(show)
                notificationManager.sendMessagingNotification(context?.getText(R.string.messaging_description).toString(), context!!)
        })

        viewModel.showInboxNotification.observe(viewLifecycleOwner, Observer { show ->
            if(show)
                notificationManager.sendInboxtNotification(context?.getText(R.string.inbox_description).toString(), context!!)
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
                .setContentTitle(applicationContext.getString(R.string.big_text))
                .setContentText(messageBody)
                .setContentIntent(contentPendingIntent)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
        notify(NOTIFICATIONID, builder.build())
    }

    // BIG PICTURE NOTIFICATION
    private fun NotificationManager.sendBigPictureNotification(messageBody: String, applicationContext: Context) {

        cancelAll()
        val contentIntent = Intent(applicationContext, MainActivity::class.java)
        val contentPendingIntent  = PendingIntent.getActivity(applicationContext, NOTIFICATIONID, contentIntent, PendingIntent.FLAG_UPDATE_CURRENT)

        // create the bitmap
        val rainbowImage = BitmapFactory.decodeResource(
            applicationContext.resources,
            R.drawable.rainbow
        )

        val bigPicStyle = NotificationCompat.BigPictureStyle()
            .bigPicture(rainbowImage)
            .bigLargeIcon(null)

        // Build the notification
        val builder = NotificationCompat
            .Builder(applicationContext,
                CHANNELID)
            .setStyle(bigPicStyle)
            .setSmallIcon(R.drawable.rainbow)
            .setContentTitle(applicationContext.getString(R.string.big_picture))
            .setContentText(messageBody)
            .setContentIntent(contentPendingIntent)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
        notify(NOTIFICATIONID, builder.build())
    }

    // INBOX NOTIFICATION
    private fun NotificationManager.sendInboxtNotification(messageBody: String, applicationContext: Context) {

        cancelAll()
        val contentIntent = Intent(applicationContext, MainActivity::class.java)
        val contentPendingIntent  = PendingIntent.getActivity(applicationContext, NOTIFICATIONID, contentIntent, PendingIntent.FLAG_UPDATE_CURRENT)

        val inboxStyle = NotificationCompat.InboxStyle()
            .addLine("Thanks for subscribe")
            .addLine("Here the news for today")
            .addLine("We need you to pay ... ")
            .setBigContentTitle("You got 20 Emails")
            .setSummaryText("You got 17 more emails")

        // create the bitmap
        val rainbowImage = BitmapFactory.decodeResource(
            applicationContext.resources,
            R.drawable.rainbow
        )


        // Build the notification
        val builder = NotificationCompat
            .Builder(applicationContext,
                CHANNELID)
            .setStyle(inboxStyle)
            .setSmallIcon(R.drawable.rainbow)
            .setLargeIcon(rainbowImage)
            .setContentTitle(applicationContext.getString(R.string.inbox))
            .setContentText(messageBody)
            .setContentIntent(contentPendingIntent)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
        notify(NOTIFICATIONID, builder.build())
    }

    // MESSAGING NOTIFICATION
    private fun NotificationManager.sendMessagingNotification(messageBody: String, applicationContext: Context) {

        cancelAll()
        val contentIntent = Intent(applicationContext, MainActivity::class.java)
        val contentPendingIntent  = PendingIntent.getActivity(applicationContext, NOTIFICATIONID, contentIntent, PendingIntent.FLAG_UPDATE_CURRENT)

        val messagingStyle = NotificationCompat.MessagingStyle("Jessica")
            .setConversationTitle(applicationContext.getString(R.string.messaging))
            .addMessage("Hi! I'm hungry!",System.currentTimeMillis()-60000,"Cat")
            .addMessage("Where are you?",System.currentTimeMillis()-60000,"Cat")
            .addMessage("Lets go play",System.currentTimeMillis()-30000,"Dog")
            .addMessage("Miss you",System.currentTimeMillis(),"Dog")
            .setGroupConversation(true)



        // Build the notification
        val builder = NotificationCompat
            .Builder(applicationContext,
                CHANNELID)
            .setStyle(messagingStyle)
            .setSmallIcon(R.drawable.rainbow)
            .setContentTitle(applicationContext.getString(R.string.messaging))
            .setContentText(messageBody)
            .setContentIntent(contentPendingIntent)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
        notify(NOTIFICATIONID, builder.build())
    }

}