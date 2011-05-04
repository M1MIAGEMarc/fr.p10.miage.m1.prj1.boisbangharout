/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package client;

import java.io.IOException;
import java.net.MalformedURLException;
import java.rmi.ConnectException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Map.Entry;
import java.util.Scanner;

/**
 * Cette classe est chargée d'effectuer les affichages des menus et
 * les contrôles sur les saisies de l'utilisateur.
 *
 * @author Marc Boisban
 * @author Kamel Gharout
 */
public class IHM {

  /****************************   Attributs   *********************************/
  private Noeud noeud;
  private Scanner scanner;

  /****************************   Constructeur(s)   ***************************/
  /**
   * Constructeur vide : instancie les attributs de la classe
   */
  public IHM() {
    super();
    try {
      noeud = new Noeud();
      scanner = new Scanner(System.in);
    }
    catch (RemoteException re) {
      re.printStackTrace();
    }
  }

  /**
   * Initialise les attributs de la classe avec les valeurs passées en paramètres
   *
   * @param noeud
   *        la nouvelle valeur de l'attribut noeud
   *
   * @param scanner
   *        la nouvelle valeur de l'attribut scanner
   */
  public IHM(Noeud noeud, Scanner scanner) {
    super();
    this.noeud = noeud;
    this.scanner = scanner;
  }

  /****************************   Accesseurs   ********************************/
  /**
   * Renvoie le noeud client de la classe
   * 
   * @return le noeud client de la classe
   */
  public Noeud getNoeud() {
    return noeud;
  }

  /**
   * Affecte à l'attribut noeud la valeur passée en paramètre
   *
   * @param noeud
   *        la nouvelle valeur de l'attribut noeud
   */
  public void setNoeud(Noeud noeud) {
    this.noeud = noeud;
  }

