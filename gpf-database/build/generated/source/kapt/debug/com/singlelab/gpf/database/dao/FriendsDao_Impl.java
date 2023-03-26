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
import com.singlelab.gpf.database.entity.Person;
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
public final class FriendsDao_Impl extends FriendsDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<Person> __insertionAdapterOfPerson;

  private final EntityDeletionOrUpdateAdapter<Person> __deletionAdapterOfPerson;

  private final EntityDeletionOrUpdateAdapter<Person> __updateAdapterOfPerson;

  private final SharedSQLiteStatement __preparedStmtOfClear$gpf_database_debug;

  public FriendsDao_Impl(RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfPerson = new EntityInsertionAdapter<Person>(__db) {
      @Override
      public String createQuery() {
        return "INSERT OR REPLACE INTO `persons` (`personUid`,`name`,`login`,`description`,`age`,`cityName`,`imageContentUid`,`isFriend`,`friendshipApprovalRequired`,`participantStatus`) VALUES (?,?,?,?,?,?,?,?,?,?)";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, Person value) {
        if (value.getPersonUid() == null) {
          stmt.bindNull(1);
        } else {
          stmt.bindString(1, value.getPersonUid());
        }
        if (value.getName() == null) {
          stmt.bindNull(2);
        } else {
          stmt.bindString(2, value.getName());
        }
        if (value.getLogin() == null) {
          stmt.bindNull(3);
        } else {
          stmt.bindString(3, value.getLogin());
        }
        if (value.getDescription() == null) {
          stmt.bindNull(4);
        } else {
          stmt.bindString(4, value.getDescription());
        }
        stmt.bindLong(5, value.getAge());
        if (value.getCityName() == null) {
          stmt.bindNull(6);
        } else {
          stmt.bindString(6, value.getCityName());
        }
        if (value.getImageContentUid() == null) {
          stmt.bindNull(7);
        } else {
          stmt.bindString(7, value.getImageContentUid());
        }
        final int _tmp;
        _tmp = value.isFriend() ? 1 : 0;
        stmt.bindLong(8, _tmp);
        final int _tmp_1;
        _tmp_1 = value.getFriendshipApprovalRequired() ? 1 : 0;
        stmt.bindLong(9, _tmp_1);
        stmt.bindLong(10, value.getParticipantStatus());
      }
    };
    this.__deletionAdapterOfPerson = new EntityDeletionOrUpdateAdapter<Person>(__db) {
      @Override
      public String createQuery() {
        return "DELETE FROM `persons` WHERE `personUid` = ?";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, Person value) {
        if (value.getPersonUid() == null) {
          stmt.bindNull(1);
        } else {
          stmt.bindString(1, value.getPersonUid());
        }
      }
    };
    this.__updateAdapterOfPerson = new EntityDeletionOrUpdateAdapter<Person>(__db) {
      @Override
      public String createQuery() {
        return "UPDATE OR ABORT `persons` SET `personUid` = ?,`name` = ?,`login` = ?,`description` = ?,`age` = ?,`cityName` = ?,`imageContentUid` = ?,`isFriend` = ?,`friendshipApprovalRequired` = ?,`participantStatus` = ? WHERE `personUid` = ?";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, Person value) {
        if (value.getPersonUid() == null) {
          stmt.bindNull(1);
        } else {
          stmt.bindString(1, value.getPersonUid());
        }
        if (value.getName() == null) {
          stmt.bindNull(2);
        } else {
          stmt.bindString(2, value.getName());
        }
        if (value.getLogin() == null) {
          stmt.bindNull(3);
        } else {
          stmt.bindString(3, value.getLogin());
        }
        if (value.getDescription() == null) {
          stmt.bindNull(4);
        } else {
          stmt.bindString(4, value.getDescription());
        }
        stmt.bindLong(5, value.getAge());
        if (value.getCityName() == null) {
          stmt.bindNull(6);
        } else {
          stmt.bindString(6, value.getCityName());
        }
        if (value.getImageContentUid() == null) {
          stmt.bindNull(7);
        } else {
          stmt.bindString(7, value.getImageContentUid());
        }
        final int _tmp;
        _tmp = value.isFriend() ? 1 : 0;
        stmt.bindLong(8, _tmp);
        final int _tmp_1;
        _tmp_1 = value.getFriendshipApprovalRequired() ? 1 : 0;
        stmt.bindLong(9, _tmp_1);
        stmt.bindLong(10, value.getParticipantStatus());
        if (value.getPersonUid() == null) {
          stmt.bindNull(11);
        } else {
          stmt.bindString(11, value.getPersonUid());
        }
      }
    };
    this.__preparedStmtOfClear$gpf_database_debug = new SharedSQLiteStatement(__db) {
      @Override
      public String createQuery() {
        final String _query = "delete from persons";
        return _query;
      }
    };
  }

  @Override
  public Object insertOrReplace(final Collection<? extends Person> roomEntities,
      final Continuation<? super Unit> p1) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __insertionAdapterOfPerson.insert(roomEntities);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, p1);
  }

  @Override
  public Object insertOrReplace(final Person roomEntity, final Continuation<? super Unit> p1) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __insertionAdapterOfPerson.insert(roomEntity);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, p1);
  }

  @Override
  public Object delete(final Person roomEntity, final Continuation<? super Unit> p1) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __deletionAdapterOfPerson.handle(roomEntity);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, p1);
  }

  @Override
  public Object update(final Person roomEntity, final Continuation<? super Unit> p1) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __updateAdapterOfPerson.handle(roomEntity);
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
  public Object getFriends$gpf_database_debug(final Continuation<? super List<Person>> p0) {
    final String _sql = "select * from persons";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return CoroutinesRoom.execute(__db, false, new Callable<List<Person>>() {
      @Override
      public List<Person> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfPersonUid = CursorUtil.getColumnIndexOrThrow(_cursor, "personUid");
          final int _cursorIndexOfName = CursorUtil.getColumnIndexOrThrow(_cursor, "name");
          final int _cursorIndexOfLogin = CursorUtil.getColumnIndexOrThrow(_cursor, "login");
          final int _cursorIndexOfDescription = CursorUtil.getColumnIndexOrThrow(_cursor, "description");
          final int _cursorIndexOfAge = CursorUtil.getColumnIndexOrThrow(_cursor, "age");
          final int _cursorIndexOfCityName = CursorUtil.getColumnIndexOrThrow(_cursor, "cityName");
          final int _cursorIndexOfImageContentUid = CursorUtil.getColumnIndexOrThrow(_cursor, "imageContentUid");
          final int _cursorIndexOfIsFriend = CursorUtil.getColumnIndexOrThrow(_cursor, "isFriend");
          final int _cursorIndexOfFriendshipApprovalRequired = CursorUtil.getColumnIndexOrThrow(_cursor, "friendshipApprovalRequired");
          final int _cursorIndexOfParticipantStatus = CursorUtil.getColumnIndexOrThrow(_cursor, "participantStatus");
          final List<Person> _result = new ArrayList<Person>(_cursor.getCount());
          while(_cursor.moveToNext()) {
            final Person _item;
            final String _tmpPersonUid;
            _tmpPersonUid = _cursor.getString(_cursorIndexOfPersonUid);
            final String _tmpName;
            _tmpName = _cursor.getString(_cursorIndexOfName);
            final String _tmpLogin;
            _tmpLogin = _cursor.getString(_cursorIndexOfLogin);
            final String _tmpDescription;
            _tmpDescription = _cursor.getString(_cursorIndexOfDescription);
            final int _tmpAge;
            _tmpAge = _cursor.getInt(_cursorIndexOfAge);
            final String _tmpCityName;
            _tmpCityName = _cursor.getString(_cursorIndexOfCityName);
            final String _tmpImageContentUid;
            _tmpImageContentUid = _cursor.getString(_cursorIndexOfImageContentUid);
            final boolean _tmpIsFriend;
            final int _tmp;
            _tmp = _cursor.getInt(_cursorIndexOfIsFriend);
            _tmpIsFriend = _tmp != 0;
            final boolean _tmpFriendshipApprovalRequired;
            final int _tmp_1;
            _tmp_1 = _cursor.getInt(_cursorIndexOfFriendshipApprovalRequired);
            _tmpFriendshipApprovalRequired = _tmp_1 != 0;
            final int _tmpParticipantStatus;
            _tmpParticipantStatus = _cursor.getInt(_cursorIndexOfParticipantStatus);
            _item = new Person(_tmpPersonUid,_tmpName,_tmpLogin,_tmpDescription,_tmpAge,_tmpCityName,_tmpImageContentUid,_tmpIsFriend,_tmpFriendshipApprovalRequired,_tmpParticipantStatus);
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
