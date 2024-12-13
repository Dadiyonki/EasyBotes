package com.example.ultimaoportunidad.activities;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.ultimaoportunidad.R;
import com.example.ultimaoportunidad.database.DatabaseHelper;

public class SettleDebtsActivity extends AppCompatActivity {

    TextView tvDebtsSummary;
    Button btnSettleDebts;
    DatabaseHelper db;
    int groupId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_debt_details);

        // Inicializar vistas
        tvDebtsSummary = findViewById(R.id.debt_title);
        btnSettleDebts = findViewById(R.id.btnSettleDebts);
        db = new DatabaseHelper(this);

        // Obtener el ID del grupo desde el intent
        groupId = getIntent().getIntExtra("GROUP_ID", -1);

        // Cargar deudas personales
        loadDebts();

        // Botón para saldar las deudas
        btnSettleDebts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                settleDebts();
            }
        });
    }

    private void loadDebts() {
        // Aquí simulamos la carga de deudas personales desde la base de datos
        String debtsSummary = db.getDebtsSummaryForGroup(groupId);
        tvDebtsSummary.setText(debtsSummary);
    }

    private void settleDebts() {
        // Lógica para saldar las deudas en la base de datos
        boolean result = db.settleDebtsForGroup(groupId);

        if (result) {
            Toast.makeText(SettleDebtsActivity.this, "Deudas saldadas exitosamente", Toast.LENGTH_SHORT).show();
            finish(); // Cerrar la actividad y volver a GroupDetailsActivity
        } else {
            Toast.makeText(SettleDebtsActivity.this, "Error al saldar las deudas", Toast.LENGTH_SHORT).show();
        }
    }
}
