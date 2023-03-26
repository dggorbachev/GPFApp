package com.singlelab.gpf.database.dao;

import java.lang.System;

@androidx.room.Dao()
@kotlin.Metadata(mv = {1, 1, 16}, bv = {1, 0, 3}, k = 1, d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0002\b\u0003\b!\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0003J\u0019\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00020\u0005H\u00a1@\u00f8\u0001\u0000\u00a2\u0006\u0004\b\u0006\u0010\u0007J\u0013\u0010\b\u001a\u00020\tH\u00a1@\u00f8\u0001\u0000\u00a2\u0006\u0004\b\n\u0010\u0007J\u001d\u0010\u000b\u001a\u0004\u0018\u00010\u00022\u0006\u0010\f\u001a\u00020\rH\u00a1@\u00f8\u0001\u0000\u00a2\u0006\u0004\b\u000e\u0010\u000f\u0082\u0002\u0004\n\u0002\b\u0019\u00a8\u0006\u0010"}, d2 = {"Lcom/singlelab/gpf/database/dao/EventsSummaryDao;", "Lcom/singlelab/gpf/database/dao/BaseDao;", "Lcom/singlelab/gpf/database/entity/EventSummary;", "()V", "all", "", "all$gpf_database_debug", "(Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "clear", "", "clear$gpf_database_debug", "getEvent", "eventUid", "", "getEvent$gpf_database_debug", "(Ljava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "gpf-database_debug"})
public abstract class EventsSummaryDao implements com.singlelab.gpf.database.dao.BaseDao<com.singlelab.gpf.database.entity.EventSummary> {
    
    @org.jetbrains.annotations.Nullable()
    @androidx.room.Query(value = "select * from events")
    public abstract java.lang.Object all$gpf_database_debug(@org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.util.List<com.singlelab.gpf.database.entity.EventSummary>> p0);
    
    @org.jetbrains.annotations.Nullable()
    @androidx.room.Query(value = "select * from events where eventUid = :eventUid limit 1")
    public abstract java.lang.Object getEvent$gpf_database_debug(@org.jetbrains.annotations.NotNull()
    java.lang.String eventUid, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super com.singlelab.gpf.database.entity.EventSummary> p1);
    
    @org.jetbrains.annotations.Nullable()
    @androidx.room.Query(value = "delete from events")
    public abstract java.lang.Object clear$gpf_database_debug(@org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> p0);
    
    public EventsSummaryDao() {
        super();
    }
}