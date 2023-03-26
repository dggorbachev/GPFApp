package com.singlelab.gpf.database.repository

import com.singlelab.gpf.database.GPFDatabase
import com.singlelab.gpf.database.entity.EventSummary

interface EventsSummaryRepository {
    suspend fun insert(events: List<EventSummary>)
    suspend fun getEvents(): List<EventSummary>?
    suspend fun getEvent(eventUid: String): EventSummary?
    suspend fun clear()
}

class RoomEventsSummaryRepository(db: GPFDatabase) : EventsSummaryRepository {
    private val dao = db.eventsSummaryDao()

    override suspend fun insert(events: List<EventSummary>) =
        dao.insertOrReplace(events)

    override suspend fun getEvents(): List<EventSummary> = dao.all()

    override suspend fun getEvent(eventUid: String): EventSummary? = dao.getEvent(eventUid)

    override suspend fun clear() {
        dao.clear()
    }
}