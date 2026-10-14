package com.example.baitap_1;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private TextView tvTotalAmount;
    private ListView lvExpenses;
    private Button btnAddExpense;

    public static ArrayList<Expense> expenseList = new ArrayList<>();
    private ExpenseAdapter adapter;
    private DecimalFormat decimalFormat;

    private static final int REQUEST_CODE_ADD = 100;
    private static final int REQUEST_CODE_DETAIL = 200;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvTotalAmount = findViewById(R.id.tvTotalAmount);
        lvExpenses = findViewById(R.id.lvExpenses);
        btnAddExpense = findViewById(R.id.btnAddExpense);
        decimalFormat = new DecimalFormat("#,###");

        if (expenseList.isEmpty()) {
            expenseList.add(new Expense("Ăn sáng", 30000, "Ăn uống", "12/10/2026", "Phở bò"));
            expenseList.add(new Expense("Đổ xăng", 50000, "Đi lại", "12/10/2026", "Xăng xe máy"));
            expenseList.add(new Expense("Mua sách", 120000, "Học tập", "13/10/2026", "Sách Lập trình Android"));
        }

        adapter = new ExpenseAdapter(this, expenseList);
        lvExpenses.setAdapter(adapter);
        updateTotalAmount();

        btnAddExpense.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AddExpenseActivity.class);
                startActivityForResult(intent, REQUEST_CODE_ADD);
            }
        });

        lvExpenses.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Expense expense = expenseList.get(position);
                Intent intent = new Intent(MainActivity.this, ExpenseDetailActivity.class);
                intent.putExtra("expense", expense);
                intent.putExtra("position", position);
                startActivityForResult(intent, REQUEST_CODE_DETAIL);
            }
        });
    }

    private void updateTotalAmount() {
        double total = 0;
        for (Expense e : expenseList) {
            total += e.getAmount();
        }
        tvTotalAmount.setText(decimalFormat.format(total) + " đ");
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && data != null) {
            if (requestCode == REQUEST_CODE_ADD) {
                Expense newExpense = (Expense) data.getSerializableExtra("new_expense");
                if (newExpense != null) {
                    expenseList.add(newExpense);
                    adapter.notifyDataSetChanged();
                    updateTotalAmount();
                }
            } else if (requestCode == REQUEST_CODE_DETAIL) {
                boolean isDeleted = data.getBooleanExtra("is_deleted", false);
                int pos = data.getIntExtra("position", -1);
                if (isDeleted && pos != -1 && pos < expenseList.size()) {
                    expenseList.remove(pos);
                    adapter.notifyDataSetChanged();
                    updateTotalAmount();
                }
            }
        }
    }
}