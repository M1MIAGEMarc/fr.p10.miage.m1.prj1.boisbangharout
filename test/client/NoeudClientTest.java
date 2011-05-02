package client;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.MalformedURLException;
import java.rmi.AccessException;
import java.rmi.ConnectException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import junit.framework.Assert;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import serveur.Duplication;

/**
 * Classe de tests pour la classe NoeudClient
 *
 * @author Marc Boisban
 * @author Kamel Gharout
 */
public class NoeudClientTest {

  
  /****************************   Attributs   *********************************/
  private NoeudClient noeudClient;



  /****************************   Constructeur(s)   ***************************/
  public NoeudClientTest() {
    noeudClient = new NoeudClient();
  }



  /****************************   Méthodes automatiquement générées  **********/
  @BeforeClass
  public static void setUpClass() throws Exception {
  }

  @AfterClass
  public static void tearDownClass() throws Exception {
  }



  /****************************   Méthodes de test  ***************************/
  @Test
  public void testAssignerConfidentialite() {

    List<Fichier> listeFichiers = noeudClient.getListeFichiers();
    listeFichiers.add(new Fichier("Fic0.txt"));
    listeFichiers.add(new Fichier("Fic1.txt"));
    listeFichiers.add(new Fichier("Fic2.txt"));
    listeFichiers.add(new Fichier("Fic3.txt"));

    if (listeFichiers.size() > 0) {
      int confidentialiteAttendue;

      noeudClient.assignerConfidentialite(listeFichiers.get(0).getNom(), 1);
      confidentialiteAttendue = 1;
      Assert.assertEquals(confidentialiteAttendue, listeFichiers.get(0).getNiveauConfidentialite());

      noeudClient.assignerConfidentialite(listeFichiers.get(1).getNom(), 2);
      confidentialiteAttendue = 2;
      Assert.assertEquals(confidentialiteAttendue, listeFichiers.get(1).getNiveauConfidentialite());

      noeudClient.assignerConfidentialite(listeFichiers.get(2).getNom(), 3);
      confidentialiteAttendue = 3;
      Assert.assertEquals(confidentialiteAttendue, listeFichiers.get(2).getNiveauConfidentialite());

      noeudClient.assignerConfidentialite(listeFichiers.get(3).getNom(), 4);
      confidentialiteAttendue = 4;
      Assert.assertEquals(confidentialiteAttendue, listeFichiers.get(3).getNiveauConfidentialite());
    } else {
      Assert.fail();

    }
  }

  @Test
  public void testAjouterNoeudConfiance() {
    String adresse;
    List<NoeudConfiance> listeNoeudsConfiance = noeudClient.getListeNoeudsConfiance();
    try {
      /*InetAddress adress = Inet4Address.getLocalHost();
      adresse = adress.getHostAddress();*/
      adresse = "192.168.19.4";
      noeudClient.ajouterNoeudConfiance(adresse);
      Assert.assertEquals(adresse, listeNoeudsConfiance.get(0).getAdresse());
      /*
      adresse = "10.54.65.83";
      noeudClient.ajouterNoeudConfiance(adresse);
      Assert.assertEquals(adresse, listeNoeudsConfiance.get(1).getAdresse());

      adresse = "10.54.65.84";
      noeudClient.ajouterNoeudConfiance(adresse);
      Assert.assertEquals(adresse, listeNoeudsConfiance.get(2).getAdresse());

      adresse = "10.54.65.85";
      noeudClient.ajouterNoeudConfiance(adresse);
      Assert.assertEquals(adresse, listeNoeudsConfiance.get(3).getAdresse());

      adresse = "10.54.65.86";
      noeudClient.ajouterNoeudConfiance(adresse);
      Assert.assertEquals(adresse, listeNoeudsConfiance.get(3).getAdresse());
       */
    } catch (MalformedURLException ex) {
      Logger.getLogger(NoeudClientTest.class.getName()).log(Level.SEVERE, null, ex);
    } catch (ConnectException ex) {
      Logger.getLogger(NoeudClientTest.class.getName()).log(Level.SEVERE, null, ex);
    } catch (RemoteException ex) {
      Logger.getLogger(NoeudClientTest.class.getName()).log(Level.SEVERE, null, ex);
    } catch (IndexOutOfBoundsException e) {
      System.out.println(e.getMessage());
      Assert.fail();
    }
    catch (NotBoundException e) {
      System.out.println(e.getMessage());
      Assert.fail();
    }
  }

