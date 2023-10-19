package com.example.projecttwo;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.Button;
import android.widget.Toast;


import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    // initialize variables
    Button loginButton, createActButton;
    EditText name, passwordInput;
    LoginDB myLoginDB;
    static ArrayList<String> usernameList, passwordList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // variables
        loginButton = findViewById(R.id.loginButton);
        createActButton = findViewById(R.id.createButton);
        name = findViewById(R.id.editTextUsername);
        passwordInput = findViewById(R.id.editTextPassword);

        // Presetting buttons to false
        loginButton.setEnabled(false);
        createActButton.setEnabled(false);

        // checks for changes in the EditText field
        name.addTextChangedListener(nameWatcher);
        passwordInput.addTextChangedListener(nameWatcher);

        // get login data from db
        myLoginDB = new LoginDB(MainActivity.this);
        usernameList = new ArrayList<>();
        passwordList = new ArrayList<>();
        storeLoginData();
    }

    // function to login if username and password are correct
    public void Login(View v) {
        Boolean username = false, password = false;
        // check username
        for(String S : usernameList) {
            if(S.equals(name.getText().toString())){
                username = true;}}
        // check password
        for(String S : passwordList) {
            if(S.equals(passwordInput.getText().toString())){
                password = true;}}
        // if both are true, move to next screen
        if (username == true && password == true) {
            finish();
            Intent intent = new Intent(MainActivity.this, Graph.class);
            startActivity(intent);}

        Toast.makeText(this, "Invalid Username", Toast.LENGTH_SHORT).show();
    }

    // function to create account
    public void CreateAccount(View v) {
        Boolean username = false, password = false;
        // check username
        for(String S : usernameList) {
            if(S.equals(name.getText().toString())){
                username = true;}}
        // check password
        for(String S : passwordList) {
            if(S.equals(passwordInput.getText().toString())){
                password = true;}}
        // if both are true, move to next screen
        if (username == true && password == true) {
            finish();
            Intent intent = new Intent(MainActivity.this, Graph.class);
            startActivity(intent);
        }else{
        // else create a new account
            LoginDB loginDB = new LoginDB(MainActivity.this);
            loginDB.addAccount(name.getText().toString(), passwordInput.getText().toString());
            finish();
            Intent intent = new Intent(MainActivity.this, Graph.class);
            startActivity(intent);
        }
    }

    // checks if EditText field is empty
    TextWatcher nameWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

        // Dynamically turns button on and off based on value
        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2)
        {
            String user = name.getText().toString();
            String password = passwordInput.getText().toString();
            loginButton.setEnabled(!user.isEmpty() && !password.isEmpty());
            createActButton.setEnabled(!user.isEmpty() && !password.isEmpty());
        }
        // currently unused, but must be included
        @Override
        public void afterTextChanged(Editable editable) {}
        public void afterTextChanged() {}
    };

    // gathers the login data
    void storeLoginData() {
        Cursor cursor = myLoginDB.readLoginData();
        if(cursor.getCount() == 0) {
        }else{
            while(cursor.moveToNext()) {
                usernameList.add(cursor.getString(1));
                passwordList.add(cursor.getString(2));
            }
        }
    }
}
