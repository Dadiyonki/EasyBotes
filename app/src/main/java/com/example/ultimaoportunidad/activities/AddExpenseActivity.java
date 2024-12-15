package com.example.ultimaoportunidad.activities;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.ultimaoportunidad.R;
import com.example.ultimaoportunidad.database.DatabaseHelper;
import com.example.ultimaoportunidad.utils.SessionManager;

public class AddExpenseActivity extends AppCompatActivity {

    EditText etExpenseName, etExpenseAmount, etExpenseDescription;
    Button btnAddExpense;
    DatabaseHelper db;
    int groupId;
    SessionManager session;
    String username = session.getUsername();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_expense);

        db = new DatabaseHelper(this);

        etExpenseName = findViewById(R.id.etExpenseName);
        etExpenseAmount = findViewById(R.id.etExpenseAmount);
        etExpenseDescription = findViewById(R.id.etExpenseDescription);
        btnAddExpense = findViewById(R.id.btnAddExpense);

        // Obtener el ID del grupo desde el intent
        groupId = getIntent().getIntExtra("GROUP_ID", -1);

        session = new SessionManager(this);
        username = session.getUsername();

        btnAddExpense.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String expenseName = etExpenseName.getText().toString();
                String expenseAmountStr = etExpenseAmount.getText().toString();
                String expenseDescription = etExpenseDescription.getText().toString();

                if (expenseName.isEmpty() || expenseAmountStr.isEmpty()) {
                    Toast.makeText(AddExpenseActivity.this, "Por favor, completa todos los campos obligatorios", Toast.LENGTH_SHORT).show();
                } else {
                    float expenseAmount = Float.parseFloat(expenseAmountStr);
                    boolean isInserted = db.insertExpense(expenseName, expenseAmount, expenseDescription, groupId, username);

                    if (isInserted) {
                        Toast.makeText(AddExpenseActivity.this, "Gasto añadido exitosamente", Toast.LENGTH_SHORT).show();
                        finish(); // Volver a GroupDetailsActivity
                    } else {
                        Toast.makeText(AddExpenseActivity.this, "Error al añadir el gasto", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }
}
