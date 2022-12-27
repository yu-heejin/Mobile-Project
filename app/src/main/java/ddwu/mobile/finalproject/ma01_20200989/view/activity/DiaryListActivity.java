package ddwu.mobile.finalproject.ma01_20200989.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import ddwu.mobile.finalproject.ma01_20200989.R;
import ddwu.mobile.finalproject.ma01_20200989.database.DiaryDatabase;
import ddwu.mobile.finalproject.ma01_20200989.model.repository.DiaryDao;
import ddwu.mobile.finalproject.ma01_20200989.view.adapter.DiaryAdapter;
import ddwu.mobile.finalproject.ma01_20200989.model.domain.dto.DirayDto;

public class DiaryListActivity extends AppCompatActivity {
    private List<DirayDto> diaries;
    private DiaryAdapter diaryAdapter;
    private ListView listView;
    private DiaryDatabase diaryDatabase;
    private DiaryDao diaryDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.diary_list);

        diaryDatabase = Room.databaseBuilder(this, DiaryDatabase.class, "diaryDB.db").build();
        diaryDao = diaryDatabase.diaryDao();

        diaries = new ArrayList<>();

        /* mock data */
        diaries.add(new DirayDto(1, "testTitle", "안녕하세요", "좋음", Date.valueOf("2020-11-11")));

        diaryAdapter = new DiaryAdapter(this, R.layout.custom_diary_adapter, diaries);
        listView = (ListView) findViewById(R.id.diaryListView);
        listView.setAdapter(diaryAdapter);
    }

    public void addDiaryButtonOnClick(View view) {
        Intent addIntent = new Intent(this, DetailDiaryActivity.class);
        startActivity(addIntent);
    }
}
