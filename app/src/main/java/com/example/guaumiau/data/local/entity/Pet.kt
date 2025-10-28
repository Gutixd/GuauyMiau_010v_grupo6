package com.example.guaumiau.data.local.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import com.example.guaumiau.domain.model.PetType

@Entity(
    tableName = "pets",
    foreignKeys = [
        ForeignKey(
            entity = User::class,
            parentColumns = ["id"],
            childColumns = ["userId"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [Index("userId")]
)
data class Pet(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val userId: Long,
    val type: PetType,
    val name: String
)
