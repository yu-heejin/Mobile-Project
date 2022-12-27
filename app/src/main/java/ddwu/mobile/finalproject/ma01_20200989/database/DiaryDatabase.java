package ddwu.mobile.finalproject.ma01_20200989.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import ddwu.mobile.finalproject.ma01_20200989.model.domain.entity.Diary;
import ddwu.mobile.finalproject.ma01_20200989.model.repository.DiaryDao;

@Database(entities = {Diary.class}, version = 1)
public abstract class DiaryDatabase extends RoomDatabase {
    public abstract DiaryDao diaryDao();
    private static volatile DiaryDatabase INSTANSE;

    static DiaryDatabase getDatabase(final Context context) {
        if (INSTANSE == null) {
            synchronized (DiaryDatabase.class) {
                if (INSTANSE == null) {
                    INSTANSE = Room.databaseBuilder(context.getApplicationContext(),
                                DiaryDatabase.class, "diaryDB.db"
                            ).build();
                }
            }
        }

        return INSTANSE;
    }
}
