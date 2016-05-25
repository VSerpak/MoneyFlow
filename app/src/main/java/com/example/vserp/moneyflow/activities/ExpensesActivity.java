package com.example.vserp.moneyflow.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.vserp.moneyflow.R;
import com.example.vserp.moneyflow.adapters.ExpensesAdapter;
import com.example.vserp.moneyflow.utils.Prefs;

public class ExpensesActivity extends AppCompatActivity {

    private RecyclerView rvExpenses;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expenses);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        rvExpenses = (RecyclerView) findViewById(R.id.rvExpenses);

        rvExpenses.setLayoutManager(new LinearLayoutManager(this));
        rvExpenses.setAdapter(new ExpensesAdapter(this,getContentResolver().query(Prefs.URI_ALL_EXPENSES,null,null,null,"desc")));
    }
}
