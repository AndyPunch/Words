package com.example.words.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.words.domain.entity.Word


@Database(entities = [Word::class], version = 1, exportSchema = false)
@TypeConverters(RoomConverters::class)
abstract class PersistentDatabase : RoomDatabase() {
    abstract fun wordsDao(): WordsDao
}
