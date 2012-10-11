package fr.nordri;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ImageView;
import fr.nordri.game.*;

import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: miragecentury
 * Date: 10/10/12
 * Time: 11:03
 * To change this template use File | Settings | File Templates.
 */
public class MyPlay extends Activity{

    Belote belote;
    public Boolean rep;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.rep = false;
        this.setTitle("Belote v1 - Play");
        setContentView(R.layout.play2);

        this.belote = new Belote(this.getApplicationContext(),this);
        this.belote.addJoueur(new Joueur_IA(0,"TOTO1",0));
        this.belote.addJoueur(new Joueur_IA(1,"TOTO2",1));
        this.belote.addJoueur(new Joueur_IA(2,"TOTO3",0));
        this.belote.addJoueur(new Joueur(3,"Vous",1,this));
        this.belote.StartRound();

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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode){
            case 1:
                if(resultCode==RESULT_OK){
                    this.rep = true;
                }else{
                    this.rep = false;
                }
                break;

        }
    }
}
