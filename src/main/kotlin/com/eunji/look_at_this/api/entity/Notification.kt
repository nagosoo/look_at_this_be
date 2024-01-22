package com.eunji.look_at_this.api.entity

data class Notification(
    val notificationId: Long,
    val notificationTitle: String,
    val notificationContent: String,
    val notificationCreatedAt: String
)