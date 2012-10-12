package fr.nordri.game.sqLite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

public class ScoreBDD {

    private static final int VERSION_BDD = 1;
    private static final String NOM_BDD = "eleves.db";

    private static final String TABLE_SCORES = "table_scores";
    private static final String COL_ID = "ID";
    private static final int NUM_COL_ID = 0;
    private static final String COL_JOUEUR = "Joueur";
    private static final int NUM_COL_JOUEUR = 1;
    private static final String COL_SCORE = "Score";
    private static final int NUM_COL_SCORE = 2;

    private SQLiteDatabase bdd;

    private MaBaseSQLite maBaseSQLite;

    public ScoreBDD(Context context){
        //On créer la BDD et sa table
        maBaseSQLite = new MaBaseSQLite(context, NOM_BDD, null, VERSION_BDD);
    }

    public void open(){
        //on ouvre la BDD en écriture
        bdd = maBaseSQLite.getWritableDatabase();
    }

    public void close(){
        //on ferme l'accès à la BDD
        bdd.close();
    }

    public SQLiteDatabase getBDD(){
        return bdd;
    }

    public long insertScore(Score score){
        //Création d'un ContentValues (fonctionne comme une HashMap)
        ContentValues values = new ContentValues();
        //on lui ajoute une valeur associé à une clé (qui est le nom de la colonne dans laquelle on veut mettre la valeur)
        values.put(COL_JOUEUR, score.getJoueur());
        values.put(COL_SCORE, score.getScore());
        //on insère l'objet dans la BDD via le ContentValues
        return bdd.insert(TABLE_SCORES, null, values);
    }

    public int updateScore(int id, Score score){
        //La mise à jour d'un livre dans la BDD fonctionne plus ou moins comme une insertion
        //il faut simple préciser quelle livre on doit mettre à jour grâce à l'ID
        ContentValues values = new ContentValues();
        values.put(COL_JOUEUR, score.getJoueur());
        values.put(COL_SCORE, score.getScore());
        return bdd.update(TABLE_SCORES, values, COL_ID + " = " +id, null);
    }

    public int removeScoreWithID(int id){
        //Suppression d'un livre de la BDD grâce à l'ID
        return bdd.delete(TABLE_SCORES, COL_ID + " = " +id, null);
    }

    public Score getScoreWithTitre(String score){
        //Récupère dans un Cursor les valeur correspondant à un livre contenu dans la BDD (ici on sélectionne le livre grâce à son titre)
        Cursor c = bdd.query(TABLE_SCORES, new String[] {COL_ID, COL_JOUEUR, COL_SCORE}, COL_SCORE + " LIKE \"" + score +"\"", null, null, null, null);
        return cursorToScore(c);
    }

    //Cette méthode permet de convertir un cursor en un livre
    private Score cursorToScore(Cursor c){
        //si aucun élément n'a été retourné dans la requête, on renvoie null
        if (c.getCount() == 0)
            return null;

        //Sinon on se place sur le premier élément
        c.moveToFirst();
        //On créé un livre
        Score score = new Score();
        //on lui affecte toutes les infos grâce aux infos contenues dans le Cursor
        score.setId(c.getInt(NUM_COL_ID));
        score.setJoueur(c.getString(NUM_COL_JOUEUR));
        score.setScore(c.getString(NUM_COL_SCORE));
        //On ferme le cursor
        c.close();

        //On retourne le score
        return score;
    }

    /**
     * Fonction get All Highscores
     * @return ArrayList<Highscore>
     */
    public ArrayList<Score> getScores(){
        Cursor c = bdd.query(TABLE_SCORES, new String[] {COL_ID, COL_ID, COL_SCORE}, null, null, null, null, null);

        if (c.getCount() == 0)
            return null;

        ArrayList<Score> scores = new ArrayList<Score>();
        if (c != null ) {
            if  (c.moveToFirst()) {
                do {
                    Score score = new Score();
                    score.setId(c.getInt(NUM_COL_ID));
                    score.setJoueur(c.getString(NUM_COL_JOUEUR));
                    score.setScore(c.getString(NUM_COL_SCORE));

                    scores.add(score);
                }while (c.moveToNext());
            }
        }
        c.close();

        return scores;
    }

}
