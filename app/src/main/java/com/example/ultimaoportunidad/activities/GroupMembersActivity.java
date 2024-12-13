package com.example.ultimaoportunidad.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.ultimaoportunidad.R;
import com.example.ultimaoportunidad.adapters.MemberAdapter;
import com.example.ultimaoportunidad.database.DatabaseHelper;
import com.example.ultimaoportunidad.models.Member;

import java.util.List;

public class GroupMembersActivity extends AppCompatActivity {

    ListView lvMembers;
    Button btnAddMember;
    DatabaseHelper db;
    List<Member> memberList;
    MemberAdapter adapter;
    int groupId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_users);

        lvMembers = findViewById(R.id.lvMembers);
        btnAddMember = findViewById(R.id.add_user_button);
        db = new DatabaseHelper(this);

        groupId = getIntent().getIntExtra("GROUP_ID", -1);

        loadMembers();

        btnAddMember.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Acción para añadir un nuevo miembro (se puede implementar una nueva actividad)
                Toast.makeText(GroupMembersActivity.this, "Función de añadir miembro pendiente de implementar", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void loadMembers() {
        memberList = db.getMembersForGroup(groupId);
        adapter = new MemberAdapter(this, memberList);
        lvMembers.setAdapter(adapter);
    }
}
