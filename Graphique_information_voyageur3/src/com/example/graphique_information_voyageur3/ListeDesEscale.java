package com.example.graphique_information_voyageur3;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.app.Activity;
import android.app.ListActivity;
import android.os.Bundle;
import android.widget.GridView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;

public class ListeDesEscale extends Activity {
	/**
	 * @see android.app.Activity#onCreate(Bundle)
	 */
	GridView gridView=null;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		gridView = new GridView(this);
		setContentView(gridView);
		gridView.setNumColumns(3);
		//On récupère une ListView de notre layout en XML, c'est la vue qui représente la liste
        
        /*
         * On entrepose nos données dans un tableau à deux dimensions :
         *    - la première contiendra le nom de l'utilisateur
         *    - la seconde contiendra le numéro de téléphone de l'utilisateur
         */
        String[] repertoire = new String[]{
    		"Ville",
    		"Heure prévue",
            "Heure effective",
            "Départ Paris",
            "15h",
            "15h05",
            "Escale1 : Madrid",
            "16h",
            "16h05",
            "Escale2 : Lisbone",
            "18h30",
            "18h35",
            "Arrivée :  Alger",
            "19h10",
            "19h20"};
        
        /*
         * On doit donner à notre adaptateur une liste du type « List<Map<String, ?> » :
         *    - la clé doit forcément être une chaîne de caractères
         *    - en revanche la valeur peut être n'importe quoi, un objet ou un entier par exemple,
         *    si c'est un objet on affichera son contenu avec la méthode « toString() »
         *
         * Dans notre cas, la valeur sera une chaîne de caractères puisque le nom et le numéro de téléphone
         * sont entreposés dans des chaînes de caractères
         */
        List<HashMap<String, String>> liste = new ArrayList<HashMap<String, String>>();
        
        HashMap<String, String> element;
        //Pour chaque personne dans notre répertoire…
        for(int i = 0 ; i < repertoire.length ; i++) {
            //… on crée un élément pour la liste…
            element = new HashMap<String, String>();
            /*
             * … on déclare que la clé est « text1 » (j'ai choisi ce mot au hasard, sans sens technique particulier)  
             * pour le nom de la personne (première dimension du tableau de valeurs)…
            */
            element.put("text1", repertoire[i]);
            /*
             * … on déclare que la clé est « text2 »
             * pour le numéro de cette personne (seconde dimension du tableau de valeurs)
            */
            liste.add(element);
        }
        
        ListAdapter adapter = new SimpleAdapter(this,  
            //Valeurs à insérer
            liste, 
            /*
             * Layout de chaque élément (là il s'agit d'un layout par défaut
             * pour avoir deux textes l'un au-dessus de l'autre, c'est pourquoi on 
             * n'affiche que le nom et le numéro d'une personne)
            */
            android.R.layout.simple_list_item_1,
            /*
             * Les clés des informations à afficher pour chaque élément :
             *    - la valeur associée à la clé « text1 » sera la première information
             *    - la valeur associée à la clé « text2 » sera la seconde information
            */
            new String[] {"text1"}, 
            /*
             * Enfin, les layouts à appliquer à chaque widget de notre élément
             * (ce sont des layouts fournis par défaut) :
             *    - la première information appliquera le layout « android.R.id.text1 »
             *    - la seconde information appliquera le layout « android.R.id.text2 »
            */
            new int[] {android.R.id.text1});
        //Pour finir, on donne à la ListView le SimpleAdapter
        gridView.setAdapter(adapter);
	}
}
