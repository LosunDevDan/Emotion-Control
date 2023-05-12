package com.losundev.emotioncontrol;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private Button sign_in;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        addListenerOnButton();
    }

    public void addListenerOnButton(){
        sign_in = (Button) findViewById(R.id.image_Button);
        sign_in.setOnClickListener(
                new View.OnClickListener(){
                    @Override
                    public void onClick (View v){
                        Intent intent = new Intent("com.losundev.emotioncontrol.MainCalendar");
                        startActivity(intent);
                    }
                });
    }

    public void onButtonClick(View v) {
        EditText password = (EditText) findViewById(R.id.password_Textfield);
        EditText email = (EditText) findViewById(R.id.email_Textfield);
        String email_fu = email.getText().toString();
        String password_fu = password.getText().toString();

        String fileName = "logdata.txt";
        String line = null;
        ArrayList<String> wordsList = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(new File(getFilesDir(), fileName)))) {
            while ((line = reader.readLine()) != null) {
                String[] words = line.split(" ");
                for (String word : words) {
                    wordsList.add(word);
                }
            }
            if (wordsList.isEmpty()) {
                // File is empty
                writeStringToFile(this, fileName, email_fu + "" + password_fu);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    private void writeStringToFile(Context context, String fileName, String stringToWrite) {
        try {
            File file = new File(context.getFilesDir(), fileName);
            FileWriter fileWriter = new FileWriter(file);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
            bufferedWriter.write(stringToWrite);
            bufferedWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}