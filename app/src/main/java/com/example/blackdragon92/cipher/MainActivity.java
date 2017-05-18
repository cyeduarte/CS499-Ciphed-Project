package com.Crypto.blackdragon92.cipher;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

public class MainActivity extends AppCompatActivity {

    private static ImageButton encryptButton;
    private static ImageButton caesarButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        onClickButtonListener();
        onClickButtonListener2();
    }

    public void onClickButtonListener(){
        encryptButton =(ImageButton)findViewById(R.id.cipher);
        encryptButton.setOnClickListener(
            new View.OnClickListener(){
                @Override
                public void onClick(View v){
                    Intent intent = new Intent("com.Crypto.blackdragon92.cipher.CipherActivity");
                    startActivity(intent);
                }
            }
        );
    }

    public void onClickButtonListener2(){
        caesarButton = (ImageButton)findViewById(R.id.caesarButton);
        caesarButton.setOnClickListener(
                new View.OnClickListener(){
                    @Override
                    public void onClick(View v){
                        Intent intent = new Intent("com.Crypto.blackdragon92.cipher.CaesarCipherActivity");
                        startActivity(intent);
                    }
                }
        );
    }
}
