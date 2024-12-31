package com.example.sqlitedemo_s66428;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class ExportNotesActivity extends AppCompatActivity {

    private Button exportButton;
    private NotesDatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_export_notes);

        exportButton = findViewById(R.id.exportButton);
        dbHelper = new NotesDatabaseHelper(this);

        // Request storage permission
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
        }

        exportButton.setOnClickListener(v -> exportNotesToFile());
    }

    private void exportNotesToFile() {
        List<Note> notes = dbHelper.getAllNotes();

        if (notes.isEmpty()) {
            Toast.makeText(this, "No notes to export", Toast.LENGTH_SHORT).show();
            return;
        }

        File file = new File(Environment.getExternalStorageDirectory(), "notes_export.txt");

        try (FileWriter writer = new FileWriter(file)) {
            for (Note note : notes) {
                writer.write("Title: " + note.getTitle() + "\n");
                writer.write("Content: " + note.getContent() + "\n");
                writer.write("Created At: " + note.getCreatedAt() + "\n");
                writer.write("Modified At: " + note.getModifiedAt() + "\n");
                writer.write("\n---\n\n");
            }
            Toast.makeText(this, "Notes exported to " + file.getAbsolutePath(), Toast.LENGTH_LONG).show();
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(this, "Failed to export notes", Toast.LENGTH_SHORT).show();
        }
    }
}
