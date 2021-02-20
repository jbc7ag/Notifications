package com.jbc7ag.notifications.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MainViewModel : ViewModel() {

    val TEXT: Int = 0
    val BIGTEXT: Int = 1
    val BIGPICTURE: Int = 2
    val INBOX: Int = 3
    val MESSAGING: Int = 4

    private val _showTextNotification = MutableLiveData<Boolean>()
    val showTextNotification: LiveData<Boolean>
        get() = _showTextNotification

    private val _showBigTextNotification = MutableLiveData<Boolean>()
    val showBigTextNotification: LiveData<Boolean>
        get() = _showBigTextNotification

    private val _showBigPictureNotification = MutableLiveData<Boolean>()
    val showBigPictureNotification: LiveData<Boolean>
        get() = _showBigPictureNotification

    private val _showInboxNotification = MutableLiveData<Boolean>()
    val showInboxNotification: LiveData<Boolean>
        get() = _showInboxNotification

    private val _showMessagingNotification = MutableLiveData<Boolean>()
    val showMessagingNotification: LiveData<Boolean>
        get() = _showMessagingNotification

    init {
        _showTextNotification.value = false;
        _showBigTextNotification.value = false;
        _showBigPictureNotification.value = false;
        _showInboxNotification.value = false;
        _showMessagingNotification.value = false;

    }

    fun showTexNotification (type: Int) {

        when(type){
            TEXT ->   _showTextNotification.value = true
            BIGTEXT ->   _showBigTextNotification.value = true
            BIGPICTURE ->   _showBigPictureNotification.value = true
            INBOX ->   _showInboxNotification.value = true
            MESSAGING ->   _showMessagingNotification.value = true
        }
    }


}