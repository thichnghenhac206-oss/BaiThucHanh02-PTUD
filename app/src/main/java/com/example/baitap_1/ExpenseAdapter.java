package com.example.baitap_1;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.util.List;

public class ExpenseAdapter extends BaseAdapter {
    private Context context;
    private List<Expense> expenseList;
    private LayoutInflater inflater;
    private DecimalFormat decimalFormat;

    public ExpenseAdapter(Context context, List<Expense> expenseList) {
        this.context = context;
        this.expenseList = expenseList;
        this.inflater = LayoutInflater.from(context);
        this.decimalFormat = new DecimalFormat("#,###");
    }

    @Override
    public int getCount() {
        return expenseList.size();
    }

    @Override
    public Object getItem(int position) {
        return expenseList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.item_expense, parent, false);
        }

        TextView tvTitle = convertView.findViewById(R.id.tvTitle);
        TextView tvAmount = convertView.findViewById(R.id.tvAmount);
        TextView tvCategory = convertView.findViewById(R.id.tvCategory);
        TextView tvDate = convertView.findViewById(R.id.tvDate);

        Expense expense = expenseList.get(position);

        tvTitle.setText(expense.getTitle());
        tvAmount.setText(decimalFormat.format(expense.getAmount()) + " đ");
        tvCategory.setText(expense.getCategory());
        tvDate.setText(expense.getDate());

        return convertView;
    }
}