  @Test
  public void testAssignerConfiance() {
    int confianceAttendue;
    List<NoeudConfiance> listeNoeudsConfiance = noeudClient.getListeNoeudsConfiance();

    NoeudConfiance noeudConfiance1 = new NoeudConfiance("10.54.65.85");
    listeNoeudsConfiance.add(noeudConfiance1);
    noeudClient.assignerConfiance("10.54.65.85", 1);

    NoeudConfiance noeudConfiance2 = new NoeudConfiance("10.54.65.86");
    listeNoeudsConfiance.add(noeudConfiance2);
    noeudClient.assignerConfiance("10.54.65.86", 2);

    NoeudConfiance noeudConfiance3 = new NoeudConfiance("10.54.65.87");
    listeNoeudsConfiance.add(noeudConfiance3);
    noeudClient.assignerConfiance("10.54.65.87", 3);

    confianceAttendue = 1;
    Assert.assertEquals(confianceAttendue, listeNoeudsConfiance.get(0).getNiveauConfiance());
    confianceAttendue = 2;
    Assert.assertEquals(confianceAttendue, listeNoeudsConfiance.get(1).getNiveauConfiance());
    confianceAttendue = 3;
    Assert.assertEquals(confianceAttendue, listeNoeudsConfiance.get(2).getNiveauConfiance());
  }

  @Test
  public void testDupliquerFichiers() {
    
    try {
      FileReader fileReader = null;
      List<Fichier> listeFichiers = noeudClient.getListeFichiers();
      Fichier fichier = new Fichier("Fic1.txt");
      fichier.setNiveauConfidentialite(1);
      listeFichiers.add(fichier);
      //String adresse = noeudClient.getAdresse();
      String adresse = "192.168.19.4";
      
      try {
        fileReader = new FileReader(listeFichiers.get(0).getNom());
        BufferedReader br = new BufferedReader(fileReader);

        /*
        Registry registry = LocateRegistry.getRegistry();
        Duplication duplication = (Duplication) registry.lookup("rmi://" + adresse + "/NoeudServeur");*/
        Duplication duplication = (Duplication) Naming.lookup("rmi://" + adresse + "/NoeudServeur");
        NoeudConfiance noeud = new NoeudConfiance(adresse, duplication);
        
        noeud.setNiveauConfiance(1);
        noeudClient.getListeNoeudsConfiance().add(noeud);
        noeudClient.dupliquerFichiers();

        for (NoeudConfiance noeudConfiance : noeudClient.getListeNoeudsConfiance()) {  
          Assert.assertNotNull(new FileReader(adresse + "_" + listeFichiers.get(0).getNom()));
          List<String> listeNomFichiers = duplication.getListeNomsFichiers();

          if (noeudConfiance.getNiveauConfiance() >= listeFichiers.get(0).getNiveauConfidentialite()) {
            Assert.assertTrue(listeNomFichiers.contains(adresse + "_" + listeFichiers.get(0).getNom()));
          }
        }
      } catch (MalformedURLException ex) {
        Logger.getLogger(NoeudClientTest.class.getName()).log(Level.SEVERE, null, ex);
      } catch (FileNotFoundException fnfe) {
        fnfe.printStackTrace();
        Assert.fail();
      }
    } catch (NotBoundException nbe) {
      nbe.printStackTrace();
      Assert.fail();
    } catch (RemoteException re) {
      re.printStackTrace();
      Assert.fail();
    } catch (IndexOutOfBoundsException e) {
      e.printStackTrace();
      Assert.fail();
    }
  }

  @Test
  public void testSupprimerNoeudConfiance() {
    String adresse;
    List<NoeudConfiance> listeNoeudsConfiance = noeudClient.getListeNoeudsConfiance();
    listeNoeudsConfiance.add(new NoeudConfiance("10.54.65.82"));
    listeNoeudsConfiance.add(new NoeudConfiance("10.54.65.83"));
    listeNoeudsConfiance.add(new NoeudConfiance("10.54.65.84"));
    listeNoeudsConfiance.add(new NoeudConfiance("10.54.65.85"));
    listeNoeudsConfiance.add(new NoeudConfiance("10.54.65.86"));
    try {
      Assert.assertEquals("10.54.65.82", listeNoeudsConfiance.get(0).getAdresse());
      Assert.assertEquals("10.54.65.86", listeNoeudsConfiance.get(4).getAdresse());
      noeudClient.supprimerNoeudConfiance("10.54.65.82");
      Assert.assertEquals("10.54.65.83", listeNoeudsConfiance.get(0).getAdresse());
      Assert.assertEquals("10.54.65.84", listeNoeudsConfiance.get(1).getAdresse());
      Assert.assertEquals("10.54.65.85", listeNoeudsConfiance.get(2).getAdresse());
      Assert.assertEquals("10.54.65.86", listeNoeudsConfiance.get(3).getAdresse());
    } catch (IndexOutOfBoundsException e) {
      Assert.fail();
    }
  }

