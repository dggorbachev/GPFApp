package com.singlelab.gpf.database;

import java.lang.System;

@androidx.room.TypeConverters(value = {com.singlelab.gpf.database.converter.ChatMessageImageConverter.class, com.singlelab.gpf.database.converter.EventTypesConverter.class})
@androidx.room.Database(version = 13, entities = {com.singlelab.gpf.database.entity.Chat.class, com.singlelab.gpf.database.entity.ChatMessage.class, com.singlelab.gpf.database.entity.EventSummary.class, com.singlelab.gpf.database.entity.Profile.class, com.singlelab.gpf.database.entity.Person.class}, exportSchema = false)
@kotlin.Metadata(mv = {1, 1, 16}, bv = {1, 0, 3}, k = 1, d1 = {"\u00004\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\b\'\u0018\u0000 \u00122\u00020\u0001:\u0001\u0012B\u0005\u00a2\u0006\u0002\u0010\u0002J\r\u0010\u0003\u001a\u00020\u0004H \u00a2\u0006\u0002\b\u0005J\r\u0010\u0006\u001a\u00020\u0007H \u00a2\u0006\u0002\b\bJ\r\u0010\t\u001a\u00020\nH \u00a2\u0006\u0002\b\u000bJ\r\u0010\f\u001a\u00020\rH \u00a2\u0006\u0002\b\u000eJ\r\u0010\u000f\u001a\u00020\u0010H \u00a2\u0006\u0002\b\u0011\u00a8\u0006\u0013"}, d2 = {"Lcom/singlelab/gpf/database/GPFDatabase;", "Landroidx/room/RoomDatabase;", "()V", "chatMessagesDao", "Lcom/singlelab/gpf/database/dao/ChatMessagesDao;", "chatMessagesDao$gpf_database_debug", "chatsDao", "Lcom/singlelab/gpf/database/dao/ChatsDao;", "chatsDao$gpf_database_debug", "eventsSummaryDao", "Lcom/singlelab/gpf/database/dao/EventsSummaryDao;", "eventsSummaryDao$gpf_database_debug", "friendsDao", "Lcom/singlelab/gpf/database/dao/FriendsDao;", "friendsDao$gpf_database_debug", "profileDao", "Lcom/singlelab/gpf/database/dao/ProfileDao;", "profileDao$gpf_database_debug", "Companion", "gpf-database_debug"})
public abstract class GPFDatabase extends androidx.room.RoomDatabase {
    private static final java.lang.String DATABASE_NAME = "gpfdatabase.name";
    public static final com.singlelab.gpf.database.GPFDatabase.Companion Companion = null;
    
    @org.jetbrains.annotations.NotNull()
    public abstract com.singlelab.gpf.database.dao.ChatsDao chatsDao$gpf_database_debug();
    
    @org.jetbrains.annotations.NotNull()
    public abstract com.singlelab.gpf.database.dao.ChatMessagesDao chatMessagesDao$gpf_database_debug();
    
    @org.jetbrains.annotations.NotNull()
    public abstract com.singlelab.gpf.database.dao.EventsSummaryDao eventsSummaryDao$gpf_database_debug();
    
    @org.jetbrains.annotations.NotNull()
    public abstract com.singlelab.gpf.database.dao.ProfileDao profileDao$gpf_database_debug();
    
    @org.jetbrains.annotations.NotNull()
    public abstract com.singlelab.gpf.database.dao.FriendsDao friendsDao$gpf_database_debug();
    
    public GPFDatabase() {
        super();
    }
    
    @kotlin.Metadata(mv = {1, 1, 16}, bv = {1, 0, 3}, k = 1, d1 = {"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\u000e\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\bR\u000e\u0010\u0003\u001a\u00020\u0004X\u0082T\u00a2\u0006\u0002\n\u0000\u00a8\u0006\t"}, d2 = {"Lcom/singlelab/gpf/database/GPFDatabase$Companion;", "", "()V", "DATABASE_NAME", "", "create", "Lcom/singlelab/gpf/database/GPFDatabase;", "context", "Landroid/content/Context;", "gpf-database_debug"})
    public static final class Companion {
        
        @org.jetbrains.annotations.NotNull()
        public final com.singlelab.gpf.database.GPFDatabase create(@org.jetbrains.annotations.NotNull()
        android.content.Context context) {
            return null;
        }
        
        private Companion() {
            super();
        }
    }
}