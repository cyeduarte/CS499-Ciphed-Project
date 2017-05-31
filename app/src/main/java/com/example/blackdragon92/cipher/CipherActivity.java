package com.Crypto.blackdragon92.cipher;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.Crypto.blackdragon92.cipher.MainActivity;

import java.security.MessageDigest;
import java.util.ArrayList;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

//keystore identification: https://developer.android.com/training/articles/keystore.html
public class CipherActivity extends AppCompatActivity {

    EditText inputText, inputPassword, phoneNumber;
    TextView outputText;
    Button encButton, decButton, sendButton, emailButton;
    String output;
    String AES = "AES";
    SmsManager smsManager = SmsManager.getDefault();
    //protected Ciph cipher;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cipher);

        if(ContextCompat.checkSelfPermission(CipherActivity.this, Manifest.permission.SEND_SMS)
                != PackageManager.PERMISSION_GRANTED)
        {
            if(ActivityCompat.shouldShowRequestPermissionRationale(CipherActivity.this,
                    Manifest.permission.SEND_SMS))
            {
                ActivityCompat.requestPermissions(CipherActivity.this,
                        new String[]{Manifest.permission.SEND_SMS}, 1);
            }
            else
            {
                ActivityCompat.requestPermissions(CipherActivity.this,
                        new String[]{Manifest.permission.SEND_SMS}, 1);
            }
        }
        else
        {
            //do nothing
        }

        inputText =(EditText) findViewById(R.id.inputText);
        inputPassword = (EditText) findViewById(R.id.password);
        phoneNumber = (EditText) findViewById(R.id.phoneNumber);

        outputText = (TextView) findViewById(R.id.outputText);
        encButton = (Button) findViewById(R.id.encButton);
        decButton = (Button) findViewById(R.id.decButton);
        sendButton = (Button) findViewById(R.id.Send);
        emailButton = (Button) findViewById(R.id.email);

        onClickButtonListener();

        onClickButtonListener2();

        sendOnClickListener();
    }

    //@Override
    public void onRequestPermission(int requestCode, String[] permission, int[] grantResults)
    {
        switch(requestCode)
        {
            case 1:{
                if(grantResults.length > 0 && grantResults[0] ==  PackageManager.PERMISSION_GRANTED)
                {
                    if(ContextCompat.checkSelfPermission(CipherActivity.this,
                            Manifest.permission.SEND_SMS) == PackageManager.PERMISSION_GRANTED)
                    {
                        Toast.makeText(this, "permission granted!", Toast.LENGTH_SHORT).show();
                    }
                }
                else
                {
                    Toast.makeText(this, "permission not granted!", Toast.LENGTH_SHORT).show();
                }
                return;
            }


        }
    }


    public void sendOnClickListener()
    {
        sendButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){

                String number = phoneNumber.getText().toString();
                String  sms = outputText.getText().toString();

                try{
                    SmsManager smsManager = SmsManager.getDefault();
                    smsManager.sendTextMessage(number, null, sms, null, null);
                    Toast.makeText(CipherActivity.this, "Sent!", Toast.LENGTH_SHORT).show();
                }
                catch(Exception e){
                    Toast.makeText(CipherActivity.this, "Failed!", Toast.LENGTH_SHORT).show();

                }

            }
        });
    }

    public void emailOnClickListener()
    {
        emailButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){

                Intent intent = null, chooser = null;

                try{
                    intent = new Intent(Intent.ACTION_SEND);
                    intent.setData(Uri.parse("mailto:"));
                    String[] to = {phoneNumber.getText().toString()};
                    intent.putExtra(Intent.EXTRA_EMAIL, to);
                    intent.putExtra(Intent.EXTRA_SUBJECT, " ");
                    intent.putExtra(Intent.EXTRA_TEXT, outputText.getText().toString());
                    intent.setType("message/rfc822");
                    chooser = Intent.createChooser(intent, "Send Email");
                    startActivity(chooser);
                }
                catch(Exception e){
                    Toast.makeText(CipherActivity.this, "Failed!", Toast.LENGTH_SHORT).show();

                }

            }
        });
    }

    public void sendEmail(View v)
    {
        Intent intent = null, chooser = null;

        if(v.getId() == R.id.email)
        {
            intent = new Intent(Intent.ACTION_SEND);
            intent.setData(Uri.parse("mailto:"));
            String[] to = {phoneNumber.getText().toString()};
            intent.putExtra(Intent.EXTRA_EMAIL, to);
            intent.putExtra(Intent.EXTRA_SUBJECT, " ");
            intent.putExtra(Intent.EXTRA_TEXT, outputText.getText().toString());
            intent.setType("message/rfc822");
            chooser = Intent.createChooser(intent, "Send Email");
            startActivity(chooser);
        }
    }

    /**
     * AES Encryption/Decryption button events
     */

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

    /**
     * Encryption/Decryption algorithms
     */

    private String encrypt(String data, String password) throws Exception
    {
        SecretKeySpec key = generateKey(password);
        Cipher c = Cipher.getInstance(AES);
        c.init(Cipher.ENCRYPT_MODE, key);
        byte[] encVal = c.doFinal(data.getBytes());
        String encryptedValue = Base64.encodeToString(encVal, Base64.DEFAULT);
        return encryptedValue;
    }

    private String decrypt(String output, String password) throws Exception{
        SecretKeySpec key = generateKey(password);
        Cipher c = Cipher.getInstance(AES);
        c.init(Cipher.DECRYPT_MODE, key);
        byte[] decodedValue = Base64.decode(output, Base64.DEFAULT);
        byte[] decValue = c.doFinal(decodedValue);
        String decryptedValue = new String(decValue);
        return decryptedValue;
    }

    private SecretKeySpec generateKey(String password)throws Exception
    {
        final MessageDigest digest = MessageDigest.getInstance("SHA-256");
        byte[] bytes = password.getBytes("UTF-8");
        digest.update(bytes, 0, bytes.length);
        byte[] key = digest.digest();
        SecretKeySpec secretKeySpec = new SecretKeySpec(key, "AES");
        return secretKeySpec;
    }

}
