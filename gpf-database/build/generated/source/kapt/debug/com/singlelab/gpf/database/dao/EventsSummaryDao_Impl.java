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
import com.singlelab.gpf.database.converter.EventTypesConverter;
import com.singlelab.gpf.database.entity.EventSummary;
import java.lang.Double;
import java.lang.Exception;
import java.lang.Integer;
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
public final class EventsSummaryDao_Impl extends EventsSummaryDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<EventSummary> __insertionAdapterOfEventSummary;

  private final EventTypesConverter __eventTypesConverter = new EventTypesConverter();

  private final EntityDeletionOrUpdateAdapter<EventSummary> __deletionAdapterOfEventSummary;

  private final EntityDeletionOrUpdateAdapter<EventSummary> __updateAdapterOfEventSummary;

  private final SharedSQLiteStatement __preparedStmtOfClear$gpf_database_debug;

  public EventsSummaryDao_Impl(RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfEventSummary = new EntityInsertionAdapter<EventSummary>(__db) {
      @Override
      public String createQuery() {
        return "INSERT OR REPLACE INTO `events` (`eventUid`,`name`,`description`,`startTime`,`endTime`,`types`,`eventPrimaryImageContentUid`,`xCoordinate`,`yCoordinate`,`status`,`isAdministrator`,`participantStatus`,`anyPersonWaitingForApprove`,`isOnline`,`chatUid`,`cityId`,`cityName`) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, EventSummary value) {
        if (value.getEventUid() == null) {
          stmt.bindNull(1);
        } else {
          stmt.bindString(1, value.getEventUid());
        }
        if (value.getName() == null) {
          stmt.bindNull(2);
        } else {
          stmt.bindString(2, value.getName());
        }
        if (value.getDescription() == null) {
          stmt.bindNull(3);
        } else {
          stmt.bindString(3, value.getDescription());
        }
        if (value.getStartTime() == null) {
          stmt.bindNull(4);
        } else {
          stmt.bindString(4, value.getStartTime());
        }
        if (value.getEndTime() == null) {
          stmt.bindNull(5);
        } else {
          stmt.bindString(5, value.getEndTime());
        }
        final String _tmp;
        _tmp = __eventTypesConverter.fromTypes(value.getTypes());
        if (_tmp == null) {
          stmt.bindNull(6);
        } else {
          stmt.bindString(6, _tmp);
        }
        if (value.getEventPrimaryImageContentUid() == null) {
          stmt.bindNull(7);
        } else {
          stmt.bindString(7, value.getEventPrimaryImageContentUid());
        }
        if (value.getXCoordinate() == null) {
          stmt.bindNull(8);
        } else {
          stmt.bindDouble(8, value.getXCoordinate());
        }
        if (value.getYCoordinate() == null) {
          stmt.bindNull(9);
        } else {
          stmt.bindDouble(9, value.getYCoordinate());
        }
        stmt.bindLong(10, value.getStatus());
        final int _tmp_1;
        _tmp_1 = value.isAdministrator() ? 1 : 0;
        stmt.bindLong(11, _tmp_1);
        stmt.bindLong(12, value.getParticipantStatus());
        final int _tmp_2;
        _tmp_2 = value.getAnyPersonWaitingForApprove() ? 1 : 0;
        stmt.bindLong(13, _tmp_2);
        final int _tmp_3;
        _tmp_3 = value.isOnline() ? 1 : 0;
        stmt.bindLong(14, _tmp_3);
        if (value.getChatUid() == null) {
          stmt.bindNull(15);
        } else {
          stmt.bindString(15, value.getChatUid());
        }
        if (value.getCityId() == null) {
          stmt.bindNull(16);
        } else {
          stmt.bindString(16, value.getCityId());
        }
        if (value.getCityName() == null) {
          stmt.bindNull(17);
        } else {
          stmt.bindString(17, value.getCityName());
        }
      }
    };
    this.__deletionAdapterOfEventSummary = new EntityDeletionOrUpdateAdapter<EventSummary>(__db) {
      @Override
      public String createQuery() {
        return "DELETE FROM `events` WHERE `eventUid` = ?";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, EventSummary value) {
        if (value.getEventUid() == null) {
          stmt.bindNull(1);
        } else {
          stmt.bindString(1, value.getEventUid());
        }
      }
    };
    this.__updateAdapterOfEventSummary = new EntityDeletionOrUpdateAdapter<EventSummary>(__db) {
      @Override
      public String createQuery() {
        return "UPDATE OR ABORT `events` SET `eventUid` = ?,`name` = ?,`description` = ?,`startTime` = ?,`endTime` = ?,`types` = ?,`eventPrimaryImageContentUid` = ?,`xCoordinate` = ?,`yCoordinate` = ?,`status` = ?,`isAdministrator` = ?,`participantStatus` = ?,`anyPersonWaitingForApprove` = ?,`isOnline` = ?,`chatUid` = ?,`cityId` = ?,`cityName` = ? WHERE `eventUid` = ?";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, EventSummary value) {
        if (value.getEventUid() == null) {
          stmt.bindNull(1);
        } else {
          stmt.bindString(1, value.getEventUid());
        }
        if (value.getName() == null) {
          stmt.bindNull(2);
        } else {
          stmt.bindString(2, value.getName());
        }
        if (value.getDescription() == null) {
          stmt.bindNull(3);
        } else {
          stmt.bindString(3, value.getDescription());
        }
        if (value.getStartTime() == null) {
          stmt.bindNull(4);
        } else {
          stmt.bindString(4, value.getStartTime());
        }
        if (value.getEndTime() == null) {
          stmt.bindNull(5);
        } else {
          stmt.bindString(5, value.getEndTime());
        }
        final String _tmp;
        _tmp = __eventTypesConverter.fromTypes(value.getTypes());
        if (_tmp == null) {
          stmt.bindNull(6);
        } else {
          stmt.bindString(6, _tmp);
        }
        if (value.getEventPrimaryImageContentUid() == null) {
          stmt.bindNull(7);
        } else {
          stmt.bindString(7, value.getEventPrimaryImageContentUid());
        }
        if (value.getXCoordinate() == null) {
          stmt.bindNull(8);
        } else {
          stmt.bindDouble(8, value.getXCoordinate());
        }
        if (value.getYCoordinate() == null) {
          stmt.bindNull(9);
        } else {
          stmt.bindDouble(9, value.getYCoordinate());
        }
        stmt.bindLong(10, value.getStatus());
        final int _tmp_1;
        _tmp_1 = value.isAdministrator() ? 1 : 0;
        stmt.bindLong(11, _tmp_1);
        stmt.bindLong(12, value.getParticipantStatus());
        final int _tmp_2;
        _tmp_2 = value.getAnyPersonWaitingForApprove() ? 1 : 0;
        stmt.bindLong(13, _tmp_2);
        final int _tmp_3;
        _tmp_3 = value.isOnline() ? 1 : 0;
        stmt.bindLong(14, _tmp_3);
        if (value.getChatUid() == null) {
          stmt.bindNull(15);
        } else {
          stmt.bindString(15, value.getChatUid());
        }
        if (value.getCityId() == null) {
          stmt.bindNull(16);
        } else {
          stmt.bindString(16, value.getCityId());
        }
        if (value.getCityName() == null) {
          stmt.bindNull(17);
        } else {
          stmt.bindString(17, value.getCityName());
        }
        if (value.getEventUid() == null) {
          stmt.bindNull(18);
        } else {
          stmt.bindString(18, value.getEventUid());
        }
      }
    };
    this.__preparedStmtOfClear$gpf_database_debug = new SharedSQLiteStatement(__db) {
      @Override
      public String createQuery() {
        final String _query = "delete from events";
        return _query;
      }
    };
  }

  @Override
  public Object insertOrReplace(final Collection<? extends EventSummary> roomEntities,
      final Continuation<? super Unit> p1) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __insertionAdapterOfEventSummary.insert(roomEntities);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, p1);
  }

  @Override
  public Object insertOrReplace(final EventSummary roomEntity,
      final Continuation<? super Unit> p1) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __insertionAdapterOfEventSummary.insert(roomEntity);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, p1);
  }

  @Override
  public Object delete(final EventSummary roomEntity, final Continuation<? super Unit> p1) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __deletionAdapterOfEventSummary.handle(roomEntity);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, p1);
  }

  @Override
  public Object update(final EventSummary roomEntity, final Continuation<? super Unit> p1) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __updateAdapterOfEventSummary.handle(roomEntity);
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
  public Object all$gpf_database_debug(final Continuation<? super List<EventSummary>> p0) {
    final String _sql = "select * from events";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return CoroutinesRoom.execute(__db, false, new Callable<List<EventSummary>>() {
      @Override
      public List<EventSummary> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfEventUid = CursorUtil.getColumnIndexOrThrow(_cursor, "eventUid");
          final int _cursorIndexOfName = CursorUtil.getColumnIndexOrThrow(_cursor, "name");
          final int _cursorIndexOfDescription = CursorUtil.getColumnIndexOrThrow(_cursor, "description");
          final int _cursorIndexOfStartTime = CursorUtil.getColumnIndexOrThrow(_cursor, "startTime");
          final int _cursorIndexOfEndTime = CursorUtil.getColumnIndexOrThrow(_cursor, "endTime");
          final int _cursorIndexOfTypes = CursorUtil.getColumnIndexOrThrow(_cursor, "types");
          final int _cursorIndexOfEventPrimaryImageContentUid = CursorUtil.getColumnIndexOrThrow(_cursor, "eventPrimaryImageContentUid");
          final int _cursorIndexOfXCoordinate = CursorUtil.getColumnIndexOrThrow(_cursor, "xCoordinate");
          final int _cursorIndexOfYCoordinate = CursorUtil.getColumnIndexOrThrow(_cursor, "yCoordinate");
          final int _cursorIndexOfStatus = CursorUtil.getColumnIndexOrThrow(_cursor, "status");
          final int _cursorIndexOfIsAdministrator = CursorUtil.getColumnIndexOrThrow(_cursor, "isAdministrator");
          final int _cursorIndexOfParticipantStatus = CursorUtil.getColumnIndexOrThrow(_cursor, "participantStatus");
          final int _cursorIndexOfAnyPersonWaitingForApprove = CursorUtil.getColumnIndexOrThrow(_cursor, "anyPersonWaitingForApprove");
          final int _cursorIndexOfIsOnline = CursorUtil.getColumnIndexOrThrow(_cursor, "isOnline");
          final int _cursorIndexOfChatUid = CursorUtil.getColumnIndexOrThrow(_cursor, "chatUid");
          final int _cursorIndexOfCityId = CursorUtil.getColumnIndexOrThrow(_cursor, "cityId");
          final int _cursorIndexOfCityName = CursorUtil.getColumnIndexOrThrow(_cursor, "cityName");
          final List<EventSummary> _result = new ArrayList<EventSummary>(_cursor.getCount());
          while(_cursor.moveToNext()) {
            final EventSummary _item;
            final String _tmpEventUid;
            _tmpEventUid = _cursor.getString(_cursorIndexOfEventUid);
            final String _tmpName;
            _tmpName = _cursor.getString(_cursorIndexOfName);
            final String _tmpDescription;
            _tmpDescription = _cursor.getString(_cursorIndexOfDescription);
            final String _tmpStartTime;
            _tmpStartTime = _cursor.getString(_cursorIndexOfStartTime);
            final String _tmpEndTime;
            _tmpEndTime = _cursor.getString(_cursorIndexOfEndTime);
            final List<Integer> _tmpTypes;
            final String _tmp;
            _tmp = _cursor.getString(_cursorIndexOfTypes);
            _tmpTypes = __eventTypesConverter.toTypes(_tmp);
            final String _tmpEventPrimaryImageContentUid;
            _tmpEventPrimaryImageContentUid = _cursor.getString(_cursorIndexOfEventPrimaryImageContentUid);
            final Double _tmpXCoordinate;
            if (_cursor.isNull(_cursorIndexOfXCoordinate)) {
              _tmpXCoordinate = null;
            } else {
              _tmpXCoordinate = _cursor.getDouble(_cursorIndexOfXCoordinate);
            }
            final Double _tmpYCoordinate;
            if (_cursor.isNull(_cursorIndexOfYCoordinate)) {
              _tmpYCoordinate = null;
            } else {
              _tmpYCoordinate = _cursor.getDouble(_cursorIndexOfYCoordinate);
            }
            final int _tmpStatus;
            _tmpStatus = _cursor.getInt(_cursorIndexOfStatus);
            final boolean _tmpIsAdministrator;
            final int _tmp_1;
            _tmp_1 = _cursor.getInt(_cursorIndexOfIsAdministrator);
            _tmpIsAdministrator = _tmp_1 != 0;
            final int _tmpParticipantStatus;
            _tmpParticipantStatus = _cursor.getInt(_cursorIndexOfParticipantStatus);
            final boolean _tmpAnyPersonWaitingForApprove;
            final int _tmp_2;
            _tmp_2 = _cursor.getInt(_cursorIndexOfAnyPersonWaitingForApprove);
            _tmpAnyPersonWaitingForApprove = _tmp_2 != 0;
            final boolean _tmpIsOnline;
            final int _tmp_3;
            _tmp_3 = _cursor.getInt(_cursorIndexOfIsOnline);
            _tmpIsOnline = _tmp_3 != 0;
            final String _tmpChatUid;
            _tmpChatUid = _cursor.getString(_cursorIndexOfChatUid);
            final String _tmpCityId;
            _tmpCityId = _cursor.getString(_cursorIndexOfCityId);
            final String _tmpCityName;
            _tmpCityName = _cursor.getString(_cursorIndexOfCityName);
            _item = new EventSummary(_tmpEventUid,_tmpName,_tmpDescription,_tmpStartTime,_tmpEndTime,_tmpTypes,_tmpEventPrimaryImageContentUid,_tmpXCoordinate,_tmpYCoordinate,_tmpStatus,_tmpIsAdministrator,_tmpParticipantStatus,_tmpAnyPersonWaitingForApprove,_tmpIsOnline,_tmpChatUid,_tmpCityId,_tmpCityName);
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

  @Override
  public Object getEvent$gpf_database_debug(final String eventUid,
      final Continuation<? super EventSummary> p1) {
    final String _sql = "select * from events where eventUid = ? limit 1";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    if (eventUid == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, eventUid);
    }
    return CoroutinesRoom.execute(__db, false, new Callable<EventSummary>() {
      @Override
      public EventSummary call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfEventUid = CursorUtil.getColumnIndexOrThrow(_cursor, "eventUid");
          final int _cursorIndexOfName = CursorUtil.getColumnIndexOrThrow(_cursor, "name");
          final int _cursorIndexOfDescription = CursorUtil.getColumnIndexOrThrow(_cursor, "description");
          final int _cursorIndexOfStartTime = CursorUtil.getColumnIndexOrThrow(_cursor, "startTime");
          final int _cursorIndexOfEndTime = CursorUtil.getColumnIndexOrThrow(_cursor, "endTime");
          final int _cursorIndexOfTypes = CursorUtil.getColumnIndexOrThrow(_cursor, "types");
          final int _cursorIndexOfEventPrimaryImageContentUid = CursorUtil.getColumnIndexOrThrow(_cursor, "eventPrimaryImageContentUid");
          final int _cursorIndexOfXCoordinate = CursorUtil.getColumnIndexOrThrow(_cursor, "xCoordinate");
          final int _cursorIndexOfYCoordinate = CursorUtil.getColumnIndexOrThrow(_cursor, "yCoordinate");
          final int _cursorIndexOfStatus = CursorUtil.getColumnIndexOrThrow(_cursor, "status");
          final int _cursorIndexOfIsAdministrator = CursorUtil.getColumnIndexOrThrow(_cursor, "isAdministrator");
          final int _cursorIndexOfParticipantStatus = CursorUtil.getColumnIndexOrThrow(_cursor, "participantStatus");
          final int _cursorIndexOfAnyPersonWaitingForApprove = CursorUtil.getColumnIndexOrThrow(_cursor, "anyPersonWaitingForApprove");
          final int _cursorIndexOfIsOnline = CursorUtil.getColumnIndexOrThrow(_cursor, "isOnline");
          final int _cursorIndexOfChatUid = CursorUtil.getColumnIndexOrThrow(_cursor, "chatUid");
          final int _cursorIndexOfCityId = CursorUtil.getColumnIndexOrThrow(_cursor, "cityId");
          final int _cursorIndexOfCityName = CursorUtil.getColumnIndexOrThrow(_cursor, "cityName");
          final EventSummary _result;
          if(_cursor.moveToFirst()) {
            final String _tmpEventUid;
            _tmpEventUid = _cursor.getString(_cursorIndexOfEventUid);
            final String _tmpName;
            _tmpName = _cursor.getString(_cursorIndexOfName);
            final String _tmpDescription;
            _tmpDescription = _cursor.getString(_cursorIndexOfDescription);
            final String _tmpStartTime;
            _tmpStartTime = _cursor.getString(_cursorIndexOfStartTime);
            final String _tmpEndTime;
            _tmpEndTime = _cursor.getString(_cursorIndexOfEndTime);
            final List<Integer> _tmpTypes;
            final String _tmp;
            _tmp = _cursor.getString(_cursorIndexOfTypes);
            _tmpTypes = __eventTypesConverter.toTypes(_tmp);
            final String _tmpEventPrimaryImageContentUid;
            _tmpEventPrimaryImageContentUid = _cursor.getString(_cursorIndexOfEventPrimaryImageContentUid);
            final Double _tmpXCoordinate;
            if (_cursor.isNull(_cursorIndexOfXCoordinate)) {
              _tmpXCoordinate = null;
            } else {
              _tmpXCoordinate = _cursor.getDouble(_cursorIndexOfXCoordinate);
            }
            final Double _tmpYCoordinate;
            if (_cursor.isNull(_cursorIndexOfYCoordinate)) {
              _tmpYCoordinate = null;
            } else {
              _tmpYCoordinate = _cursor.getDouble(_cursorIndexOfYCoordinate);
            }
            final int _tmpStatus;
            _tmpStatus = _cursor.getInt(_cursorIndexOfStatus);
            final boolean _tmpIsAdministrator;
            final int _tmp_1;
            _tmp_1 = _cursor.getInt(_cursorIndexOfIsAdministrator);
            _tmpIsAdministrator = _tmp_1 != 0;
            final int _tmpParticipantStatus;
            _tmpParticipantStatus = _cursor.getInt(_cursorIndexOfParticipantStatus);
            final boolean _tmpAnyPersonWaitingForApprove;
            final int _tmp_2;
            _tmp_2 = _cursor.getInt(_cursorIndexOfAnyPersonWaitingForApprove);
            _tmpAnyPersonWaitingForApprove = _tmp_2 != 0;
            final boolean _tmpIsOnline;
            final int _tmp_3;
            _tmp_3 = _cursor.getInt(_cursorIndexOfIsOnline);
            _tmpIsOnline = _tmp_3 != 0;
            final String _tmpChatUid;
            _tmpChatUid = _cursor.getString(_cursorIndexOfChatUid);
            final String _tmpCityId;
            _tmpCityId = _cursor.getString(_cursorIndexOfCityId);
            final String _tmpCityName;
            _tmpCityName = _cursor.getString(_cursorIndexOfCityName);
            _result = new EventSummary(_tmpEventUid,_tmpName,_tmpDescription,_tmpStartTime,_tmpEndTime,_tmpTypes,_tmpEventPrimaryImageContentUid,_tmpXCoordinate,_tmpYCoordinate,_tmpStatus,_tmpIsAdministrator,_tmpParticipantStatus,_tmpAnyPersonWaitingForApprove,_tmpIsOnline,_tmpChatUid,_tmpCityId,_tmpCityName);
          } else {
            _result = null;
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
