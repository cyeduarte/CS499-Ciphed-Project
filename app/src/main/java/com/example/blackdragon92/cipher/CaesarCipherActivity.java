package com.Crypto.blackdragon92.cipher;

import android.content.Intent;
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
import android.telephony.SmsManager;

import android.util.Log;
import android.view.Menu;

import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class CaesarCipherActivity extends AppCompatActivity {

    EditText key, password, email, message;
    TextView outputText;
    Button encButton, decButton, sendButton;
    String stringOutput;
    public static final String ALPHABET = "abcdefghijklmnopqrstuvwxyz";

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_caesar_cipher);

        email  = (EditText) findViewById(R.id.email);
        key =(EditText) findViewById(R.id.password);
        password = (EditText) findViewById(R.id.password);
        message = (EditText) findViewById(R.id.inputText);

        outputText = (TextView) findViewById(R.id.outputText);
        encButton = (Button) findViewById(R.id.encButton);
        decButton = (Button) findViewById(R.id.decButton);
        sendButton = (Button) findViewById(R.id.Send);

        onClickButtonListener();

        onClickButtonListener2();

        send(); //possible runtime error
    }

    public void onClickButtonListener(){
        encButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                String editValue = key.getText().toString();
                int shift = Integer.parseInt(editValue);

                try {
                    stringOutput = encrypt(message.getText().toString(), shift);
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
                String editValue = key.getText().toString();
                int shift = Integer.parseInt(editValue);

                try {
                    stringOutput = decrypt(message.getText().toString(), shift);
                    outputText.setText(stringOutput);

                }
                catch(Exception e){
                    e.printStackTrace();
                }
            }
        });
    }

    public void send()
    {
        sendButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                String phoneNo = email.getText().toString();
                String sms = outputText.getText().toString();

                try {
                    SmsManager smsManager = SmsManager.getDefault();
                    smsManager.sendTextMessage(phoneNo, null, sms, null, null);
                    Toast.makeText(getApplicationContext(), "SMS Sent!",
                            Toast.LENGTH_LONG).show();
                } catch (Exception e) {
                    Toast.makeText(getApplicationContext(),
                            "SMS faild, please try again later!",
                            Toast.LENGTH_LONG).show();
                    e.printStackTrace();
                }

            }
        });
    }

    public void sendByEmail(){
        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            //https://www.youtube.com/watch?v=O0wrbfKuDEA
            public void onClick(View v) {
                Intent emailIntent = new Intent(Intent.ACTION_SEND);

                emailIntent.putExtra(Intent.EXTRA_EMAIL, new String[]{""});
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
            int pos = ALPHABET.indexOf(cipher.charAt(i));
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
