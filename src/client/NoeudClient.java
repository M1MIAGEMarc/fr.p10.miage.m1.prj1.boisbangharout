/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package client;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Serializable;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import serveur.Duplication;
import serveur.NoeudServeur;

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
    } catch (UnknownHostException ex) {
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
      if (fichier.getNom().equals(nomFichier)) {
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
  public void ajouterNoeudConfiance(String adresse) {
    try {
      Registry registry = LocateRegistry.getRegistry();

      Duplication duplication = (Duplication) registry.lookup("rmi://" + adresse + "/NoeudServeur");
      System.out.println("rmi://" + adresse + "/NoeudServeur");
      NoeudConfiance noeudConfiance = new NoeudConfiance(adresse, duplication);
      listeNoeudsConfiance.add(noeudConfiance);
    } catch (Exception e) {
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
  public void assignerConfiance(String adresse, int niveauConfiance) {

    for (NoeudConfiance noeudConfiance : listeNoeudsConfiance) {
      if (noeudConfiance.getAdresse().equals(adresse)) {
        noeudConfiance.setNiveauConfiance(niveauConfiance);
        break;
      }
    }

  }

  /**
   * Cette méthode est le traitement principal du projet, il permet la duplication
   * de fichiers sur des noeuds de confiance. Cette duplication se peut s'effectuer que
   * sous certaines conditions:
   *
   * - les degrés de confidentialité des fichiers à dupliquer doivent être compatibles
   *   avec les degrés de confiance des noeuds
   *
   * - un noeud de confiance peuvent être soumis à un traitement particulier pour
   *   certains fichiers. Dans ce cas précis, son degré de confiance n'est pas
   *   pris en compte pour la détermination du droit de duplication pour ces fichiers
   *   en question.
   *
   * - si le degré de confidentialité du fichier est 4 (non duplicable), il ne pourra
   *   de toute façon pas être dupliqué, il n'y aura donc pas de traitements à effectuer
   *   pour ceux-ci
   * 
   */
  public void dupliquerFichier() {
    for (Fichier fichier : listeFichiers) {
      if (fichier.getNiveauConfidentialite() != 4) {
        for (NoeudConfiance noeudConfiance : listeNoeudsConfiance) {
          try {
            if (fichier.getNoeudsConfianceMap().containsKey(noeudConfiance.getAdresse())) {
              if (fichier.getNoeudsConfianceMap().get(noeudConfiance.getAdresse())) {
                Duplication duplication = noeudConfiance.getDuplication();
                File file = new File(fichier.getNom());
                duplication.ecrireFichier(adresse, file, fichier.getNom());
              }
            } else {
              if (noeudConfiance.getNiveauConfiance() >= fichier.getNiveauConfidentialite()) {
                Duplication duplication = noeudConfiance.getDuplication();
                File file = new File(fichier.getNom());
                duplication.ecrireFichier(adresse, file, fichier.getNom());
              }
            }
          } catch (RemoteException re) {
            re.printStackTrace();
          }
        }
      }
    }
  }

  /**
   * Permet de supprimer un noeud de confiance de la liste des noeuds de confiance.
   * La suppression d'un noeud de confiance n'entraîne pas la suppression des fichiers
   * dupliqués sur celui-ci.
   *
   * @param adresse
   *        L'adresse du noeud de confiance à supprimer
   */
  public void supprimerNoeudConfiance(String adresse) {

    for (NoeudConfiance noeudConfiance : listeNoeudsConfiance) {
      if (noeudConfiance.getAdresse().equals(adresse)) {
        listeNoeudsConfiance.remove(noeudConfiance);
        break;
      }
    }
  }

  /**
   * L'objectif de cette méthode est d'ajouter un noeud à la liste des noeuds
   * de confiance spécifique pour un fichier.
   *
   * @param nomFichier
   *        Le nom du fichier pour lequel on souhaite ajouter le noeud de confiance
   * @param adresse
   *        L'adresse du noeude de confiance à ajouter à la liste du fichier
   * @param mode
   *        Accepter la duplication du fichier sur le noeud de confiance
   *
   */
  public void ajouterNoeudConfianceFichier(String nomFichier, String adresse, boolean mode) {

    for (Fichier fichier : listeFichiers) {
      if (fichier.getNom().equals(nomFichier)) {
        fichier.getNoeudsConfianceMap().put(adresse, mode);
        break;
      }
    }
  }

  /**
   * L'objectif de cette méthode est de supprimer un noeud de la liste des noeuds
   * de confiance spécifique pour un fichier.
   * 
   * @param nomFichier
   *        Le nom du fichier pour lequel on souhaite supprimer le noeud de confiance
   * @param adresse
   *        L'adresse du noeude de confiance à supprimer de la liste du fichier
   */
  public void supprimerNoeudConfianceFichier(String nomFichier, String adresse) {

    for (Fichier fichier : listeFichiers) {
      if (fichier.getNom().equals(nomFichier)) {
        fichier.getNoeudsConfianceMap().remove(adresse);
        break;
      }
    }
  }

  /**
   * Permet de supprimer sur les noeuds de confiance les fichiers dupliqués par le client
   * courant et qui ne figurent plus sur sa propre machine.
   *
   * Pour savoir si les fichiers du noeud de confiance sont des fichiers dupliqués par le
   * noeud client, il suffit de vérifier que le nom du fichier commence par l'adresse de
   * celui-ci.
   *
   * Exemple : 10.54.65.81_fic.txt (si l'adresse du noeud client est 10.54.65.81)
   */
  public void nettoyerFichiersDupliques() {

    boolean trouve = false;
    for (NoeudConfiance noeudConfiance : listeNoeudsConfiance) {

      try {
        List<String> listeFichiersServeur = noeudConfiance.getDuplication().getListeNomsFichiers();

        for (String nomFichier : listeFichiersServeur) {
          trouve = false;
          System.out.println(nomFichier);
          if (nomFichier.length() > adresse.length()
                  && nomFichier.substring(0, adresse.length()).equals(adresse)) {

            for (Fichier fichier : listeFichiers) {
              System.out.println("nomFichier : " + fichier.getNom());
              System.out.println("nomFichierClient : " + nomFichier.substring(adresse.length() + 1, nomFichier.length()));
              System.out.println("");
              if (fichier.getNom().equals(nomFichier.substring(adresse.length() + 1, nomFichier.length()))) {

                trouve = true;
                break;
              }
            }
            if (!trouve) {
              noeudConfiance.getDuplication().supprimerFichier(nomFichier);
              System.out.println("Fichier '" + nomFichier + "' supprimé.");
            }
          }
        }
      } catch (RemoteException re) {
        re.printStackTrace();
      }
    }
  }

  public void recupererFichiersPerdus() {
  }

  /**
   * Permet d'écrire sur le client un fichier à partir des données récupérées d'un
   * noeud de confiance.
   *
   * @param nomFichier
   *        Le nom du fichier à créer sur la machine cliente
   *
   * @param fichier
   *        Le fichier à ecrire sur la machine cliente
   */
  public void ecrireFichierPerdu(String nomFichier, File fichier) {

    String ligne;
    File file = new File(nomFichier);
    //On supprime le fichier s'il existe déjà pour l'écrire ensuite (cela évite d'écrire plusieurs fois les même données dans le fichier)
    file.delete(); 
    try {
      
      file.createNewFile();
      FileWriter fw = new FileWriter(file, true);
      BufferedWriter bw = new BufferedWriter(fw);
      BufferedReader donnees = new BufferedReader(new FileReader(fichier));

      while ((ligne = donnees.readLine()) != null) {
        bw.write(ligne);
        bw.flush();
      }
      bw.close();

    } catch (IOException ioe) {
      ioe.printStackTrace();
    }
  }
}
