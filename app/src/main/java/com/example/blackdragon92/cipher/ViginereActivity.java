package com.example.blackdragon92.cipher;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.Crypto.blackdragon92.cipher.R;

public class ViginereActivity extends AppCompatActivity {

    EditText inputText, inputPassword;
    TextView outputText;
    Button encButton, decButton;
    String output;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_viginiere);
    }

    public void onClickButtonListener(){
        encButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                try {
                    output = encrypt(inputText.getText().toString(), inputPassword.getText().toString());
                    outputText.setText(output);
                }
                catch(Exception e){
                    e.printStackTrace();
                }
            }
        });
    }

    public void onClickButtonListener2()
    {
        decButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                try {
                    output = decrypt(output, inputPassword.getText().toString());
                }
                catch(Exception e){
                    outputText.setText(output);

                }
            }
        });
    }

    public static String encrypt(String text, final String key){
        String cipher = "";
        text = text.toUpperCase();
        for(int i = 0, j = 0; i < text.length(); i++){
            char c = text.charAt(i);
            if(c < 'A' || c > 'Z'){
                continue;
            }

            cipher += (char)((c + key.charAt(j) - 2 * 'A') % 26 + 'A');
            j = ++j % key.length();
        }

        return cipher;
    }

    static String decrypt(String text, final String key) {
        String plaintext = "";
        text = text.toUpperCase();
        for (int i = 0, j = 0; i < text.length(); i++) {
            char c = text.charAt(i);
            if (c < 'A' || c > 'Z') continue;
            plaintext += (char)((c - key.charAt(j) + 26) % 26 + 'A');
            j = ++j % key.length();
        }
        return plaintext;
    }
}


