package com.Crypto.blackdragon92.cipher;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import java.security.MessageDigest;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

public class CaesarCipherActivity extends AppCompatActivity {

    EditText key, password;
    TextView outputText;
    Button encButton, decButton;
    String stringOutput;
    public static final String ALPHABET = "abcdefghijklmnopqrstuvwxyz";

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_caesar_cipher);

        key =(EditText) findViewById(R.id.password);
        password = (EditText) findViewById(R.id.password);

        outputText = (TextView) findViewById(R.id.outputText);
        encButton = (Button) findViewById(R.id.encButton);
        decButton = (Button) findViewById(R.id.decButton);

        onClickButtonListener();

        onClickButtonListener2();
    }

    public void onClickButtonListener(){
        encButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                String editValue = password.getText().toString();
                int shift = Integer.parseInt(editValue);

                try {
                    stringOutput = encrypt(key.getText().toString(), shift);
                    outputText.setText(stringOutput);
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
                String editValue = password.getText().toString();
                int shift = Integer.parseInt(editValue);

                try {
                    stringOutput = decrypt(key.getText().toString(), shift);
                }
                catch(Exception e){
                    outputText.setText(stringOutput);

                }
            }
        });
    }

    private String encrypt(String message, int shift){
        message = message.toLowerCase();
        String cipherText = " ";

        for(int i = 0; i < message.length(); i++){
            int pos = ALPHABET.indexOf(message.charAt(i));
            int newShift = (shift + pos)%26;
            char shiftValue = ALPHABET.charAt(newShift);
            cipherText += shiftValue;
        }

        return cipherText;
    }

    private String decrypt(String cipher, int shift){
        cipher = cipher.toLowerCase();
        String message = "";

        for(int i = 0; i < cipher.length(); i++){
            int pos = ALPHABET.indexOf(i);
            int newShift = (pos - shift)%26;

            if(newShift < 0){
                newShift = ALPHABET.length() + newShift;
            }

            char shiftValue = ALPHABET.charAt(newShift);
            message += shiftValue;

        }

        return message;
    }


}
