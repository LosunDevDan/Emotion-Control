package com.losundev.emotioncontrol;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;

public class Event
{
    public static ArrayList<Event> eventsList = new ArrayList<>();

    public static ArrayList<Event> eventsForDate(LocalDate date)
    {
        ArrayList<Event> events = new ArrayList<>();

        for(Event event : eventsList)
        {
            if(event.getDate().equals(date))
                events.add(event);
        }

        return events;
    }


    public static ArrayList<Event> eventsForDateAndTime(LocalDate date, LocalTime time)
    {
        ArrayList<Event> events = new ArrayList<>();

        for(Event event : eventsList)
        {
            int eventHour = event.time.getHour();
            int cellHour = time.getHour();
            if(event.getDate().equals(date) && eventHour == cellHour)
                events.add(event);
        }

        return events;
    }

    public static ArrayList<Event> parseEventsList(String data) {
        ArrayList<Event> eventsList = new ArrayList<>();

        // Parse the data string and create Event objects
        // Example: Assuming data is in the format: "eventName,date,time;eventName,date,time;..."
        String[] eventStrings = data.split(";");
        for (String eventString : eventStrings) {
            String[] eventParts = eventString.split(",");
            if (eventParts.length == 3) {
                String eventName = eventParts[0];
                LocalDate date = LocalDate.parse(eventParts[1]);
                LocalTime time = LocalTime.parse(eventParts[2]);

                Event event = new Event(eventName, date, time);
                eventsList.add(event);
            }
        }

        return eventsList;
    }



    private String name;
    private LocalDate date;
    private LocalTime time;

    public Event(String name, LocalDate date, LocalTime time)
    {
        this.name = name;
        this.date = date;
        this.time = time;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public LocalDate getDate()
    {
        return date;
    }

    public void setDate(LocalDate date)
    {
        this.date = date;
    }

    public LocalTime getTime()
    {
        return time;
    }

    public void setTime(LocalTime time)
    {
        this.time = time;
    }
}
