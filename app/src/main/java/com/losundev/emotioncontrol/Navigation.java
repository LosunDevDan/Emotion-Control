package com.losundev.emotioncontrol;

import android.content.Intent;
import android.view.View;
import android.widget.Button;

public class Navigation {

    private Button tomenu;
    private Button tostat;
    private Button taadvice;

    public void addListenerOnButton(){
        tomenu = (Button) findViewById(R.id.main_btn);
        tomenu.setOnClickListener(
                new View.OnClickListener(){
                    @Override
                    public void onClick (View v){
                        Intent intent = new Intent("com.losundev.emotioncontrol.MainCalendar");
                        startActivity(intent);
                    }

                    private void startActivity(Intent intent) {
                    }
                });
    }

    private Object findViewById(int menu_btn) {
        return null;
    }
}
