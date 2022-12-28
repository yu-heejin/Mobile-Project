package ddwu.mobile.finalproject.ma01_20200989.view.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;
import ddwu.mobile.finalproject.ma01_20200989.R;
import ddwu.mobile.finalproject.ma01_20200989.model.service.database.DiaryDatabase;
import ddwu.mobile.finalproject.ma01_20200989.model.domain.entity.Diary;
import ddwu.mobile.finalproject.ma01_20200989.model.repository.DiaryDao;
import ddwu.mobile.finalproject.ma01_20200989.view.adapter.DiaryAdapter;
import ddwu.mobile.finalproject.ma01_20200989.model.domain.dto.DiaryDto;
import io.reactivex.Completable;
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

        new Thread(new Runnable() {
            @Override
            public void run() {
                diaries = initList();

                if (diaries == null) {
                    diaries = new ArrayList<>();
                }

                diaryAdapter = new DiaryAdapter(DiaryListActivity.this, R.layout.custom_diary_adapter, diaries);
                listView = (ListView) findViewById(R.id.diaryListView);
                listView.setAdapter(diaryAdapter);

                listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                    @Override
                    public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                        DiaryDto diaryDto = diaries.get(i);
                        String title = diaries.get(i).getTitle();

                        AlertDialog.Builder builder = new AlertDialog.Builder(DiaryListActivity.this);
                        builder.setTitle("다이어리 삭제")
                                .setMessage("\"" + title + "\" 기록을 정말 삭제하시겠습니까?")
                                .setPositiveButton("삭제", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        Diary diary = new Diary(diaryDto.getTitle(), diaryDto.getContent(), diaryDto.getStatus(), diaryDto.getDate());
                                        diary.id = diaryDto.get_id();

                                        Completable deleteResult = diaryDao.deleteDiary(diary);
                                        DISPOSABLE.add(deleteResult
                                                .subscribeOn(Schedulers.io())
                                                .observeOn(AndroidSchedulers.mainThread())
                                                .subscribe(() -> {
                                                    Log.d(TAG, "Delete success: ");
                                                            onResume();
                                                        },
                                                        throwable -> Log.d(TAG, "error")) );
                                    }
                                })
                                .setNegativeButton("취소", null)
                                .setCancelable(false)
                                .show();
                        return true;
                    }
                });
            }
        }
        ).start();

        /* mock data */
        // diaries.add(new DirayDto(1, "testTitle", "안녕하세요", "좋음", "2022-11-11"));
    }

    private List<DiaryDto> initList() {
        List<Diary> resultDiaries = diaryDao.findAllDiaryAtStart();
        List<DiaryDto> diariesForReturn = new ArrayList<DiaryDto>();

        for (Diary diary : resultDiaries) {
            DiaryDto diaryDto = new DiaryDto(diary.id, diary.title, diary.content, diary.status, diary.date);
            diariesForReturn.add(diaryDto);
        }

        return diariesForReturn;
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

    protected void onResume() {
        super.onResume();

        Flowable<List<Diary>> resultDiaries = diaryDao.findAllDiary();

        DISPOSABLE.add ( resultDiaries
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(allDiary -> {
                            diaries.clear();
                            for (Diary diary : allDiary) {
                                DiaryDto diaryDto = new DiaryDto(diary.id, diary.title, diary.content, diary.status, diary.date);
                                diaries.add(diaryDto);
                            }
                        },
                        throwable -> Log.d(TAG, "error", throwable))
        );

        if (diaries == null) {
            diaries = new ArrayList<>();
        }

        diaryAdapter.notifyDataSetChanged();
    }
}
