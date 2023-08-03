package com.example.showmsg

import android.app.Application
import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import java.util.Timer
import java.util.TimerTask
import kotlin.concurrent.thread
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.livedata.observeAsState

class MainViewModel(
    //application: Application
) : ViewModel() {
    //live data
    public val liveDataChanged = MutableLiveData<Boolean>(false)
    //string
    var count = 1
    var text by mutableStateOf<String>("")
    //state
    var state1 by mutableStateOf<Boolean>(false)
    //timer starter
    var start:Boolean = false
    init {
        val clockTimer = Timer()
        clockTimer.scheduleAtFixedRate(object : TimerTask() {
            override fun run() {
                //this triggers error
//                Log.v("viewModelScope","scheduleAtFixedRate:${state}")
                //this works
                if(start){
                    viewModelScope.launch {
                        text = String.format("MainViewModel.count:%02d",count++)
                        state1 = state1.not()
                    }
                }
            }
        }, 0, 3000)
        //The Snapshots are transactional, hence needed to be run on main thread here
        //And cannot be updated from another thread except main.
//        thread(start = true){
//            Log.v("sssssssssss","liveDataChanged1:${liveDataChanged.value}")
//            Log.v("sssssssssss","state:${state}")
//            Thread.sleep(10000)
//            state = true
//            liveDataChanged.postValue(true)
//            Log.v("sssssssssss","liveDataChanged2:${liveDataChanged.value}")
//            Log.v("sssssssssss","state:${state}")
//        }
    }
}

//class MainViewModel(
//    //application: Application
//) : ViewModel() {
//
//}
//class MainViewModelFactory(
//    val application: Application,
//) : ViewModelProvider.Factory {
//    override fun <T : ViewModel> create(modelClass: Class<T>): T {
//        return MainViewModel(application) as T
//    }
//}