package com.example.ultimaoportunidad.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.ultimaoportunidad.R;
import com.example.ultimaoportunidad.adapters.ExpenseAdapter;
import com.example.ultimaoportunidad.database.DatabaseHelper;
import com.example.ultimaoportunidad.models.Expense;

import java.util.List;

public class GroupDetailsActivity extends AppCompatActivity {

    TextView tvGroupName, tvGroupDescription, tvGroupBalance;
    ListView lvExpenses;
    Button btnAddExpense, btnSettleDebts, btnViewMembers;
    DatabaseHelper db;
    int groupId;
    ExpenseAdapter adapter;
    List<Expense> expenseList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_details);

        // Inicializar vistas
        tvGroupName = findViewById(R.id.tvGroupName);
        tvGroupDescription = findViewById(R.id.tvGroupDescription);
        tvGroupBalance = findViewById(R.id.group_total_expenses);
        lvExpenses = findViewById(R.id.expenses_list);
        btnAddExpense = findViewById(R.id.btnAddExpense);
        btnSettleDebts = findViewById(R.id.btnSettleDebts);
        btnViewMembers = findViewById(R.id.btnViewMembers);

        db = new DatabaseHelper(this);

        // Obtener datos del intent
        groupId = getIntent().getIntExtra("GROUP_ID", -1);
        String groupName = getIntent().getStringExtra("GROUP_NAME");
        String groupDescription = getIntent().getStringExtra("GROUP_DESCRIPTION");

        // Mostrar los detalles del grupo
        tvGroupName.setText(groupName);
        tvGroupDescription.setText(groupDescription);

        // Cargar el balance del grupo
        loadGroupBalance();

        // Cargar los gastos del grupo
        loadExpenses();

        // Botón para añadir un nuevo gasto
        btnAddExpense.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(GroupDetailsActivity.this, AddExpenseActivity.class);
                intent.putExtra("GROUP_ID", groupId);
                startActivity(intent);
            }
        });

        // Botón para saldar deudas
        btnSettleDebts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(GroupDetailsActivity.this, SettleDebtsActivity.class);
                intent.putExtra("GROUP_ID", groupId);
                startActivity(intent);
            }
        });

        // Botón para ver los miembros del grupo
        btnViewMembers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(GroupDetailsActivity.this, GroupMembersActivity.class);
                intent.putExtra("GROUP_ID", groupId);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Recargar el balance y los gastos al volver de otra actividad
        loadGroupBalance();
        loadExpenses();
    }

    private void loadGroupBalance() {
        float balance = db.getGroupBalance(groupId);
        tvGroupBalance.setText(String.format("Balance: $%.2f", balance));
    }

    private void loadExpenses() {
        expenseList = db.getExpensesForGroup(groupId);
        adapter = new ExpenseAdapter(this, expenseList);
        lvExpenses.setAdapter(adapter);
    }
}
