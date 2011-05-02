/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package client;

import serveur.Duplication;

/**
 * Fait le lien entre un noeud serveur et un niveau de confiance
 * 
 * @author Marc Boisban
 * @author Kamel Gharout
 */
public class NoeudConfiance {

  /****************************   Attributs   *********************************/
  private String adresse;
  private Duplication duplication;
  private int niveauConfiance;

  /****************************   Constructeur(s)   ***************************/
  /**
   * Constructeur vide
   */
  public NoeudConfiance() {
    super();
  }

  /**
   * Constructeur qui initialise l'adresse avec la valeur passée en paramètre et
   * la valeur 1 pour le degré de confiance
   * 
   * @param adresse
   *        la valeur à affecter pour l'attribut adresse
   */
  public NoeudConfiance(String adresse) {
    this.adresse = adresse;
    niveauConfiance = 1;
  }

  /**
   * Initialise les attributs de la classe avec les valeurs passées en paramètres
   * et la valeur 1 pour le degré de confiance
   *
   * @param adresse
   *        la valeur à affecter pour l'attribut adresse
   * 
   * @param duplication
   *        l'interface RMI associée au noeud serveur
   */
  public NoeudConfiance(String adresse, Duplication duplication) {
    this.adresse = adresse;
    this.duplication = duplication;
    this.niveauConfiance = 1;
  }


  
  /****************************   Accesseurs   ********************************/
  /**
   * Renvoie l'adresse du noeud de confiance
   * 
   * @return l'adresse du noeud de confiance
   */
  public String getAdresse() {
    return adresse;
  }

  /**
   * Affecte à l'attribut adresse la valeur passée en paramètre
   *
   * @param adresse
   *        la nouvelle valeur de l'adresse
   */
  public void setAdresse(String adresse) {
    this.adresse = adresse;
  }

  /**
   * Renvoie le degré de confiance
   * 
   * @return le degré de confiance
   */
  public int getNiveauConfiance() {
    return niveauConfiance;
  }

  /**
   * Affecte à l'attribut niveauConfiance la valeur passée en paramètre
   *
   * @param niveauConfiance
   *        la nouvelle valeur du degré de confiance
   */
  public void setNiveauConfiance(int niveauConfiance) {
    this.niveauConfiance = niveauConfiance;
  }

  /**
   * Renvoie l'interface RMI du noeud serveur
   *
   * @return l'interface RMI du noeud serveur
   */
  public Duplication getDuplication() {
    return duplication;
  }

  /**
   * Affecte à l'attribut duplication la valeur passée en paramètre
   * @param duplication
   *        la nouvelle valeur de l'interface du noeud serveur
   */
  public void setDuplication(Duplication duplication) {
    this.duplication = duplication;
  }

}
