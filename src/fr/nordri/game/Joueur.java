package fr.nordri.game;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import fr.nordri.MyPlay;

import java.util.ArrayList;
import java.util.concurrent.Semaphore;

/**
 * Created with IntelliJ IDEA.
 * User: miragecentury
 * Date: 10/10/12
 * Time: 08:59
 * To change this template use File | Settings | File Templates.
 */
public class Joueur {

    /**
     * Numéro de l'équipe
     */
    protected final int equipe;

    public Semaphore dialogSemaphore;

    /**
     * Ordre dans le Jeux
     */
    protected final int ordre;

    /**
     * Nom du Joueur
     */
    protected final String nom;

    /**
     * Cartes dans la main du joueur
     */
    protected ArrayList<Carte> main;

    protected MyPlay myBelote;


    public Boolean diag;
    public Boolean rep;
    public Couleur rep_couleur;
    /******************************************************************************************************************/

    public String getType(){
        return "Joueur";
    }

    public Joueur(int ordre, String nom,int equipe){
        this.ordre = ordre;
        this.nom = nom;
        this.equipe = equipe;
        this.main = new ArrayList<Carte>();
    }

    public Joueur(int ordre, String nom,int equipe, MyPlay myBelote){
        this.ordre = ordre;
        this.nom = nom;
        this.equipe = equipe;
        this.myBelote = myBelote;
        this.main = new ArrayList<Carte>();
    }

    public Joueur(Joueur j){
       this.ordre = j.getOrdre();
       this.nom = j.getNom();
       this.main = j.getMain();
       this.equipe = j.getEquipe();
    }

    /******************************************************************************************************************/

    public int getOrdre() {
        return ordre;
    }

    public String getNom() {
        return nom;
    }

    public ArrayList<Carte> getMain() {
        return main;
    }

    public int getEquipe(){
        return this.equipe;
    }

    /******************************************************************************************************************/

    public void addCarte(Carte c){
        this.main.add(c);
        ImageView view_c = (ImageView) this.myBelote.findViewById(this.myBelote.getResources().getIdentifier("carte"+(this.main.size()-1),"id",this.myBelote.getPackageName()));
        int id_c = this.myBelote.getResources().getIdentifier(c.getDraw(),"drawable",this.myBelote.getPackageName());
        view_c.setImageDrawable(this.myBelote.getResources().getDrawable(id_c));
    }

    public void delCarte(Carte c){
        this.delCarte(this.main.indexOf(c));
    }

    public void delCarte(int index){
        ImageView view_c = (ImageView) this.myBelote.findViewById(this.myBelote.getResources().getIdentifier("carte"+index,"id",this.myBelote.getPackageName()));
        int id_c = this.myBelote.getResources().getIdentifier("c0","drawable",this.myBelote.getPackageName());
        view_c.setImageDrawable(this.myBelote.getResources().getDrawable(id_c));
        //TODO nettoyage
        view_c.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Pas d'Action
            }
        });
        this.main.set(index,null);
    }

    public Carte getCarte(int indice){
        return this.main.get(indice);
    }

    public int getSizeMain(){
        return this.main.size();
    }
}

