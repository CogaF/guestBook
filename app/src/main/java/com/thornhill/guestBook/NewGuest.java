package com.thornhill.guestBook;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;



public class NewGuest extends AppCompatActivity {


    Guests_DB dbh;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scrolling);
        dbh = new Guests_DB(this);
    }


}
