/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package serveur;

import java.io.BufferedReader;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author maboisba
 */
public class NoeudServeur implements Duplication{

  private List<String> listeNomsFichiers;

  public NoeudServeur() {
    listeNomsFichiers  = new ArrayList<String>();
  }

  public List<String> getListeNomsFichiers() {
    return listeNomsFichiers;
  }

  public void ecrireFichier(String adresse, BufferedReader donnees, String nomFichier){
  }

  public void supprimerFichier(String nomFichier){
  }

  public BufferedReader extraireDonnees(String nomFichier){
    return null;
  }


}
