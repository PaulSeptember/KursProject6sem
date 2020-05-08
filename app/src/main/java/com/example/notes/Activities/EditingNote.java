package com.example.notes.Activities;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.notes.Adapters.NoteDatabaseAdapter;
import com.example.notes.Models.Note;
import com.example.notes.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;


public class EditingNote extends Fragment {

    private Note note;
    private NoteDatabaseAdapter dbAdapter;

    public EditingNote() {
        // Required empty public constructor
    }

    public static EditingNote newInstance() {
        EditingNote fragment = new EditingNote();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_note, container, false);
        dbAdapter = new NoteDatabaseAdapter(getActivity());
        final TextView bodyView = view.findViewById(R.id.note_body);
        final TextView titleView = view.findViewById(R.id.note_title);
        final TextView passwordView = view.findViewById(R.id.note_password);
        //final TextView tagView = view.findViewById(R.id.tag_place);

        note = (Note) getArguments().getSerializable("note");
        bodyView.setText(note.getBody());
        titleView.setText(note.getTitle());
        titleView.setText(note.getPassword());

        if (savedInstanceState != null) {
            bodyView.setText(savedInstanceState.getString("body"));
            titleView.setText(savedInstanceState.getString("title"));
            passwordView.setText(savedInstanceState.getString("password"));

            //tagView.setText(savedInstanceState.getString("tag"));
            //note.tags = note.createTagListFromString(savedInstanceState.getString("tags"));
        }

        Button btn = view.findViewById(R.id.save);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!bodyView.getText().toString().isEmpty()) {
                    note.saveNote(titleView.getText().toString(), bodyView.getText().toString(), passwordView.getText().toString());
                    dbAdapter.open();
                    dbAdapter.update(note);
                    dbAdapter.close();
                    FragmentManager fragmentManager = getFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    Notes notes = new Notes();
                    fragmentTransaction.replace(R.id.nav_host_fragment, notes);
                    fragmentTransaction.commit();
                }
                else {
                    showWarningAboutBody();
                }
            }
        });

        /*ListView listView = view.findViewById(R.id.tag_list);
        final ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(), R.layout.taglist, R.id.label, note.tags);
        listView.setAdapter(adapter);
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
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
                        note.tags.remove(deletePosition);
                        adapter.notifyDataSetChanged();
                        adapter.notifyDataSetInvalidated();

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
        });*/

        /*FloatingActionButton addTagBtn = view.findViewById(R.id.add_tag);
        addTagBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!tagView.getText().toString().isEmpty())
                {
                    note.addTag(tagView.getText().toString());
                    adapter.notifyDataSetChanged();
                    tagView.setText("");
                }
            }
        });*/
        return view;
    }

    @Override
    public void onSaveInstanceState(Bundle saveInstance)
    {
        final TextView bodyView = getView().findViewById(R.id.note_body);
        final TextView titleView = getView().findViewById(R.id.note_title);
        final TextView passwordView = getView().findViewById(R.id.note_password);
        //final TextView tagView = getView().findViewById(R.id.tag_place);
        saveInstance.putString("body", bodyView.getText().toString());
        saveInstance.putString("title", titleView.getText().toString());
        saveInstance.putString("password", passwordView.getText().toString());
        //saveInstance.putString("tag", tagView.getText().toString());
        //saveInstance.putString("tags", note.getTagsString());
        super.onSaveInstanceState(saveInstance);
    }


    public void showWarningAboutBody()
    {
        Toast toast = Toast.makeText(getActivity(),
                "Can't save note without body.",
                Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER, 0, 0);
        LinearLayout toastContainer = (LinearLayout) toast.getView();
        ImageView catImageView = new ImageView(getActivity());
        catImageView.setImageResource(R.drawable.angry_kitty);
        toastContainer.addView(catImageView, 0);
        toast.show();
    }

}
