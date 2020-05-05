package com.example.notes.Models;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.ArrayList;

public class Note  implements Serializable {

    //@PrimaryKey(autoincrement = true)
    private long id;
    private String title;
    private String body;
    private String password;
    //public ArrayList<String> tags;
    private String CreationDate;

    public Note() {
        /*tags = new ArrayList<>();*/
    }

    public Note(long id, String title, String body, String password, /*String tags,*/ String creationDate)
    {
        this.id = id;
        this.title = title;
        this.body = body;
        this.password = password;
        this.CreationDate = creationDate;
        //this.tags = createTagListFromString(tags);
    }

    public long getId() {return id;}

    public String getTitle(){
        return title;
    }

    public void setTitle(String title){
        this.title = title;
    }

    public String getBody(){
        return body;
    }

    public void setBody(String body){
        this.body = body;
    }

    public String getDate() { return CreationDate; }

    public String getPassword(){
        return password;
    }

    public void setPassword(String password){
        this.password = password;
    }

    /*public void addTag(String tag)
    {
        if (!tags.contains(tag.toLowerCase()))
        {
            tags.add(tag.toLowerCase());
        }
    }*/

    /*public String getTagsString()
    {
        String tagString = "";
        if (!this.tags.isEmpty())
        {
            for (String tag : this.tags)
            {
                if (!tags.get(0).equals(tag))
                    tagString += "#";
                tagString += tag;
            }
        }
        return tagString;
    }*/

    /*public ArrayList<String> createTagListFromString(String tagString)
    {
        ArrayList<String> tags = new ArrayList<>(Arrays.asList(tagString.split("#")));
        return tags;
    }*/

    public void saveNote(String title, String body, String password)
    {
        this.body = body;
        Date now = new Date();
        CreationDate =  new SimpleDateFormat("dd.MM.yyyy  'at' HH:mm").format(now);
        if (title.isEmpty())
            this.title = CreationDate;
        else this.title = title;
        this.password = password;
    }

}
