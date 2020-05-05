package com.example.notes.Adapters;
import com.example.notes.R;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import com.example.notes.Models.Note;
import java.util.List;


public class NoteAdapter extends ArrayAdapter<Note> {

    private final List<Note> Notes;
    private static LayoutInflater inflater;
    private final Context context;

    class ViewHolderItem {
        TextView titleView;
        TextView dateView;
        TextView bodyView;
        TextView passwordView;
        //ListView tagsView;
    }

    public NoteAdapter(Context context, int resourceId, List<Note> notes) {
        super(context, resourceId, notes);
        this.context = context;
        this.Notes = notes;
        this.inflater = LayoutInflater.from(context);
    }


    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolderItem viewHolderItem = new ViewHolderItem();

        if (convertView == null) {

            convertView = inflater.inflate(R.layout.note, parent, false);
            viewHolderItem.titleView = convertView.findViewById(R.id.titleView);
            viewHolderItem.dateView = convertView.findViewById(R.id.dateView);
            viewHolderItem.bodyView = convertView.findViewById(R.id.bodyView);
            viewHolderItem.passwordView = convertView.findViewById(R.id.passwordView);
            //viewHolderItem.tagsView = convertView.findViewById(R.id.tagsView);
            convertView.setTag(viewHolderItem);

        } else {
            viewHolderItem = (ViewHolderItem) convertView.getTag();
        }

        Note note = Notes.get(position);

        viewHolderItem.titleView.setText(note.getTitle());
        viewHolderItem.dateView.setText(note.getDate());
        viewHolderItem.bodyView.setText(note.getBody());
        viewHolderItem.passwordView.setText(note.getPassword());
        //final ArrayAdapter<String> adapter = new ArrayAdapter<>(context,R.layout.taglist, R.id.label, note.tags);
        //viewHolderItem.tagsView.setAdapter(adapter);
        return convertView;
    }



}

