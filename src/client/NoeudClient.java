/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package client;

import java.io.BufferedReader;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author maboisba
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

  public void assignerConfidentialite(String nomFichier, int niveauConfidentialite) {
  }

  public void ajouterNoeudConfiance(String adresse){
  }

  public void assignerConfiance(String adresse, int niveauConfiance){
  }

  public void dupliquerFichier(BufferedReader donnees, String adresse){
  }

  public void supprimerNoeudConfiance(String adresse){
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
