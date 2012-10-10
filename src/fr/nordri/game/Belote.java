package fr.nordri.game;

import android.content.Context;
import android.widget.TableLayout;

import java.io.Console;
import java.util.ArrayList;
import java.util.Queue;
import java.util.Set;

import android.util.Log;

/**
 * Created with IntelliJ IDEA.
 * User: miragecentury
 * Date: 10/10/12
 * Time: 08:42
 * To change this template use File | Settings | File Templates.
 */
public class Belote {
    protected ArrayList<Carte> JeuxCarte;
    protected ArrayList<Joueur> lstJoueur;
    protected ArrayList<Carte> tapis;
    protected ArrayList<Carte> pile;
    protected int nbJoueur = 0;
    protected Couleur atout;

    /******************************************************************************************************************/

    public Belote() {
        Log.e("Belote","Start GetNewJeux");
        this.JeuxCarte = Belote.getNewJeux();
        Log.e("Belote","Fin GetNewJeux");
        Log.e("Belote","Start MelangeJeuxCarte");
        this.JeuxCarte = Belote.melangeJeuxCarte(this.JeuxCarte);
        Log.e("Belote","Fin MelangeJeuxCarte");
    }

    /******************************************************************************************************************/





    /******************************************************************************************************************/
    /**
     * Alloue un nouveau jeux de carte
     * @return
     */
    private static ArrayList<Carte> getNewJeux(){
        ArrayList<Carte> jeuxCarte = new ArrayList<Carte>();

        for(Couleur c : Couleur.values()){
            for(CarteValeur v : CarteValeur.values()){
                if(v == CarteValeur.JOKER){
                    //Pas de JOKER dans la belote XD
                }else{
                    jeuxCarte.add(new Carte(c, v));
                }
            }
        }
        return jeuxCarte;
    }

    /**
     *
     * @param JeuxNonMelange
     * @return ArrayList<Carte> JeuxMelange
     */
    private static ArrayList<Carte> melangeJeuxCarte(ArrayList<Carte> JeuxNonMelange){
        ArrayList<Carte> JeuxMelange = new ArrayList<Carte>();
        Carte tmpCarte;

        int random = 0;


        while(JeuxNonMelange.size() != 0){
            random = (int)(Math.random() * (JeuxNonMelange.size()-0)) + 0;
            Log.e("Belote","random: "+random);
            JeuxMelange.add(JeuxNonMelange.get(random));
            JeuxNonMelange.remove(random);

        }

        return JeuxMelange;
    }

}
