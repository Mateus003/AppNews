package com.example.appnews.util

import androidx.appcompat.widget.SearchView
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.coroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class UtilQueryTextListener(lifeCycle: Lifecycle,
                            private val utilQueryTextListener: (String?)-> Unit):
    SearchView.OnQueryTextListener, LifecycleObserver {
    private val coroutine = lifeCycle.coroutineScope
    var job: Job? = null
    override fun onQueryTextSubmit(query: String?): Boolean {
        job?.cancel()
        job = coroutine.launch {
            query?.let {
                delay(500L)
                utilQueryTextListener(query!!)
            }
        }
        return true
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        return false
    }


}