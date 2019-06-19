package s.barana.bayrakquiz;

import android.Manifest;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {
    private Button buttonBasla;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        buttonBasla = findViewById(R.id.buttonBasla);

        veritabaniKopyala();

        buttonBasla.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this,QuizActivity.class));
            }
        });
    }

    public void veritabaniKopyala(){
        DatabaseCopyHelper copyHelper = new DatabaseCopyHelper(this);

        try {
            copyHelper.createDataBase();
        } catch (IOException e) {
            e.printStackTrace();
        }

        copyHelper.openDataBase();
    }
}
