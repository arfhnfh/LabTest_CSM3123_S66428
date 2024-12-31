package com.example.sqlitedemo_s66428;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class AddEditNoteActivity extends AppCompatActivity {  // Extend AppCompatActivity

    private EditText titleEditText, contentEditText;
    private Button saveButton;
    private NotesDatabaseHelper dbHelper;
    private boolean isEditMode = false;
    private int noteId = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_edit_note);


        titleEditText = findViewById(R.id.titleEditText);
        contentEditText = findViewById(R.id.contentEditText);
        saveButton = findViewById(R.id.saveButton);
        dbHelper = new NotesDatabaseHelper(this);

        // Check if the intent contains a noteId for editing
        if (getIntent().hasExtra("noteId")) {
            isEditMode = true;
            noteId = getIntent().getIntExtra("noteId", -1);
            Note note = dbHelper.getNoteById(noteId);
            if (note != null) {
                titleEditText.setText(note.getTitle());
                contentEditText.setText(note.getContent());
            }
        }

        saveButton.setOnClickListener(v -> saveNote());
    }

    private void saveNote() {
        String title = titleEditText.getText().toString().trim();
        String content = contentEditText.getText().toString().trim();

        // Ensure title is not empty
        if (title.isEmpty()) {
            Toast.makeText(this, "Title cannot be empty", Toast.LENGTH_SHORT).show();
            return;
        }

        // Get the current time for saving
        String currentTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).format(new Date());

        // Save the note depending on whether it's in edit mode or new note mode
        if (isEditMode) {
            dbHelper.updateNote(noteId, title, content, currentTime);
        } else {
            dbHelper.insertNote(title, content, currentTime);
        }

        // Finish the activity and return to the previous screen
        finish();
    }
}
