package ddwu.mobile.finalproject.ma01_20200989.model.repository;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import ddwu.mobile.finalproject.ma01_20200989.model.domain.entity.Diary;
import io.reactivex.Completable;
import io.reactivex.Flowable;
import io.reactivex.Single;

@Dao
public interface DiaryDao {
    @Insert
    Single<Long> insertDiary(Diary diary);

    @Update
    Completable updateDiary(Diary diary);

    @Delete
    Completable deleteDiary(Diary diary);

    @Query("SELECT * FROM diary")
    Flowable<List<Diary>> findAllDiary();

    @Query("SELECT * FROM diary")
    List<Diary> findAllDiaryAtStart();
}
