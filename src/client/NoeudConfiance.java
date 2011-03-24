/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package client;

import serveur.NoeudServeur;

/**
 *
 * @author maboisba
 */
public class NoeudConfiance {

  private String adresse;
  private NoeudServeur noeudServeur;
  private int niveauConfiance;

  public NoeudConfiance() {
  }

  public NoeudConfiance(String adresse) {
    this.adresse = adresse;
  }

  public NoeudConfiance(String adresse, int niveauConfiance) {
    this.adresse = adresse;
    this.niveauConfiance = niveauConfiance;
  }

  public String getAdresse() {
    return adresse;
  }

  public void setAdresse(String adresse) {
    this.adresse = adresse;
  }

  public int getNiveauConfiance() {
    return niveauConfiance;
  }

  public void setNiveauConfiance(int niveauConfiance) {
    this.niveauConfiance = niveauConfiance;
  }

  public NoeudServeur getNoeudServeur() {
    return noeudServeur;
  }

  public void setNoeudServeur(NoeudServeur noeudServeur) {
    this.noeudServeur = noeudServeur;
  }
  
}
