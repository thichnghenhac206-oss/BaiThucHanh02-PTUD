package com.example.baitap_1;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.text.DecimalFormat;

public class ExpenseDetailActivity extends AppCompatActivity {

    private TextView tvTitle, tvAmount, tvCategory, tvDate, tvNote;
    private Button btnBack, btnDelete;
    private int position = -1;
    private DecimalFormat decimalFormat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expense_detail);

        tvTitle = findViewById(R.id.tvDetailTitle);
        tvAmount = findViewById(R.id.tvDetailAmount);
        tvCategory = findViewById(R.id.tvDetailCategory);
        tvDate = findViewById(R.id.tvDetailDate);
        tvNote = findViewById(R.id.tvDetailNote);
        btnBack = findViewById(R.id.btnBack);
        btnDelete = findViewById(R.id.btnDelete);
        decimalFormat = new DecimalFormat("#,###");

        Intent intent = getIntent();
        if (intent != null) {
            Expense expense = (Expense) intent.getSerializableExtra("expense");
            position = intent.getIntExtra("position", -1);

            if (expense != null) {
                tvTitle.setText(expense.getTitle());
                tvAmount.setText(decimalFormat.format(expense.getAmount()) + " đ");
                tvCategory.setText(expense.getCategory());
                tvDate.setText(expense.getDate());
                tvNote.setText(expense.getNote() != null && !expense.getNote().isEmpty() ? expense.getNote() : "(Không có ghi chú)");
            }
        }

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent resultIntent = new Intent();
                resultIntent.putExtra("is_deleted", true);
                resultIntent.putExtra("position", position);
                setResult(RESULT_OK, resultIntent);
                finish();
            }
        });
    }
}