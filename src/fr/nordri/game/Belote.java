package fr.nordri.game;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.widget.ImageView;

import java.util.ArrayList;

import android.util.Log;
import android.widget.Toast;
import fr.nordri.MyPlay;

/**
 * Created with IntelliJ IDEA.
 * User: miragecentury
 * Date: 10/10/12
 * Time: 08:42
 * To change this template use File | Settings | File Templates.
 */
public class Belote {
    protected Context ctx;
    protected ArrayList<Carte> JeuxCarte;
    protected ArrayList<Joueur> lstJoueur;
    protected ArrayList<Carte> tapis;
    protected ArrayList<Carte> pile;
    protected ArrayList<Carte> pileEquipe0;
    protected ArrayList<Carte> pileEquipe1;
    protected int nbJoueur = 0;
    protected int nbRound = 0;
    protected int cipher_joueur_atout = 10;
    protected int cipher_joueur_distribution;
    protected Couleur atout;
    protected MyPlay myBelote;

    public int cipher_question = 0;

    /******************************************************************************************************************/

    public Belote(Context ctx, MyPlay myPlay) {
        this.ctx = ctx;
        this.lstJoueur = new ArrayList<Joueur>();
        this.JeuxCarte = Belote.getNewJeux();
        this.JeuxCarte = Belote.melangeJeuxCarte(this.JeuxCarte);
        this.tapis = new ArrayList<Carte>();
        this.pile = new ArrayList<Carte>();
        this.pileEquipe0 = new ArrayList<Carte>();
        this.pileEquipe1 = new ArrayList<Carte>();
        this.myBelote = myPlay;
        this.nbRound = 0;
        this.pile = this.JeuxCarte;
    }

    public void addJoueur(Joueur j){
        this.lstJoueur.add(j);
        this.nbJoueur++;
    }

    public void StartRound(){
        this.cipher_joueur_atout = 10;
        this.cipher_question = 0;
        if(this.nbRound == 0){
            this.cipher_joueur_distribution = 0;
        } else{
            this.cipher_joueur_distribution  = this.nbRound % 4;
        }

        /* Récupération des cartes dans les piles des équipes */
        while(this.pileEquipe0.size() != 0){
            this.pile.add(this.pileEquipe0.get(0));
            this.pileEquipe0.remove(0);
        }
        while(this.pileEquipe1.size() != 0){
            this.pile.add(this.pileEquipe1.get(0));
            this.pileEquipe1.remove(0);
        }

        //Distribut 3 cartes à tous le monde dans l'ordre
        this.donnerCartes(3);
        //Distribut 2 cartes à tous le monde dans l'ordre
        this.donnerCartes(2);

        // Affiche la première carte de la pile
        Carte c = this.pile.get(0);
        ImageView view_c = (ImageView) this.myBelote.findViewById(this.myBelote.getResources().getIdentifier("pile","id",this.myBelote.getPackageName()));
        int id_c = this.myBelote.getResources().getIdentifier(this.pile.get(0).getDraw(),"drawable",this.myBelote.getPackageName());
        view_c.setImageDrawable(this.myBelote.getResources().getDrawable(id_c));

        this.cipher_question = 0;

    }

    //
    public void LaunchQuestionPop(){
        //TODO: clean
    }

    // Prendre en CoP
    public void Prendre(Joueur j,Couleur c){

    }

    // Prendre en PoP
    public void Prendre(Joueur j){

    }

    //
    public void RecepQuestionPoP(){
        if(this.cipher_question != 4 && this.cipher_joueur_atout == 10)
        {
            this.cipher_question++;
            //On continue à poser les questions
            this.LaunchQuestionPop();
        }else if(this.cipher_question == 4){
            this.cipher_question = 0;
            //Deuxième Passage avec choix
            this.LaunchQuestionCoP();
        }else if(this.cipher_joueur_atout != 10){
            //Efface de la pile (Visuel)
            ImageView view_c = (ImageView) this.myBelote.findViewById(this.myBelote.getResources().getIdentifier("pile","id",this.myBelote.getPackageName()));
            int id_c = this.myBelote.getResources().getIdentifier("c0","drawable",this.myBelote.getPackageName());
            view_c.setImageDrawable(this.myBelote.getResources().getDrawable(id_c));

            //Donne la carte au joueur qui a pris
            this.lstJoueur.get(this.cipher_joueur_atout).addCarte(this.pile.get(0));
            this.pile.remove(0);


            this.donnerCartes(2,true);
            this.donnerCartes(3);

            //Démarre à jouer
            this.SlotPlay();
        }
    }

