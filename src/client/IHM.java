/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package client;

import java.io.IOException;
import java.net.MalformedURLException;
import java.rmi.ConnectException;
import java.rmi.NotBoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;
import java.util.Scanner;

/**
 *
 * @author Marc Boisban
 * @author Kamel Gharout
 */
public class IHM {

  private NoeudClient noeudClient;
  private Scanner scanner;
  private List<Fichier> listeFichiers;
  private List<NoeudConfiance> listeNoeudsConfiance;
  private HashMap<String, Boolean> NoeudFichier;

  public IHM() {
    super();
    noeudClient = new NoeudClient();
    ;
    scanner = new Scanner(System.in);
  }

  public IHM(NoeudClient noeudClient, Scanner scanner) {
    super();
    this.noeudClient = noeudClient;
    this.scanner = scanner;
  }

  public NoeudClient getNoeudClient() {
    return noeudClient;
  }

  public void setNoeudClient(NoeudClient noeudClient) {
    this.noeudClient = noeudClient;
  }

  public void menu() {
    int choix = 0;
    do {
      System.out.println("Bienvenue");
      System.out.println("=========\n");
      System.out.println("   1. Afficher les fichiers du répertoire courant");
      System.out.println("   2. Assigner un degré de confidentialité à un fichier");
      System.out.println("   3. Ajouter un noeud à la liste des noeuds de confiance");
      System.out.println("   4. Enlever un noeud de la liste des noeuds de confiance");
      System.out.println("   5. Modifier la liste des noeuds de confiance pour un fichier");
      System.out.println("   6. Nettoyer les fichiers dupliqués et n’étant plus dans le répertoire");
      System.out.println("   7. Récupérer les données perdues");
      System.out.println("   8. Quitter");
      System.out.println("");
      System.out.println("Veuillez saisir l’action désirée :");
      scanner = new Scanner(System.in);

      choix = scanner.nextInt();

      switch (choix) {

        case 1:
          listerFichiers();
          break;
        case 2:
          assignerConfidentialite();
          break;
        case 3:
          ajouterNoeudConfiance();
          break;
        case 4:
          supprimerNoeudConfiance();
          break;
        case 5:
          modifierListeNoeudsConfianceFichier();
          break;
        case 6:
          nettoyer();
          break;
        case 7:
          recuperer();
          break;
        case 8:
          break;
        default:
          System.out.println("Erreur de saisie: vous devez saisir un nombre entre 1 et 8.");
          break;
      }

    } while (choix != 8);


    System.out.println("Sortie du menu général.");
  }

  public void listerFichiers() {
    try {
      System.out.println("Liste des fichiers du répertoire courant");
      System.out.println("========================================\n");
      for (Fichier fichier : noeudClient.getListeFichiers()) {
        System.out.println("   " + fichier.getNom());
      }
      System.out.println("\nAppuyer sur entrée revenir au menu principal");
      System.in.read();
    } catch (IOException ioe) {
      ioe.printStackTrace();
    }
  }

  public void assignerConfidentialite() {
    //int taille;
    int cpt = 1;
    int choix, choix2 = 0;
    do {
      System.out.println("Assigner un degré de confidentialité");
      System.out.println("====================================\n");
      /*
      listeFichiers = noeudClient.getListeFichiers();
      taille = listeFichiers.size() + 1;*/
      for (Fichier fichier : noeudClient.getListeFichiers()) {
        System.out.println("   " + cpt + ". " + fichier.getNom());
        cpt = cpt + 1;
      }

      System.out.println("\n   " + cpt + ". Retourner au menu principal");
      System.out.println("\nVeuillez sélectionner le fichier dont vous voulez assigner un degré de confidentialité :");
      scanner = new Scanner(System.in);

      choix = scanner.nextInt();

    } while (choix > cpt);
    if (choix == cpt) {
      menu();
    } else {
      System.out.println("Quel degré de confidentialité souhaitez-vous lui assigner ?");
      choix2 = scanner.nextInt();
      //listeFichiers.get(choix).setNiveauConfidentialite(choix2);
      String nomFichier = noeudClient.getListeFichiers().get(choix).getNom();
      noeudClient.assignerConfidentialite(nomFichier, choix2);
      System.out.println("Degré de confidentialité assigné avec succès.");
      assignerConfidentialite();

    }

  }

