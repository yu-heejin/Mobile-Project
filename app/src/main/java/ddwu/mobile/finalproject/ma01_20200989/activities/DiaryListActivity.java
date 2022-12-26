package ddwu.mobile.finalproject.ma01_20200989.activities;

import android.os.Bundle;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import ddwu.mobile.finalproject.ma01_20200989.R;
import ddwu.mobile.finalproject.ma01_20200989.adapters.DiaryAdapter;
import ddwu.mobile.finalproject.ma01_20200989.dtos.DirayDto;

public class DiaryListActivity extends AppCompatActivity {
    private List<DirayDto> diaries;
    private DiaryAdapter diaryAdapter;
    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.diary_list);

        diaries = new ArrayList<>();

        diaries.add(new DirayDto(1, "testTitle", "안녕하세요", "좋음", Date.valueOf("2020-11-11")));

        diaryAdapter = new DiaryAdapter(this, R.layout.custom_diary_adapter, diaries);
        listView = (ListView) findViewById(R.id.diaryListView);
        listView.setAdapter(diaryAdapter);
    }
}
