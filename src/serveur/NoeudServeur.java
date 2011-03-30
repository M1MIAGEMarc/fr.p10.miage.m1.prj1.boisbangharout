/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package serveur;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;


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

   /**
   * Permet d'écrire un fichier sur le noeud de confiance.
   * Cette méthode écrit le paramètre données dans un fichier
   * sur le serveur et est nommé avec le paramètre nom prefixé du paramètre adresse
   * à partir du nom du fichier en question.
   *
   * @param  adresse
   *         L'adresse de provenance du fichier
   *
   * @param  donnees
   *         Le contenu du fichier
   *
   * @param  nomFichier
   *         Le nom du fichier dont on souhaite écrire le contenu
   *
   */
  public void ecrireFichier(String adresse, BufferedReader donnees, String nomFichier){
      boolean FichierCreer = false;
      String ligne;
      File file = new File ("adresse" + "nomFichier"); 
        
        try {
                FichierCreer = file.createNewFile();
                FileWriter fw = new FileWriter(file, true);
                BufferedWriter bw = new BufferedWriter(fw);

                while ((ligne = donnees.readLine()) != null) {
                    bw.write(ligne);
                    bw.flush();

                }
                bw.close();

        } catch (IOException ex) {
            Logger.getLogger(NoeudServeur.class.getName()).log(Level.SEVERE, null, ex);
        }


  }

  /**
   * Permet de supprimer un fichier dont le nom est passé en paramètre.
   * Cette méthode sera appelée lors du nettoyage des fichiers supprimés.
   *
   * @param nomFichier
   *        Le nom du fichier à supprimer
   */
  public void supprimerFichier(String nomFichier){
    File file = new File(nomFichier);
    file.delete();
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
