package com.example.ultimaoportunidad.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.ultimaoportunidad.R;
import com.example.ultimaoportunidad.adapters.GroupAdapter;
import com.example.ultimaoportunidad.database.DatabaseHelper;
import com.example.ultimaoportunidad.models.Group;
import com.example.ultimaoportunidad.utils.SessionManager;

import java.util.List;

public class GroupsActivity extends AppCompatActivity {

    ListView lvGroups;
    TextView tvNoGroups;
    Button btnAddGroup;
    DatabaseHelper db;
    SessionManager session;
    List<Group> groupList;
    GroupAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_groups);

        lvGroups = findViewById(R.id.lvGroups);
        tvNoGroups = findViewById(R.id.tvNoGroups);
        btnAddGroup = findViewById(R.id.add_group_button);

        db = new DatabaseHelper(this);
        session = new SessionManager(this);

        String username = session.getUsername();
        loadGroups(username);

        // Botón para añadir un nuevo grupo
        btnAddGroup.setOnClickListener(v -> {
            Intent intent = new Intent(GroupsActivity.this, AddGroupActivity.class);
            startActivity(intent);
        });
    }

    private void loadGroups(String username) {
        groupList = db.getGroupsForUser(username);

        if (groupList.isEmpty()) {
            tvNoGroups.setVisibility(View.VISIBLE);
            lvGroups.setVisibility(View.GONE);
        } else {
            tvNoGroups.setVisibility(View.GONE);
            lvGroups.setVisibility(View.VISIBLE);

            adapter = new GroupAdapter(this, groupList);
            lvGroups.setAdapter(adapter);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadGroups(session.getUsername());
    }
}

