package s.barana.bayrakquiz;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashSet;

public class QuizActivity extends AppCompatActivity {
    private TextView textViewDogru,textViewYanlis,textViewSoruSayi;
    private ImageView ımageViewBayrak;
    private Button buttonA,buttonB,buttonC,buttonD;
    //database den çektiğim nesneleri aldım...
    private ArrayList<Bayraklar> sorular;
    private ArrayList<Bayraklar> yanlisSecenekler;
    //anlık olarak doğru soruyu elimde tutabilmek için!
    private Bayraklar dogruSoru;
    private VeriTabani vt;
    //Soru sayaçları
    private int soruSayac = 0 ;
    private int dogruSayac = 0;
    private int yanlisSayac = 0;
    //seçenekleri karıştırmak için

    //hash set ile karıştırdım.
    private HashSet<Bayraklar> secenekleriKaristirmaListe = new HashSet<>();
    //karışik listeyi tek tek alabilmek için array list tanımladım index index alabilmek için.
    private ArrayList<Bayraklar> secenekler = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        textViewDogru = findViewById(R.id.textViewDogru);
        textViewYanlis = findViewById(R.id.textViewYanlis);
        textViewSoruSayi = findViewById(R.id.textViewSoruSayisi);
        ımageViewBayrak = findViewById(R.id.imageViewBayrak);
        buttonA = findViewById(R.id.buttonA);
        buttonB = findViewById(R.id.buttonB);
        buttonC = findViewById(R.id.buttonC);
        buttonD = findViewById(R.id.buttonD);

        vt = new VeriTabani(this);

        sorular = new BayraklarDao().rasgele5getir(vt);

        soruYukle();

        buttonA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dogruKontrol(buttonA);
                soruSayacKontrol();
                Log.e("SAYAC",String.valueOf(soruSayac));
            }
        });

        buttonB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dogruKontrol(buttonB);
                soruSayacKontrol();
            }
        });

        buttonC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dogruKontrol(buttonC);
                soruSayacKontrol();
            }
        });

        buttonD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dogruKontrol(buttonD);
                soruSayacKontrol();
            }
        });
    }

    public void soruYukle(){
        textViewSoruSayi.setText((soruSayac+1)+". SORU");
        textViewDogru.setText("Doğru : "+(dogruSayac));
        textViewYanlis.setText("Yanlış : "+(yanlisSayac));

        dogruSoru = sorular.get(soruSayac);

        yanlisSecenekler = new BayraklarDao().rasgele3YanlisSecenekGetir(vt,dogruSoru.getBayrak_id());

        ımageViewBayrak.setImageResource(getResources().getIdentifier(dogruSoru.getBayrak_resim()
                ,"drawable",getPackageName()));

        //Tüm secenekleri hashset yardımıyla karıştırma
        secenekleriKaristirmaListe.clear();
        secenekleriKaristirmaListe.add(dogruSoru);//Doğru secenek
        secenekleriKaristirmaListe.add(yanlisSecenekler.get(0));
        secenekleriKaristirmaListe.add(yanlisSecenekler.get(1));
        secenekleriKaristirmaListe.add(yanlisSecenekler.get(2));

        //Hashset ile butonlara dinamik şekilde yazı yazdıramadığımızdan arraylist dönüşümü yaptık.
        secenekler.clear();

        for(Bayraklar b: secenekleriKaristirmaListe){
            secenekler.add(b);
        }
        //Secenekleri buttonlara yerleştirdik.
        buttonA.setText(secenekler.get(0).getBayrak_ad());
        buttonB.setText(secenekler.get(1).getBayrak_ad());
        buttonC.setText(secenekler.get(2).getBayrak_ad());
        buttonD.setText(secenekler.get(3).getBayrak_ad());

    }

    public void dogruKontrol(Button button){

        String buttonYazi = button.getText().toString();
        String dogruCevap = dogruSoru.getBayrak_ad();
        Log.e("Doğru",dogruCevap);
        Log.e("ButtonYazi",buttonYazi);

        if(buttonYazi.equals(dogruCevap)){
            dogruSayac++;
        }else{
            yanlisSayac++;
        }

        textViewDogru.setText("Doğru : "+(dogruSayac));
        textViewYanlis.setText("Yanlış : "+(yanlisSayac));
    }

    public void soruSayacKontrol(){

        soruSayac++;

        //soru sayısı 5 olduysa sonuca git
        if(soruSayac != 5){
            soruYukle();
        }else{
            Intent i = new Intent(QuizActivity.this,ResultActivity.class);
            i.putExtra("dogruSayac",dogruSayac);
            startActivity(i);
            finish();
        }
    }
}