  /****************************   Autres méthodes   ***************************/
  /**
   * Cette méthode affiche le menu principal.
   *
   * Contraintes sur les saisies :
   * <ul>
   * <li> Les valeurs saisies doivent être numériques </li>
   * <li> Les valeurs saisies doivent être comprises entre 1 et 8 </li>
   * </ul>
   */
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
      /*
      System.out.println("Veuillez saisir l’action désirée :");
      scanner = new Scanner(System.in);
      */
      //choix = saisieControle(8);
      choix = saisir("Veuillez saisir l’action désirée : ", 8);
      
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
      }
    }
    while (choix != 8);


    System.out.println("Sortie du menu général.");
  }

  
  /**
   * Affiche la liste des fichiers que contient le noeud client (la machine
   * courante)
   */
  public void listerFichiers() {
    try {
      System.out.println("Liste des fichiers du répertoire courant");
      System.out.println("========================================\n");
      for (Fichier fichier : noeud.getListeFichiers()) {
        System.out.println("   " + fichier.getNom());
      }
      System.out.println("\nAppuyer sur entrée revenir au menu principal");
      System.in.read();
    }
    catch (IOException ioe) {
      ioe.printStackTrace();
    }
  }

  /**
   * Ecran permettant d'assigner des degrés de confidentialité aux fichiers
   *
   * Contraintes sur les saisies :
   * <ul>
   * <li> Les valeurs saisies doivent être numériques </li>
   * <li> La valeur saisie pour le choix du fichier doit être comprise entre 1 et n+1 (n étant le nombre de fichiers de l'utilisateur </li>
   * <li> Le degré de confidentialité saisis doit être compris entre 1 et 4 </li>
   * </ul>
   */
  public void assignerConfidentialite() {
    //int taille;
    int cpt;
    int choix, choix2 = 0;

    try {
      do {
        cpt = 1;
        System.out.println("Assigner un degré de confidentialité");
        System.out.println("====================================\n");
        /*
        listeFichiers = noeud.getListeFichiers();
        taille = listeFichiers.size() + 1;*/
        for (Fichier fichier : noeud.getListeFichiers()) {
          System.out.println("   " + cpt + ". " + fichier.getNom());
          cpt = cpt + 1;
        }

        System.out.println("\n   " + cpt + ". Retourner au menu principal");


        do {
          System.out.println("\nVeuillez sélectionner le fichier dont vous voulez assigner un degré de confidentialité :");
          scanner = new Scanner(System.in);

          /*
          choix = scanner.nextInt();
          if (choix < 1 || choix > cpt) {
          System.out.println("Erreur de saisie: votre choix doit être compris entre 1 et " + cpt);
          }*/
          choix = saisieControle(cpt);
        }
        while (choix == -1);
        /*
        if (choix == cpt) {
        menu();
        } else {
         */
        if (choix < cpt) {
          do {
            System.out.println("Quel degré de confidentialité souhaitez-vous lui assigner ?");
            choix2 = saisieControle(4);
            /*
            if (choix2 < 1 || choix2 > 4) {
            System.out.println("Erreur de saisie: votre choix doit être compris entre 1 et 4");
            }
             */
            //listeFichiers.get(choix).setNiveauConfidentialite(choix2);

            //assignerConfidentialite();
          }
          while (choix2 == -1);
          String nomFichier = noeud.getListeFichiers().get(choix - 1).getNom();
          noeud.assignerConfidentialite(nomFichier, choix2);
          System.out.println("Degré de confidentialité assigné avec succès.");
        }
      }
      while (choix < cpt);
    }
    catch (InputMismatchException ime) {
      System.out.println("La valeur saisie doit être un nombre");
      System.out.println("\nAppuyer sur entrée revenir au menu principal");
      try {
        System.in.read();
      }
      catch (IOException ioe) {
        ioe.printStackTrace();
      }
    }
  }

  /**
   * Permet d'ajouter un noeud de confiance
   *
   * Contraintes sur les saisies :
   * <ul>
   * <li> L'adresse réseau saisie doit être valide </li>
   * <li> Le degré de confiance saisi doit être compris entre 1 et 3</li>
   * </ul>
   */
  public void ajouterNoeudConfiance() {
    int choix;
    String adresse;
    System.out.println("Ajout d’un noeud de confiance");
    System.out.println("====================================\n");

    if (noeud.getListeNoeudsConfiance().isEmpty()) {
      System.out.println("Aucun noeud de confiance enregistré.");
    }
    for (NoeudConfiance noeudConfiance : noeud.getListeNoeudsConfiance()) {
      System.out.println("   " + noeudConfiance.getAdresse());
    }
    System.out.println("\n1. Retourner au menu principal\n");
    //System.out.println("Appuyer sur n'importe quel chiffre pour ajouter un  noeud de confiance");
    System.out.println("Veuillez saisir l’adresse réseau du nouveau noeud de confiance :");
    scanner = new Scanner(System.in);
    adresse = scanner.next();
    //if (choix == 1) {
    if (!adresse.equals("1")) {
      try {
        //System.out.println("Veuillez saisir l’adresse réseau du nouveau noeud de confiance :");
        System.out.println("Tentative de connexion à la machine " + adresse + " ...\n");
        if (noeud.ajouterNoeudConfiance(adresse)) {
          do {
            System.out.println("Quel degré de confiance souhaitez-vous lui assigner ?");
            //choix = scanner.nextInt();
            choix = saisieControle(3);
          }
          while (choix == -1);

          noeud.assignerConfiance(adresse, choix);
          System.out.println("Noeud de confiance ajouté avec succès.\n");
          noeud.dupliquerFichiers();
        }
        else {
          System.out.println("Noeud de confiance déjà présent dans la liste.");
          System.out.println("Aucun ajout effectué.\n");
        }
      }
      catch (MalformedURLException mue) {
        System.out.println("Erreur lors de l'ajout du noeud de confiance : adresse IP non valide.");
      }
      catch (ConnectException ce) {
        System.out.println("Erreur lors de l'ajout du noeud de confiance : service de noms de la machine distante non trouvé.");
      }
      catch (NotBoundException ex) {
        System.out.println("Erreur lors de l'ajout du noeud de confiance : objet distant non trouvé.");
      }
      catch (IOException ioe) {
        ioe.printStackTrace();
      }

      System.out.println("Appuyer sur entrée pour revenir au menu principal");

      try {
        System.in.read();
      }
      catch (IOException ioe) {
        ioe.printStackTrace();
      }
    }
  }

  /**
   * Permet de supprimer un noeud de confiance
   *
   * Contraintes sur les saisies :
   * <ul>
   * <li> Le choix du noeud de confiance saisi doit être compris entre 1 et n+1 (n étant le nombre de noeud de confiance de la liste) </li>
   * <li> Le degré de confiance saisi doit être compris entre 1 et 3</li>
   * </ul>
   */
  public void supprimerNoeudConfiance() {

    int taille;
    int choix = 0;
    do {
      System.out.println("Suppression d’un noeud de confiance");
      System.out.println("====================================\n");

      //listeNoeudsConfiance = noeud.getListeNoeudsConfiance();
      if (noeud.getListeNoeudsConfiance().isEmpty()) {
        System.out.println("Aucun noeud de confiance enregistré.");
      }
      taille = noeud.getListeNoeudsConfiance().size();
      for (int i = 0; i < taille; i++) {
        System.out.println("   " + (i + 1) + ". " + noeud.getListeNoeudsConfiance().get(i).getAdresse());
      }
      if (taille > 0) {
        System.out.println("\n   " + (taille + 1) + ". Retourner au menu principal");
        do {
          System.out.println("Veuillez choisir le noeud de confiance à supprimer :");
          //scanner = new Scanner(System.in);

          //choix = scanner.nextInt();
          choix = saisieControle(choix);
        }
        while (choix == -1);
      }
    }
    while (choix < taille);

    if (taille > 0 && choix <= taille) {
      noeud.supprimerNoeudConfiance(noeud.getListeNoeudsConfiance().get(choix - 1).getAdresse());
      System.out.println("\nNoeud de confiance supprimé avec succès.\n");

    }

    System.out.println("Appuyer sur entrée pour revenir au menu principal\n");

    try {
      System.in.read();
    }
    catch (IOException ioe) {
      ioe.printStackTrace();
    }
  }

  /**
   * Permet de modifier la liste des noeuds de confiance spécifique à un fichier.
   *
   * Contrainte sur les saisies pour le premier écran:
   * <ul>
   * <li> Le choix d'ajout,de suppression ou de retour doit être compris entre 1 et 3 </li>
   * </ul>
   *
   * Contrainte sur les saisies pour les écrans d'ajout et de suppression:
   * <ul>
   * <li> Le choix du noeud de confiance saisi doit être compris entre 1 et n+1 (n étant le nombre de noeud de confiance de la liste) </li>
   * </ul>
   */
  public void modifierListeNoeudsConfianceFichier() {

    List<String> listeAutorise = new ArrayList<String>();
    int taille;
    int cpt2 = 1;
    int choix, choix2, choix3, choix4, choix5, cpt = 0;
    boolean autorise;
    do {
      System.out.println("Modification de la liste des noeuds de confiance");
      System.out.println("===================================================\n");
      /*
      listeFichiers = noeud.getListeFichiers();
      taille = listeFichiers.size() + 1;
       */
      taille = noeud.getListeFichiers().size() + 1;
      for (Fichier fichier : noeud.getListeFichiers()) {
        System.out.println("   " + cpt2 + ". " + fichier.getNom());
        cpt2 = cpt2 + 1;
        //System.out.println(fichier.getNom());
      }
      System.out.println("   " + cpt2 + ". Retourner au menu principal");
      System.out.println("\nVeuillez sélectionner le fichier dont vous voulez modifier la liste des noeuds :");
      scanner = new Scanner(System.in);


      choix = scanner.nextInt();

    }
    while (choix > taille);

    /*
    if (choix == taille) {
    menu();
    } else {*/
    if (choix < taille) {
      do {
        HashMap<String, Boolean> noeudFichierMap;
        System.out.println(" 1. Ajouter un noeud de confiance à un fichier");
        System.out.println(" 2. Enlever un noeud de confiance à un fichier");
        System.out.println(" 3. Retourner au menu principal");
        System.out.println("Veuillez sélectionner la modification que vous souhaitez réaliser :");
        choix2 = scanner.nextInt();
        /*
        if (choix2 == 3) {
        menu();
        }else*/
        if (choix2 == 2) {
          do {
            cpt = 0;
            System.out.println("Suppression d'un noeud de confiance pour le fichier");
            System.out.println("===================================================");
            System.out.println("Liste des noeuds de confiance dont la duplication est autorisée");
            noeudFichierMap = noeud.getListeFichiers().get(choix).getNoeudsConfianceMap();

            int niveauConfidentialite = noeud.getListeFichiers().get(choix - 1).getNiveauConfidentialite();

            for (NoeudConfiance noeudConfiance : noeud.getListeNoeudsConfiance()) {
              if (noeudConfiance.getNiveauConfiance() >= niveauConfidentialite) {
                listeAutorise.add(noeudConfiance.getAdresse());
              }
            }

            for (Entry<String, Boolean> currentEntry : noeudFichierMap.entrySet()) {
              if (currentEntry.getValue()) {
                if (!listeAutorise.contains(currentEntry.getKey())) {
                  listeAutorise.add(currentEntry.getKey());
                }
              }
              else {
                if (listeAutorise.contains(currentEntry.getKey())) {
                  listeAutorise.remove(currentEntry.getKey());
                }
              }
            }

            for (String adresse : listeAutorise) {
              cpt += 1;
              System.out.println("   " + cpt + ". " + adresse);
            }

            cpt += 1;
            System.out.println("\n   " + cpt + ". Retourner au menu principal");

            System.out.println("\nVeuillez selectionner le noeud de confiance à supprimer pour le fichier :");
            choix3 = scanner.nextInt();
          }
          while (choix3 > cpt);
          /*
          if (choix3 == cpt) {
          modifierListeNoeudsConfianceFichier();
          } else {
           */
          if (choix3 < cpt) {
            //noeudFichierMap.remove(listeAutorise.get(choix3 - 1));
            noeudFichierMap.put(listeAutorise.get(choix3 - 1), false);
            System.out.println("Le noeud a été supprimé");
            listeAutorise.clear();
          }
        }
        else if (choix2 == 1) {


          do {
            //noeudFichierMap = noeud.getListeFichiers().get(choix).getNoeudsConfianceMap();
            cpt = 0;
            System.out.println("Ajout d'un noeud de confiance pour le fichier");
            System.out.println("===================================================\n");
            System.out.println("Liste des noeuds de confiance dont la duplication n'est pas autorisée");
            //noeudFichierMap = listeFichiers.get(choix).getNoeudsConfianceMap();
            noeudFichierMap = noeud.getListeFichiers().get(choix).getNoeudsConfianceMap();

            int niveauConfidentialite = noeud.getListeFichiers().get(choix - 1).getNiveauConfidentialite();

            for (NoeudConfiance noeudConfiance : noeud.getListeNoeudsConfiance()) {
              if (noeudConfiance.getNiveauConfiance() < niveauConfidentialite) {
                if (!listeAutorise.contains(noeudConfiance.getAdresse())) {
                  listeAutorise.add(noeudConfiance.getAdresse());
                }
              }
            }

            for (Entry<String, Boolean> currentEntry : noeudFichierMap.entrySet()) {
              if (currentEntry.getValue() == false) {
                if (!listeAutorise.contains(currentEntry.getKey())) {
                  listeAutorise.add(currentEntry.getKey());
                }
              }
              else {
                if (listeAutorise.contains(currentEntry.getKey())) {
                  listeAutorise.remove(currentEntry.getKey());
                }
              }
            }

            for (String adresse : listeAutorise) {
              cpt += 1;
              System.out.println("   " + cpt + ". " + adresse);
            }

            cpt += 1;
            System.out.println("\n   " + cpt + ". Retourner au menu principal");
            System.out.println("Veuillez selectionner le noeud de confiance à ajouter pour le fichier :");
            choix4 = scanner.nextInt();
          }
          while (choix4 > cpt);
          /*
          if (choix4 == cpt) {
          modifierListeNoeudsConfianceFichier();
          } else {*/
          if (choix4 < cpt) {
            //noeudFichierMap.remove(listeAutorise.get(choix4 - 1));
            noeudFichierMap.put(listeAutorise.get(choix4 - 1), true);
            System.out.println("Le noeud a été ajouté");
            //modifierListeNoeudsConfianceFichier();
            listeAutorise.clear();
          }
        }
      }
      while (choix != 1 && choix != 2 && choix != 3);
    }
  }

  /**
   * Permet de nettoyer les fichiers dupliqués n'étant plus présent sur le noeud client.
   * 
   */
  public void nettoyer() {
    noeud.nettoyerFichiersDupliques();
    System.out.println("Nettoyage des fichiers effectué");
    System.out.println("Appuyer sur entrée pour continuer");
  }

  /**
   * Permet de récupérer les fichiers perdus qui ont été dupliqués sur les noeuds de confiance.
   * 
   */
  public void recuperer() {
    noeud.recupererFichiersPerdus();
    System.out.println("Fichiers récupérés");
    System.out.println("------------------");
    //afficher les fichier recuperer
  }

  /**
   * Récupère la saisie du client, effectue un contrôle dessus en fonction des valeurs
   * passées en paramètre et renvoie la valeur saisie si elle passe le contrôle avec succès,
   * renvoie -1 sinon.
   *
   * Pour passer le contrôle avec succès, la valeur doit être numérique et comprise
   * entre 1 et le paramètre max (inclus)
   *
   * @param max
   *        la valeur maximale qui peut être saisie
   * 
   * @return la valeur saisie par l'utilisateur, -1 si elle n'est pas conforme
   */
  public int saisieControle(int max) {
    int choix = 0;

    try {
      choix = scanner.nextInt();

      if (choix < 1 || choix > max) {
        System.out.println("Erreur de saisie: la valeur saisie doit être comprise entre 1 et " + max + ". Valeur trouvée : " + choix + ".");
        System.out.println("\nAppuyer sur entrée pour recommencer la saisie.");
        choix = -1;
        try {
          System.in.read();
        }
        catch (IOException ioe) {
          ioe.printStackTrace();
        }
      }
    }
    catch (InputMismatchException ime) {
      System.out.println("La valeur saisie doit être numérique.");
      choix = -1;
    }

    return choix;
  }

  /**
   *
   * @param message
   *        le message à afficher pour l'invite de commandes
   *
   * @param max
   *        la valeur maximale de la saisie
   */
  public int saisir(String message, int max) {

    int saisie = 0;
    int controle = -1;
    
    do {
      System.out.println(message);
      try {
        saisie = scanner.nextInt();
        controle = controler(saisie, max);
      }
      catch (InputMismatchException ime) {
        System.out.println("La valeur saisie doit être numérique.");
      }
    } while (controle == -1);

    return saisie;
  }

  /**
   *
   * @param saisie
   *        la valeur saisie par l'utilisateur
   *
   * @param max
   *        la valeur maximale de la saisie
   *
   * @return la valeur saisie par l'utilisateur si le controle passe avec succès,
   *         renvoie -1 sinon
   */
  public int controler(int valeur, int max) {

    if (valeur < 1 || valeur > max) {
      System.out.println("Erreur de saisie: la valeur saisie doit être comprise entre 1 et " + max + ". Valeur trouvée : " + valeur + ".");
      System.out.println("\nAppuyer sur entrée pour recommencer la saisie.");
      valeur = -1;
      try {
        System.in.read();
      }
      catch (IOException ioe) {
        ioe.printStackTrace();
      }
    }
    return valeur;
  }

  /**
   * Permet de revenir au menu précédent en appuyant sur la touche entrée
   * 
   */
  public void revenir() {
    System.out.println("\nAppuyer sur entrée revenir au menu principal.");
    try {
      System.in.read();
    }
    catch (IOException ioe) {
      ioe.printStackTrace();
    }
  }
}
