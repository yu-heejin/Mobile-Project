package ddwu.mobile.finalproject.ma01_20200989.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import ddwu.mobile.finalproject.ma01_20200989.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
    }

    public void hospitalOnClick(View view) {
        Intent hospitalIntent = new Intent(this, HospitalActivity.class);
        startActivity(hospitalIntent);
    }

    public void diaryOnClick(View view) {
        Intent diaryIntent = new Intent(this, DiaryListActivity.class);
        startActivity(diaryIntent);
    }
}