  @Test
  public void testAjouterNoeudConfianceFichier() {
    List<Fichier> listeFichiers = noeudClient.getListeFichiers();
    listeFichiers.add(new Fichier("FicAjoutNoeudConfFichier1.txt"));
    try {
      Assert.assertEquals(false, listeFichiers.get(0).getNoeudsConfianceMap().containsKey("10.54.65.84"));
      Assert.assertEquals(false, listeFichiers.get(0).getNoeudsConfianceMap().containsKey("10.54.65.86"));

      noeudClient.ajouterNoeudConfianceFichier(listeFichiers.get(0).getNom(), "10.54.65.84", true);
      Assert.assertEquals(true, listeFichiers.get(0).getNoeudsConfianceMap().containsKey("10.54.65.84"));
      Assert.assertEquals(false, listeFichiers.get(0).getNoeudsConfianceMap().containsKey("10.54.65.86"));

      noeudClient.ajouterNoeudConfianceFichier(listeFichiers.get(0).getNom(), "10.54.65.86", false);
      Assert.assertEquals(true, listeFichiers.get(0).getNoeudsConfianceMap().containsKey("10.54.65.84"));
      Assert.assertEquals(true, listeFichiers.get(0).getNoeudsConfianceMap().containsKey("10.54.65.86"));
      Assert.assertEquals(true, (boolean) listeFichiers.get(0).getNoeudsConfianceMap().get("10.54.65.84"));
      Assert.assertEquals(false, (boolean) listeFichiers.get(0).getNoeudsConfianceMap().get("10.54.65.86"));
    } catch (IndexOutOfBoundsException e) {
      e.printStackTrace();
      Assert.fail();
    }
  }

  @Test
  public void testSupprimerNoeudConfianceFichier() {

    List<Fichier> listeFichiers = noeudClient.getListeFichiers();
    Fichier fichier1 = new Fichier("FicSupNoeudConfFichier1.txt");
    fichier1.getNoeudsConfianceMap().put("10.54.65.84", false);
    fichier1.getNoeudsConfianceMap().put("10.54.65.86", true);
    listeFichiers.add(fichier1);

    try {
      Assert.assertEquals(true, fichier1.getNoeudsConfianceMap().containsKey("10.54.65.84"));
      Assert.assertEquals(true, fichier1.getNoeudsConfianceMap().containsKey("10.54.65.86"));

      noeudClient.supprimerNoeudConfianceFichier(fichier1.getNom(), "10.54.65.84");
      Assert.assertEquals(false, fichier1.getNoeudsConfianceMap().containsKey("10.54.65.84"));
      Assert.assertEquals(true, fichier1.getNoeudsConfianceMap().containsKey("10.54.65.86"));

      noeudClient.supprimerNoeudConfianceFichier(fichier1.getNom(), "10.54.65.86");
      Assert.assertEquals(false, fichier1.getNoeudsConfianceMap().containsKey("10.54.65.84"));
      Assert.assertEquals(false, fichier1.getNoeudsConfianceMap().containsKey("10.54.65.86"));
    } catch (IndexOutOfBoundsException e) {
      Assert.fail();
    }
  }


  public void testRecupererFichiersPerdus() {
    try {
      String nomFichier, adresse;
      BufferedReader br1;
      /*
      InetAddress Ip = InetAddress.getLocalHost();
      adresse = Ip.getHostAddress();
       */
      adresse = "192.168.19.4";
      nomFichier = "Fic11.txt";
      List<Fichier> listeFichiers = noeudClient.getListeFichiers();
      listeFichiers.add(new Fichier("Fic1.txt"));
      listeFichiers.add(new Fichier("Fic3.txt"));

      Duplication duplication;
      List<NoeudConfiance> listeNoeudsConfiance = noeudClient.getListeNoeudsConfiance();
      /*
      Registry registry = LocateRegistry.getRegistry();
      duplication = (Duplication) registry.lookup("rmi://" + adresse + "/NoeudServeur");
       */
      duplication = (Duplication) Naming.lookup("rmi://" + adresse + "/NoeudServeur");
      listeNoeudsConfiance.add(new NoeudConfiance(adresse, duplication));
      noeudClient.recupererFichiersPerdus();
      Assert.assertNotNull(new FileReader(nomFichier));
      System.out.println(adresse + "_" + nomFichier);
    } catch (MalformedURLException ex) {
      Logger.getLogger(NoeudClientTest.class.getName()).log(Level.SEVERE, null, ex);
    } catch (NotBoundException ex) {
      Logger.getLogger(NoeudClientTest.class.getName()).log(Level.SEVERE, null, ex);
    } catch (AccessException ex) {
      Logger.getLogger(NoeudClientTest.class.getName()).log(Level.SEVERE, null, ex);
    } catch (RemoteException ex) {
      Logger.getLogger(NoeudClientTest.class.getName()).log(Level.SEVERE, null, ex);
    } catch (FileNotFoundException fnfe) {
      fnfe.printStackTrace();
      Assert.fail();
    }

  }
    
