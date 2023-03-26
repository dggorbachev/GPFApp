package com.singlelab.gpf.database.dao;

import java.lang.System;

@androidx.room.Dao()
@kotlin.Metadata(mv = {1, 1, 16}, bv = {1, 0, 3}, k = 1, d1 = {"\u0000 \n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0010 \n\u0002\b\u0002\b!\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0003J\u0013\u0010\u0004\u001a\u00020\u0005H\u00a1@\u00f8\u0001\u0000\u00a2\u0006\u0004\b\u0006\u0010\u0007J\u0019\u0010\b\u001a\b\u0012\u0004\u0012\u00020\u00020\tH\u00a1@\u00f8\u0001\u0000\u00a2\u0006\u0004\b\n\u0010\u0007\u0082\u0002\u0004\n\u0002\b\u0019\u00a8\u0006\u000b"}, d2 = {"Lcom/singlelab/gpf/database/dao/FriendsDao;", "Lcom/singlelab/gpf/database/dao/BaseDao;", "Lcom/singlelab/gpf/database/entity/Person;", "()V", "clear", "", "clear$gpf_database_debug", "(Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getFriends", "", "getFriends$gpf_database_debug", "gpf-database_debug"})
public abstract class FriendsDao implements com.singlelab.gpf.database.dao.BaseDao<com.singlelab.gpf.database.entity.Person> {
    
    @org.jetbrains.annotations.Nullable()
    @androidx.room.Query(value = "select * from persons")
    public abstract java.lang.Object getFriends$gpf_database_debug(@org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.util.List<com.singlelab.gpf.database.entity.Person>> p0);
    
    @org.jetbrains.annotations.Nullable()
    @androidx.room.Query(value = "delete from persons")
    public abstract java.lang.Object clear$gpf_database_debug(@org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> p0);
    
    public FriendsDao() {
        super();
    }
}