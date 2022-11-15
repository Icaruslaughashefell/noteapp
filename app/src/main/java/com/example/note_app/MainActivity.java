package com.example.note_app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ArrayAdapter<String> arrayAdapter;
    ArrayList<String> array_note_list = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ListView listNote = findViewById(R.id.list_notes_main);
        arrayAdapter = new ArrayAdapter<>(this,android.R.layout.simple_list_item_1,array_note_list);
        listNote.setAdapter(arrayAdapter);
    }


    @Override
    public void onStart(){
        super.onStart();
        File notes_file = new File(getApplicationContext().getFilesDir(), "notes.txt");
        array_note_list.clear();
        try {
            BufferedReader br = new BufferedReader(new FileReader(notes_file));
            String line;
            while ((line = br.readLine()) != null) {
                array_note_list.add(line);
            }
            br.close();
        }
        catch (Exception e) {
            Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
        }
        arrayAdapter.notifyDataSetChanged();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.toolbar,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == R.id.add){
            Intent add = new Intent (MainActivity.this,AddNoteActivity.class);
            startActivity(add);
        }
        else if (item.getItemId() == R.id.delete){
            Intent delete = new Intent(MainActivity.this,DeleteActivity.class);
            startActivity(delete);
        }
        return super.onOptionsItemSelected(item);
    }
}