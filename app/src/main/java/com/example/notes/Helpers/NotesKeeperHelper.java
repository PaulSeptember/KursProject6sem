package com.example.notes.Helpers;

import android.content.ContentValues;
import android.content.Context;
import android.provider.BaseColumns;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase;

public final class NotesKeeperHelper extends SQLiteOpenHelper {

    // database definition
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "yourNotes3";

    public NotesKeeperHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public static final class NoteEntry implements BaseColumns {
        public static final String TABLE_NAME = "note";
        public static final String _ID = "_id";
        public static final String COLUMN_NAME_TITLE = "title";
        public static final String COLUMN_NAME_BODY = "body";
        public static final String COLUMN_NAME_PASSWORD = "password";
        public static final String COLUMN_NAME_DATE = "date";
        //public static final String COLUMN_NAME_TAGS = "tags";
    }

    private static final String SQL_CREATE_NOTES_TABLE =
            "CREATE TABLE " + NoteEntry.TABLE_NAME + " (" +
                    NoteEntry._ID + " INTEGER PRIMARY KEY," +
                    NoteEntry.COLUMN_NAME_TITLE + " TEXT," +
                    NoteEntry.COLUMN_NAME_BODY + " TEXT," +
                    NoteEntry.COLUMN_NAME_PASSWORD + " TEXT," +
                    NoteEntry.COLUMN_NAME_DATE + " DATETIME"  + ")" ;

