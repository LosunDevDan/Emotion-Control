package com.losundev.emotioncontrol;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private Button sign_in;
    private RecyclerView recyclerView;
    private EventAdapter eventAdapter; // Adapter for displaying events
    private ArrayList<Event> eventsList; // List to store events

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        addListenerOnButton();
    }

    public void addListenerOnButton() {
        sign_in = (Button) findViewById(R.id.image_Button);
        sign_in.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent("com.losundev.emotioncontrol.MainCalendar");
                startActivity(intent);
                try {
                    FileInputStream fileInput = openFileInput("data.txt");
                    BufferedReader reader = new BufferedReader(new InputStreamReader(fileInput));
                    StringBuilder stringBuilder = new StringBuilder();
                    String line;

                    while ((line = reader.readLine()) != null) {
                        stringBuilder.append(line);
                    }

                    reader.close();
                    fileInput.close();

                    String data = stringBuilder.toString();

                    // Assuming that the data in the file represents the eventsList as a string
                    Event.eventsList = Event.parseEventsList(data);

                    // Initialize the eventAdapter with the updated eventsList
                    eventAdapter = new EventAdapter(getApplicationContext(), Event.eventsList);

                    // Set the eventAdapter to the RecyclerView
                    recyclerView.setAdapter(eventAdapter);

                    Toast.makeText(getApplicationContext(), "Data loaded successfully", Toast.LENGTH_LONG).show();
                } catch (FileNotFoundException e) {
                    throw new RuntimeException(e);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }


    public void onButtonClick(View v) {
        EditText password = (EditText) findViewById(R.id.password_Textfield);
        EditText username = (EditText) findViewById(R.id.username_Textfield);
        String username_fu = username.getText().toString();
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
                writeStringToFile(this, fileName, username_fu + "" + password_fu);
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

    private void displayEvents(ArrayList<Event> events) {
        // Assuming you have a RecyclerView with id "eventsRecyclerView"
        RecyclerView eventsRecyclerView = findViewById(R.id.eventsRecyclerView);

        // Set up the eventAdapter with the events list
        eventAdapter = new EventAdapter(events);

        // Set the eventAdapter to the eventsRecyclerView
        eventsRecyclerView.setAdapter(eventAdapter);

        // Set the layout manager for the eventsRecyclerView
        eventsRecyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    private void saveEventsToFile(ArrayList<Event> events, String fileName) {
        try {
            File file = new File(getFilesDir(), fileName);
            FileWriter fileWriter = new FileWriter(file, false);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);

            for (Event event : events) {
                // Write event details to the file
                String eventString = event.getName() + "," + event.getDate() + "," + event.getTime();
                bufferedWriter.write(eventString);
                bufferedWriter.newLine();
            }

            bufferedWriter.close();
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Method to load events from a file
    private ArrayList<Event> loadEventsFromFile(String fileName) {
        ArrayList<Event> events = new ArrayList<>();

        try {
            File file = new File(getFilesDir(), fileName);
            BufferedReader reader = new BufferedReader(new FileReader(file));
            String line;

            while ((line = reader.readLine()) != null) {
                // Split the line by the delimiter (assuming comma-separated values)
                String[] eventDetails = line.split(",");
                if (eventDetails.length == 3) {
                    // Create a new Event object from the loaded details
                    String name = eventDetails[0];
                    String date = eventDetails[1];
                    String time = eventDetails[2];
                    Event event = new Event(name, date, time);

                    // Add the event to the events list
                    events.add(event);
                }
            }

            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return events;
    }

}