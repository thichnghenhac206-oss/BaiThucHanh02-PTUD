package com.example.baitap_1;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class AddExpenseActivity extends AppCompatActivity {

    private EditText edtTitle, edtAmount, edtDate, edtNote;
    private Spinner spinnerCategory;
    private Button btnSave;
    private ImageView btnBackIcon;
    private Calendar calendar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_expense);

        // Ánh xạ các view
        edtTitle = findViewById(R.id.edtTitle);
        edtAmount = findViewById(R.id.edtAmount);
        edtDate = findViewById(R.id.edtDate);
        edtNote = findViewById(R.id.edtNote);
        spinnerCategory = findViewById(R.id.spinnerCategory);
        btnSave = findViewById(R.id.btnSave);
        btnBackIcon = findViewById(R.id.btnBackIcon);

        btnBackIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        String[] categories = {"Ăn uống", "Đi lại", "Học tập", "Giải trí", "Khác"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, categories);
        spinnerCategory.setAdapter(adapter);

        calendar = Calendar.getInstance();
        updateDateInView();

        edtDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                int day = calendar.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        AddExpenseActivity.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int selectedYear, int selectedMonth, int selectedDay) {
                                calendar.set(Calendar.YEAR, selectedYear);
                                calendar.set(Calendar.MONTH, selectedMonth);
                                calendar.set(Calendar.DAY_OF_MONTH, selectedDay);
                                updateDateInView();
                            }
                        },
                        year, month, day
                );
                datePickerDialog.show();
            }
        });

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String title = edtTitle.getText().toString().trim();
                String amountStr = edtAmount.getText().toString().trim();
                String category = spinnerCategory.getSelectedItem().toString();
                String date = edtDate.getText().toString().trim();
                String note = edtNote.getText().toString().trim();

                if (title.isEmpty()) {
                    edtTitle.setError("Tên khoản chi không được để trống");
                    return;
                }

                if (amountStr.isEmpty()) {
                    edtAmount.setError("Vui lòng nhập số tiền");
                    return;
                }

                double amount;
                try {
                    amount = Double.parseDouble(amountStr);
                    if (amount <= 0) {
                        edtAmount.setError("Số tiền phải lớn hơn 0");
                        return;
                    }
                } catch (NumberFormatException e) {
                    edtAmount.setError("Số tiền không hợp lệ");
                    return;
                }

                if (date.isEmpty()) {
                    updateDateInView();
                    date = edtDate.getText().toString().trim();
                }

                Expense newExpense = new Expense(title, amount, category, date, note);

                Intent resultIntent = new Intent();
                resultIntent.putExtra("new_expense", newExpense);
                setResult(RESULT_OK, resultIntent);
                finish();
            }
        });
    }

    private void updateDateInView() {
        String myFormat = "dd/MM/yyyy";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.getDefault());
        edtDate.setText(sdf.format(calendar.getTime()));
    }
}