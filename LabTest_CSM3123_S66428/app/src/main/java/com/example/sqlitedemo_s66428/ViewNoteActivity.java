package com.example.sqlitedemo_s66428;

import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class ViewNoteActivity extends AppCompatActivity {

    private TextView titleTextView, contentTextView, dateTextView;
    private NotesDatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_note);

        titleTextView = findViewById(R.id.titleTextView);
        contentTextView = findViewById(R.id.contentTextView);
        dateTextView = findViewById(R.id.dateTextView);
        dbHelper = new NotesDatabaseHelper(this);

        int noteId = getIntent().getIntExtra("noteId", -1);
        Note note = dbHelper.getNoteById(noteId);

        if (note != null) {
            titleTextView.setText(note.getTitle());
            contentTextView.setText(note.getContent());
            dateTextView.setText(note.getCreatedAt());
        }
    }
}
