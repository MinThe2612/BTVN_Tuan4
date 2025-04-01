package com.example.myapplication;

import static android.provider.MediaStore.ACTION_IMAGE_CAPTURE;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

public class FirstActivity extends AppCompatActivity {
    private EditText edtName, edtMSSV, edtClass, edtPhone, edtPlan;
    private RadioGroup rgYear;
    private CheckBox cbMajor1, cbMajor2, cbMajor3;
    private Button btnSubmit, btnsms, btncall, btncap;
    private ImageView imghinh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first);

        edtName = findViewById(R.id.edtName);
        edtMSSV = findViewById(R.id.edtMSSV);
        edtClass = findViewById(R.id.edtClass);
        edtPhone = findViewById(R.id.edtPhone);
        edtPlan = findViewById(R.id.edtPlan);
        rgYear = findViewById(R.id.rgYear);
        cbMajor1 = findViewById(R.id.cbMajor1);
        cbMajor2 = findViewById(R.id.cbMajor2);
        cbMajor3 = findViewById(R.id.cbMajor3);
        btnSubmit = findViewById(R.id.btnSubmit);
        btnsms = findViewById(R.id.btnsms);
        btncall = findViewById(R.id.btncall);
        btncap = findViewById(R.id.btncap);
        imghinh = findViewById(R.id.imghinh);


        btnSubmit.setOnClickListener(v -> {
            if (validateInput()) {
                sendData();
            }
        });
        btnsms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent smsintent=new Intent(Intent.ACTION_SENDTO,Uri.parse("smsto:"+edtPhone.getText().toString()));
                startActivity(smsintent);
            }
        });
        btncall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent callintent=new Intent(Intent.ACTION_CALL,Uri.parse("tel:"+edtPhone.getText().toString()));
                if (ActivityCompat.checkSelfPermission(FirstActivity.this, android.Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(FirstActivity.this, new
                            String[]{android.Manifest.permission.CALL_PHONE},1);
                    return;
                }
                startActivity(callintent);
            }
        });
        btncap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent pic_intent = new Intent(ACTION_IMAGE_CAPTURE);
                if (ActivityCompat.checkSelfPermission(FirstActivity.this,
                        android.Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(FirstActivity.this, new
                            String[]{android.Manifest.permission.CAMERA}, 1);
                    return;
                }
                startActivityForResult(pic_intent, 99);
            }

        });
    }

    private boolean validateInput() {
        String name = edtName.getText().toString().trim();
        String mssv = edtMSSV.getText().toString().trim();
        String phone = edtPhone.getText().toString().trim();

        if (TextUtils.isEmpty(name) || !name.matches("[a-zA-ZÀ-Ỹà-ỹ\\s]+")) {
            edtName.setError("Họ tên chỉ được chứa chữ cái!");
            return false;
        }

        if (TextUtils.isEmpty(mssv) || !mssv.matches("\\d+")) {
            edtMSSV.setError("MSSV chỉ được nhập số!");
            return false;
        }

        return true;
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==99 && resultCode== Activity.RESULT_OK)
        {
            Bitmap picture =(Bitmap) data.getExtras().get("data");
            imghinh.setImageBitmap(picture);
            Intent intent = new Intent(FirstActivity.this, SecondActivity.class);
            intent.putExtra("capturedImage", picture);
            startActivity(intent);
        }
    }


    private void sendData() {
        // Lấy dữ liệu
        String name = edtName.getText().toString().trim();
        String mssv = edtMSSV.getText().toString().trim();
        String className = edtClass.getText().toString().trim();
        String phone = edtPhone.getText().toString().trim();
        String plan = edtPlan.getText().toString().trim();

        int selectedYearId = rgYear.getCheckedRadioButtonId();
        RadioButton selectedYear = findViewById(selectedYearId);
        String year = (selectedYear != null) ? selectedYear.getText().toString() : "Chưa chọn";

        StringBuilder majorBuilder = new StringBuilder();
        if (cbMajor1.isChecked()) majorBuilder.append("Máy tính HTN, ");
        if (cbMajor2.isChecked()) majorBuilder.append("Điện tử, ");
        if (cbMajor3.isChecked()) majorBuilder.append("Viễn thông, ");

        String major = majorBuilder.toString();
        if (!major.isEmpty()) {
            major = major.substring(0, major.length() - 2);
        } else {
            major = "Chưa chọn";
        }

        Intent intent = new Intent(FirstActivity.this, SecondActivity.class);
        intent.putExtra("name", name);
        intent.putExtra("mssv", mssv);
        intent.putExtra("class", className);
        intent.putExtra("phone", phone);
        intent.putExtra("year", year);
        intent.putExtra("major", major);
        intent.putExtra("plan", plan);
        startActivity(intent);
    }

}
