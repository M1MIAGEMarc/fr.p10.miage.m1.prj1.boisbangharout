/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package serveur;

import java.io.File;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

/**
 * Interface RMI définissant les services étant disponibles à distance
 * 
 * @author Marc Boisban
 * @author Kamel Gharout
 */
public interface Duplication extends Remote{

  /**
   * Renvoie la liste des noms des fichiers du noeud serveur
   *
   * @return la liste des noms des fichiers du noeud serveur
   */
  public List<String> getListeNomsFichiers() throws RemoteException;

  
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
  public void ecrireFichier(String adresse, File fichier, String nomFichier) throws RemoteException;

  
  /**
   * Permet de supprimer un fichier dont le nom est passé en paramètre.
   * Cette méthode sera appelée lors du nettoyage des fichiers supprimés.
   *
   * @param nomFichier
   *        Le nom du fichier à supprimer
   */
  public void supprimerFichier(String nomFichier) throws RemoteException;


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
  public File extraireDonnees(String nomFichier) throws RemoteException;

}
