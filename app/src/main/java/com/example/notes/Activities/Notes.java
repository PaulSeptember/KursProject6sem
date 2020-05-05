package com.example.notes.Activities;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.SearchView;
import android.widget.Toast;

import com.example.notes.Adapters.NoteAdapter;
import com.example.notes.Adapters.NoteDatabaseAdapter;
import com.example.notes.Models.Note;
import com.example.notes.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import java.util.Comparator;
import java.util.ArrayList;
import java.util.List;


public class Notes extends Fragment {


    private List<Note> notes = new ArrayList<>();
    private AdapterView notesView;
    private NoteAdapter noteAdapter;
    private NoteDatabaseAdapter dbAdapter;

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

        Button sortByDateBtn = view.findViewById(R.id.date_sort);
        sortByDateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //sortByDate();
                noteAdapter.notifyDataSetChanged();
            }
        });
        sortByDateBtn.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                //sortByDateReverse();
                noteAdapter.notifyDataSetChanged();
                return true;
            }
        });

        /*Button sortByTitleBtn = view.findViewById(R.id.sort_title);
        sortByTitleBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sortByTitle();
                noteAdapter.notifyDataSetChanged();
            }
        });
        sortByTitleBtn.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                sortByTitleReverse();
                noteAdapter.notifyDataSetChanged();
                return true;
            }
        });*/

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


    private void sortByDate() {
        notes.sort(new Comparator<Note>() {
            @Override
            public int compare(Note note1, Note note2) {
                return note2.getDate().compareTo(note1.getDate());
            }
        });
    }

    private void sortByDateReverse() {
        notes.sort(new Comparator<Note>() {
            @Override
            public int compare(Note note1, Note note2) {
                return note1.getDate().compareTo(note2.getDate());
            }
        });
    }

    private void sortByTitle() {
        notes.sort(new Comparator<Note>() {
            @Override
            public int compare(Note note1, Note note2) {
                return note2.getTitle().compareTo(note1.getTitle());
            }
        });
    }

    private void sortByTitleReverse() {
        notes.sort(new Comparator<Note>() {
            @Override
            public int compare(Note note1, Note note2) {
                return note1.getTitle().compareTo(note2.getTitle());
            }
        });
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
