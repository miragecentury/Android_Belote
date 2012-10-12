package fr.nordri;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.*;
import fr.nordri.game.sqLite.Score;
import fr.nordri.game.sqLite.ScoreBDD;

import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: miragecentury
 * Date: 10/10/12
 * Time: 10:12
 * To change this template use File | Settings | File Templates.
 */
public class MyScores extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setTitle("Belote v1 - Score");
        setContentView(R.layout.scores);

        ScoreBDD ScoreBdd = new ScoreBDD(this);
        ScoreBdd.open();
        ArrayList<Score> scores = ScoreBdd.getScores();
        ScoreBdd.close();

        ListView lstscores = (ListView)  this.findViewById(this.getResources().getIdentifier("scores","id",this.getPackageName()));

        ArrayList<String> lstString = new ArrayList<String>();

        for(int i = 0; i< scores.size(); i++){
            lstString.add(scores.get(i).toString());
            Log.e("Belote","XD"+i);
        }

        ArrayAdapter<String> lstscoresAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,lstString);
        lstscores.setAdapter(lstscoresAdapter);

        Toast.makeText(this,"Fin Récupération des Scores",Toast.LENGTH_LONG);

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

                return true;
            case R.id.m_quitter:
                //Pour fermer l'application il suffit de faire finish()
                finish();
                return true;
        }
        return false;
    }
}
