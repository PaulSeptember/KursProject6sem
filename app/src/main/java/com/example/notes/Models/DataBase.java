package com.example.notes.Models;

import com.example.notes.Helpers.DESCrypto;
import com.example.notes.Helpers.KeyGenerator;
import com.example.notes.Helpers.Utils;

import java.util.ArrayList;

public class DataBase {
    public ArrayList<Entry> base = new ArrayList<>();

    public String name = "New database 1.morra";

    public Entry get(int n){
        return base.get(n);
    }

    public void set(int n, Entry e){
        base.set(n,e);
    }

    public boolean isOpened = false;

    public String content;
    public String password;

    public void add(Entry s){
        if (isOpened)
            base.add(s);
    }

    @Override
    public String toString(){
        StringBuilder ans = new StringBuilder();
        ans.append(password);
        ans.append("\n");
        for(Entry s : base)
            ans.append(s.title).append("*").append(s.login).append("*").append(s.password).append("\n");
        return ans.toString();
    }

    public boolean tryToOpen(){
        String key = KeyGenerator.generateKey(password);
        DESDerivedKey k = new DESDerivedKey(key);

        String dec = Utils.hex2text(DESCrypto.decrypt(content, k));
        String[] entries = dec.split("\n");

        if (!entries[0].equals(password)){
            return false;
        }
        isOpened = true;
        for (int i = 1; i < entries.length - 1; i++) {
            Entry temp = new Entry(entries[i].split("\\*")[0], entries[i].split("\\*")[1], entries[i].split("\\*")[2]);
            this.add(temp);
            System.out.print("SASAI");
        }
        return true;
    }

    public DataBase(){
        isOpened = true;
    }

    public DataBase(String s){
        content = s;
    }


    public String getFile(){
        String key = KeyGenerator.generateKey(password);
        DESDerivedKey k = new DESDerivedKey(key);

        return DESCrypto.encrypt(Utils.text2hex(toString()), k);
    }

}
