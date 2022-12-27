package ddwu.mobile.finalproject.ma01_20200989.model.repository;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import ddwu.mobile.finalproject.ma01_20200989.model.domain.entity.Diary;

@Dao
public interface DiaryDao {
    @Insert
    long insertDiary(Diary diary);

    @Update
    void updateDiary(Diary diary);

    @Delete
    void deleteDiary(Diary diary);

    @Query("SELECT * FROM diary")
    List<Diary> findAllDiary();
}
