package com.singlelab.gpf.database.dao;

import android.database.Cursor;
import androidx.room.CoroutinesRoom;
import androidx.room.EntityDeletionOrUpdateAdapter;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.room.SharedSQLiteStatement;
import androidx.room.util.CursorUtil;
import androidx.room.util.DBUtil;
import androidx.sqlite.db.SupportSQLiteStatement;
import com.singlelab.gpf.database.entity.Chat;
import java.lang.Exception;
import java.lang.Object;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.Callable;
import kotlin.Unit;
import kotlin.coroutines.Continuation;

@SuppressWarnings({"unchecked", "deprecation"})
public final class ChatsDao_Impl extends ChatsDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<Chat> __insertionAdapterOfChat;

  private final EntityDeletionOrUpdateAdapter<Chat> __deletionAdapterOfChat;

  private final EntityDeletionOrUpdateAdapter<Chat> __updateAdapterOfChat;

  private final SharedSQLiteStatement __preparedStmtOfClear$gpf_database_debug;

  public ChatsDao_Impl(RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfChat = new EntityInsertionAdapter<Chat>(__db) {
      @Override
      public String createQuery() {
        return "INSERT OR REPLACE INTO `chats` (`uid`,`title`,`isGroup`,`image`,`lastMessage`,`lastMessagePersonUid`,`lastMessagePersonName`,`isLastMessageImage`,`unreadMessagesCount`) VALUES (?,?,?,?,?,?,?,?,?)";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, Chat value) {
        if (value.getUid() == null) {
          stmt.bindNull(1);
        } else {
          stmt.bindString(1, value.getUid());
        }
        if (value.getTitle() == null) {
          stmt.bindNull(2);
        } else {
          stmt.bindString(2, value.getTitle());
        }
        final int _tmp;
        _tmp = value.isGroup() ? 1 : 0;
        stmt.bindLong(3, _tmp);
        if (value.getImage() == null) {
          stmt.bindNull(4);
        } else {
          stmt.bindString(4, value.getImage());
        }
        if (value.getLastMessage() == null) {
          stmt.bindNull(5);
        } else {
          stmt.bindString(5, value.getLastMessage());
        }
        if (value.getLastMessagePersonUid() == null) {
          stmt.bindNull(6);
        } else {
          stmt.bindString(6, value.getLastMessagePersonUid());
        }
        if (value.getLastMessagePersonName() == null) {
          stmt.bindNull(7);
        } else {
          stmt.bindString(7, value.getLastMessagePersonName());
        }
        final int _tmp_1;
        _tmp_1 = value.isLastMessageImage() ? 1 : 0;
        stmt.bindLong(8, _tmp_1);
        stmt.bindLong(9, value.getUnreadMessagesCount());
      }
    };
    this.__deletionAdapterOfChat = new EntityDeletionOrUpdateAdapter<Chat>(__db) {
      @Override
      public String createQuery() {
        return "DELETE FROM `chats` WHERE `uid` = ?";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, Chat value) {
        if (value.getUid() == null) {
          stmt.bindNull(1);
        } else {
          stmt.bindString(1, value.getUid());
        }
      }
    };
    this.__updateAdapterOfChat = new EntityDeletionOrUpdateAdapter<Chat>(__db) {
      @Override
      public String createQuery() {
        return "UPDATE OR ABORT `chats` SET `uid` = ?,`title` = ?,`isGroup` = ?,`image` = ?,`lastMessage` = ?,`lastMessagePersonUid` = ?,`lastMessagePersonName` = ?,`isLastMessageImage` = ?,`unreadMessagesCount` = ? WHERE `uid` = ?";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, Chat value) {
        if (value.getUid() == null) {
          stmt.bindNull(1);
        } else {
          stmt.bindString(1, value.getUid());
        }
        if (value.getTitle() == null) {
          stmt.bindNull(2);
        } else {
          stmt.bindString(2, value.getTitle());
        }
        final int _tmp;
        _tmp = value.isGroup() ? 1 : 0;
        stmt.bindLong(3, _tmp);
        if (value.getImage() == null) {
          stmt.bindNull(4);
        } else {
          stmt.bindString(4, value.getImage());
        }
        if (value.getLastMessage() == null) {
          stmt.bindNull(5);
        } else {
          stmt.bindString(5, value.getLastMessage());
        }
        if (value.getLastMessagePersonUid() == null) {
          stmt.bindNull(6);
        } else {
          stmt.bindString(6, value.getLastMessagePersonUid());
        }
        if (value.getLastMessagePersonName() == null) {
          stmt.bindNull(7);
        } else {
          stmt.bindString(7, value.getLastMessagePersonName());
        }
        final int _tmp_1;
        _tmp_1 = value.isLastMessageImage() ? 1 : 0;
        stmt.bindLong(8, _tmp_1);
        stmt.bindLong(9, value.getUnreadMessagesCount());
        if (value.getUid() == null) {
          stmt.bindNull(10);
        } else {
          stmt.bindString(10, value.getUid());
        }
      }
    };
    this.__preparedStmtOfClear$gpf_database_debug = new SharedSQLiteStatement(__db) {
      @Override
      public String createQuery() {
        final String _query = "delete from chats";
        return _query;
      }
    };
  }

  @Override
  public Object insertOrReplace(final Collection<? extends Chat> roomEntities,
      final Continuation<? super Unit> p1) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __insertionAdapterOfChat.insert(roomEntities);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, p1);
  }

  @Override
  public Object insertOrReplace(final Chat roomEntity, final Continuation<? super Unit> p1) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __insertionAdapterOfChat.insert(roomEntity);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, p1);
  }

  @Override
  public Object delete(final Chat roomEntity, final Continuation<? super Unit> p1) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __deletionAdapterOfChat.handle(roomEntity);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, p1);
  }

  @Override
  public Object update(final Chat roomEntity, final Continuation<? super Unit> p1) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __updateAdapterOfChat.handle(roomEntity);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, p1);
  }

  @Override
  public Object clear$gpf_database_debug(final Continuation<? super Unit> p0) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      public Unit call() throws Exception {
        final SupportSQLiteStatement _stmt = __preparedStmtOfClear$gpf_database_debug.acquire();
        __db.beginTransaction();
        try {
          _stmt.executeUpdateDelete();
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
          __preparedStmtOfClear$gpf_database_debug.release(_stmt);
        }
      }
    }, p0);
  }

  @Override
  public Object all$gpf_database_debug(final Continuation<? super List<Chat>> p0) {
    final String _sql = "select * from chats";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return CoroutinesRoom.execute(__db, false, new Callable<List<Chat>>() {
      @Override
      public List<Chat> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfUid = CursorUtil.getColumnIndexOrThrow(_cursor, "uid");
          final int _cursorIndexOfTitle = CursorUtil.getColumnIndexOrThrow(_cursor, "title");
          final int _cursorIndexOfIsGroup = CursorUtil.getColumnIndexOrThrow(_cursor, "isGroup");
          final int _cursorIndexOfImage = CursorUtil.getColumnIndexOrThrow(_cursor, "image");
          final int _cursorIndexOfLastMessage = CursorUtil.getColumnIndexOrThrow(_cursor, "lastMessage");
          final int _cursorIndexOfLastMessagePersonUid = CursorUtil.getColumnIndexOrThrow(_cursor, "lastMessagePersonUid");
          final int _cursorIndexOfLastMessagePersonName = CursorUtil.getColumnIndexOrThrow(_cursor, "lastMessagePersonName");
          final int _cursorIndexOfIsLastMessageImage = CursorUtil.getColumnIndexOrThrow(_cursor, "isLastMessageImage");
          final int _cursorIndexOfUnreadMessagesCount = CursorUtil.getColumnIndexOrThrow(_cursor, "unreadMessagesCount");
          final List<Chat> _result = new ArrayList<Chat>(_cursor.getCount());
          while(_cursor.moveToNext()) {
            final Chat _item;
            final String _tmpUid;
            _tmpUid = _cursor.getString(_cursorIndexOfUid);
            final String _tmpTitle;
            _tmpTitle = _cursor.getString(_cursorIndexOfTitle);
            final boolean _tmpIsGroup;
            final int _tmp;
            _tmp = _cursor.getInt(_cursorIndexOfIsGroup);
            _tmpIsGroup = _tmp != 0;
            final String _tmpImage;
            _tmpImage = _cursor.getString(_cursorIndexOfImage);
            final String _tmpLastMessage;
            _tmpLastMessage = _cursor.getString(_cursorIndexOfLastMessage);
            final String _tmpLastMessagePersonUid;
            _tmpLastMessagePersonUid = _cursor.getString(_cursorIndexOfLastMessagePersonUid);
            final String _tmpLastMessagePersonName;
            _tmpLastMessagePersonName = _cursor.getString(_cursorIndexOfLastMessagePersonName);
            final boolean _tmpIsLastMessageImage;
            final int _tmp_1;
            _tmp_1 = _cursor.getInt(_cursorIndexOfIsLastMessageImage);
            _tmpIsLastMessageImage = _tmp_1 != 0;
            final int _tmpUnreadMessagesCount;
            _tmpUnreadMessagesCount = _cursor.getInt(_cursorIndexOfUnreadMessagesCount);
            _item = new Chat(_tmpUid,_tmpTitle,_tmpIsGroup,_tmpImage,_tmpLastMessage,_tmpLastMessagePersonUid,_tmpLastMessagePersonName,_tmpIsLastMessageImage,_tmpUnreadMessagesCount);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
          _statement.release();
        }
      }
    }, p0);
  }
}
