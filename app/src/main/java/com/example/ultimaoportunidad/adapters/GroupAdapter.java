package com.example.ultimaoportunidad.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.example.ultimaoportunidad.R;
import com.example.ultimaoportunidad.activities.GroupDetailsActivity;
import com.example.ultimaoportunidad.activities.GroupMembersActivity;
import com.example.ultimaoportunidad.models.Group;

import java.util.List;

public class GroupAdapter extends BaseAdapter {

    private Context context;
    private List<Group> groupList;

    public GroupAdapter(Context context, List<Group> groupList) {
        this.context = context;
        this.groupList = groupList;
    }

    @Override
    public int getCount() {
        return groupList.size();
    }

    @Override
    public Object getItem(int position) {
        return groupList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_group, parent, false);
        }

        TextView tvGroupName = convertView.findViewById(R.id.tvGroupName);
        TextView tvGroupDescription = convertView.findViewById(R.id.tvGroupDescription);
        Button btnViewDetails = convertView.findViewById(R.id.btnViewDetails);
        Button btnViewMembers = convertView.findViewById(R.id.btnViewMembers);

        Group group = groupList.get(position);

        tvGroupName.setText(group.getName());
        tvGroupDescription.setText(group.getDescription());

        // Evento para ver detalles del grupo
        btnViewDetails.setOnClickListener(v -> {
            Intent intent = new Intent(context, GroupDetailsActivity.class);
            intent.putExtra("GROUP_ID", group.getId());
            intent.putExtra("GROUP_NAME", group.getName());
            intent.putExtra("GROUP_DESCRIPTION", group.getDescription());
            context.startActivity(intent);
        });

        // Evento para ver miembros del grupo
        btnViewMembers.setOnClickListener(v -> {
            Intent intent = new Intent(context, GroupMembersActivity.class);
            intent.putExtra("GROUP_ID", group.getId());
            intent.putExtra("GROUP_NAME", group.getName());
            context.startActivity(intent);
        });

        return convertView;
    }
}

