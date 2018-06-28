/*
 * Copyright (c) 2015-present, Parse, LLC.
 * All rights reserved.
 *
 * This source code is licensed under the BSD-style license found in the
 * LICENSE file in the root directory of this source tree. An additional grant
 * of patent rights can be found in the PATENTS file in the same directory.
 */
package com.parse.starter;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.LogInCallback;
import com.parse.Parse;
import com.parse.ParseAnalytics;
import com.parse.ParseAnonymousUtils;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;
import com.parse.SignUpCallback;

import java.util.List;

import static com.google.android.gms.analytics.internal.zzy.e;


public class MainActivity extends AppCompatActivity {

    public void logIn(View view)
    {
        EditText usernameEditText = (EditText) findViewById(R.id.usernameEditText);
        EditText passwordEditText = (EditText) findViewById(R.id.passwordEditText);

        if(usernameEditText.getText().toString().matches("")|| passwordEditText.getText().toString().matches(""))
        {
            Toast.makeText(this , "A username and password is required",Toast.LENGTH_SHORT).show();

        }
        else
        {
            ParseUser user = new ParseUser();

            user.setUsername(usernameEditText.getText().toString());
            user.setPassword(passwordEditText.getText().toString());
            user.signUpInBackground(new SignUpCallback() {
                @Override
                public void done(ParseException e) {
                    if(e==null)
                    {
                        Log.i("Signup","successful");
                    }
                    else
                    {
                        Toast.makeText(MainActivity.this,e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

     ParseQuery<ParseObject> query = ParseQuery.getQuery("Score");

      query.whereEqualTo("username","apoorv");
      query.setLimit(1);
             query.findInBackground(new FindCallback<ParseObject>() {
               @Override
               public void done(List<ParseObject> objects, ParseException e) {
                 if( e== null )
                 {
                   Log.i("findInBackground", "Retrieved" + objects.size() + " objects");
                   if (objects.size() > 0)
                   {
                     for (ParseObject object : objects)
                     {
                       Log.i("findInBackgroundResult", Integer.toString(object.getInt("score")));
                     }
                   }
                 }
               }
             });
    ParseAnalytics.trackAppOpenedInBackground(getIntent());

  }
}