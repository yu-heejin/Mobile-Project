package ddwu.mobile.finalproject.ma01_20200989.view.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;

import ddwu.mobile.finalproject.ma01_20200989.R;
import ddwu.mobile.finalproject.ma01_20200989.model.service.parser.AdoptXmlParser;
import ddwu.mobile.finalproject.ma01_20200989.view.adapter.AdoptAdapter;
import ddwu.mobile.finalproject.ma01_20200989.model.domain.dto.AdoptDto;

public class AdoptListActivity extends AppCompatActivity {
    private List<AdoptDto> adopts;
    private AdoptAdapter adoptAdapter;
    private ListView listView;
    private AdoptXmlParser adoptXmlParser;
    String apiAddress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.adopt_list);

        adopts = new ArrayList<>();

        apiAddress = getResources().getString(R.string.adopt_url);
        adoptXmlParser = new AdoptXmlParser();

        if (!isOnline()) {
            Toast.makeText(AdoptListActivity.this, "네트워크를 사용가능하게 설정해주세요.", Toast.LENGTH_SHORT).show();
            return;
        }

        new NetworkAsyncTask().execute(apiAddress);    // server_url 에 입력한 날짜를 결합한 후 AsyncTask 실행
        // mock data
        // adopts.add(new AdoptDto(1, "경기도 성남시", "정상", "2020-11-11", "2122-11-11", "믹스견", "보호중"));
    }

    class NetworkAsyncTask extends AsyncTask<String, Void, String> {

        final static String NETWORK_ERR_MSG = "Server Error!";
        public final static String TAG = "NetworkAsyncTask";
        ProgressDialog progressDlg;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDlg = ProgressDialog.show(AdoptListActivity.this, "Wait", "Downloading...");     // 진행상황 다이얼로그 출력
        }

        @Override
        protected String doInBackground(String... strings) {
            String address = strings[0];
            String result = downloadContents(address);

            if (result == null) {
                cancel(true);
                return NETWORK_ERR_MSG;
            }
            return result;
        }

        @Override
        protected void onPostExecute(String result) {
            Log.i(TAG, result);
            progressDlg.dismiss();  // 진행상황 다이얼로그 종료
            adopts.clear();        // 어댑터에 남아있는 이전 내용이 있다면 클리어

//          parser 생성 및 OpenAPI 수신 결과를 사용하여 parsing 수행
            // HospitalXmlParser parser = new HospitalXmlParser();
            adopts = adoptXmlParser.parse(result);
            Log.i(TAG, adopts.toString());

            if (adopts == null) {       // 올바른 결과를 수신하지 못하였을 경우 안내
                Toast.makeText(AdoptListActivity.this, "결과가 없습니다.", Toast.LENGTH_SHORT).show();
            } else if (!adopts.isEmpty()) {
                adoptAdapter = new AdoptAdapter(AdoptListActivity.this, R.layout.custom_adopt_adapter, adopts);
                listView = (ListView) findViewById(R.id.adoptListView);
                listView.setAdapter(adoptAdapter);
            }
        }


        @Override
        protected void onCancelled(String msg) {
            super.onCancelled();
            progressDlg.dismiss();
            Toast.makeText(AdoptListActivity.this, msg, Toast.LENGTH_SHORT).show();
        }
    }


    /* 이하 네트워크 접속을 위한 메소드 */


    /* 네트워크 환경 조사 */
    private boolean isOnline() {
        ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        return (networkInfo != null && networkInfo.isConnected());
    }


    /* 주소(apiAddress)에 접속하여 문자열 데이터를 수신한 후 반환 */
    protected String downloadContents(String address) {
        HttpURLConnection conn = null;
        InputStream stream = null;
        String result = null;

        try {
            URL url = new URL(address);
            conn = (HttpURLConnection)url.openConnection();
            stream = getNetworkConnection(conn);
            result = readStreamToString(stream);
            if (stream != null) stream.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (conn != null) conn.disconnect();
        }

        return result;
    }

    /* 주소(apiAddress)에 접속하여 비트맵 데이터를 수신한 후 반환 */
    protected Bitmap downloadImage(String address) {
        HttpURLConnection conn = null;
        InputStream stream = null;
        Bitmap result = null;

        try {
            URL url = new URL(address);
            conn = (HttpURLConnection)url.openConnection();
            stream = getNetworkConnection(conn);
            result = readStreamToBitmap(stream);
            if (stream != null) stream.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (conn != null) conn.disconnect();
        }

        return result;
    }


    /* URLConnection 을 전달받아 연결정보 설정 후 연결, 연결 후 수신한 InputStream 반환 */
    private InputStream getNetworkConnection(HttpURLConnection conn) throws Exception {
        conn.setReadTimeout(3000);
        conn.setConnectTimeout(3000);
        conn.setRequestMethod("GET");
        conn.setDoInput(true);

        if (conn.getResponseCode() != HttpsURLConnection.HTTP_OK) {
            throw new IOException("HTTP error code: " + conn.getResponseCode());
        }

        return conn.getInputStream();
    }


    /* InputStream을 전달받아 문자열로 변환 후 반환 */
    protected String readStreamToString(InputStream stream){
        StringBuilder result = new StringBuilder();

        try {
            InputStreamReader inputStreamReader = new InputStreamReader(stream);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

            String readLine = bufferedReader.readLine();

            while (readLine != null) {
                result.append(readLine + "\n");
                readLine = bufferedReader.readLine();
            }

            bufferedReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return result.toString();
    }


    /* InputStream을 전달받아 비트맵으로 변환 후 반환 */
    protected Bitmap readStreamToBitmap(InputStream stream) {
        return BitmapFactory.decodeStream(stream);
    }

}