  public void ajouterNoeudConfiance() {
    int choix;
    String adresse;
    System.out.println("Ajout d’un noeud de confiance");
    System.out.println("====================================\n");

    if (noeudClient.getListeNoeudsConfiance().size() == 0) {
      System.out.println("Aucun noeud de confiance enregistré.");
    }
    for (NoeudConfiance noeudConfiance : noeudClient.getListeNoeudsConfiance()) {
      System.out.println(noeudConfiance.getAdresse());
    }
    System.out.println("\n1. Retourner au menu principal");
    //System.out.println("Appuyer sur n'importe quel chiffre pour ajouter un  noeud de confiance");
    System.out.println("Veuillez saisir l’adresse réseau du nouveau noeud de confiance :");
    scanner = new Scanner(System.in);
    adresse = scanner.next();
    //if (choix == 1) {
    if (!adresse.equals("1")) {
      try {
        //System.out.println("Veuillez saisir l’adresse réseau du nouveau noeud de confiance :");
        System.out.println("Tentative de connexion à la machine " + adresse + " ...\n");
        if (noeudClient.ajouterNoeudConfiance(adresse)) {
          System.out.println("Quel degré de confiance souhaitez-vous lui assigner ?");
          choix = scanner.nextInt();
          noeudClient.assignerConfiance(adresse, choix);
          System.out.println("Noeud de confiance ajouté avec succès.\n");
        } else {
          System.out.println("Noeud de confiance déjà présent dans la liste.");
          System.out.println("Aucun ajout effectué.\n");
        }
      } catch (MalformedURLException mue) {
        System.out.println("Erreur lors de l'ajout du noeud de confiance : adresse IP non valide.");
      } catch (ConnectException ce) {
        System.out.println("Erreur lors de l'ajout du noeud de confiance : service de noms de la machine distante non trouvé.");
      } catch (NotBoundException ex) {
        System.out.println("Erreur lors de l'ajout du noeud de confiance : objet distant non trouvé.");
      } catch (IOException ioe) {
        ioe.printStackTrace();
      }

      System.out.println("Appuyer sur entrée pour revenir au menu principal");

      try {
        System.in.read();
      } catch (IOException ioe) {
        ioe.printStackTrace();
      }
    }
  }

  public void supprimerNoeudConfiance() {

    int taille;
    int choix = 0;
    do {
      System.out.println("Suppression d’un noeud de confiance");
      System.out.println("====================================\n");

      //listeNoeudsConfiance = noeudClient.getListeNoeudsConfiance();
      if (noeudClient.getListeNoeudsConfiance().size() == 0) {
        System.out.println("Aucun noeud de confiance enregistré.");
      }
      taille = noeudClient.getListeNoeudsConfiance().size();
      for (int i = 0; i < taille; i++) {
        System.out.print((i + 1) + ". " + noeudClient.getListeNoeudsConfiance().get(i));
      }
      if(taille > 0) {
      System.out.println("\n   " + (taille+1) + ". Retourner au menu principal");
      System.out.println("Veuillez choisir le noeud de confiance à supprimer :");
      scanner = new Scanner(System.in);


      choix = scanner.nextInt();
      }
    } while (choix < taille);

    if (choix == taille) {
      menu();
    } else {
      noeudClient.supprimerNoeudConfiance(listeNoeudsConfiance.get(choix).getAdresse());
      menu();
    }


  }

