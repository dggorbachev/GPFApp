package com.singlelab.gpf.database.repository;

import java.lang.System;

@kotlin.Metadata(mv = {1, 1, 16}, bv = {1, 0, 3}, k = 1, d1 = {"\u00006\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\b\u0004\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\u0011\u0010\u0007\u001a\u00020\bH\u0096@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\tJ\u001b\u0010\n\u001a\u0004\u0018\u00010\u000b2\u0006\u0010\f\u001a\u00020\rH\u0096@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\u000eJ\u0017\u0010\u000f\u001a\b\u0012\u0004\u0012\u00020\u000b0\u0010H\u0096@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\tJ\u001f\u0010\u0011\u001a\u00020\b2\f\u0010\u0012\u001a\b\u0012\u0004\u0012\u00020\u000b0\u0010H\u0096@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\u0013R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u0082\u0002\u0004\n\u0002\b\u0019\u00a8\u0006\u0014"}, d2 = {"Lcom/singlelab/gpf/database/repository/RoomEventsSummaryRepository;", "Lcom/singlelab/gpf/database/repository/EventsSummaryRepository;", "db", "Lcom/singlelab/gpf/database/GPFDatabase;", "(Lcom/singlelab/gpf/database/GPFDatabase;)V", "dao", "Lcom/singlelab/gpf/database/dao/EventsSummaryDao;", "clear", "", "(Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getEvent", "Lcom/singlelab/gpf/database/entity/EventSummary;", "eventUid", "", "(Ljava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getEvents", "", "insert", "events", "(Ljava/util/List;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "gpf-database_debug"})
public final class RoomEventsSummaryRepository implements com.singlelab.gpf.database.repository.EventsSummaryRepository {
    private final com.singlelab.gpf.database.dao.EventsSummaryDao dao = null;
    
    @org.jetbrains.annotations.Nullable()
    @java.lang.Override()
    public java.lang.Object insert(@org.jetbrains.annotations.NotNull()
    java.util.List<com.singlelab.gpf.database.entity.EventSummary> events, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> p1) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    @java.lang.Override()
    public java.lang.Object getEvents(@org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.util.List<com.singlelab.gpf.database.entity.EventSummary>> p0) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    @java.lang.Override()
    public java.lang.Object getEvent(@org.jetbrains.annotations.NotNull()
    java.lang.String eventUid, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super com.singlelab.gpf.database.entity.EventSummary> p1) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    @java.lang.Override()
    public java.lang.Object clear(@org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> p0) {
        return null;
    }
    
    public RoomEventsSummaryRepository(@org.jetbrains.annotations.NotNull()
    com.singlelab.gpf.database.GPFDatabase db) {
        super();
    }
}