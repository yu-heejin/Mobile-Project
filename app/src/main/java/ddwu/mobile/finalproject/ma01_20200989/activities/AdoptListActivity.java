package ddwu.mobile.finalproject.ma01_20200989.activities;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.ListView;
import androidx.appcompat.app.AppCompatActivity;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import ddwu.mobile.finalproject.ma01_20200989.R;
import ddwu.mobile.finalproject.ma01_20200989.adapters.AdoptAdapter;
import ddwu.mobile.finalproject.ma01_20200989.dtos.AdoptDto;

public class AdoptListActivity extends AppCompatActivity {
    private List<AdoptDto> adopts;
    private AdoptAdapter adoptAdapter;
    private ListView listView;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.adopt_list);

        adopts = new ArrayList<>();

        // mock data
        adopts.add(new AdoptDto(1, "경기도 성남시", "정상", Date.valueOf("2022-01-01"), Date.valueOf("2122-01-01"), "믹스견", "보호중"));

        adoptAdapter = new AdoptAdapter(this, R.layout.custom_adopt_adapter, adopts);
        listView = (ListView) findViewById(R.id.adoptListView);
        listView.setAdapter(adoptAdapter);
    }
}
