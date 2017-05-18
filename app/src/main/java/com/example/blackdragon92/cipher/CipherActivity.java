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

public class CipherActivity extends AppCompatActivity {

    EditText inputText, inputPassword;
    TextView outputText;
    Button encButton, decButton;
    String output;
    String AES = "AES";
    protected ciph cipher;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cipher);

        inputText =(EditText) findViewById(R.id.inputText);
        inputPassword = (EditText) findViewById(R.id.password);

        outputText = (TextView) findViewById(R.id.outputText);
        encButton = (Button) findViewById(R.id.encButton);
        decButton = (Button) findViewById(R.id.decButton);

        onClickButtonListener();

        onClickButtonListener2();
    }

    /**
     * AES Encryption/Decryption
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
