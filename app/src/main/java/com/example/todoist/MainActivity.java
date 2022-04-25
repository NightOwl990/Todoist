package com.example.todoist;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements IClickItem{

    public static final int RESULT_PRODUCT_ACTIVITY = 1;
    private Button btnThem;
    private EditText edtSearch;
    private RecyclerView rcvPlan;
    private PlanAdapter adapter;
    private Database database;
    private ArrayList<Plan> listPlan;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Init();
        Event();
        getListPlan();

        rcvPlan.setAdapter(adapter);

    }

    private void Event() {

        btnThem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, EditPlan.class);
                intent.putExtra("isupdate", false);
                startActivityForResult(intent, RESULT_PRODUCT_ACTIVITY);
            }
        });
        edtSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                search(charSequence);

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    private void Init() {

        btnThem = findViewById(R.id.btn_them);
        edtSearch = findViewById(R.id.edt_search);
        rcvPlan = findViewById(R.id.rcv_plan);

        listPlan = new ArrayList<>();
        database = new Database(this);
        adapter = new PlanAdapter(listPlan, this);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        rcvPlan.setLayoutManager(linearLayoutManager);

    }

    private void getListPlan() {
        listPlan.clear();
        listPlan.addAll(database.getAllPlan());
    }

    void search(CharSequence str) {
        try {
            List<Plan> listTemp = new ArrayList<>();
            for (Plan item : listPlan) {
                if (item.getName().toLowerCase().contains(str.toString().toLowerCase())) {
                    listTemp.add(item);
                }
            }
            adapter = new PlanAdapter(listTemp, getApplicationContext(), R.layout.dong_plan);
            rcvPlan.setAdapter(adapter);
        } catch (Exception e) {

        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case RESULT_PRODUCT_ACTIVITY:
                getListPlan();
                adapter.notifyDataSetChanged();
                break;
            default:
                break;
        }
    }

    @Override
    public void onClickItemPlan(int position) {
        Plan plan = listPlan.get(position);
        Intent intent = new Intent(getApplicationContext(), EditPlan.class);
        intent.putExtra("isupdate", true);
        intent.putExtra("idplan", plan.id);
        startActivityForResult(intent, RESULT_PRODUCT_ACTIVITY);
    }
}