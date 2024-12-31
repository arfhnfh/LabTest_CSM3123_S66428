package com.example.sqlitedemo_s66428;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private NoteAdapter noteAdapter;
    private NotesDatabaseHelper dbHelper;
    private EditText searchEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recyclerView);
        searchEditText = findViewById(R.id.searchEditText);
        FloatingActionButton fab = findViewById(R.id.fab);

        dbHelper = new NotesDatabaseHelper(this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        loadNotes();

        fab.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, AddEditNoteActivity.class);
            startActivity(intent);
        });

        searchEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                searchNotes(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });
    }

    private void loadNotes() {
        List<Note> notes = dbHelper.getAllNotes();
        noteAdapter = new NoteAdapter(notes, note -> {
            Intent intent = new Intent(MainActivity.this, ViewNoteActivity.class);
            intent.putExtra("noteId", note.getId());
            startActivity(intent);
        });
        recyclerView.setAdapter(noteAdapter);
    }

    private void searchNotes(String query) {
        List<Note> notes = dbHelper.searchNotes(query);
        noteAdapter.updateList(notes);
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadNotes();
    }
}