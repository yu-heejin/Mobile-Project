package ddwu.mobile.finalproject.ma01_20200989.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import ddwu.mobile.finalproject.ma01_20200989.R;
import ddwu.mobile.finalproject.ma01_20200989.database.DiaryDatabase;
import ddwu.mobile.finalproject.ma01_20200989.model.domain.entity.Diary;
import ddwu.mobile.finalproject.ma01_20200989.model.repository.DiaryDao;
import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

public class DetailDiaryActivity extends AppCompatActivity {
    private final CompositeDisposable DISPOSABLE = new CompositeDisposable();
    final static String TAG = "DetailDiaryActivity";

    EditText dateInput;
    EditText titleInput;
    EditText statusInput;
    EditText contentInput;

    DiaryDatabase diaryDatabase;
    DiaryDao diaryDao;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.diary_detail);

        dateInput = findViewById(R.id.todayInput);
        titleInput = findViewById(R.id.titleInput);
        statusInput = findViewById(R.id.statusInput);
        contentInput = findViewById(R.id.contentInput);

        diaryDatabase = DiaryDatabase.getDatabase(this);
        diaryDao = diaryDatabase.diaryDao();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        DISPOSABLE.clear();
    }

    public void addOnClick(View view) {
        String date = dateInput.getText().toString();
        String title = titleInput.getText().toString();
        String status = statusInput.getText().toString();
        String content = contentInput.getText().toString();

        Single<Long> insertResult = diaryDao.insertDiary(new Diary(title, content, status, date));

        DISPOSABLE.add (
                insertResult.subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(result -> {
                                    Intent intent = new Intent();
                                    setResult(RESULT_OK, intent);
                                    finish();
                                },
                                throwable -> {
                                    Log.d(TAG, "error", throwable);
                                    Toast.makeText(this, "error " + throwable, Toast.LENGTH_LONG).show();
                                    Intent intent = new Intent();
                                    setResult(RESULT_CANCELED, intent);
                                })
        );
    }
}
