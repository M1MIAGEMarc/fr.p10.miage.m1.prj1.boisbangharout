/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package serveur;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Marc Boisban
 * @author Kamel Gharout
 */
public class NoeudServeur extends UnicastRemoteObject implements Duplication {

  private List<String> listeNomsFichiers;

  public NoeudServeur() throws RemoteException{
    listeNomsFichiers = new ArrayList<String>();
    File file = new File(".");
    File[] files = file.listFiles();
    System.out.println("test");
    for (int i = 0; i < files.length; i++) {
      if (files[i].isFile()) {
        System.out.println(files[i].getName());
        listeNomsFichiers.add(files[i].getName());
      }
    }

  }

  @Override
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
   * @param  fichier
   *         Le fichier à dupliquer
   *
   * @param  nomFichier
   *         Le nom du fichier dont on souhaite écrire le contenu
   *
   */
  @Override
  public void ecrireFichier(String adresse, File fichier, String nomFichier) {
    boolean FichierCreer = false;
    String ligne;
    File file = new File(adresse + "_" + nomFichier);
    file.delete();
    try {
      FichierCreer = file.createNewFile();
      FileWriter fw = new FileWriter(file, true);
      BufferedReader br = new BufferedReader(new FileReader(fichier));
      BufferedWriter bw = new BufferedWriter(fw);

      while ((ligne = br.readLine()) != null) {
        bw.write(ligne);
        bw.flush();
      }
      bw.close();

      if(!listeNomsFichiers.contains(adresse + "_" + nomFichier));
        listeNomsFichiers.add(adresse + "_" + nomFichier);
      System.out.println("Fichier '" + adresse + "_" + nomFichier + "' ajouté.");
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
  @Override
  public void supprimerFichier(String nomFichier) {
    System.out.println("SUPPRESSION FICHIER");
    File file = new File(nomFichier);
    file.delete();
    listeNomsFichiers.remove(nomFichier);
    System.out.println("Fichier '" + nomFichier + "' supprimé.");
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
  @Override
  public File extraireDonnees(String nomFichier) {
    File fichier;
    fichier = new File(nomFichier);
    
    return fichier;
  }
}
