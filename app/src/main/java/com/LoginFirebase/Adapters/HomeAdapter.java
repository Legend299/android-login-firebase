package com.LoginFirebase.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.LoginFirebase.Models.User;
import com.LoginFirebase.R;

import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

import de.hdodenhof.circleimageview.CircleImageView;

public class HomeAdapter extends FirebaseRecyclerAdapter<User, HomeAdapter.HomeAdapterHolder> {

    public HomeAdapter(@NonNull FirebaseRecyclerOptions<User> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull HomeAdapterHolder holder, int position, @NonNull User model) {
        holder.txtName.setText(model.getName());
        holder.txtEmail.setText(model.getEmail());
        holder.txtPhone.setText(model.getPhone());
        holder.txtAddress.setText(model.getAddress());
        Glide.with(holder.imgView.getContext()).load(model.getImagen()).into(holder.imgView);
    }

    @NonNull
    @Override
    public HomeAdapterHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_user,parent,false);
        return new HomeAdapterHolder(view);
    }

    class HomeAdapterHolder extends RecyclerView.ViewHolder {

        CircleImageView imgView;
        TextView txtName, txtEmail, txtPhone, txtAddress;
        public HomeAdapterHolder(@NonNull View itemView){
            super(itemView);
            imgView = (CircleImageView) itemView.findViewById(R.id.profile_picture_row);
            txtName = (TextView) itemView.findViewById(R.id.txtNameRow);
            txtEmail = (TextView) itemView.findViewById(R.id.txtEmailRow);
            txtPhone = (TextView) itemView.findViewById(R.id.txtPhoneRow);
            txtAddress = (TextView) itemView.findViewById(R.id.txtAddressRow);
        }

    }

}
