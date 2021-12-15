package com.example.silentcalls;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.RecyclerView;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;

public class RecyclerVAdapter extends RecyclerView.Adapter<RecyclerVAdapter.ViewHolder> {
    private static final int REQUEST_CALL = 1;
    private Context context;
    private Activity activity;
    private ArrayList<ContactModel> contactModels;


    public RecyclerVAdapter(Activity activity, ArrayList<ContactModel> contactModels, Context context) {
        this.activity = activity;
        this.contactModels = contactModels;
        this.context = context;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.contact_view,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ContactModel contactModel = contactModels.get(position);
        holder.contactName.setText(contactModel.getName());
        holder.contactNumber.setText(contactModel.getNumber());
        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    String encodedNumberToCall = URLEncoder.encode("#31#" + contactModels.
                            get(holder.getAdapterPosition()).getNumber(), "UTF-8");
                    Intent callIntent = new Intent(Intent.ACTION_CALL,
                            Uri.parse("tel:" + encodedNumberToCall));
                    if (ActivityCompat.checkSelfPermission(context,
                            android.Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                        Toast.makeText(context, "permission not granted",
                                Toast.LENGTH_SHORT).show();
                        ActivityCompat.requestPermissions(activity,
                                new String[]{Manifest.permission.CALL_PHONE},REQUEST_CALL);
                    }else{
                        context.startActivity(callIntent);
                    }
                } catch (UnsupportedEncodingException exception) {
                    Toast.makeText(context,"Wrong phone number",Toast.LENGTH_SHORT);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return contactModels.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView contactName;
        TextView contactNumber;
        ImageView imageView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            contactName = itemView.findViewById(R.id.contactName);
            contactNumber = itemView.findViewById(R.id.contactNumber);
            imageView = itemView.findViewById(R.id.make_phone_call);
        }
    }
}
