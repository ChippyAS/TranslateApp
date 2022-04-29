package com.example.translateapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.res.Configuration;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.speech.tts.TextToSpeech;
import android.text.Editable;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.translateapp.databinding.ActivityMainBinding;

import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    TextToSpeech textToSpeech;
    Editable input;
    static final String SOME_VALUE = "given_value";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //using viewbinding to link java variables with xml components
        ActivityMainBinding binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        //getting the input we have passed
        input = binding.editinput.getText();

        //on image click, if the word has a vowel then it is converted and printed in the textoutput
        //else a toast is shown with a message that piglatin is not possible
        binding.imageconvert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String inputvalue = String.valueOf(input);

                if (!(inputvalue.length() == 0)) {

                    String value = pigLatinWord(inputvalue);
                    if (value == "-1")
                        Toast.makeText(MainActivity.this, "Pig Latin is not possible, the word must have at least a vowel.", Toast.LENGTH_SHORT).show();
                    else
                        binding.textoutput.setText(value);

                } else {
                    Toast.makeText(MainActivity.this, "Provide an input", Toast.LENGTH_SHORT).show();
                }

            }
        });

        //clear the text in both editinput and textoutput
        binding.imageclear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                binding.editinput.getText().clear();
                binding.textoutput.setText(" ");

            }
        });

        // create an object textToSpeech and import feautures to it
        textToSpeech = new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int i) {

                // if No error is found then only it will run
                if (i != TextToSpeech.ERROR) {
                    // choose language of speech
                    textToSpeech.setLanguage(Locale.UK);
                }
            }
        });

        binding.imagespeak.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String outputvalue = binding.textoutput.getText().toString();
                Toast.makeText(MainActivity.this, outputvalue, Toast.LENGTH_SHORT).show();
                //Queue mode where all entries in the playback queue are dropped and replaced by the new entry.
                textToSpeech.speak(outputvalue, TextToSpeech.QUEUE_FLUSH, null);

            }
        });

        // Retrieving value
        if (savedInstanceState != null) {
            binding.editinput.setText(savedInstanceState.getString(SOME_VALUE));
        }

    }

    //checking condition if in inputvalue vowel is present
    static boolean isVowel(char ch) {
        return (ch == 'A' || ch == 'a' || ch == 'E' || ch == 'e' || ch == 'I' || ch == 'i' || ch == 'O' || ch == 'o' || ch == 'U' || ch == 'u');
    }


    //method where word or string is converted to piglatin
    static String pigLatinWord(String string) {
        //the index of the first vowel is stored
        int stringlength = string.length();
        int index = -1;
        for (int i = 0; i < stringlength; i++) {
            if (isVowel(string.charAt(i))) {
                index = i;
                break;
            }
        }
        //Pig Latin is possible only if vowels is present
        if (index == -1)
            return "-1";
        // Take all characters after index (including index). Append all characters which are before index.
        return string.substring(index) + string.substring(0, index) + "ay";


    }

    // Saving State
    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        // Save custom values into the bundle
        outState.putString(SOME_VALUE, String.valueOf(input));
        //  call the superclass so it can save the view hierarchy state
        super.onSaveInstanceState(outState);
    }


    //checks the device orientation
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);

        // Checks the orientation of the screen
        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            Toast.makeText(this, "landscape", Toast.LENGTH_SHORT).show();
        } else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT) {
            Toast.makeText(this, "portrait", Toast.LENGTH_SHORT).show();
        }
    }
}
