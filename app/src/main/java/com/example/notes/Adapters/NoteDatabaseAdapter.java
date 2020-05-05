package com.example.notes.Adapters;

import android.database.sqlite.SQLiteDatabase;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import com.example.notes.Helpers.NotesKeeperHelper;
import com.example.notes.Models.Note;

import java.util.ArrayList;
import java.util.List;

public class NoteDatabaseAdapter {

    private final NotesKeeperHelper dbHelper;
    private SQLiteDatabase database;

    public NoteDatabaseAdapter(Context context){
        dbHelper = new NotesKeeperHelper(context.getApplicationContext());
    }

    public void open() {
        database = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }

    private Cursor getAllEntries() {
        String[] columns = new String[] {NotesKeeperHelper.NoteEntry._ID,
                NotesKeeperHelper.NoteEntry.COLUMN_NAME_TITLE, NotesKeeperHelper.NoteEntry.COLUMN_NAME_BODY,
                /*NotesKeeperHelper.NoteEntry.COLUMN_NAME_TAGS,*/ NotesKeeperHelper.NoteEntry.COLUMN_NAME_DATE};
        return  database.query(NotesKeeperHelper.NoteEntry.TABLE_NAME, columns,
                null, null, null, null, null);
    }

    public List<Note> getNotes() {
        ArrayList<Note> notes = new ArrayList<>();
        Cursor cursor = getAllEntries();
        if(cursor.moveToFirst()) {
            do {
                long id = cursor.getInt(cursor.getColumnIndex(NotesKeeperHelper.NoteEntry._ID));
                String title = cursor.getString(cursor.getColumnIndex(NotesKeeperHelper.NoteEntry.COLUMN_NAME_TITLE));
                String body = cursor.getString(cursor.getColumnIndex(NotesKeeperHelper.NoteEntry.COLUMN_NAME_BODY));
                String password = cursor.getString(cursor.getColumnIndex(NotesKeeperHelper.NoteEntry.COLUMN_NAME_PASSWORD));
                //String tags = cursor.getString(cursor.getColumnIndex(NotesKeeperHelper.NoteEntry.COLUMN_NAME_TAGS));
                String date = cursor.getString(cursor.getColumnIndex(NotesKeeperHelper.NoteEntry.COLUMN_NAME_DATE));
                notes.add(new Note(id, title, body, password, /*tags,*/ date));
            }
            while (cursor.moveToNext());
        }
        cursor.close();
        return notes;
    }

    public void deleteNote(long id) {
        database.isOpen();
        database.delete(NotesKeeperHelper.NoteEntry.TABLE_NAME,
                NotesKeeperHelper.NoteEntry._ID + "=" + id, null);
    }

    public void update(Note note) {
        ContentValues values = getValues(note);
        database.update(NotesKeeperHelper.NoteEntry.TABLE_NAME, values,
                NotesKeeperHelper.NoteEntry._ID + "=" + note.getId(), null);
    }

    private ContentValues getValues(Note note) {
        ContentValues values = new ContentValues();
        values.put(NotesKeeperHelper.NoteEntry.COLUMN_NAME_TITLE, note.getTitle());
        values.put(NotesKeeperHelper.NoteEntry.COLUMN_NAME_BODY, note.getBody());
        //values.put(NotesKeeperHelper.NoteEntry.COLUMN_NAME_TAGS, note.getTagsString());
        values.put(NotesKeeperHelper.NoteEntry.COLUMN_NAME_DATE, note.getDate());
        return values;
    }

    public void insert(Note note) {
        ContentValues values = getValues(note);
        database.insert(NotesKeeperHelper.NoteEntry.TABLE_NAME, null, values);
    }

}
