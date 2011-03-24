/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package client;

import java.util.HashMap;

/**
 *
 * @author maboisba
 */
public class Fichier {

  private String nom;
  private int niveauConfidentialite;
  private HashMap<String, Boolean> noeudsConfianceMap;

  public Fichier() {
  }

  public Fichier(String nom, int niveauConfidentialite, HashMap<String, Boolean> noeudsConfianceMap) {
    this.nom = nom;
    this.niveauConfidentialite = niveauConfidentialite;
    this.noeudsConfianceMap = noeudsConfianceMap;
  }

  public int getNiveauConfidentialite() {
    return niveauConfidentialite;
  }

  public void setNiveauConfidentialite(int niveauConfidentialite) {
    this.niveauConfidentialite = niveauConfidentialite;
  }

  public HashMap<String, Boolean> getNoeudsConfianceMap() {
    return noeudsConfianceMap;
  }

  public void setNoeudsConfianceMap(HashMap<String, Boolean> noeudsConfianceMap) {
    this.noeudsConfianceMap = noeudsConfianceMap;
  }

  public String getNom() {
    return nom;
  }

  public void setNom(String nom) {
    this.nom = nom;
  }

}
