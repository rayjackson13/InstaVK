package tk.rayjackson.instatovk;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;

/**
 * Created by rayjackson on 11/14/17.
 */

public class User{
    private int id;
    private String name;
    private String surname;

    public User(int id, String name, String surname) {
        this.id = id;
        this.name = name;
        this.surname = surname;
    }

    public User(Bundle bundle){
        this.id = bundle.getInt("user_id");
        this.name = bundle.getString("user_name");
        this.surname = bundle.getString("user_surname");
    }

    public User(SharedPreferences sp){
        id = sp.getInt("user_id", 0);
        name = sp.getString("user_name", "");
        surname = sp.getString("user_surname", "");
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public void toSharedPreferences(SharedPreferences sharedPreferences){
        sharedPreferences.edit().
                putInt("user_id", id).
                putString("user_name", name).
                putString("user_surname", surname).
                apply();
    }

    @Override
    public String toString() {
        return "User{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                '}';
    }
}
