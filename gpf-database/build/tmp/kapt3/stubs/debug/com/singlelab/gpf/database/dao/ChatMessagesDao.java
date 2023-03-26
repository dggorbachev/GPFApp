package com.singlelab.gpf.database.dao;

import java.lang.System;

@androidx.room.Dao()
@kotlin.Metadata(mv = {1, 1, 16}, bv = {1, 0, 3}, k = 1, d1 = {"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010 \n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0003\b!\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0003J!\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u0007H\u00a1@\u00f8\u0001\u0000\u00a2\u0006\u0004\b\b\u0010\t\u0082\u0002\u0004\n\u0002\b\u0019\u00a8\u0006\n"}, d2 = {"Lcom/singlelab/gpf/database/dao/ChatMessagesDao;", "Lcom/singlelab/gpf/database/dao/BaseDao;", "Lcom/singlelab/gpf/database/entity/ChatMessage;", "()V", "byChatUid", "", "chatUid", "", "byChatUid$gpf_database_debug", "(Ljava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "gpf-database_debug"})
public abstract class ChatMessagesDao implements com.singlelab.gpf.database.dao.BaseDao<com.singlelab.gpf.database.entity.ChatMessage> {
    
    @org.jetbrains.annotations.Nullable()
    @androidx.room.Query(value = "select * from chat_messages where chatUid = :chatUid order by date")
    public abstract java.lang.Object byChatUid$gpf_database_debug(@org.jetbrains.annotations.NotNull()
    java.lang.String chatUid, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.util.List<com.singlelab.gpf.database.entity.ChatMessage>> p1);
    
    public ChatMessagesDao() {
        super();
    }
}