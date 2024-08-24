package ru.wizand.contactsman;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.Toolbar;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

import ru.wizand.contactsman.adapter.ContactsAdapter;
import ru.wizand.contactsman.db.DatabaseHelper;
import ru.wizand.contactsman.db.entity.Contact;

public class MainActivity extends AppCompatActivity {

    // Variables
    private ContactsAdapter contactsAdapter;
    private ArrayList<Contact> contactArrayList = new ArrayList<>();
    private RecyclerView recyclerView;
    private DatabaseHelper db;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Contacts Manager");

        // RecyclerView
        recyclerView = findViewById(R.id.recycler_view_contacts);
        db = new DatabaseHelper(this);

        // Contacts List
        contactArrayList.addAll(db.getAllContacts());

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

    private void addAndEditContacts(final boolean isUpdated, final Contact contact, final int position) {
        LayoutInflater layoutInflater = LayoutInflater.from.(getApplicationContext());
        View view = layoutInflater.inflate(R.layout.layout_add_contact,null);

        AlertDialog.Builder alertDialog =  new AlertDialog.Builder(MainActivity.this);
        alertDialog.setView(view);

        TextView contactTitle = view.findViewById(R.id.new_contact_title);
        final EditText newContact = view.findViewById(R.id.name);



    }
}