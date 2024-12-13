package com.example.ultimaoportunidad.activities;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.ultimaoportunidad.R;
import com.example.ultimaoportunidad.database.DatabaseHelper;

public class AddGroupActivity extends AppCompatActivity {

    EditText etGroupName, etGroupDescription;
    Button btnAddGroup;
    DatabaseHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_group);

        db = new DatabaseHelper(this);

        etGroupName = findViewById(R.id.group_name_input);
        etGroupDescription = findViewById(R.id.group_description_input);
        btnAddGroup = findViewById(R.id.save_group_button);

        btnAddGroup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String groupName = etGroupName.getText().toString();
                String groupDescription = etGroupDescription.getText().toString();

                if (groupName.isEmpty()) {
                    Toast.makeText(AddGroupActivity.this, "Por favor, introduce un nombre para el grupo", Toast.LENGTH_SHORT).show();
                } else {
                    boolean isInserted = db.insertGroup(groupName, groupDescription);

                    if (isInserted) {
                        Toast.makeText(AddGroupActivity.this, "Grupo añadido exitosamente", Toast.LENGTH_SHORT).show();
                        finish(); // Volver a GroupsActivity
                    } else {
                        Toast.makeText(AddGroupActivity.this, "Error al añadir el grupo", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }
}
