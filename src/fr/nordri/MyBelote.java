package fr.nordri;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import fr.nordri.game.Belote;

public class MyBelote extends Activity {
    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        this.setTitle("Belote v1");

        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        this.initButtonMyMenu();
    }

    @Override
    protected void onDestroy() {

        super.onDestroy();
    }

    //Méthode qui se déclenchera lorsque vous appuierez sur le bouton menu du téléphone
    public boolean onCreateOptionsMenu(Menu menu) {

        //Création d'un MenuInflater qui va permettre d'instancier un Menu XML en un objet Menu
        MenuInflater inflater = getMenuInflater();
        //Instanciation du menu XML spécifier en un objet Menu
        inflater.inflate(R.layout.menu, menu);

        return true;
    }

    //Méthode qui se déclenchera au clic sur un item
    public boolean onOptionsItemSelected(MenuItem item) {
        //On regarde quel item a été cliqué grâce à son id et on déclenche une action
        switch (item.getItemId()) {
            case R.id.m_scores:
                //lancer
                Intent intent = new Intent(MyBelote.this, MyScores.class);
                startActivity(intent);
                return true;
            case R.id.m_quitter:
                //Pour fermer l'application il suffit de faire finish()
                finish();
                return true;
        }
        return false;
    }

    public void initButtonMyMenu(){
        Button button_m_mquitter = (Button) findViewById(R.id.m_mquitter);
        button_m_mquitter.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                finish();
            }
        });

        Button button_m_mscores = (Button) findViewById(R.id.m_mscores);
        button_m_mscores.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(MyBelote.this, MyScores.class);
                startActivity(intent);
            }
        });

        Button button_m_play = (Button) findViewById(R.id.m_play);
        button_m_play.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(MyBelote.this, MyPlay.class);
                startActivity(intent);
            }
        });
    }


}
