/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package client;

import java.io.BufferedReader;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import serveur.Duplication;

/**
 *
 * @author Marc Boisban
 * @author Kamel Gharout
 */
public class NoeudClient {

  private String adresse;
  private List<Fichier> listeFichiers;
  private List<NoeudConfiance> listeNoeudsConfiance;

  public NoeudClient() {
    try {
      InetAddress Ip = InetAddress.getLocalHost();
      adresse = Ip.getHostAddress();
      listeFichiers = new ArrayList<Fichier>();
      listeNoeudsConfiance = new ArrayList<NoeudConfiance>();
    }
    catch (UnknownHostException ex) {
      Logger.getLogger(NoeudClient.class.getName()).log(Level.SEVERE, null, ex);
    }
  }

  public NoeudClient(String adresse, List<Fichier> listeFichiers, List<NoeudConfiance> listeNoeudsConfiance) {
    this.adresse = adresse;
    this.listeFichiers = listeFichiers;
    this.listeNoeudsConfiance = listeNoeudsConfiance;
  }

  public String getAdresse() {
    return adresse;
  }

  public void setAdresse(String adresse) {
    this.adresse = adresse;
  }

  public List<Fichier> getListeFichiers() {
    return listeFichiers;
  }

  public void setListeFichiers(List<Fichier> listeFichiers) {
    this.listeFichiers = listeFichiers;
  }

  public List<NoeudConfiance> getListeNoeudsConfiance() {
    return listeNoeudsConfiance;
  }

  public void setListeNoeudsConfiance(List<NoeudConfiance> listeNoeudsConfiance) {
    this.listeNoeudsConfiance = listeNoeudsConfiance;
  }

  /**
   * Permet d'affecter un degré de confidentialité à un fichier. Ce degré a pour but
   * de déterminer les noeuds de confiance sur lesquels le fichier en question peut
   * être dupliqué.
   *
   * Il existe 4 degrés de confidentialité :
   * - 1 : Peu confidentiel
   * - 2 : Moyennent confidentiel
   * - 3 : Très confidentiel
   * - 4 : Non duplicable (affecté par défaut lors de la création d'un objet Fichier)
   *
   * @param nomFichier
   *        Le nom du fichier auquel on souhaite affecter un degré de confidentialité
   *
   * @param niveauConfidentialite
   *        Le degré de confidentialité que l'ou souhaite affecter au fichier
   */
  public void assignerConfidentialite(String nomFichier, int niveauConfidentialite) {

    for (Fichier fichier : listeFichiers) {
      if(fichier.getNom().equals(nomFichier)) {
        fichier.setNiveauConfidentialite(niveauConfidentialite);
        break;
      }
    }
    
  }

  /**
   * Ajoute un noeud de confiance à la liste des noeuds de confiance.
   * Pour cela, on cherche l'interface de l'objet distant du noeud désiré et on
   * l'ajoute à l'attribut duplication du nouvel objet de type NoeudConfiance
   *
   * @param adresse
   *        L'adresse du noeud de confiance que l'on souhaite ajouter
   */
  public void ajouterNoeudConfiance(String adresse){
    try {
      Registry registry = LocateRegistry.getRegistry();
      Duplication duplication = (Duplication) registry.lookup("rmi://"+adresse+"/NoeudServeur");
      NoeudConfiance noeudConfiance = new NoeudConfiance(adresse, duplication);
      listeNoeudsConfiance.add(noeudConfiance);
    }
    catch (Exception e) {
      e.printStackTrace();
    }
  }

  /**
   * Cette méthode se charge d'affecter un degré de confiance à une distante, aussi appelée
   * noeud de confiance. Ce degré de confiance a pour but de déterminer quels fichier peuvent
   * être dupliqués sur ce noeud.
   *
   * Il existe 3 degrés de confiance :
   * - 1 : Peu sûr
   * - 2 : Moyennent sûr
   * - 3 : Très sûr
   *
   * @param adresse
   *        L'adresse du noeud auquel on souhaite affecter un degré de confiance
   *
   * @param niveauConfiance
   *        Le degré de confiance que l'on souhaite affecter au noeud
   */
  public void assignerConfiance(String adresse, int niveauConfiance){

    for (NoeudConfiance noeudConfiance : listeNoeudsConfiance) {
      if(noeudConfiance.getAdresse().equals(adresse)) {
        noeudConfiance.setNiveauConfiance(niveauConfiance);
        break;
      }
    }
    
  }

  public void dupliquerFichier(BufferedReader donnees, String adresse){
  }

  /**
   * Permet de supprimer un noeud de confiance de la liste des noeuds de confiance.
   * La suppression d'un noeud de confiance n'entraîne pas la suppression des fichiers
   * dupliqués sur celui-ci.
   *
   * @param adresse
   *        L'adresse du noeud de confiance à supprimer
   */
  public void supprimerNoeudConfiance(String adresse){

    for (NoeudConfiance noeudConfiance : listeNoeudsConfiance) {
      if(noeudConfiance.getAdresse().equals(adresse)) {
        listeNoeudsConfiance.remove(noeudConfiance);
        break;
      }
    }
  }

  public void ajouterNoeudConfianceFichier(String nomFichier, String adresse, boolean mode){
  }

  public void supprimerNoeudConfianceFichier(String nomFichier, String adresse){
  }

  public void nettoyerFichiersDupliques(){
  }

  public void recupererFichiersPerdus(){
  }

  public void ecrireFichierPerdu(String nomFichier, BufferedReader donnees){
  }
}
