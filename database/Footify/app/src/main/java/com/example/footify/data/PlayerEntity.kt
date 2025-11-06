package com.example.footify.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.example.footify.model.Position
import java.util.Date

@Entity(tableName = "players")
@TypeConverters(Converters::class)
data class PlayerEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val name: String,
    val age: Int,
    val position: Position,
    val rating: Double,
    val shirtNumber: Int,
    val goals: Int,
    val image: String,
    val image1: String,
    val image2: String,
    val createdAt: Date,
    val updatedAt: Date
)

