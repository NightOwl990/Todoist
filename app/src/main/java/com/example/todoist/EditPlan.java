package com.example.todoist;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class EditPlan extends AppCompatActivity {

    boolean isupdate;
    int idplan;
    EditText editUpdateName, edtUpdateContent;
    TextView tvUpdateDate;
    Plan plan;
    Database database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_plan);

        database = new Database(this);

        Intent intent = getIntent();
        isupdate = intent.getBooleanExtra("isupdate", false);
        if (isupdate) {
            idplan = intent.getIntExtra("idplan", 0);
            plan = database.getPlanId(idplan);
            findViewById(R.id.btn_xoa).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    database.deletePlanByID(idplan);
                    finish();
                }
            });
        } else {
            plan = new Plan(0, "","", "Ngày: _ / _ / _ /");
            findViewById(R.id.btn_xoa).setVisibility(View.GONE);
            ((Button) findViewById(R.id.btn_luu)).setText("Tạo nhật ký mới");
        }
        editUpdateName = findViewById(R.id.edt_updateName);
        edtUpdateContent = findViewById(R.id.edt_updateContent);
        tvUpdateDate = findViewById(R.id.tv_updateDate);
        editUpdateName.setText(plan.getName() + "");
        edtUpdateContent.setText(plan.getContent() + "");
        tvUpdateDate.setText(plan.getDate() + "");

        tvUpdateDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ChonNgay();
            }
        });

        findViewById(R.id.btn_luu).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                plan.name = editUpdateName.getText().toString();
                plan.content = edtUpdateContent.getText().toString();
                plan.date = tvUpdateDate.getText().toString();

                if (isupdate) {
                    database.updatePlan(plan);
                } else {
                    database.insertPlan(plan);
                }
                finish();
            }
        });
    }

    private void ChonNgay(){
        Calendar calendar = Calendar.getInstance();
        int ngay = calendar.get(Calendar.DATE);
        int thang = calendar.get(Calendar.MONTH);
        int nam = calendar.get(Calendar.YEAR);
        DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                // i:năm - i1:tháng - i2:ngày
                calendar.set(i, i1, i2);
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/YYYY");
                tvUpdateDate.setText(simpleDateFormat.format(calendar.getTime()));
            }
        }, nam, thang, ngay);
        datePickerDialog.show();
    }
}