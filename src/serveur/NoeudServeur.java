/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package serveur;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Marc Boisban
 * @author Kamel Gharout
 */
public class NoeudServeur implements Duplication{

  private List<String> listeNomsFichiers;

  public NoeudServeur() {
    listeNomsFichiers  = new ArrayList<String>();
  }

  public List<String> getListeNomsFichiers() {
    return listeNomsFichiers;
  }

  public void ecrireFichier(String adresse, BufferedReader donnees, String nomFichier){
  }

  public void supprimerFichier(String nomFichier){
  }

  
  /**
   * Permet d'extraire les données d'un fichier contenu sur le serveur
   * à partir du nom du fichier en question.
   * Cette méthode sera utile pour la fonctionalité de récupération de fichier
   * perdu sur le client.
   *
   *
   * @param  nomFichier
   *         Le nom du fichier dont on souhaite extraire  le contenu
   *
   * @return Les données extraites du fichier dont le nom est passé
   *         en paramètre
   */
  public BufferedReader extraireDonnees(String nomFichier){
    FileReader fr;
    try {
      fr = new FileReader(nomFichier);
    }
    catch (FileNotFoundException fnfe) {
      System.out.println("Erreur lors de l'ouverture du fichier " + nomFichier);
      fnfe.printStackTrace();
      fr = null;
    }
    return new BufferedReader(fr);
  }


}
