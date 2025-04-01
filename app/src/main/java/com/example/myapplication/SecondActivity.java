package com.example.myapplication;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class  SecondActivity extends AppCompatActivity {
    private TextView txtInfo;
    private Button btnBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        txtInfo = findViewById(R.id.txtInfo);
        btnBack = findViewById(R.id.btnBack);


        // Nhận dữ liệu từ Intent
        Intent intent = getIntent();
        String name = intent.getStringExtra("name");
        String mssv = intent.getStringExtra("mssv");
        String className = intent.getStringExtra("class");
        String phone = intent.getStringExtra("phone"); // Thêm số điện thoại
        String year = intent.getStringExtra("year");
        String major = intent.getStringExtra("major");
        String plan = intent.getStringExtra("plan");

        if (major == null || major.isEmpty()) {
            major = "Chưa chọn";
        }

        // Hiển thị dữ liệu
        String info = "🔹 Họ và Tên: " + name + "\n"
                + "🔹 MSSV: " + mssv + "\n"
                + "🔹 Lớp: " + className + "\n"
                + "🔹 Số điện thoại: " + phone + "\n\n"
                + "📌 Năm học: " + year + "\n\n"
                + "📚 Chuyên ngành: " + major + "\n\n"
                + "🎯 Kế hoạch phát triển bản thân:\n" + plan;

        txtInfo.setText(info);

        btnBack.setOnClickListener(v -> finish());

    }

}