  public void modifierListeNoeudsConfianceFichier() {

    List<String> listeAutorise = new ArrayList<String>();
    int taille;
    int cpt2 = 1;
    int choix, choix2, choix3, choix4, choix5, cpt = 0;
    boolean autorise;
    do {
      System.out.println("Modification de la liste des noeuds de confiance");
      System.out.println("===================================================\n");
      listeFichiers = noeudClient.getListeFichiers();
      taille = listeFichiers.size() + 1;
      for (Fichier fichier : listeFichiers) {
        System.out.print(cpt2 + ". ");
        cpt2 = cpt2 + 1;
        System.out.println(fichier.getNom());
      }
      System.out.println(taille + ". Retourner au menu principal");
      System.out.println("Veuillez sélectionner le fichier dont vous voulez modifier la liste des noeuds :");
      scanner = new Scanner(System.in);


      choix = scanner.nextInt();

    } while (choix > taille);

    if (choix == taille) {
      menu();
    } else {
      do {
        System.out.println(" 1. Ajouter un noeud de confiance à un fichier");
        System.out.println(" 2. Enlever un noeud de confiance à un fichier");
        System.out.println(" 3. Retourner au menu principale");
        System.out.println("Veuillez sélectionner la modification que vous souhaitez réaliser :");
        choix2 = scanner.nextInt();
        if (choix2 == 3) {
          menu();
        } else if (choix2 == 2) {
          do {
            cpt = 0;
            System.out.println("Suppression d'un noeud de confiance pour le fichier");
            System.out.println("===================================================");
            System.out.println("Liste des noeuds de confiance dont la duplication est autorisée");
            NoeudFichier = listeFichiers.get(choix).getNoeudsConfianceMap();

            for (Entry<String, Boolean> currentEntry : NoeudFichier.entrySet()) {
              if (currentEntry.getValue()) {
                listeAutorise.add(currentEntry.getKey());
                cpt += 1;
                System.out.println(cpt + ". " + currentEntry.getKey());
              }
            }
            cpt += 1;
            System.out.println(cpt + ". Retourner au menu principal");
            System.out.println("Veuillez selectionner le noeud de confiance à supprimer pour le fichier :");
            choix3 = scanner.nextInt();
          } while (choix3 > cpt);
          if (choix3 == cpt) {
            modifierListeNoeudsConfianceFichier();
          } else {
            NoeudFichier.remove(listeAutorise.get(choix3 - 1));
            System.out.println("Le noeud a été supprimé");
            modifierListeNoeudsConfianceFichier();
            listeAutorise.clear();
          }


        } else if (choix2 == 1) {


          do {
            cpt = 0;
            System.out.println("Ajout d'un noeud de confiance pour le fichier");
            System.out.println("===================================================\n");
            System.out.println("Liste des noeuds de confiance dont la duplication n'est pas autorisée");
            NoeudFichier = listeFichiers.get(choix).getNoeudsConfianceMap();

            for (Entry<String, Boolean> currentEntry : NoeudFichier.entrySet()) {
              if (currentEntry.getValue() == false) {
                cpt += 1;
                listeAutorise.add(currentEntry.getKey());
                System.out.println(cpt + ". " + currentEntry.getKey());
              }
            }
            cpt += 1;
            System.out.println(cpt + ". Retourner au menu principal");
            System.out.println("Veuillez selectionner le noeud de confiance à ajouter pour le fichier :");
            choix4 = scanner.nextInt();
          } while (choix4 > cpt);
          if (choix4 == cpt) {
            modifierListeNoeudsConfianceFichier();
          } else {
            NoeudFichier.remove(listeAutorise.get(choix4 - 1));
            NoeudFichier.put(listeAutorise.get(choix4 - 1), true);
            System.out.println("Le noeud a été ajouté");
            modifierListeNoeudsConfianceFichier();
            listeAutorise.clear();
          }
        }
      } while (choix != 1 || choix != 2 || choix != 3);
    }
  }

  public void nettoyer() {
    noeudClient.nettoyerFichiersDupliques();
    System.out.println("Nettoyage des fichiers effectué");
    System.out.println("Appuyer sur entrée pour continuer");
  }

  public void recuperer() {
    noeudClient.recupererFichiersPerdus();
    System.out.println("Fichiers récupérés");
    System.out.println("------------------");
    noeudClient.recupererFichiersPerdus();
    //afficher les fichier recuperer

  }
}




