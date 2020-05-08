package com.example.notes.Activities;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.SearchView;
import android.widget.Toast;

import com.example.notes.Adapters.NoteAdapter;
import com.example.notes.Adapters.NoteDatabaseAdapter;
import com.example.notes.Helpers.God;
import com.example.notes.Models.DataBase;
import com.example.notes.Models.Note;
import com.example.notes.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.nbsp.materialfilepicker.MaterialFilePicker;
import com.nbsp.materialfilepicker.ui.FilePickerActivity;
//import com.nbsp.materialfilepicker.MaterialFilePicker;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.Comparator;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Pattern;
import java.io.FileInputStream;
import java.io.FileOutputStream;

import static android.content.Context.MODE_PRIVATE;
import static com.example.notes.Helpers.God.fileString;
import static com.example.notes.Helpers.God.filename;


public class Notes extends Fragment {


    private List<Note> notes = new ArrayList<>();
    private AdapterView notesView;
    private NoteAdapter noteAdapter;
    private NoteDatabaseAdapter dbAdapter;
    private boolean selected;


    public Notes() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_notes, container, false);
        dbAdapter = new NoteDatabaseAdapter(getContext());
        FloatingActionButton btn = view.findViewById(R.id.add_note);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                CreatingNote creatingNote = new CreatingNote();
                fragmentTransaction.replace(R.id.nav_host_fragment, creatingNote);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });

        Notes q = this;
        Button sortByDateBtn = view.findViewById(R.id.open);
        sortByDateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!selected){new MaterialFilePicker()
                        .withSupportFragment(q)
                        .withRequestCode(1000)
                        .start();
                    selected = !selected;}
                else{
                    File sdcard = Environment.getExternalStorageDirectory();
                    File file = new File(sdcard, filename);
                    StringBuilder text = new StringBuilder();
                    try {
                        BufferedReader br = new BufferedReader(new FileReader(file));
                        String line;
                        while ((line = br.readLine()) != null) {
                            text.append(line);
                            text.append('\n');
                        }
                        br.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                        //You'll need to add proper error handling here
                    }
                    fileString = text.toString();
                    DataBase d = new DataBase(text.toString());
                    d.password = "1234";
                    d.tryToOpen();

                    int q = notes.size();
                    for(int i=0;i<q;i++){
                        Note note_to_delete = notes.get(i);
                        dbAdapter.open();
                        dbAdapter.deleteNote(note_to_delete.getId());
                        dbAdapter.close();
                       
                    }
                    notes.clear();
                    noteAdapter.notifyDataSetChanged();
                    noteAdapter.notifyDataSetInvalidated();
                    for (int i = 0; i < d.base.size(); i++) {
                        Note note = new Note();
                        note.saveNote(d.base.get(i).title, d.base.get(i).login, d.base.get(i).password);
                        dbAdapter.open();
                        dbAdapter.insert(note);
                        dbAdapter.close();
                    }

                    FragmentManager fragmentManager = getFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    Notes notes = new Notes();
                    fragmentTransaction.replace(R.id.nav_host_fragment, notes);
                    fragmentTransaction.commit();
                    //}
                    noteAdapter.notifyDataSetChanged();
                    selected = !selected;
                }
            }
        });



        Button sortByTitleBtn = view.findViewById(R.id.date_sort);
        sortByTitleBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //if (!God.filename.isEmpty()){
                    File sdcard = Environment.getExternalStorageDirectory();
                    File file = new File(sdcard,"test2.morra");
                    StringBuilder text = new StringBuilder();
                    try {
                        BufferedReader br = new BufferedReader(new FileReader(file));
                        String line;
                        while ((line = br.readLine()) != null) {
                            text.append(line);
                            text.append('\n');
                        }
                        br.close();
                    }
                    catch (IOException e) {
                        e.printStackTrace();
                    }
                    fileString = text.toString();
                    Button b = view.findViewById(R.id.date_sort);
                    b.setText(text);
                //}
                DataBase d = new DataBase(text.toString());
                d.password = "1234";
                d.tryToOpen();

                for(int i = 0; i<d.base.size(); i++){
                    Note note = new Note();
                    note.saveNote(d.base.get(i).title,d.base.get(i).login,d.base.get(i).password);
                    dbAdapter.open();
                    dbAdapter.insert(note);
                    dbAdapter.close();
                }

                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                Notes notes = new Notes();
                fragmentTransaction.replace(R.id.nav_host_fragment, notes);
                fragmentTransaction.commit();
                //}
                noteAdapter.notifyDataSetChanged();
            }
        });

        SearchView searchView = view.findViewById(R.id.search);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                searchNote(s);
                noteAdapter.notifyDataSetChanged();
                return true;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                searchNote(s);
                noteAdapter.notifyDataSetChanged();
                return true;
            }
        });


        notesView = view.findViewById(R.id.notes_list);
        notesView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                EditingNote editingNote = new EditingNote();
                Bundle args = new Bundle();
                args.putSerializable("note", notes.get(position));
                editingNote.setArguments(args);
                fragmentTransaction.replace(R.id.nav_host_fragment, editingNote);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });

        notesView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {

                final int deletePosition = i;
                AlertDialog.Builder alert = new AlertDialog.Builder(
                        getActivity());
                alert.setTitle("Delete");
                alert.setMessage("Do you want delete this item?");
                alert.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Note note_to_delete = notes.get(deletePosition);
                        dbAdapter.open();
                        dbAdapter.deleteNote(note_to_delete.getId());
                        notes.remove(deletePosition);
                        dbAdapter.close();
                        noteAdapter.notifyDataSetChanged();
                        noteAdapter.notifyDataSetInvalidated();
                    }
                });
                alert.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                alert.show();
                return true;
            }
        });

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        NoteDatabaseAdapter dbAdapter = new NoteDatabaseAdapter(getContext());
        dbAdapter.open();
        notes = dbAdapter.getNotes();
        noteAdapter = new NoteAdapter(getContext(), R.layout.note, notes);
        notesView = getView().findViewById(R.id.notes_list);
        notesView.setAdapter(noteAdapter);
        dbAdapter.close();
        notesView = getView().findViewById(R.id.notes_list);
    }


    private void searchNote(String s)
    {
        List<Note> notes2 = new ArrayList<>();
        NoteDatabaseAdapter dbAdapter = new NoteDatabaseAdapter(getContext());
        dbAdapter.open();
        notes.clear();
        dbAdapter.open();
        notes.addAll(dbAdapter.getNotes());
        noteAdapter = new NoteAdapter(getContext(), R.layout.note, notes);
        notesView = getView().findViewById(R.id.notes_list);
        notesView.setAdapter(noteAdapter);
        dbAdapter.close();
        notesView = getView().findViewById(R.id.notes_list);
        dbAdapter.close();
        /*if (!s.isEmpty()) {
            for (Note note : notes) {
                if (note.getTagsString().contains(s.toLowerCase()))
                    notes2.add(note);
            }
            notes.clear();
            notes.addAll(notes2);
        }*/
    }

}
