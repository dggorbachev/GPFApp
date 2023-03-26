package com.singlelab.gpf.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.singlelab.gpf.database.dao.*
import com.singlelab.gpf.database.entity.*

@Database(
    version = 13,
    entities = [
        Chat::class,
        ChatMessage::class,
        EventSummary::class,
        Profile::class,
        Person::class
    ],
    exportSchema = false
)
abstract class GPFDatabase : RoomDatabase() {
    internal abstract fun chatsDao(): ChatsDao
    internal abstract fun chatMessagesDao(): ChatMessagesDao
    internal abstract fun eventsSummaryDao(): EventsSummaryDao
    internal abstract fun profileDao(): ProfileDao
    internal abstract fun friendsDao(): FriendsDao

    companion object {
        fun create(context: Context): GPFDatabase =
            Room.databaseBuilder(context, GPFDatabase::class.java, DATABASE_NAME)
                .fallbackToDestructiveMigration()
                .build()

        private const val DATABASE_NAME = "gpfdatabase.name"
    }
}