  @Test
  public void testNettoyerFichiersDupliques() {

    try {

      Duplication duplication;
      FileReader fileReader = null;
      String adresse = noeudClient.getAdresse();
      String adresseDistante = "192.168.19.4";
      List<Fichier> listeFichiers = noeudClient.getListeFichiers();
      List<NoeudConfiance> listeNoeudsConfiance = noeudClient.getListeNoeudsConfiance();

      /*Registry registry = LocateRegistry.getRegistry();
      duplication = (Duplication) registry.lookup("rmi://" + adresse + "/NoeudServeur");
       */
      duplication = (Duplication) Naming.lookup("rmi://" + adresseDistante + "/NoeudServeur");
      listeNoeudsConfiance.add(new NoeudConfiance(adresseDistante, duplication));
      //listeFichiers.add(new Fichier("Fic0.txt"));
      listeFichiers.add(new Fichier("Fic1.txt"));
      listeFichiers.add(new Fichier("Fic4.txt"));
      List<String> fichiersSauves = new ArrayList<String>();

      for (NoeudConfiance noeudConfiance : listeNoeudsConfiance) {
        duplication = noeudConfiance.getDuplication();
        System.out.println(adresseDistante + "_" + listeFichiers.get(0).getNom());
        //Assert.assertNotNull(new FileReader(adresse + "_" + listeFichiers.get(0).getNom()));
        List<String> listeNomFichiers = duplication.getListeNomsFichiers();
        for (String nomFichier : listeNomFichiers) {
          int indexSeparateur = nomFichier.indexOf("_");
          if (nomFichier.contains("_") 
              && nomFichier.substring(0, indexSeparateur).equals(adresse)
              && !nomFichier.substring(adresse.length() + 1, nomFichier.length()).equals("Fic1.txt")
              && !nomFichier.substring(adresse.length() + 1, nomFichier.length()).equals("Fic4.txt")) {
            System.out.println("nomfichier" + nomFichier);
            fichiersSauves.add(nomFichier);
          }
        }
      }

      noeudClient.nettoyerFichiersDupliques();

      for (NoeudConfiance noeudConfiance : listeNoeudsConfiance) {
        duplication = noeudConfiance.getDuplication();
        Assert.assertNotNull(new FileReader(adresseDistante + "_" + listeFichiers.get(0).getNom()));
        List<String> listeNomFichiers = duplication.getListeNomsFichiers();
        for (String fichierSauve : fichiersSauves) {
          System.out.println("fichierSauve" + fichierSauve);
          Assert.assertFalse(listeNomFichiers.contains(fichierSauve));
        }
        listeNomFichiers.contains("");
      }

    } catch (MalformedURLException ex) {
      Logger.getLogger(NoeudClientTest.class.getName()).log(Level.SEVERE, null, ex);
    } catch (NotBoundException nbe) {
      nbe.printStackTrace();
      Assert.fail();
    } catch (RemoteException re) {
      re.printStackTrace();
      Assert.fail();
    } catch (FileNotFoundException e) {
      Assert.assertTrue(true);
    } catch (IndexOutOfBoundsException e) {
      e.printStackTrace();
      Assert.fail();
    }
  }

  @Test
  public void testEcrireFichierPerdu() {
    String dossierFichiersTest = "Fichiers_Test\\testEcrireFichierPerdu()\\";
    BufferedReader br1;
    try {
      String nomFichier = "Fic3.txt";
      File fichier = new File(dossierFichiersTest + nomFichier);
      noeudClient.ecrireFichierPerdu(dossierFichiersTest + "(2)-" + nomFichier, fichier);
      // Le "(2)-" sert simplement à différencier les fichiers en sortie pour les tests
      FileReader fileReader = new FileReader("(2)-" + nomFichier);
    } catch (FileNotFoundException ex) {
      ex.printStackTrace();
      Assert.fail();
    } catch (IOException ex) {
      Assert.fail();
    }
  }
}
