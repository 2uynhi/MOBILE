package com.lehuuquynhnhi.k234111e_mobile;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.text.TextUtils;
import android.text.method.ScrollingMovementMethod;
import android.view.Gravity;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class NewsGoogleActivity extends AppCompatActivity {
    private EditText edtNewsText;
    private TextView txtGoogleResult;
    private Button btnSendGoogle;
    private final ExecutorService executorService = Executors.newSingleThreadExecutor();

    private final ActivityResultLauncher<String> audioPermissionLauncher =
            registerForActivityResult(new ActivityResultContracts.RequestPermission(), isGranted -> {
                if (isGranted) {
                    startSpeechRecognition();
                } else {
                    Toast.makeText(this, "Cần quyền micro để nhận dạng giọng nói", Toast.LENGTH_SHORT).show();
                }
            });

    private final ActivityResultLauncher<Intent> speechLauncher =
            registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
                if (result.getResultCode() != RESULT_OK || result.getData() == null) {
                    return;
                }

                ArrayList<String> matches = result.getData()
                        .getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                if (matches == null || matches.isEmpty()) {
                    return;
                }

                edtNewsText.setText(matches.get(0));
                edtNewsText.setSelection(edtNewsText.length());
            });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_google);

        edtNewsText = findViewById(R.id.edtNewsText);
        txtGoogleResult = findViewById(R.id.txtGoogleResult);
        txtGoogleResult.setMovementMethod(new ScrollingMovementMethod());
        Button btnRecord = findViewById(R.id.btnRecord);
        btnSendGoogle = findViewById(R.id.btnSendGoogle);

        btnRecord.setOnClickListener(view -> requestSpeechRecognition());
        btnSendGoogle.setOnClickListener(view -> sendTextToGoogle());
    }

    private void requestSpeechRecognition() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO)
                == PackageManager.PERMISSION_GRANTED) {
            startSpeechRecognition();
            return;
        }

        audioPermissionLauncher.launch(Manifest.permission.RECORD_AUDIO);
    }

    private void startSpeechRecognition() {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, "vi-VN");
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Nói nội dung tin tức X");

        try {
            speechLauncher.launch(intent);
        } catch (Exception exception) {
            Toast.makeText(this, "Thiết bị chưa hỗ trợ nhận dạng giọng nói", Toast.LENGTH_SHORT).show();
        }
    }

    private void sendTextToGoogle() {
        String query = edtNewsText.getText().toString().trim();
        if (TextUtils.isEmpty(query)) {
            edtNewsText.setError("Nhập hoặc đọc nội dung trước khi gửi");
            return;
        }

        String apiKey = getString(R.string.google_api_key);
        String searchEngineId = getString(R.string.google_search_engine_id);
        if (apiKey.startsWith("YOUR_") || searchEngineId.startsWith("YOUR_")) {
            txtGoogleResult.setGravity(Gravity.START | Gravity.TOP);
            txtGoogleResult.setText("Chưa cấu hình Google API.\n\nHãy thay google_api_key và google_search_engine_id trong strings.xml bằng key của Google Cloud / Programmable Search.");
            return;
        }

        btnSendGoogle.setEnabled(false);
        txtGoogleResult.setGravity(Gravity.START | Gravity.TOP);
        txtGoogleResult.setText("Đang gửi Google...");

        executorService.execute(() -> {
            try {
                String response = searchGoogle(query, apiKey, searchEngineId);
                runOnUiThread(() -> txtGoogleResult.setText(response));
            } catch (Exception exception) {
                runOnUiThread(() -> txtGoogleResult.setText("Lỗi khi gửi Google:\n" + exception.getMessage()));
            } finally {
                runOnUiThread(() -> btnSendGoogle.setEnabled(true));
            }
        });
    }

    private String searchGoogle(String query, String apiKey, String searchEngineId) throws Exception {
        String encodedQuery = URLEncoder.encode(query, StandardCharsets.UTF_8.name());
        String requestUrl = "https://www.googleapis.com/customsearch/v1"
                + "?key=" + apiKey
                + "&cx=" + searchEngineId
                + "&q=" + encodedQuery;

        HttpURLConnection connection = (HttpURLConnection) new URL(requestUrl).openConnection();
        connection.setRequestMethod("GET");
        connection.setConnectTimeout(15000);
        connection.setReadTimeout(15000);

        int responseCode = connection.getResponseCode();
        String responseBody = readStream(responseCode >= 200 && responseCode < 300
                ? connection.getInputStream()
                : connection.getErrorStream());
        connection.disconnect();

        if (responseCode < 200 || responseCode >= 300) {
            throw new IOException("HTTP " + responseCode + "\n" + responseBody);
        }

        return formatGoogleResponse(responseBody);
    }

    private String readStream(InputStream inputStream) throws IOException {
        if (inputStream == null) {
            return "";
        }

        StringBuilder builder = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
            String line;
            while ((line = reader.readLine()) != null) {
                builder.append(line);
            }
        }
        return builder.toString();
    }

    private String formatGoogleResponse(String responseBody) throws Exception {
        JSONObject root = new JSONObject(responseBody);
        JSONArray items = root.optJSONArray("items");
        if (items == null || items.length() == 0) {
            return "Google không trả về kết quả phù hợp.";
        }

        StringBuilder builder = new StringBuilder();
        int limit = Math.min(items.length(), 5);
        for (int index = 0; index < limit; index++) {
            JSONObject item = items.getJSONObject(index);
            builder.append(index + 1)
                    .append(". ")
                    .append(item.optString("title", "Không có tiêu đề"))
                    .append("\n")
                    .append(item.optString("snippet", ""))
                    .append("\n")
                    .append(item.optString("link", ""))
                    .append("\n\n");
        }
        return builder.toString().trim();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        executorService.shutdownNow();
    }
}
