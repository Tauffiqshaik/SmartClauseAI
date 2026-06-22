package com.legaldocai.database;

import android.database.Cursor;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import androidx.room.CoroutinesRoom;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.room.SharedSQLiteStatement;
import androidx.room.util.CursorUtil;
import androidx.room.util.DBUtil;
import androidx.sqlite.db.SupportSQLiteStatement;
import com.legaldocai.models.QueryHistory;
import java.lang.Class;
import java.lang.Exception;
import java.lang.Object;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;
import javax.annotation.processing.Generated;
import kotlin.Unit;
import kotlin.coroutines.Continuation;

@Generated("androidx.room.RoomProcessor")
@SuppressWarnings({"unchecked", "deprecation"})
public final class QueryHistoryDao_Impl implements QueryHistoryDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<QueryHistory> __insertionAdapterOfQueryHistory;

  private final SharedSQLiteStatement __preparedStmtOfClearHistory;

  public QueryHistoryDao_Impl(@NonNull final RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfQueryHistory = new EntityInsertionAdapter<QueryHistory>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR REPLACE INTO `query_history` (`id`,`query`,`response`,`documentName`,`timestamp`) VALUES (nullif(?, 0),?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @Nullable final QueryHistory entity) {
        statement.bindLong(1, entity.getId());
        if (entity.getQuery() == null) {
          statement.bindNull(2);
        } else {
          statement.bindString(2, entity.getQuery());
        }
        if (entity.getResponse() == null) {
          statement.bindNull(3);
        } else {
          statement.bindString(3, entity.getResponse());
        }
        if (entity.getDocumentName() == null) {
          statement.bindNull(4);
        } else {
          statement.bindString(4, entity.getDocumentName());
        }
        statement.bindLong(5, entity.getTimestamp());
      }
    };
    this.__preparedStmtOfClearHistory = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "DELETE FROM query_history";
        return _query;
      }
    };
  }

  @Override
  public Object insertHistory(final QueryHistory history,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __insertionAdapterOfQueryHistory.insert(history);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object clearHistory(final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        final SupportSQLiteStatement _stmt = __preparedStmtOfClearHistory.acquire();
        try {
          __db.beginTransaction();
          try {
            _stmt.executeUpdateDelete();
            __db.setTransactionSuccessful();
            return Unit.INSTANCE;
          } finally {
            __db.endTransaction();
          }
        } finally {
          __preparedStmtOfClearHistory.release(_stmt);
        }
      }
    }, $completion);
  }

  @Override
  public LiveData<List<QueryHistory>> getAllHistory() {
    final String _sql = "SELECT * FROM query_history ORDER BY timestamp DESC LIMIT 100";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return __db.getInvalidationTracker().createLiveData(new String[] {"query_history"}, false, new Callable<List<QueryHistory>>() {
      @Override
      @Nullable
      public List<QueryHistory> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfQuery = CursorUtil.getColumnIndexOrThrow(_cursor, "query");
          final int _cursorIndexOfResponse = CursorUtil.getColumnIndexOrThrow(_cursor, "response");
          final int _cursorIndexOfDocumentName = CursorUtil.getColumnIndexOrThrow(_cursor, "documentName");
          final int _cursorIndexOfTimestamp = CursorUtil.getColumnIndexOrThrow(_cursor, "timestamp");
          final List<QueryHistory> _result = new ArrayList<QueryHistory>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final QueryHistory _item;
            final int _tmpId;
            _tmpId = _cursor.getInt(_cursorIndexOfId);
            final String _tmpQuery;
            if (_cursor.isNull(_cursorIndexOfQuery)) {
              _tmpQuery = null;
            } else {
              _tmpQuery = _cursor.getString(_cursorIndexOfQuery);
            }
            final String _tmpResponse;
            if (_cursor.isNull(_cursorIndexOfResponse)) {
              _tmpResponse = null;
            } else {
              _tmpResponse = _cursor.getString(_cursorIndexOfResponse);
            }
            final String _tmpDocumentName;
            if (_cursor.isNull(_cursorIndexOfDocumentName)) {
              _tmpDocumentName = null;
            } else {
              _tmpDocumentName = _cursor.getString(_cursorIndexOfDocumentName);
            }
            final long _tmpTimestamp;
            _tmpTimestamp = _cursor.getLong(_cursorIndexOfTimestamp);
            _item = new QueryHistory(_tmpId,_tmpQuery,_tmpResponse,_tmpDocumentName,_tmpTimestamp);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    });
  }

  @NonNull
  public static List<Class<?>> getRequiredConverters() {
    return Collections.emptyList();
  }
}
