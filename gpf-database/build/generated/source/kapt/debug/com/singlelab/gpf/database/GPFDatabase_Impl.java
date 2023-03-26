package com.singlelab.gpf.database;

import androidx.room.DatabaseConfiguration;
import androidx.room.InvalidationTracker;
import androidx.room.RoomOpenHelper;
import androidx.room.RoomOpenHelper.Delegate;
import androidx.room.RoomOpenHelper.ValidationResult;
import androidx.room.util.DBUtil;
import androidx.room.util.TableInfo;
import androidx.room.util.TableInfo.Column;
import androidx.room.util.TableInfo.ForeignKey;
import androidx.room.util.TableInfo.Index;
import androidx.sqlite.db.SupportSQLiteDatabase;
import androidx.sqlite.db.SupportSQLiteOpenHelper;
import androidx.sqlite.db.SupportSQLiteOpenHelper.Callback;
import androidx.sqlite.db.SupportSQLiteOpenHelper.Configuration;
import com.singlelab.gpf.database.dao.ChatMessagesDao;
import com.singlelab.gpf.database.dao.ChatMessagesDao_Impl;
import com.singlelab.gpf.database.dao.ChatsDao;
import com.singlelab.gpf.database.dao.ChatsDao_Impl;
import com.singlelab.gpf.database.dao.EventsSummaryDao;
import com.singlelab.gpf.database.dao.EventsSummaryDao_Impl;
import com.singlelab.gpf.database.dao.FriendsDao;
import com.singlelab.gpf.database.dao.FriendsDao_Impl;
import com.singlelab.gpf.database.dao.ProfileDao;
import com.singlelab.gpf.database.dao.ProfileDao_Impl;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

@SuppressWarnings({"unchecked", "deprecation"})
public final class GPFDatabase_Impl extends GPFDatabase {
  private volatile ChatsDao _chatsDao;

  private volatile ChatMessagesDao _chatMessagesDao;

  private volatile EventsSummaryDao _eventsSummaryDao;

  private volatile ProfileDao _profileDao;

  private volatile FriendsDao _friendsDao;

