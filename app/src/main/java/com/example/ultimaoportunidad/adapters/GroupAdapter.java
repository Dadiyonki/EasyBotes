package com.example.ultimaoportunidad.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.widget.ArrayAdapter;

import com.example.ultimaoportunidad.R;
import com.example.ultimaoportunidad.activities.GroupMembersActivity;
import com.example.ultimaoportunidad.models.Group;

import java.util.List;

public class GroupAdapter extends ArrayAdapter<Group> {

    public GroupAdapter(Context context, List<Group> groups) {
        super(context, 0, groups);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_group, parent, false);
        }

        Group group = getItem(position);

        TextView tvGroupName = convertView.findViewById(R.id.tvGroupName);
        TextView tvGroupDescription = convertView.findViewById(R.id.tvGroupDescription);
        Button btnViewMembers = convertView.findViewById(R.id.btnViewMembers);

        tvGroupName.setText(group.getName());
        tvGroupDescription.setText(group.getDescription());

        // Manejar clic en el botón "Ver Miembros"
        btnViewMembers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), GroupMembersActivity.class);
                intent.putExtra("GROUP_ID", group.getId());
                getContext().startActivity(intent);
            }
        });

        return convertView;
    }
}
