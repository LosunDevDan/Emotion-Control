package com.losundev.emotioncontrol;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalTime;

public class EventEditActivity extends AppCompatActivity {
    private TextView eventDateTV, eventTimeTV;
    private LocalTime time;
    private Spinner spinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_edit);
        initWidgets();
        time = LocalTime.now();
        eventDateTV.setText("Date: " + CalendarUtils.formattedDate(CalendarUtils.selectedDate));
        eventTimeTV.setText("Time: " + CalendarUtils.formattedTime(time));

        spinner = findViewById(R.id.spinner);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedItem = parent.getItemAtPosition(position).toString();
                // Process the selected item
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Process if nothing is selected
            }
        });
    }

    // Rest of your code...

    public void saveEventAction(View view) {
        String eventName = spinner.getSelectedItem().toString();
        Event newEvent = new Event(eventName, CalendarUtils.selectedDate, time);
        Event.eventsList.add(newEvent); // Add newEvent to the eventsList

        try {
            FileOutputStream fileOutput = openFileOutput("data.txt", MODE_PRIVATE);
            String data = Event.eventsList.toString(); // Convert eventsList to a string
            fileOutput.write(data.getBytes());
            fileOutput.close();
            Toast.makeText(this, "Saved", Toast.LENGTH_LONG).show();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        finish();
    }

    private void initWidgets() {
        spinner = findViewById(R.id.spinner);
        eventDateTV = findViewById(R.id.eventDateTV);
        eventTimeTV = findViewById(R.id.eventTimeTV);
    }
}
