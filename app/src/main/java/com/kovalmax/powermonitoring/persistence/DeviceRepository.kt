package com.kovalmax.powermonitoring.persistence

import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.kovalmax.powermonitoring.domain.DeviceRepositoryInterface
import com.kovalmax.powermonitoring.domain.model.Device
import com.kovalmax.powermonitoring.domain.model.Event
import kotlinx.coroutines.tasks.await

class DeviceRepository(
    private val firestore: FirebaseFirestore
) : DeviceRepositoryInterface {
    private val collectionName = "Devices"
    private val userIdField = "userId"
    private val createAtFieldName = "createdAt"

    override fun addDevice(device: Device) {
        firestore
            .collection(collectionName)
            .document(device.id)
            .set(device)
    }

    override suspend fun getDevices(userId: String): List<Device> {
        return firestore.collection(collectionName)
            .whereEqualTo(userIdField, userId)
            .orderBy(createAtFieldName, Query.Direction.DESCENDING)
            .get()
            .await()
            .toObjects(Device::class.java)
    }

    override suspend fun getLastEvent(id: String): Event? {
        return firestore.collection(collectionName)
            .document(id)
            .collection("events")
            .orderBy("createdAt", Query.Direction.DESCENDING)
            .limit(1)
            .get()
            .await()
            .toObjects(Event::class.java)
            .firstOrNull()
    }
}