  @Override
  protected SupportSQLiteOpenHelper createOpenHelper(DatabaseConfiguration configuration) {
    final SupportSQLiteOpenHelper.Callback _openCallback = new RoomOpenHelper(configuration, new RoomOpenHelper.Delegate(13) {
      @Override
      public void createAllTables(SupportSQLiteDatabase _db) {
        _db.execSQL("CREATE TABLE IF NOT EXISTS `chats` (`uid` TEXT NOT NULL, `title` TEXT NOT NULL, `isGroup` INTEGER NOT NULL, `image` TEXT NOT NULL, `lastMessage` TEXT NOT NULL, `lastMessagePersonUid` TEXT NOT NULL, `lastMessagePersonName` TEXT NOT NULL, `isLastMessageImage` INTEGER NOT NULL, `unreadMessagesCount` INTEGER NOT NULL, PRIMARY KEY(`uid`))");
        _db.execSQL("CREATE TABLE IF NOT EXISTS `chat_messages` (`uid` TEXT NOT NULL, `text` TEXT NOT NULL, `date` TEXT NOT NULL, `personUid` TEXT NOT NULL, `chatUid` TEXT NOT NULL, `personName` TEXT NOT NULL, `personPhoto` TEXT NOT NULL, `images` TEXT NOT NULL, PRIMARY KEY(`uid`))");
        _db.execSQL("CREATE TABLE IF NOT EXISTS `events` (`eventUid` TEXT NOT NULL, `name` TEXT NOT NULL, `description` TEXT NOT NULL, `startTime` TEXT NOT NULL, `endTime` TEXT NOT NULL, `types` TEXT NOT NULL, `eventPrimaryImageContentUid` TEXT, `xCoordinate` REAL, `yCoordinate` REAL, `status` INTEGER NOT NULL, `isAdministrator` INTEGER NOT NULL, `participantStatus` INTEGER NOT NULL, `anyPersonWaitingForApprove` INTEGER NOT NULL, `isOnline` INTEGER NOT NULL, `chatUid` TEXT, `cityId` TEXT, `cityName` TEXT, PRIMARY KEY(`eventUid`))");
        _db.execSQL("CREATE TABLE IF NOT EXISTS `profile` (`personUid` TEXT NOT NULL, `login` TEXT NOT NULL, `name` TEXT NOT NULL, `description` TEXT NOT NULL, `cityId` INTEGER NOT NULL, `cityName` TEXT NOT NULL, `age` INTEGER NOT NULL, `imageContentUid` TEXT NOT NULL, `isFriend` INTEGER NOT NULL, PRIMARY KEY(`personUid`))");
        _db.execSQL("CREATE TABLE IF NOT EXISTS `persons` (`personUid` TEXT NOT NULL, `name` TEXT NOT NULL, `login` TEXT NOT NULL, `description` TEXT NOT NULL, `age` INTEGER NOT NULL, `cityName` TEXT NOT NULL, `imageContentUid` TEXT NOT NULL, `isFriend` INTEGER NOT NULL, `friendshipApprovalRequired` INTEGER NOT NULL, `participantStatus` INTEGER NOT NULL, PRIMARY KEY(`personUid`))");
        _db.execSQL("CREATE TABLE IF NOT EXISTS room_master_table (id INTEGER PRIMARY KEY,identity_hash TEXT)");
        _db.execSQL("INSERT OR REPLACE INTO room_master_table (id,identity_hash) VALUES(42, 'cdd1b5cc0642bea3a5bbbb3d447cd529')");
      }

      @Override
      public void dropAllTables(SupportSQLiteDatabase _db) {
        _db.execSQL("DROP TABLE IF EXISTS `chats`");
        _db.execSQL("DROP TABLE IF EXISTS `chat_messages`");
        _db.execSQL("DROP TABLE IF EXISTS `events`");
        _db.execSQL("DROP TABLE IF EXISTS `profile`");
        _db.execSQL("DROP TABLE IF EXISTS `persons`");
        if (mCallbacks != null) {
          for (int _i = 0, _size = mCallbacks.size(); _i < _size; _i++) {
            mCallbacks.get(_i).onDestructiveMigration(_db);
          }
        }
      }

      @Override
      protected void onCreate(SupportSQLiteDatabase _db) {
        if (mCallbacks != null) {
          for (int _i = 0, _size = mCallbacks.size(); _i < _size; _i++) {
            mCallbacks.get(_i).onCreate(_db);
          }
        }
      }

      @Override
      public void onOpen(SupportSQLiteDatabase _db) {
        mDatabase = _db;
        internalInitInvalidationTracker(_db);
        if (mCallbacks != null) {
          for (int _i = 0, _size = mCallbacks.size(); _i < _size; _i++) {
            mCallbacks.get(_i).onOpen(_db);
          }
        }
      }

      @Override
      public void onPreMigrate(SupportSQLiteDatabase _db) {
        DBUtil.dropFtsSyncTriggers(_db);
      }

      @Override
      public void onPostMigrate(SupportSQLiteDatabase _db) {
      }

      @Override
      protected RoomOpenHelper.ValidationResult onValidateSchema(SupportSQLiteDatabase _db) {
        final HashMap<String, TableInfo.Column> _columnsChats = new HashMap<String, TableInfo.Column>(9);
        _columnsChats.put("uid", new TableInfo.Column("uid", "TEXT", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsChats.put("title", new TableInfo.Column("title", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsChats.put("isGroup", new TableInfo.Column("isGroup", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsChats.put("image", new TableInfo.Column("image", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsChats.put("lastMessage", new TableInfo.Column("lastMessage", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsChats.put("lastMessagePersonUid", new TableInfo.Column("lastMessagePersonUid", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsChats.put("lastMessagePersonName", new TableInfo.Column("lastMessagePersonName", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsChats.put("isLastMessageImage", new TableInfo.Column("isLastMessageImage", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsChats.put("unreadMessagesCount", new TableInfo.Column("unreadMessagesCount", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysChats = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesChats = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoChats = new TableInfo("chats", _columnsChats, _foreignKeysChats, _indicesChats);
        final TableInfo _existingChats = TableInfo.read(_db, "chats");
        if (! _infoChats.equals(_existingChats)) {
          return new RoomOpenHelper.ValidationResult(false, "chats(com.singlelab.gpf.database.entity.Chat).\n"
                  + " Expected:\n" + _infoChats + "\n"
                  + " Found:\n" + _existingChats);
        }
        final HashMap<String, TableInfo.Column> _columnsChatMessages = new HashMap<String, TableInfo.Column>(8);
        _columnsChatMessages.put("uid", new TableInfo.Column("uid", "TEXT", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsChatMessages.put("text", new TableInfo.Column("text", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsChatMessages.put("date", new TableInfo.Column("date", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsChatMessages.put("personUid", new TableInfo.Column("personUid", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsChatMessages.put("chatUid", new TableInfo.Column("chatUid", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsChatMessages.put("personName", new TableInfo.Column("personName", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsChatMessages.put("personPhoto", new TableInfo.Column("personPhoto", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsChatMessages.put("images", new TableInfo.Column("images", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysChatMessages = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesChatMessages = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoChatMessages = new TableInfo("chat_messages", _columnsChatMessages, _foreignKeysChatMessages, _indicesChatMessages);
        final TableInfo _existingChatMessages = TableInfo.read(_db, "chat_messages");
        if (! _infoChatMessages.equals(_existingChatMessages)) {
          return new RoomOpenHelper.ValidationResult(false, "chat_messages(com.singlelab.gpf.database.entity.ChatMessage).\n"
                  + " Expected:\n" + _infoChatMessages + "\n"
                  + " Found:\n" + _existingChatMessages);
        }
        final HashMap<String, TableInfo.Column> _columnsEvents = new HashMap<String, TableInfo.Column>(17);
        _columnsEvents.put("eventUid", new TableInfo.Column("eventUid", "TEXT", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsEvents.put("name", new TableInfo.Column("name", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsEvents.put("description", new TableInfo.Column("description", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsEvents.put("startTime", new TableInfo.Column("startTime", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsEvents.put("endTime", new TableInfo.Column("endTime", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsEvents.put("types", new TableInfo.Column("types", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsEvents.put("eventPrimaryImageContentUid", new TableInfo.Column("eventPrimaryImageContentUid", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsEvents.put("xCoordinate", new TableInfo.Column("xCoordinate", "REAL", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsEvents.put("yCoordinate", new TableInfo.Column("yCoordinate", "REAL", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsEvents.put("status", new TableInfo.Column("status", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsEvents.put("isAdministrator", new TableInfo.Column("isAdministrator", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsEvents.put("participantStatus", new TableInfo.Column("participantStatus", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsEvents.put("anyPersonWaitingForApprove", new TableInfo.Column("anyPersonWaitingForApprove", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsEvents.put("isOnline", new TableInfo.Column("isOnline", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsEvents.put("chatUid", new TableInfo.Column("chatUid", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsEvents.put("cityId", new TableInfo.Column("cityId", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsEvents.put("cityName", new TableInfo.Column("cityName", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysEvents = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesEvents = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoEvents = new TableInfo("events", _columnsEvents, _foreignKeysEvents, _indicesEvents);
        final TableInfo _existingEvents = TableInfo.read(_db, "events");
        if (! _infoEvents.equals(_existingEvents)) {
          return new RoomOpenHelper.ValidationResult(false, "events(com.singlelab.gpf.database.entity.EventSummary).\n"
                  + " Expected:\n" + _infoEvents + "\n"
                  + " Found:\n" + _existingEvents);
        }
        final HashMap<String, TableInfo.Column> _columnsProfile = new HashMap<String, TableInfo.Column>(9);
        _columnsProfile.put("personUid", new TableInfo.Column("personUid", "TEXT", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsProfile.put("login", new TableInfo.Column("login", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsProfile.put("name", new TableInfo.Column("name", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsProfile.put("description", new TableInfo.Column("description", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsProfile.put("cityId", new TableInfo.Column("cityId", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsProfile.put("cityName", new TableInfo.Column("cityName", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsProfile.put("age", new TableInfo.Column("age", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsProfile.put("imageContentUid", new TableInfo.Column("imageContentUid", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsProfile.put("isFriend", new TableInfo.Column("isFriend", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysProfile = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesProfile = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoProfile = new TableInfo("profile", _columnsProfile, _foreignKeysProfile, _indicesProfile);
        final TableInfo _existingProfile = TableInfo.read(_db, "profile");
        if (! _infoProfile.equals(_existingProfile)) {
          return new RoomOpenHelper.ValidationResult(false, "profile(com.singlelab.gpf.database.entity.Profile).\n"
                  + " Expected:\n" + _infoProfile + "\n"
                  + " Found:\n" + _existingProfile);
        }
        final HashMap<String, TableInfo.Column> _columnsPersons = new HashMap<String, TableInfo.Column>(10);
        _columnsPersons.put("personUid", new TableInfo.Column("personUid", "TEXT", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsPersons.put("name", new TableInfo.Column("name", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsPersons.put("login", new TableInfo.Column("login", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsPersons.put("description", new TableInfo.Column("description", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsPersons.put("age", new TableInfo.Column("age", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsPersons.put("cityName", new TableInfo.Column("cityName", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsPersons.put("imageContentUid", new TableInfo.Column("imageContentUid", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsPersons.put("isFriend", new TableInfo.Column("isFriend", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsPersons.put("friendshipApprovalRequired", new TableInfo.Column("friendshipApprovalRequired", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsPersons.put("participantStatus", new TableInfo.Column("participantStatus", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysPersons = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesPersons = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoPersons = new TableInfo("persons", _columnsPersons, _foreignKeysPersons, _indicesPersons);
        final TableInfo _existingPersons = TableInfo.read(_db, "persons");
        if (! _infoPersons.equals(_existingPersons)) {
          return new RoomOpenHelper.ValidationResult(false, "persons(com.singlelab.gpf.database.entity.Person).\n"
                  + " Expected:\n" + _infoPersons + "\n"
                  + " Found:\n" + _existingPersons);
        }
        return new RoomOpenHelper.ValidationResult(true, null);
      }
    }, "cdd1b5cc0642bea3a5bbbb3d447cd529", "accd2d199f965714c99d1c759c38000b");
    final SupportSQLiteOpenHelper.Configuration _sqliteConfig = SupportSQLiteOpenHelper.Configuration.builder(configuration.context)
        .name(configuration.name)
        .callback(_openCallback)
        .build();
    final SupportSQLiteOpenHelper _helper = configuration.sqliteOpenHelperFactory.create(_sqliteConfig);
    return _helper;
  }

  @Override
  protected InvalidationTracker createInvalidationTracker() {
    final HashMap<String, String> _shadowTablesMap = new HashMap<String, String>(0);
    HashMap<String, Set<String>> _viewTables = new HashMap<String, Set<String>>(0);
    return new InvalidationTracker(this, _shadowTablesMap, _viewTables, "chats","chat_messages","events","profile","persons");
  }

  @Override
  public void clearAllTables() {
    super.assertNotMainThread();
    final SupportSQLiteDatabase _db = super.getOpenHelper().getWritableDatabase();
    try {
      super.beginTransaction();
      _db.execSQL("DELETE FROM `chats`");
      _db.execSQL("DELETE FROM `chat_messages`");
      _db.execSQL("DELETE FROM `events`");
      _db.execSQL("DELETE FROM `profile`");
      _db.execSQL("DELETE FROM `persons`");
      super.setTransactionSuccessful();
    } finally {
      super.endTransaction();
      _db.query("PRAGMA wal_checkpoint(FULL)").close();
      if (!_db.inTransaction()) {
        _db.execSQL("VACUUM");
      }
    }
  }

  @Override
  public ChatsDao chatsDao$gpf_database_debug() {
    if (_chatsDao != null) {
      return _chatsDao;
    } else {
      synchronized(this) {
        if(_chatsDao == null) {
          _chatsDao = new ChatsDao_Impl(this);
        }
        return _chatsDao;
      }
    }
  }

  @Override
  public ChatMessagesDao chatMessagesDao$gpf_database_debug() {
    if (_chatMessagesDao != null) {
      return _chatMessagesDao;
    } else {
      synchronized(this) {
        if(_chatMessagesDao == null) {
          _chatMessagesDao = new ChatMessagesDao_Impl(this);
        }
        return _chatMessagesDao;
      }
    }
  }

  @Override
  public EventsSummaryDao eventsSummaryDao$gpf_database_debug() {
    if (_eventsSummaryDao != null) {
      return _eventsSummaryDao;
    } else {
      synchronized(this) {
        if(_eventsSummaryDao == null) {
          _eventsSummaryDao = new EventsSummaryDao_Impl(this);
        }
        return _eventsSummaryDao;
      }
    }
  }

  @Override
  public ProfileDao profileDao$gpf_database_debug() {
    if (_profileDao != null) {
      return _profileDao;
    } else {
      synchronized(this) {
        if(_profileDao == null) {
          _profileDao = new ProfileDao_Impl(this);
        }
        return _profileDao;
      }
    }
  }

  @Override
  public FriendsDao friendsDao$gpf_database_debug() {
    if (_friendsDao != null) {
      return _friendsDao;
    } else {
      synchronized(this) {
        if(_friendsDao == null) {
          _friendsDao = new FriendsDao_Impl(this);
        }
        return _friendsDao;
      }
    }
  }
}
