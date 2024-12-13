package com.example.ultimaoportunidad.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

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
        btnAddGroup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(GroupsActivity.this, AddGroupActivity.class);
                startActivity(intent);
            }
        });

        // Eliminar un grupo mediante long click
        lvGroups.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                Group selectedGroup = groupList.get(position);
                db.deleteGroup(selectedGroup.getId());
                Toast.makeText(GroupsActivity.this, "Grupo eliminado", Toast.LENGTH_SHORT).show();
                loadGroups(username); // Recargar la lista después de eliminar
                return true;
            }
        });

        lvGroups.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Group selectedGroup = groupList.get(position);
        
                Intent intent = new Intent(GroupsActivity.this, GroupDetailsActivity.class);
                intent.putExtra("GROUP_ID", selectedGroup.getId());
                intent.putExtra("GROUP_NAME", selectedGroup.getName());
                intent.putExtra("GROUP_DESCRIPTION", selectedGroup.getDescription());
        
                startActivity(intent);
            }
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
        // Recargar los grupos al volver de AddGroupActivity
        loadGroups(session.getUsername());
    }
}
