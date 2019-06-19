package s.barana.bayrakquiz;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class ResultActivity extends AppCompatActivity {
    private TextView textViewSonuc,textViewYuzdeSonuc;
    private Button buttonTekrar;
    private int dogruSayac;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        textViewSonuc = findViewById(R.id.textViewSonuc);
        textViewYuzdeSonuc = findViewById(R.id.textViewYüzdeSonuc);
        buttonTekrar = findViewById(R.id.buttonTekrar);

        dogruSayac = getIntent().getIntExtra("dogruSayac",0);

        textViewSonuc.setText(dogruSayac+" DOĞRU "+(5-dogruSayac)+" YANLIŞ");
        textViewYuzdeSonuc.setText("% "+(dogruSayac*100)/5+" Başarı");

        buttonTekrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ResultActivity.this,QuizActivity.class));
                finish();
            }
        });
    }
}
