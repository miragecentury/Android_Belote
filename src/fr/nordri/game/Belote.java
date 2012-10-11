package fr.nordri.game;

import android.R;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.text.LoginFilter;
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
    protected int scoreEquipe0 = 0;
    protected int scoreRoundEquipe0 = 0;
    protected ArrayList<Carte> pileEquipe1;
    protected int scoreEquipe1 = 0;
    protected int scoreRoundEquipe1 = 0;
    protected int nbJoueur = 0;
    protected int nbRound = 0;
    protected int cipher_joueur_atout = 10;
    protected int cipher_master = 0;
    protected int cipher_joueur_distribution;
    protected Couleur atout;
    protected MyPlay myBelote;

    public int cipher_question = 0;
    public int index_question = 0;

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

    /******************************************************************************************************************/

    public void addJoueur(Joueur j){
        j.setBelote(this);
        this.lstJoueur.add(j);
        this.nbJoueur++;
    }

    public ArrayList<Carte> getTapis(){
        return this.tapis;
    }

    /******************************************************************************************************************/

    public void StartRound(){
        ImageView view_c;
        this.cipher_master = 0;
        this.cipher_question = 0;
        this.cipher_master = (this.cipher_joueur_distribution + 1) % this.nbJoueur;
        this.cipher_joueur_atout = 10;
        this.cipher_question = 0;
        if(this.nbRound == 0){
            this.cipher_joueur_distribution = 0;
        } else{
            this.cipher_joueur_distribution  = this.nbRound % 4;
        }

        //ByPass des Clickables
        for(int i=0; i<8; i++){
            view_c = (ImageView) this.myBelote.findViewById(this.myBelote.getResources().getIdentifier("carte"+i,"id",this.myBelote.getPackageName()));
            view_c.setClickable(false);
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
        view_c = (ImageView) this.myBelote.findViewById(this.myBelote.getResources().getIdentifier("pile","id",this.myBelote.getPackageName()));
        int id_c = this.myBelote.getResources().getIdentifier(this.pile.get(0).getDraw(),"drawable",this.myBelote.getPackageName());
        view_c.setImageDrawable(this.myBelote.getResources().getDrawable(id_c));

        this.cipher_question = 0;
        this.LaunchQuestionPop();
    }

    //Appelé à la fin du round
    public void EventEnRound(){
        this.nbRound++;
        this.nbRound = this.nbRound % 4;
        if(false){

        }else{
            this.myBelote.finish();
        }
    }

    /******************************************************************************************************************/
    //Phase de distribution

    //
    public void LaunchQuestionPop(){
        //TODO: clean
        this.index_question = 0;

        if(this.cipher_joueur_distribution == 0){
            this.index_question = cipher_question;
        }else{
            this.index_question = (cipher_question + this.cipher_joueur_distribution) % this.nbJoueur;
        }

        Log.e("Belote", "Cipher_Question: "+this.index_question);

        if(this.lstJoueur.get(this.index_question).getType() == "Joueur"){
            AlertDialog alertDialog;
            alertDialog = new AlertDialog.Builder(this.myBelote).create();
            alertDialog.setTitle("Prendre ou Laisser");
            alertDialog.setMessage("Prendre ou Laisser ?");
            alertDialog.setButton("Prendre", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    Prendre(Belote.this.lstJoueur.get(Belote.this.index_question));
                    Belote.this.RecepQuestionPoP();
                    return;
                } });
            alertDialog.setButton2("Laisser", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    Belote.this.RecepQuestionPoP();
                    return;
                }});
            alertDialog.setCancelable(false);
            alertDialog.show();
        }else{
            //IA
            Joueur_IA j = (Joueur_IA) this.lstJoueur.get(this.cipher_question);
            j.QuestionPoP();
            this.RecepQuestionPoP();
        }

    }

    // Prendre en CoP
    public void Prendre(Joueur j,Couleur c){
        this.cipher_joueur_atout = this.lstJoueur.indexOf(j);
        this.atout = c;
        CarteValeur.setCouleur(c);
        Log.e("Belote","Le Joueur "+ this.cipher_joueur_atout +" Prends");
    }

    // Prendre en PoP
    public void Prendre(Joueur j){
        this.cipher_joueur_atout = this.lstJoueur.indexOf(j);
        this.atout = this.pile.get(0).getCouleur();
        CarteValeur.setCouleur(this.atout);
        Log.e("Belote","Le Joueur "+ this.cipher_joueur_atout +" Prends");
    }

    //
    public void RecepQuestionPoP(){
        if(this.cipher_question != (this.nbJoueur-1) && this.cipher_joueur_atout == 10)
        {
            this.cipher_question++;
            //On continue à poser les questions
            this.LaunchQuestionPop();

        }else if(this.cipher_joueur_atout != 10){
            //Efface de la pile (Visuel)
            ImageView view_c = (ImageView) this.myBelote.findViewById(this.myBelote.getResources().getIdentifier("pile","id",this.myBelote.getPackageName()));
            int id_c = this.myBelote.getResources().getIdentifier("c0","drawable",this.myBelote.getPackageName());
            view_c.setImageDrawable(this.myBelote.getResources().getDrawable(id_c));

            Log.e("Belote", "Nb Carte dans la Pile:"+this.pile.size());

            //Donne la carte au joueur qui a pris
            this.lstJoueur.get(this.cipher_joueur_atout).addCarte(this.pile.get(0));
            this.pile.remove(0);

            //on donne les cartes
            this.donnerCartes(3,true);

            //Démarre à jouer
            this.LaunchPlay();
        }else if(this.cipher_question == (this.nbJoueur-1)){
            this.cipher_question = 0;
            //Deuxième Passage avec choix
            this.LaunchQuestionCoP();
        }
    }

    //
    public void LaunchQuestionCoP(){
        //TODO: clean
        this.index_question = 0;

        if(this.cipher_joueur_distribution == 0){
            this.index_question = cipher_question;
        }else{
            this.index_question = (cipher_question + this.cipher_joueur_distribution) % this.nbJoueur;
        }

        Log.e("Belote", "Cipher_Question: "+this.index_question);

        if(this.lstJoueur.get(this.index_question).getType() == "Joueur"){
            String[] lst = new String[5];
            lst[0] = "Laisser";
            lst[1] = "Coeur";
            lst[2] = "Carreaux";
            lst[3] = "Pique";
            lst[4] = "Trèfle";

            AlertDialog.Builder builder = new AlertDialog.Builder(this.myBelote);
            builder.setTitle("Prendre une Couleur ou Laisser ?");
            builder.setItems(lst,new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    switch (i){
                        case 0:
                            //Laisser
                            break;
                        case 1:
                            Belote.this.Prendre(Belote.this.lstJoueur.get(Belote.this.index_question),Couleur.COEUR);
                            break;
                        case 2:
                            Belote.this.Prendre(Belote.this.lstJoueur.get(Belote.this.index_question),Couleur.CARREAUX);
                            break;
                        case 3:
                            Belote.this.Prendre(Belote.this.lstJoueur.get(Belote.this.index_question),Couleur.PIQUE);
                            break;
                        case 4:
                            Belote.this.Prendre(Belote.this.lstJoueur.get(Belote.this.index_question),Couleur.TREFLE);
                            break;
                    }
                    Belote.this.RecepQuestionCoP();
                }
            });
            AlertDialog alertDialog = null;
            alertDialog = builder.create();
            alertDialog.setCancelable(false);
            alertDialog.show();

        }else{
            //IA
            Joueur_IA j = (Joueur_IA) this.lstJoueur.get(this.cipher_question);
            j.QuestionCoP();
            this.RecepQuestionCoP();
        }

    }

    //
    public void RecepQuestionCoP(){
        if(this.cipher_question != (this.nbJoueur-1) && this.cipher_joueur_atout == 10)
        {
            this.cipher_question++;
            //On continue à poser les questions
            this.LaunchQuestionCoP();

        }else if(this.cipher_joueur_atout != 10){
            this.EventAllCoP();
        }else if(this.cipher_question == (this.nbJoueur-1)){
            this.cipher_question = 0;
            this.EventAllCoP();
        }
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
            //this.StartRound();

            //this.myBelote.finish();
            Toast.makeText(this.myBelote,"Restart du Round XD",Toast.LENGTH_SHORT);
            this.myBelote.finish();
        }else{

            //Efface de la pile (Visuel)
            ImageView view_c = (ImageView) this.myBelote.findViewById(this.myBelote.getResources().getIdentifier("pile","id",this.myBelote.getPackageName()));
            int id_c = this.myBelote.getResources().getIdentifier("c0","drawable",this.myBelote.getPackageName());
            view_c.setImageDrawable(this.myBelote.getResources().getDrawable(id_c));


            //Donne la carte au joueur qui a pris
            this.lstJoueur.get(this.cipher_joueur_atout).addCarte(this.pile.get(0));
            this.pile.remove(0);

            this.donnerCartes(3,true);

            this.LaunchPlay();
        }
    }

    /******************************************************************************************************************/

    /**
     * Tous le monde à ces carte
     */
    public void LaunchPlay(){
        //On démarre à jouer
        this.cipher_question = 0;

        this.aToiJouer();
    }

    public void aToiJouer(){
        this.lstJoueur.get((this.cipher_master + this.cipher_question)%this.nbJoueur).aToiJouer(this.tapis);
    }

    public void aJouer(Carte c, Joueur j){

        //Récupère la carte et la place
        ImageView view_c = (ImageView) this.myBelote.findViewById(this.myBelote.getResources().getIdentifier("tapis"+this.cipher_question,"id",this.myBelote.getPackageName()));
        int id_c = this.myBelote.getResources().getIdentifier(c.getDraw(),"drawable",this.myBelote.getPackageName());
        view_c.setImageDrawable(this.myBelote.getResources().getDrawable(id_c));

        this.tapis.add(c);


        // Next ?
        if(this.cipher_question < (this.nbJoueur-1)){
            this.cipher_question++;
            this.aToiJouer();
        }else{
            //le dernier à jouer
            int index_carte = 0;
            int index_joueur = 0;
            int score = 0;
            ArrayList<Carte> pile_gagnant;
            Carte c_max = this.tapis.get(0);
            Carte c_tmp = null;

            for(int i = 1; i<this.nbJoueur;i++){
                c_tmp = this.tapis.get(i);
                if(c_tmp.getCouleur() != this.atout || c_tmp.getCouleur() == this.tapis.get(0).getCouleur()){
                    if(c_max.getValeur().getForce(c_max.getCouleur()) < c_tmp.getValeur().getForce(c_tmp.getCouleur())){
                        c_max = c_tmp;
                    }
                }
            }

            index_carte = this.tapis.indexOf(c_max);
            index_joueur = (this.cipher_master + index_carte) % this.nbJoueur;

            this.cipher_master = index_joueur;

            if(index_joueur/2 == 0 || index_joueur/2 == 1){
                pile_gagnant = pileEquipe1;
            }else{
                pile_gagnant = pileEquipe0;
            }

            for(int i = 0; i<4 ; i++){
                score += this.tapis.get(0).getValeur().getValeur(this.tapis.get(0).getCouleur());
                pile_gagnant.add(this.tapis.get(0));
                this.tapis.remove(0);
            }

            if(index_joueur/2 == 0 || index_joueur/2 == 1){
                this.pileEquipe1 = pile_gagnant;
                this.scoreRoundEquipe1+=score;
            }else{
                this.pileEquipe0 = pile_gagnant;
                this.scoreRoundEquipe0+=score;
            }

            if((this.pileEquipe0.size() + this.pileEquipe1.size()) == 32){
                //Fin de la partie
                this.myBelote.finish();
            }else{
                //C'est repartie XD
                for(int i = 0; i<this.nbJoueur;i++){
                    view_c = (ImageView) this.myBelote.findViewById(this.myBelote.getResources().getIdentifier("tapis"+i,"id",this.myBelote.getPackageName()));
                    id_c = this.myBelote.getResources().getIdentifier("c0","drawable",this.myBelote.getPackageName());
                    view_c.setImageDrawable(this.myBelote.getResources().getDrawable(id_c));
                }
                this.LaunchPlay();
            }
        }


    }

    /******************************************************************************************************************/

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
            for(int i = 0; i<8;i++){
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
        Log.e("Belote", "Nb Carte dans la Pile:"+this.pile.size());
        int i = 0;
        while(i < this.nbJoueur)
        {
            for(int j = 0; j < nbCarte; j++){
                if(j == 0 && atout == true && i == this.cipher_joueur_atout){
                   //Pas de Carte XD
                }else{
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
                score = score + CarteValeur.getValeurAtout(cartes.get(i).getValeur());
            }else{
                score = score + CarteValeur.getValeurAtout(cartes.get(i).getValeur());
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
                if(
                        v == CarteValeur.JOKER || v == CarteValeur.DEUX ||
                        v == CarteValeur.TROIS || v == CarteValeur.QUATTRE ||
                        v == CarteValeur.CINQ || v == CarteValeur.SIX
                ){
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

    /******************************************************************************************************************/

    public Couleur getAtout() {
        return atout;
    }
}
