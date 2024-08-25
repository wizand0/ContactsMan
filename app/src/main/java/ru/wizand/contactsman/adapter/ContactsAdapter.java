package ru.wizand.contactsman.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import ru.wizand.contactsman.MainActivity;
import ru.wizand.contactsman.R;
import ru.wizand.contactsman.db.entity.Contact;

public class ContactsAdapter extends RecyclerView.Adapter<ContactsAdapter.MyViewHolder> {

    //1 - variables
    private Context context;
    private ArrayList<Contact> contactArrayList;
    private MainActivity mainActivity;

    //2 - ViewhOLDER
    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView name;
        public TextView email;
        public TextView phone;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            this.name = itemView.findViewById(R.id.name);
            this.email = itemView.findViewById(R.id.email);
            this.phone = itemView.findViewById(R.id.phone);
        }
    }

    public ContactsAdapter(Context context, ArrayList<Contact> contacts, MainActivity mainActivity) {
        this.context = context;
        this.contactArrayList = contacts;
        this.mainActivity = mainActivity;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).
                inflate(R.layout.contact_list_item,parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder,int position) {
        final Contact contact = contactArrayList.get(position);

        holder.name.setText(contact.getName());
        holder.email.setText(contact.getEmail());
        holder.phone.setText(contact.getPhone());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    mainActivity.addAndEditContacts(true, contact, position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return  contactArrayList.size();
    }


}
