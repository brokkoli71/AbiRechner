package com.example.hannes.abirechner;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private EditText[] editTexts = new EditText[11];
    private TextView textView, textView2, textView3, textView5, textView6,textView7, textView8, titel1, titel2;
    int[][] noten = new int[5][11];
    int angezeigteSeite = 0;
    int schlechtesteNote = 50;
    int schlechtesteNote2 = 50;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView = findViewById(R.id.textView3);
        textView2 = findViewById(R.id.textView4);
        textView5 = findViewById(R.id.textView5);
        textView6 = findViewById(R.id.textView6);
        textView7 = findViewById(R.id.textView7);
        textView8 = findViewById(R.id.textView8);
        titel1 = findViewById(R.id.textView);
        titel2 = findViewById(R.id.textView2);


        int id = 0;
        for (int i=0; i<11; i++){
            if(i==0) {id = R.id.editText1;}
            if(i==1) {id = R.id.editText2;}
            if(i==2) {id = R.id.editText3;}
            if(i==3) {id = R.id.editText4;}
            if(i==4) {id = R.id.editText5;}
            if(i==5) {id = R.id.editText6;}
            if(i==6) {id = R.id.editText7;}
            if(i==7) {id = R.id.editText8;}
            if(i==8) {id = R.id.editText9;}
            if(i==9) {id = R.id.editText10;}
            if(i==10) {id = R.id.editText11;}

            editTexts[i] = findViewById(id);
        }
    }

        public void naechsteSeite (View view) {
            if(angezeigteSeite == 0|| angezeigteSeite == 4)
                textView7.setVisibility(View.GONE);

            if(angezeigteSeite == 3)
                textView2.setText("Abiturprüfungen");

            if(angezeigteSeite<4) {
                for (int i = 0; i < editTexts.length; i++) {
                    try{
                    noten[angezeigteSeite][i] = Integer.parseInt(editTexts[i].getText().toString());
                    }catch (NumberFormatException e){Toast.makeText(getApplicationContext(), "nicht alles ausgefüllt",Toast.LENGTH_LONG).show();
                        noten[angezeigteSeite][i] = 0;}

                    editTexts[i].setText("");
                    if (noten[angezeigteSeite + 1][i] != 0)
                        editTexts[i].setText(noten[angezeigteSeite + 1][i]);
                }
                angezeigteSeite++;
                Toast.makeText(getApplicationContext(), Integer.toString(angezeigteSeite),Toast.LENGTH_LONG).show();
            }

            if (angezeigteSeite == 4){
                textView2.setText("");
                for (int i = 0; i < editTexts.length; i++) {
                    try{
                        noten[angezeigteSeite][i] = Integer.parseInt(editTexts[i].getText().toString());
                    }catch (NumberFormatException e){Toast.makeText(getApplicationContext(), "nicht alles ausgefüllt",Toast.LENGTH_LONG).show();
                        noten[angezeigteSeite][i] = 0;}

                    editTexts[i].setText("");
                }
                angezeigteSeite++;
                textView5.setVisibility(View.VISIBLE);
                textView6.setVisibility(View.VISIBLE);
                titel1.setVisibility(View.GONE);
                titel2.setVisibility(View.GONE);
                for (int i = 0; i < editTexts.length; i++) {
                    if (i<5||i>8)
                        editTexts[i].setVisibility(View.INVISIBLE);
                }
                textView.setText("für NC\ntippen");
            }


            }



    public void schnittBerechnen (View view){
       // try {
        if (angezeigteSeite < 4) {
            for (int i = 0; i < editTexts.length; i++) {
                try {
                noten[angezeigteSeite][i] = Integer.parseInt(editTexts[i].getText().toString());
            }catch (NumberFormatException e){Toast.makeText(getApplicationContext(), "nicht alles ausgefüllt",Toast.LENGTH_LONG).show();
                noten[angezeigteSeite][i] = 0;}
            }

            int summe = 0;

            for (int j = 0; j <= angezeigteSeite; j++) {
                for (int i = 0; i < 3; i++) {
                    summe += 2 * noten[j][i];
                }
                for (int i = 3; i < 11; i++) {
                    summe += noten[j][i];
                }

            }

            summe /= angezeigteSeite+1;
            float zensur = 17f / 3f - (summe / 42f);
            textView.setText(((float) summe / 14) + " Punkte = " + zensur);
        }
        //"echter" NC
        else{
            for (int i = 0; i < 4; i++) {
                try {
                noten[4][i] = Integer.parseInt(editTexts[i+5].getText().toString());
                }catch (NumberFormatException e){Toast.makeText(getApplicationContext(), "nicht alles ausgefüllt",Toast.LENGTH_LONG).show();
                    noten[4][i] = 0;}
            }

            int gesamtpunkte = -100;//initialisierung von schlechtesteNoten abziehen

            //berechnung
            int a;
            for (int i = 3; i < 11; i++) {
                for (int j = 0; j < 4; j++) {
                    if (noten[j][i] < schlechtesteNote) {
                        //tauschen
                        a = noten[j][i];
                        noten[j][i] = schlechtesteNote;
                        schlechtesteNote = a;
                    }
                    if (noten[j][i] < schlechtesteNote2) {
                        //tauschen
                        a = noten[j][i];
                        noten[j][i] = schlechtesteNote2;
                        schlechtesteNote2 = a;
                    }
                    gesamtpunkte += noten[j][i];

                }
            }

            //schriftl. abifächer qualifikationsphase
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 4; j++) {
                    gesamtpunkte += noten[j][i] * 2;
                }
            }
            gesamtpunkte/= 54;
            gesamtpunkte = Math.round(gesamtpunkte * 40);
            //schriftl. abifächer
            for (int i = 0; i < 4; i++) {
                gesamtpunkte += noten[4][i] * 5;
            }


            double ungerundet = ((900d-78d-gesamtpunkte)/180d)+1.05d;


            double schnitt = Math.round((ungerundet)*10)/10d;
            if (schnitt < 1) schnitt = 1d;

            textView8.setText("Tendenz: " + (Math.round((ungerundet)*1000)/1000d));
            textView8.setVisibility(View.VISIBLE);

            textView.setText("" + gesamtpunkte + " = " + schnitt);

        }
    } //catch (NumberFormatException e){
     //       Toast.makeText(getApplicationContext(), "nicht alles ausgefüllt",Toast.LENGTH_LONG).show();}}

    public void hochrechnen (View view){
        for (int j = 0; j < 4; j++) {
            for (int i = 0; i < editTexts.length; i++) {
                try {
                    noten[j][i] = Integer.parseInt(editTexts[i].getText().toString());
                } catch (NumberFormatException e) {
                    Toast.makeText(getApplicationContext(), "nicht alles ausgefüllt", Toast.LENGTH_LONG).show();
                    noten[j][i] = 0;
                }
            }
        }
        angezeigteSeite = 4;
        naechsteSeite(view);
    }


    }