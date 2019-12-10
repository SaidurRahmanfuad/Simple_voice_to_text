package com.saidur.voice_to_text;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.speech.RecognizerIntent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private final int request_code_speach_input = 200;

    TextView speach_to_text;
    Spinner language_selector;

    String[] all_languages = {"Bengali","English", "Hindi","Urdu", "Arabi", "Russia", "Japan"} ;
    String selectedlanguage = "bn" ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.stoolbar);
        setSupportActionBar(toolbar);
        speach_to_text = findViewById(R.id.language);
        language_selector = findViewById(R.id.spinner);

        ArrayAdapter spinnerAdapter = new ArrayAdapter(MainActivity.this,android.R.layout.simple_spinner_item,all_languages);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        language_selector.setAdapter(spinnerAdapter);

        language_selector.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                switch (i){
                    case 0: selectedlanguage = "bn" ;
                        break;
                    case 1: selectedlanguage = "en" ;
                        break;
                    case 2: selectedlanguage = "hi" ;
                        break;
                    case 3: selectedlanguage = "ur" ;
                        break;
                    case 4: selectedlanguage = "ar" ;
                        break;
                    case 5: selectedlanguage = "ru" ;
                        break;
                    case 6: selectedlanguage = "jp" ;
                        break;

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.mike);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                promptSpeechInput();
            }
        });
    }


    public void promptSpeechInput(){
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, selectedlanguage);
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT,
                getString(R.string.speech_show));
        try {
            startActivityForResult(intent, request_code_speach_input);
        } catch (ActivityNotFoundException a) {
            Toast.makeText(getApplicationContext(),
                    getString(R.string.speech_not_supported),
                    Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case request_code_speach_input: {
                if (resultCode == RESULT_OK && null != data) {

                    ArrayList<String> result = data
                            .getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    speach_to_text.setText(result.get(0));
                }
                break;
            }

        }
    }

}
