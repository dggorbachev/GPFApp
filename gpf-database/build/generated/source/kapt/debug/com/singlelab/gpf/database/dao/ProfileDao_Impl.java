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
import com.singlelab.gpf.database.entity.Profile;
import java.lang.Exception;
import java.lang.Object;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.Collection;
import java.util.concurrent.Callable;
import kotlin.Unit;
import kotlin.coroutines.Continuation;

@SuppressWarnings({"unchecked", "deprecation"})
public final class ProfileDao_Impl extends ProfileDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<Profile> __insertionAdapterOfProfile;

  private final EntityDeletionOrUpdateAdapter<Profile> __deletionAdapterOfProfile;

  private final EntityDeletionOrUpdateAdapter<Profile> __updateAdapterOfProfile;

  private final SharedSQLiteStatement __preparedStmtOfClear$gpf_database_debug;

  public ProfileDao_Impl(RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfProfile = new EntityInsertionAdapter<Profile>(__db) {
      @Override
      public String createQuery() {
        return "INSERT OR REPLACE INTO `profile` (`personUid`,`login`,`name`,`description`,`cityId`,`cityName`,`age`,`imageContentUid`,`isFriend`) VALUES (?,?,?,?,?,?,?,?,?)";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, Profile value) {
        if (value.getPersonUid() == null) {
          stmt.bindNull(1);
        } else {
          stmt.bindString(1, value.getPersonUid());
        }
        if (value.getLogin() == null) {
          stmt.bindNull(2);
        } else {
          stmt.bindString(2, value.getLogin());
        }
        if (value.getName() == null) {
          stmt.bindNull(3);
        } else {
          stmt.bindString(3, value.getName());
        }
        if (value.getDescription() == null) {
          stmt.bindNull(4);
        } else {
          stmt.bindString(4, value.getDescription());
        }
        stmt.bindLong(5, value.getCityId());
        if (value.getCityName() == null) {
          stmt.bindNull(6);
        } else {
          stmt.bindString(6, value.getCityName());
        }
        stmt.bindLong(7, value.getAge());
        if (value.getImageContentUid() == null) {
          stmt.bindNull(8);
        } else {
          stmt.bindString(8, value.getImageContentUid());
        }
        final int _tmp;
        _tmp = value.isFriend() ? 1 : 0;
        stmt.bindLong(9, _tmp);
      }
    };
    this.__deletionAdapterOfProfile = new EntityDeletionOrUpdateAdapter<Profile>(__db) {
      @Override
      public String createQuery() {
        return "DELETE FROM `profile` WHERE `personUid` = ?";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, Profile value) {
        if (value.getPersonUid() == null) {
          stmt.bindNull(1);
        } else {
          stmt.bindString(1, value.getPersonUid());
        }
      }
    };
    this.__updateAdapterOfProfile = new EntityDeletionOrUpdateAdapter<Profile>(__db) {
      @Override
      public String createQuery() {
        return "UPDATE OR ABORT `profile` SET `personUid` = ?,`login` = ?,`name` = ?,`description` = ?,`cityId` = ?,`cityName` = ?,`age` = ?,`imageContentUid` = ?,`isFriend` = ? WHERE `personUid` = ?";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, Profile value) {
        if (value.getPersonUid() == null) {
          stmt.bindNull(1);
        } else {
          stmt.bindString(1, value.getPersonUid());
        }
        if (value.getLogin() == null) {
          stmt.bindNull(2);
        } else {
          stmt.bindString(2, value.getLogin());
        }
        if (value.getName() == null) {
          stmt.bindNull(3);
        } else {
          stmt.bindString(3, value.getName());
        }
        if (value.getDescription() == null) {
          stmt.bindNull(4);
        } else {
          stmt.bindString(4, value.getDescription());
        }
        stmt.bindLong(5, value.getCityId());
        if (value.getCityName() == null) {
          stmt.bindNull(6);
        } else {
          stmt.bindString(6, value.getCityName());
        }
        stmt.bindLong(7, value.getAge());
        if (value.getImageContentUid() == null) {
          stmt.bindNull(8);
        } else {
          stmt.bindString(8, value.getImageContentUid());
        }
        final int _tmp;
        _tmp = value.isFriend() ? 1 : 0;
        stmt.bindLong(9, _tmp);
        if (value.getPersonUid() == null) {
          stmt.bindNull(10);
        } else {
          stmt.bindString(10, value.getPersonUid());
        }
      }
    };
    this.__preparedStmtOfClear$gpf_database_debug = new SharedSQLiteStatement(__db) {
      @Override
      public String createQuery() {
        final String _query = "delete from profile";
        return _query;
      }
    };
  }

  @Override
  public Object insertOrReplace(final Collection<? extends Profile> roomEntities,
      final Continuation<? super Unit> p1) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __insertionAdapterOfProfile.insert(roomEntities);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, p1);
  }

  @Override
  public Object insertOrReplace(final Profile roomEntity, final Continuation<? super Unit> p1) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __insertionAdapterOfProfile.insert(roomEntity);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, p1);
  }

  @Override
  public Object delete(final Profile roomEntity, final Continuation<? super Unit> p1) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __deletionAdapterOfProfile.handle(roomEntity);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, p1);
  }

  @Override
  public Object update(final Profile roomEntity, final Continuation<? super Unit> p1) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __updateAdapterOfProfile.handle(roomEntity);
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
  public Object getProfile$gpf_database_debug(final String uid,
      final Continuation<? super Profile> p1) {
    final String _sql = "select * from profile where personUid=? limit 1";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    if (uid == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, uid);
    }
    return CoroutinesRoom.execute(__db, false, new Callable<Profile>() {
      @Override
      public Profile call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfPersonUid = CursorUtil.getColumnIndexOrThrow(_cursor, "personUid");
          final int _cursorIndexOfLogin = CursorUtil.getColumnIndexOrThrow(_cursor, "login");
          final int _cursorIndexOfName = CursorUtil.getColumnIndexOrThrow(_cursor, "name");
          final int _cursorIndexOfDescription = CursorUtil.getColumnIndexOrThrow(_cursor, "description");
          final int _cursorIndexOfCityId = CursorUtil.getColumnIndexOrThrow(_cursor, "cityId");
          final int _cursorIndexOfCityName = CursorUtil.getColumnIndexOrThrow(_cursor, "cityName");
          final int _cursorIndexOfAge = CursorUtil.getColumnIndexOrThrow(_cursor, "age");
          final int _cursorIndexOfImageContentUid = CursorUtil.getColumnIndexOrThrow(_cursor, "imageContentUid");
          final int _cursorIndexOfIsFriend = CursorUtil.getColumnIndexOrThrow(_cursor, "isFriend");
          final Profile _result;
          if(_cursor.moveToFirst()) {
            final String _tmpPersonUid;
            _tmpPersonUid = _cursor.getString(_cursorIndexOfPersonUid);
            final String _tmpLogin;
            _tmpLogin = _cursor.getString(_cursorIndexOfLogin);
            final String _tmpName;
            _tmpName = _cursor.getString(_cursorIndexOfName);
            final String _tmpDescription;
            _tmpDescription = _cursor.getString(_cursorIndexOfDescription);
            final int _tmpCityId;
            _tmpCityId = _cursor.getInt(_cursorIndexOfCityId);
            final String _tmpCityName;
            _tmpCityName = _cursor.getString(_cursorIndexOfCityName);
            final int _tmpAge;
            _tmpAge = _cursor.getInt(_cursorIndexOfAge);
            final String _tmpImageContentUid;
            _tmpImageContentUid = _cursor.getString(_cursorIndexOfImageContentUid);
            final boolean _tmpIsFriend;
            final int _tmp;
            _tmp = _cursor.getInt(_cursorIndexOfIsFriend);
            _tmpIsFriend = _tmp != 0;
            _result = new Profile(_tmpPersonUid,_tmpLogin,_tmpName,_tmpDescription,_tmpCityId,_tmpCityName,_tmpAge,_tmpImageContentUid,_tmpIsFriend);
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
