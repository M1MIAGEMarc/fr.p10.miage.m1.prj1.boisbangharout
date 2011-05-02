/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package client;

import java.util.HashMap;

/**
 * Classe modélisant un fichier sur la machine courante
 *
 * @author Marc Boisban
 * @author Kamel Gharout
 */
public class Fichier {

  /****************************   Attributs   *********************************/
  private String nom;
  private int niveauConfidentialite;
  private HashMap<String, Boolean> noeudsConfianceMap;



  /****************************   Constructeur(s)   ***************************/

  /**
   * Initialise un fichier en affectant à l'attribut nom la valeur passée en paramètre
   *
   * @param nom
   *        La nouvelle valeur du nom
   */
  public Fichier(String nom) {
    this.nom = nom;
    niveauConfidentialite = 4;
    noeudsConfianceMap = new HashMap<String, Boolean>();
  }

  /*
  public Fichier(String nom, int niveauConfidentialite, HashMap<String, Boolean> noeudsConfianceMap) {
    this.nom = nom;
    this.niveauConfidentialite = niveauConfidentialite;
    this.noeudsConfianceMap = noeudsConfianceMap;
  }*/



  /****************************   Accesseurs   ********************************/

  /**
   * Renvoie le niveau de confidentialité du fichier
   *
   * @return le niveau de confidentialité du fichier
   */
  public int getNiveauConfidentialite() {
    return niveauConfidentialite;
  }

  /**
   * Affecte à l'attribut niveauConfidentialite la valeur passée en paramètre
   *
   * @param niveauConfidentialite
   *        Le nouvelle valeur du niveau de confidentialité du fichier
   */
  public void setNiveauConfidentialite(int niveauConfidentialite) {
    this.niveauConfidentialite = niveauConfidentialite;
  }

  /**
   * Renvoie le hashmap des noeuds de confiance du fichier
   *
   * @return le hashmap des noeuds de confiance du fichier
   */
  public HashMap<String, Boolean> getNoeudsConfianceMap() {
    return noeudsConfianceMap;
  }

  /**
   * Affecte à l'attribut noeudsConfianceMap la valeur passée en paramètre;
   * 
   * @param noeudsConfianceMap
   */
  public void setNoeudsConfianceMap(HashMap<String, Boolean> noeudsConfianceMap) {
    this.noeudsConfianceMap = noeudsConfianceMap;
  }

  /**
   * Renvoie le nom du fichier
   *
   * @return le nom du fichier
   */
  public String getNom() {
    return nom;
  }
  

  /**
   * Affecte à l'attribut nom la valeur passée en paramètre
   *
   * @param nom
   *        Le nouvelle valeur du nom du fichier
   */
  public void setNom(String nom) {
    this.nom = nom;
  }

}
