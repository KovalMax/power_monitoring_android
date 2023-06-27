package com.kovalmax.powermonitoring.presentation.model

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kovalmax.powermonitoring.domain.DeviceRepositoryInterface
import com.kovalmax.powermonitoring.domain.IDispatcherProvider
import com.kovalmax.powermonitoring.domain.model.Device
import com.kovalmax.powermonitoring.intent.signin.GoogleAuthClient
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CompletableJob
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val client: GoogleAuthClient,
    private val repository: DeviceRepositoryInterface,
    private val contextProvider: IDispatcherProvider,
) : ViewModel(), CoroutineScope {
    private val jobTracker: CompletableJob = Job()
    override val coroutineContext get() = contextProvider.provideIOContext(jobTracker)

    val devices = MutableLiveData(listOf<Device>())
    fun updateList() {
        val user = client.getSignedInUser() ?: return
        viewModelScope.launch {
            devicesList(user.id)
        }
    }

    private suspend fun devicesList(userId: String) {
        val devices = repository.getDevices(userId)

        for (device in devices) {
            val event = repository.getLastEvent(device.id)
            if (event != null && device.lastEvent == null) {
                device.lastEvent = event
            }
        }
        this.devices.value = devices
    }
}