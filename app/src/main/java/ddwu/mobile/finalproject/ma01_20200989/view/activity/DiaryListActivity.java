package ddwu.mobile.finalproject.ma01_20200989.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;
import ddwu.mobile.finalproject.ma01_20200989.R;
import ddwu.mobile.finalproject.ma01_20200989.database.DiaryDatabase;
import ddwu.mobile.finalproject.ma01_20200989.model.domain.entity.Diary;
import ddwu.mobile.finalproject.ma01_20200989.model.repository.DiaryDao;
import ddwu.mobile.finalproject.ma01_20200989.view.adapter.DiaryAdapter;
import ddwu.mobile.finalproject.ma01_20200989.model.domain.dto.DiaryDto;
import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

public class DiaryListActivity extends AppCompatActivity {
    final int REQ_CODE = 100;
    private final CompositeDisposable DISPOSABLE = new CompositeDisposable();
    final static String TAG = "DiaryListActivity";

    private List<DiaryDto> diaries;
    private DiaryAdapter diaryAdapter;
    private ListView listView;
    private DiaryDatabase diaryDatabase;
    private DiaryDao diaryDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.diary_list);

        diaryDatabase = DiaryDatabase.getDatabase(this);
        diaryDao = diaryDatabase.diaryDao();

        diaries = new ArrayList<>();

        Flowable<List<Diary>> resultDiaries = diaryDao.findAllDiary();

        DISPOSABLE.add ( resultDiaries
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(allDiary -> {
                    // diaries.clear();
                            for (Diary diary : allDiary) {
                                DiaryDto diaryDto = new DiaryDto(diary.id, diary.title, diary.content, diary.status, diary.date);
                                diaries.add(diaryDto);
                            }
                        },
                        throwable -> Log.d(TAG, "error", throwable))
        );

        /* mock data */
        // diaries.add(new DirayDto(1, "testTitle", "안녕하세요", "좋음", "2022-11-11"));

        diaryAdapter = new DiaryAdapter(this, R.layout.custom_diary_adapter, diaries);
        listView = (ListView) findViewById(R.id.diaryListView);
        listView.setAdapter(diaryAdapter);
    }

    public void addDiaryButtonOnClick(View view) {
        Intent addIntent = new Intent(this, DetailDiaryActivity.class);
        startActivityForResult(addIntent, REQ_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == REQ_CODE) {
            switch(resultCode) {
                case RESULT_OK:
                    Toast.makeText(this, "기록이 완료되었습니다.", Toast.LENGTH_SHORT).show();
                    break;

                case RESULT_CANCELED:
                    Toast.makeText(this, "오류가 발생하여 등록하지 못했습니다.", Toast.LENGTH_SHORT).show();
            }

        }
    }
}
