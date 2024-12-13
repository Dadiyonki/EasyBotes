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
import com.example.ultimaoportunidad.models.Member;

import java.util.List;

public class MemberAdapter extends ArrayAdapter<Member> {

    public MemberAdapter(Context context, List<Member> members) {
        super(context, 0, members);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_group, parent, false);
        }

        Member member = getItem(position);

        TextView tvMemberName = convertView.findViewById(R.id.tvGroupName);
        tvMemberName.setText(member.getUsername());

        return convertView;
    }
}
