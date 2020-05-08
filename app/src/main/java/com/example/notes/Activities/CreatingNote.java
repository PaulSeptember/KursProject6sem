package com.example.notes.Activities;

import android.app.AlertDialog;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;

import com.example.notes.Adapters.NoteDatabaseAdapter;
import com.example.notes.R;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;
import android.view.Gravity;

import com.example.notes.Models.Note;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.SimpleDateFormat;
import java.util.Date;


public class CreatingNote extends Fragment {

    private Note note;
    private NoteDatabaseAdapter dbAdapter;

    public CreatingNote() {
        // Required empty public constructor
    }

    public static CreatingNote newInstance() {
        CreatingNote fragment = new CreatingNote();
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
        /*Date now = new Date();
        titleView.setHint(new SimpleDateFormat("dd.MM.yyyy  'at' HH:mm").format(now));*/


        note = new Note();

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
                    dbAdapter.insert(note);
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

        Button btn2 = view.findViewById(R.id.copy_password);
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ClipboardManager clipboard = (ClipboardManager) getActivity().getSystemService(Context.CLIPBOARD_SERVICE);
                ClipData clip = ClipData.newPlainText("Copied to clipboard!", note.getPassword());
                clipboard.setPrimaryClip(clip);
            }
        });
        //ListView listView = view.findViewById(R.id.tag_list);
        //final ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(),R.layout.taglist, R.id.label, note.tags);
        //listView.setAdapter(adapter);
        /*listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
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

    /*@Override
    public void onSaveInstanceState(Bundle saveInstance)
    {
        final TextView bodyView = getView().findViewById(R.id.note_body);
        final TextView titleView = getView().findViewById(R.id.note_title);
        //final TextView tagView = getView().findViewById(R.id.tag_place);
        saveInstance.putString("body", bodyView.getText().toString());
        saveInstance.putString("title", titleView.getText().toString());
        //saveInstance.putString("tag", tagView.getText().toString());
        //saveInstance.putString("tags", note.getTagsString());
        super.onSaveInstanceState(saveInstance);
    }*/

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
