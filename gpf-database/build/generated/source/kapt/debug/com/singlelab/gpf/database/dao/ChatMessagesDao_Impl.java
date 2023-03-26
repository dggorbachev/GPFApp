package com.singlelab.gpf.database.dao;

import android.database.Cursor;
import androidx.room.CoroutinesRoom;
import androidx.room.EntityDeletionOrUpdateAdapter;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.room.util.CursorUtil;
import androidx.room.util.DBUtil;
import androidx.sqlite.db.SupportSQLiteStatement;
import com.singlelab.gpf.database.converter.ChatMessageImageConverter;
import com.singlelab.gpf.database.entity.ChatMessage;
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
public final class ChatMessagesDao_Impl extends ChatMessagesDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<ChatMessage> __insertionAdapterOfChatMessage;

  private final ChatMessageImageConverter __chatMessageImageConverter = new ChatMessageImageConverter();

  private final EntityDeletionOrUpdateAdapter<ChatMessage> __deletionAdapterOfChatMessage;

  private final EntityDeletionOrUpdateAdapter<ChatMessage> __updateAdapterOfChatMessage;

  public ChatMessagesDao_Impl(RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfChatMessage = new EntityInsertionAdapter<ChatMessage>(__db) {
      @Override
      public String createQuery() {
        return "INSERT OR REPLACE INTO `chat_messages` (`uid`,`text`,`date`,`personUid`,`chatUid`,`personName`,`personPhoto`,`images`) VALUES (?,?,?,?,?,?,?,?)";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, ChatMessage value) {
        if (value.getUid() == null) {
          stmt.bindNull(1);
        } else {
          stmt.bindString(1, value.getUid());
        }
        if (value.getText() == null) {
          stmt.bindNull(2);
        } else {
          stmt.bindString(2, value.getText());
        }
        if (value.getDate() == null) {
          stmt.bindNull(3);
        } else {
          stmt.bindString(3, value.getDate());
        }
        if (value.getPersonUid() == null) {
          stmt.bindNull(4);
        } else {
          stmt.bindString(4, value.getPersonUid());
        }
        if (value.getChatUid() == null) {
          stmt.bindNull(5);
        } else {
          stmt.bindString(5, value.getChatUid());
        }
        if (value.getPersonName() == null) {
          stmt.bindNull(6);
        } else {
          stmt.bindString(6, value.getPersonName());
        }
        if (value.getPersonPhoto() == null) {
          stmt.bindNull(7);
        } else {
          stmt.bindString(7, value.getPersonPhoto());
        }
        final String _tmp;
        _tmp = __chatMessageImageConverter.fromMessages(value.getImages());
        if (_tmp == null) {
          stmt.bindNull(8);
        } else {
          stmt.bindString(8, _tmp);
        }
      }
    };
    this.__deletionAdapterOfChatMessage = new EntityDeletionOrUpdateAdapter<ChatMessage>(__db) {
      @Override
      public String createQuery() {
        return "DELETE FROM `chat_messages` WHERE `uid` = ?";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, ChatMessage value) {
        if (value.getUid() == null) {
          stmt.bindNull(1);
        } else {
          stmt.bindString(1, value.getUid());
        }
      }
    };
    this.__updateAdapterOfChatMessage = new EntityDeletionOrUpdateAdapter<ChatMessage>(__db) {
      @Override
      public String createQuery() {
        return "UPDATE OR ABORT `chat_messages` SET `uid` = ?,`text` = ?,`date` = ?,`personUid` = ?,`chatUid` = ?,`personName` = ?,`personPhoto` = ?,`images` = ? WHERE `uid` = ?";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, ChatMessage value) {
        if (value.getUid() == null) {
          stmt.bindNull(1);
        } else {
          stmt.bindString(1, value.getUid());
        }
        if (value.getText() == null) {
          stmt.bindNull(2);
        } else {
          stmt.bindString(2, value.getText());
        }
        if (value.getDate() == null) {
          stmt.bindNull(3);
        } else {
          stmt.bindString(3, value.getDate());
        }
        if (value.getPersonUid() == null) {
          stmt.bindNull(4);
        } else {
          stmt.bindString(4, value.getPersonUid());
        }
        if (value.getChatUid() == null) {
          stmt.bindNull(5);
        } else {
          stmt.bindString(5, value.getChatUid());
        }
        if (value.getPersonName() == null) {
          stmt.bindNull(6);
        } else {
          stmt.bindString(6, value.getPersonName());
        }
        if (value.getPersonPhoto() == null) {
          stmt.bindNull(7);
        } else {
          stmt.bindString(7, value.getPersonPhoto());
        }
        final String _tmp;
        _tmp = __chatMessageImageConverter.fromMessages(value.getImages());
        if (_tmp == null) {
          stmt.bindNull(8);
        } else {
          stmt.bindString(8, _tmp);
        }
        if (value.getUid() == null) {
          stmt.bindNull(9);
        } else {
          stmt.bindString(9, value.getUid());
        }
      }
    };
  }

  @Override
  public Object insertOrReplace(final Collection<? extends ChatMessage> roomEntities,
      final Continuation<? super Unit> p1) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __insertionAdapterOfChatMessage.insert(roomEntities);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, p1);
  }

  @Override
  public Object insertOrReplace(final ChatMessage roomEntity, final Continuation<? super Unit> p1) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __insertionAdapterOfChatMessage.insert(roomEntity);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, p1);
  }

  @Override
  public Object delete(final ChatMessage roomEntity, final Continuation<? super Unit> p1) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __deletionAdapterOfChatMessage.handle(roomEntity);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, p1);
  }

  @Override
  public Object update(final ChatMessage roomEntity, final Continuation<? super Unit> p1) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __updateAdapterOfChatMessage.handle(roomEntity);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, p1);
  }

  @Override
  public Object byChatUid$gpf_database_debug(final String chatUid,
      final Continuation<? super List<ChatMessage>> p1) {
    final String _sql = "select * from chat_messages where chatUid = ? order by date";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    if (chatUid == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, chatUid);
    }
    return CoroutinesRoom.execute(__db, false, new Callable<List<ChatMessage>>() {
      @Override
      public List<ChatMessage> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfUid = CursorUtil.getColumnIndexOrThrow(_cursor, "uid");
          final int _cursorIndexOfText = CursorUtil.getColumnIndexOrThrow(_cursor, "text");
          final int _cursorIndexOfDate = CursorUtil.getColumnIndexOrThrow(_cursor, "date");
          final int _cursorIndexOfPersonUid = CursorUtil.getColumnIndexOrThrow(_cursor, "personUid");
          final int _cursorIndexOfChatUid = CursorUtil.getColumnIndexOrThrow(_cursor, "chatUid");
          final int _cursorIndexOfPersonName = CursorUtil.getColumnIndexOrThrow(_cursor, "personName");
          final int _cursorIndexOfPersonPhoto = CursorUtil.getColumnIndexOrThrow(_cursor, "personPhoto");
          final int _cursorIndexOfImages = CursorUtil.getColumnIndexOrThrow(_cursor, "images");
          final List<ChatMessage> _result = new ArrayList<ChatMessage>(_cursor.getCount());
          while(_cursor.moveToNext()) {
            final ChatMessage _item;
            final String _tmpUid;
            _tmpUid = _cursor.getString(_cursorIndexOfUid);
            final String _tmpText;
            _tmpText = _cursor.getString(_cursorIndexOfText);
            final String _tmpDate;
            _tmpDate = _cursor.getString(_cursorIndexOfDate);
            final String _tmpPersonUid;
            _tmpPersonUid = _cursor.getString(_cursorIndexOfPersonUid);
            final String _tmpChatUid;
            _tmpChatUid = _cursor.getString(_cursorIndexOfChatUid);
            final String _tmpPersonName;
            _tmpPersonName = _cursor.getString(_cursorIndexOfPersonName);
            final String _tmpPersonPhoto;
            _tmpPersonPhoto = _cursor.getString(_cursorIndexOfPersonPhoto);
            final List<String> _tmpImages;
            final String _tmp;
            _tmp = _cursor.getString(_cursorIndexOfImages);
            _tmpImages = __chatMessageImageConverter.toMessages(_tmp);
            _item = new ChatMessage(_tmpUid,_tmpText,_tmpDate,_tmpPersonUid,_tmpChatUid,_tmpPersonName,_tmpPersonPhoto,_tmpImages);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
          _statement.release();
        }
      }
    }, p1);
  }
}
