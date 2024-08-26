package ru.wizand.contactsman;

import android.content.DialogInterface;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.Toolbar;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

import ru.wizand.contactsman.adapter.ContactsAdapter;
import ru.wizand.contactsman.db.ContactAppDatabase;

import ru.wizand.contactsman.db.entity.Contact;

public class MainActivity extends AppCompatActivity {

    // Variables
    private ContactsAdapter contactsAdapter;
    private ArrayList<Contact> contactArrayList = new ArrayList<>();
    private RecyclerView recyclerView;
    private ContactAppDatabase contactAppDatabase;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(R.string.contacts_manager_string);

        // RecyclerView
        recyclerView = findViewById(R.id.recycler_view_contacts);

        // Database
        contactAppDatabase = Room.databaseBuilder(
                getApplicationContext(),
                ContactAppDatabase.class,
                "ContactDB")
                .allowMainThreadQueries()
                .build();

        // Contacts List
        contactArrayList.addAll(contactAppDatabase.getContactDAO().getContacts());

        contactsAdapter = new ContactsAdapter(this, contactArrayList, MainActivity.this);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);

        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(contactsAdapter);

        FloatingActionButton fab = (FloatingActionButton)  findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addAndEditContacts(false, null, -1);
            }
        });
    }

    public void addAndEditContacts(final boolean isUpdated, final Contact contact, final int position) {
        LayoutInflater layoutInflater = LayoutInflater.from(getApplicationContext());
        View view = layoutInflater.inflate(R.layout.layout_add_contact,null);

        AlertDialog.Builder alerDialogBuilder =  new AlertDialog.Builder(MainActivity.this);
        alerDialogBuilder.setView(view);

        TextView contactTitle = view.findViewById(R.id.new_contact_title);
        final EditText newContact = view.findViewById(R.id.name);
        final EditText contactEmail = view.findViewById(R.id.email);
        final EditText contactPhone = view.findViewById(R.id.phone);

        contactTitle.setText(!isUpdated ? getString(R.string.add_new_contact) : getString(R.string.edit_contact));

        if (isUpdated && contact != null) {
            newContact.setText(contact.getName());
            contactEmail.setText(contact.getEmail());
            contactPhone.setText(contact.getPhone());
        }

        alerDialogBuilder.setCancelable(false)
                .setPositiveButton(isUpdated ? getString(R.string.update) : getString(R.string.save), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                })
                .setNegativeButton("Delete",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                if (isUpdated){
                                    DeleteContact(contact, position);

                                } else{
                                    dialogInterface.cancel();
                                }
                            }
                        });

        final AlertDialog alertDialog = alerDialogBuilder.create();
        alertDialog.show();

        alertDialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (TextUtils.isEmpty(newContact.getText().toString())){
                    Toast.makeText(MainActivity.this, getString(R.string.please_enter_the_name),
                            Toast.LENGTH_SHORT).show();
                 return; } else {
                    alertDialog.dismiss();
                }
                if (isUpdated && contact != null) {
                    UpdateContact(newContact.getText().toString(),
                            contactEmail.getText().toString(),
                            contactPhone.getText().toString(),
                            position);
                } else {
                    CreateContact(newContact.getText().toString(),
                            contactEmail.getText().toString(),
                            contactPhone.getText().toString(),
                            position);
                }
            }
        });

    }

    private void CreateContact(String name, String email, String phone, int position) {

        long id = contactAppDatabase.getContactDAO().addContact(new Contact(name, email, phone, 0));
        Contact contact = contactAppDatabase.getContactDAO().getContact(id);

        if (contact != null) {
            contactArrayList.add(0, contact);
            contactsAdapter.notifyDataSetChanged();
        }
    }

    private void UpdateContact(String name, String email, String phone, int position) {
        Contact contact = contactArrayList.get(position);

        contact.setName(name);
        contact.setEmail(email);
        contact.setPhone(phone);

        contactAppDatabase.getContactDAO().updateContact(contact);

        contactArrayList.set(position,contact);
        contactsAdapter.notifyDataSetChanged();
    }

    private void DeleteContact(Contact contact, int position) {

        contactArrayList.remove(position);
        contactAppDatabase.getContactDAO().deleteContact(contact);
        contactsAdapter.notifyDataSetChanged();
    }

    // Menu bar


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            return true;
        }
        return  super.onOptionsItemSelected(item);
    }
}