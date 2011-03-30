/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package client;

import serveur.Duplication;
import serveur.NoeudServeur;

/**
 *
 * @author Marc Boisban
 * @author Kamel Gharout
 */
public class NoeudConfiance {

  private String adresse;
  private Duplication duplication;
  private int niveauConfiance;

  public NoeudConfiance() {
  }

  public NoeudConfiance(String adresse) {
    this.adresse = adresse;
    niveauConfiance = 1;
  }

  public NoeudConfiance(String adresse, Duplication duplication) {
    this.adresse = adresse;
    this.duplication = duplication;
    this.niveauConfiance = 1;
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

  public Duplication getDuplication() {
    return duplication;
  }

  public void setDuplication(Duplication duplication) {
    this.duplication = duplication;
  }


}
