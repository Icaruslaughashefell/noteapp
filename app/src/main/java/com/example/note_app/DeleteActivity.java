package com.example.note_app;

import android.app.AlertDialog;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Arrays;


public class DeleteActivity extends AppCompatActivity {

    ArrayList<String> notes_array_list = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deletenotes);

        ListView listViewNotes = findViewById(R.id.list_notes);

        File notes_file = new File(getApplicationContext().getFilesDir(), "notes.txt");
        StringBuilder text = new StringBuilder();
        try {
            BufferedReader br = new BufferedReader(new FileReader(notes_file));
            String line;
            while ((line = br.readLine()) != null) {
                notes_array_list.add(line);
                text.append(line);
                text.append('\n');
            }
            br.close();
        }
        catch (Exception e) {
            Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
        }

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_list_item_1,
                notes_array_list);

        listViewNotes.setAdapter(arrayAdapter);

        listViewNotes.setOnItemLongClickListener((arg0, arg1, pos, id) -> {
            getDialog(pos, arrayAdapter).show();
            return true;
        });
    }

    private AlertDialog getDialog(int selectedPos, ArrayAdapter<String> arrayAdapter){
        AlertDialog.Builder builder = new AlertDialog.Builder(DeleteActivity.this);
        builder.setMessage(R.string.message)
                .setPositiveButton(R.string.yes, (dialog, id) -> {
                    notes_array_list.remove(selectedPos);
                    File notes_file = new File(getApplicationContext().getFilesDir(), "notes.txt");
                    try {
                        FileWriter writer = new FileWriter(notes_file, false);
                        for (int i = 0; i < notes_array_list.size(); i++) {
                            writer.write(notes_array_list.get(i) + "\n");
                        }
                        writer.flush();
                        writer.close();
                        arrayAdapter.notifyDataSetChanged();
                    }
                    catch (Exception e) {
                        Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
                    }
                })
                .setNegativeButton(R.string.no, (dialog, id) -> dialog.dismiss());
        return builder.create();
    }
}