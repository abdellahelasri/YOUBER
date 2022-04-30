package com.example.youber;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.Properties;
import java.util.Random;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class SignUpActivity extends AppCompatActivity {

    EditText textmail, textname,textpass,textconf;
    Button btnsend,btnsignup;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup_activity);
        textpass= (EditText)findViewById(R.id.textpass1);
        textconf= (EditText)findViewById(R.id.textpass2);
        textconf.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean h) {
                if (h==false)
                {
                    if (textpass.getText().toString().equals(textconf.getText().toString()));
                    else textconf.setError("Les deux mots passe sont différents");
                }

            }
        });
        textmail= (EditText)findViewById(R.id.textmail);
        textmail.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                String reg = "[a-zA-Z0-9._-]+@[a-z]+\\.[a-z]+";
                if (textmail.getText().toString().matches(reg)){
                    ;
                }
                else
                {
                    textmail.setError("Adresse mail invalide");
                }
            }
        });
        textname= (EditText)findViewById(R.id.textname);
        btnsend= (Button)findViewById(R.id.btnsend);


        btnsend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String reg = "[a-zA-Z0-9._-]+@[a-z]+\\.[a-z]+";
                if (textpass.getText().toString().equals(textconf.getText().toString())) {
                    if (textmail.getText().toString().matches(reg)) {
                        Random r = new Random();
                        int activation = r.nextInt((99999 - 11111 + 1) + 11111);
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                final String username = "your-email@gmail.com";
                                final String password = "xxxxxxxx";

                                Properties props = new Properties();
                                props.put("mail.smtp.auth", "true");
                                props.put("mail.smtp.starttls.enable", "true");
                                props.put("mail.smtp.host", "smtp.gmail.com");
                                props.put("mail.smtp.port", "587");

                                Session session = Session.getInstance(props,
                                        new javax.mail.Authenticator() {
                                            protected PasswordAuthentication getPasswordAuthentication() {
                                                return new PasswordAuthentication(username, password);
                                            }
                                        });
                                try {
                                    Message message = new MimeMessage(session);
                                    message.setFrom(new InternetAddress(username));
                                    message.setRecipients(Message.RecipientType.TO,
                                            InternetAddress.parse(textmail.getText().toString()));
                                    message.setSubject("Activation code : YOUBER");
                                    message.setText("Bonjour "+textname.getText().toString()+","
                                            + "\n\n Ton code d'activation est : "
                                            + activation+"\n\n Cordialement,"
                                            + "\n\nYOUBER© 2022");
                                    Transport.send(message);

                                    System.out.println("Done ");

                                } catch (MessagingException e) {
                                    throw new RuntimeException(e);
                                }

                            }
                        }).start();
                        Toast.makeText(getApplication(), "Activation has been sent successfully", Toast.LENGTH_LONG).show();
                    } else {
                        textmail.setError("Adresse mail invalide");
                    }
                }
                else {
                    textconf.setError("Les deux mots passe sont différents");
                }
            }
        });
    }
}