    //
    public void LaunchQuestionCoP(){
        //TODO clean
    }

    //
    public void RecepQuestionCoP(){

    }

    //Appelé à la fin du round
    public void EventEnRound(){
        this.nbRound++;
        this.nbRound = this.nbRound % 4;
    }

    /**
     * Tous le monde a répondu à Choix ou Passer
     */
    public void EventAllCoP(){
        if(this.cipher_joueur_atout == 10){
            //Nettoyage des Mains des Joueurs dans la pile
            this.CleanMainJoueur(this.lstJoueur.get(0));
            this.CleanMainJoueur(this.lstJoueur.get(1));
            this.CleanMainJoueur(this.lstJoueur.get(2));
            this.CleanMainJoueur(this.lstJoueur.get(3));

            //Nettoyage retourne la première carte de la pile
            ImageView view_c = (ImageView) this.myBelote.findViewById(this.myBelote.getResources().getIdentifier("pile","id",this.myBelote.getPackageName()));
            int id_c = this.myBelote.getResources().getIdentifier("c0","drawable",this.myBelote.getPackageName());
            view_c.setImageDrawable(this.myBelote.getResources().getDrawable(id_c));

            //on redémarre un round
            this.StartRound();
        }else{
            this.donnerCartes(2,true);
            this.donnerCartes(3);
            this.SlotPlay();
        }
    }

    /**
     * Tous le monde à ces carte
     */
    public void SlotPlay(){
        //On démarre à jouer

    }

    public void Play(Carte c, Joueur j){

    }

    /**
     * Nettoie la Main du joueur en remettant ces cartes dans la pile
     * @param  j
     */
    private void CleanMainJoueur(Joueur j){
        ImageView view_c;
        int id_c;
        while(j.getMain().size() != 0){
            if(j.getMain().get(0) != null){
                this.pile.add(j.getMain().get(0));
            }
            j.getMain().remove(0);
        }

        if(j.getClass() == Joueur.class && j.getClass() != Joueur_IA.class){
            //Clean Main
            for(int i = 0; i<13;i++){
                view_c = (ImageView) this.myBelote.findViewById(this.myBelote.getResources().getIdentifier("carte"+i,"id",this.myBelote.getPackageName()));
                id_c = this.myBelote.getResources().getIdentifier("c0","drawable",this.myBelote.getPackageName());
                view_c.setImageDrawable(this.myBelote.getResources().getDrawable(id_c));
            }
        }
    }

    private void donnerCartes(int nbCarte){
        this.donnerCartes(nbCarte,false);
    }

    private void donnerCartes(int nbCarte, boolean atout){
        int i = 0;
        while(i < this.nbJoueur)
        {
            for(int j = 0; j < nbCarte; j++){
                if((j == 0) && atout && this.cipher_joueur_atout == j){
                   //Pas de Carte XD
                }   else{
                    if(this.cipher_joueur_distribution != 0){
                        this.lstJoueur.get((i + this.cipher_joueur_distribution) % this.nbJoueur).addCarte(this.pile.get(0));
                    }else{
                        this.lstJoueur.get(i).addCarte(this.pile.get(0));
                    }
                    this.pile.remove(0);
                }
            }
            i++;
        }
    }

    private int calcScore(ArrayList<Carte> cartes, Couleur atout){
        int score = 0;

       for(int i=0;i<cartes.size();i++){
            if(cartes.get(i).getCouleur() != atout){
                switch (cartes.get(i).getValeur()){
                    case AS:
                        score = score + 11;
                        break;
                    case DIX:
                        score = score + 10;
                        break;
                    case VALET:
                        score = score + 2;
                        break;
                    case DAME:
                        score = score + 3;
                        break;
                    case ROI:
                        score = score + 4;
                        break;
                    default:
                        break;
                }
            }else{
                switch (cartes.get(i).getValeur()){
                    case AS:
                        score = score + 11;
                        break;
                    case DIX:
                        score = score + 10;
                        break;
                    case VALET:
                        score = score + 20;
                        break;
                    case DAME:
                        score = score + 3;
                        break;
                    case ROI:
                        score = score + 4;
                        break;
                    case NEUF:
                        score = score + 14;
                        break;
                    default:
                        break;
                }
            }
       }

       return score;
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
