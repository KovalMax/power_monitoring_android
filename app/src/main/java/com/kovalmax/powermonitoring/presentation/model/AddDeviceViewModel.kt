package com.kovalmax.powermonitoring.presentation.model

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.kovalmax.powermonitoring.domain.DeviceRepositoryInterface
import com.kovalmax.powermonitoring.domain.IDispatcherProvider
import com.kovalmax.powermonitoring.domain.model.Device
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import java.util.UUID
import javax.inject.Inject

@HiltViewModel
class AddDeviceViewModel @Inject constructor(
    private val repository: DeviceRepositoryInterface,
    private val contextProvider: IDispatcherProvider,
) : ViewModel(), CoroutineScope {
    private val jobTracker = Job()

    val deviceId = MutableLiveData("")
    val deviceName = MutableLiveData("")
    val deviceLocation = MutableLiveData("")

    override val coroutineContext get() = contextProvider.provideIOContext(jobTracker)

    fun onDeviceIdChange(newDeviceId: String) {
        deviceId.value = newDeviceId.filter {
            it.isDigit()
        }
    }

    fun onDeviceNameChange(newDeviceName: String) {
        deviceName.value = newDeviceName.filter {
            it.isWhitespace() || it.isLetterOrDigit()
        }
    }

    fun onDeviceLocationChange(newDeviceName: String) {
        deviceLocation.value = newDeviceName.filter {
            it.isWhitespace() || it.isLetterOrDigit()
        }
    }

    fun onNewDevice(deviceId: String, deviceName: String, deviceLocation: String, userId: String) {
        if (deviceId.isEmpty() || deviceName.isEmpty() || deviceLocation.isEmpty()) {
            throw Exception()
        }

        repository.addDevice(
            Device(
                id = UUID.randomUUID().toString(),
                userId = userId,
                deviceId = deviceId,
                deviceName = deviceName,
                deviceLocation = deviceLocation
            )
        )
    }
}
