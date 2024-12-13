package com.example.ultimaoportunidad.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.widget.ArrayAdapter;

import com.example.ultimaoportunidad.R;
import com.example.ultimaoportunidad.models.Expense;

import java.util.List;

public class ExpenseAdapter extends ArrayAdapter<Expense> {

    public ExpenseAdapter(Context context, List<Expense> expenses) {
        super(context, 0, expenses);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_expense, parent, false);
        }

        Expense expense = getItem(position);

        TextView tvExpenseName = convertView.findViewById(R.id.tvExpenseName);
        TextView tvExpenseAmount = convertView.findViewById(R.id.tvExpenseAmount);

        tvExpenseName.setText(expense.getName());
        tvExpenseAmount.setText(String.format("$%.2f", expense.getAmount()));

        return convertView;
    }
}