    private static final String SQL_DELETE_NOTES_TABLE =
            "DROP TABLE IF EXISTS " + NoteEntry.TABLE_NAME;


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_NOTES_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQL_DELETE_NOTES_TABLE);
        onCreate(db);
    }

    public void insert(String title, String body, String password, String date)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues new_note = new ContentValues();
        new_note.put(NoteEntry.COLUMN_NAME_TITLE, title);
        new_note.put(NoteEntry.COLUMN_NAME_BODY, body);
        new_note.put(NoteEntry.COLUMN_NAME_PASSWORD, password);
        new_note.put(NoteEntry.COLUMN_NAME_DATE, date);
        //new_note.put(NoteEntry.COLUMN_NAME_TAGS, tags);
        db.insert(NoteEntry.TABLE_NAME, null, new_note);
    }


       /*private NotesKeeperContract(){}

    public static class NoteEntry implements BaseColumns {
        public static final String TABLE_NAME = "note";
        public static final String COLUMN_NAME_TITLE = "title";
        public static final String COLUMN_NAME_BODY = "body";
        public static final String COLUMN_NAME_DATE = "date";
    }

    public static class TagEntry implements BaseColumns {
        public static final String TABLE_NAME = "tag";
        public static final String COLUMN_NAME_TAG = "tag";
    }

    public static class NoteTagEntry implements BaseColumns {
        public static final String TABLE_NAME = "note_tag";
        public static final String COLUMN_NAME_TITLE = "note";
        public static final String COLUMN_NAME_BODY = "tag";
    }


    private static final String SQL_CREATE_NOTES_TABLE =
            "CREATE TABLE " + NoteEntry.TABLE_NAME + " (" +
                    NoteEntry._ID + " INTEGER PRIMARY KEY," +
                    NoteEntry.COLUMN_NAME_TITLE + " TEXT," +
                    NoteEntry.COLUMN_NAME_BODY + " TEXT)" +
                    NoteEntry.COLUMN_NAME_DATE + " TEXT)";

        private static final String SQL_DELETE_NOTES_TABLE =
            "DROP TABLE IF EXISTS " + NoteEntry.TABLE_NAME;


    private static final String SQL_CREATE_TAGS_TABLE =
            "CREATE TABLE " + TagEntry.TABLE_NAME + " (" +
                    TagEntry._ID + " INTEGER PRIMARY KEY," +
                    TagEntry.COLUMN_NAME_TAG + " TEXT," ;

    private static final String SQL_DELETE_TAGS_TABLE =
            "DROP TABLE IF EXISTS " + TagEntry.TABLE_NAME;

    private static final String SQL_CREATE_TAG_N_NOTE_TABLE =
            "CREATE TABLE " + TagEntry.TABLE_NAME + " (" +
                    TagEntry._ID + " INTEGER PRIMARY KEY," +
                    TagEntry.COLUMN_NAME_TAG + " TEXT," ;
*/

       /*
           private static final String KEY_ID = "_id";     // common column name

    //table for Notes
    private static final String TABLE_NOTES = "note";
    private static final String COLUMN_NAME_TITLE = "title";
    private static final String COLUMN_NAME_BODY = "body";
    private static final String COLUMN_NAME_DATE = "date";

    // table for tags
    private static final String TABLE_TAGS = "tag";
    private static final String COLUMN_NAME_TAG = "tag";

    //table for notes and tags
    private static final String TABLE_NOTES_N_TAGS = "note_n_tag";
    private static final String KEY_NOTE_ID = "note_id";
    private static final String KEY_TAG_ID = "tag_id";
        */


       /*    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "yourNotes";

    public NotesKeeperHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    private static final class NoteEntry implements BaseColumns {
        private static final String TABLE_NAME = "note";
        private static final String _ID = "_id";
        private static final String COLUMN_NAME_TITLE = "title";
        private static final String COLUMN_NAME_BODY = "body";
        private static final String COLUMN_NAME_DATE = "date";
    }

    private static final class TagEntry implements BaseColumns {
        private static final String _ID = "_id";
        private static final String TABLE_NAME = "tag";
        private static final String COLUMN_NAME_TAG = "tag";
    }

    private static final class NoteTagEntry implements BaseColumns {
        private static final String _ID = "_id";
        private static final String TABLE_NAME = "note_n_tag";
        private static final String KEY_NOTE_ID = "note_id";
        private static final String KEY_TAG_ID = "tag_id";
    }

    private static final String SQL_CREATE_NOTES_TABLE =
            "CREATE TABLE " + NoteEntry.TABLE_NAME + " (" +
                    NoteEntry._ID + " INTEGER PRIMARY KEY," +
                    NoteEntry.COLUMN_NAME_TITLE + " TEXT," +
                    NoteEntry.COLUMN_NAME_BODY + " TEXT," +
                    NoteEntry.COLUMN_NAME_DATE + " DATETIME"  + ")" ;

    private static final String SQL_DELETE_NOTES_TABLE =
            "DROP TABLE IF EXISTS " + NoteEntry.TABLE_NAME;


    private static final String SQL_CREATE_TAGS_TABLE =
            "CREATE TABLE " + TagEntry.TABLE_NAME + " (" +
                    TagEntry._ID + " INTEGER PRIMARY KEY," +
                    TagEntry.COLUMN_NAME_TAG + " TEXT" + ")" ;

    private static final String SQL_DELETE_TAGS_TABLE =
            "DROP TABLE IF EXISTS " + TagEntry.TABLE_NAME;

    private static final String SQL_CREATE_TAG_N_NOTE_TABLE =
            "CREATE TABLE " + NoteTagEntry.TABLE_NAME + " (" +
                    NoteTagEntry._ID + " INTEGER PRIMARY KEY," +
                    NoteTagEntry.KEY_NOTE_ID+ " TEXT," +
                    NoteTagEntry.KEY_TAG_ID +  " TEXT" + ")" ;

    private static final String SQL_DELETE_NOTE_N_TAG_TABLE =
            "DROP TABLE IF EXISTS " + NoteTagEntry.TABLE_NAME;

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_NOTES_TABLE);
        db.execSQL(SQL_CREATE_TAGS_TABLE);
        db.execSQL(SQL_CREATE_TAG_N_NOTE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQL_DELETE_NOTES_TABLE);
        db.execSQL(SQL_DELETE_TAGS_TABLE);
        db.execSQL(SQL_DELETE_NOTE_N_TAG_TABLE);

        onCreate(db);
    }

    public void inserteNote(Note note)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues new_note = new ContentValues();
        new_note.put(NoteEntry.COLUMN_NAME_TITLE, note.getTitle());
        new_note.put(NoteEntry.COLUMN_NAME_BODY, note.getBody());
        new_note.put(NoteEntry.COLUMN_NAME_DATE, note.getDate());

        long note_id = db.insert(NoteEntry.TABLE_NAME, null, new_note);

        long [] tag_ids;

     */

}
