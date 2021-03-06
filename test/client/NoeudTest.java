/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package client;

import java.net.UnknownHostException;
import java.util.ArrayList;
import java.io.FileWriter;
import java.io.IOException;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.rmi.Naming;
import java.util.logging.Level;
import java.util.logging.Logger;
import serveur.Duplication;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.MalformedURLException;
import java.rmi.ConnectException;
import java.rmi.NotBoundException;
import junit.framework.Assert;
import java.io.File;
import java.io.FileInputStream;
import java.rmi.AccessException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.List;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author maboisba
 */
public class NoeudTest {

  /****************************   Attributs   *********************************/
  private Noeud noeud;

  public NoeudTest() {
    try {
      noeud = new Noeud();
    }
    catch (RemoteException rme) {
      rme.printStackTrace();
    }
  }

  @BeforeClass
  public static void setUpClass() throws Exception {
  }

  @AfterClass
  public static void tearDownClass() throws Exception {
  }

  /**
   * Test of assignerConfidentialite method, of class Noeud.
   */
  @Test
  public void testAssignerConfidentialite() {

    List<Fichier> listeFichiers = noeud.getListeFichiers();
    listeFichiers.add(new Fichier("Fic0.txt"));
    listeFichiers.add(new Fichier("Fic1.txt"));
    listeFichiers.add(new Fichier("Fic2.txt"));
    listeFichiers.add(new Fichier("Fic3.txt"));

    if (listeFichiers.size() > 0) {
      int confidentialiteAttendue;

      noeud.assignerConfidentialite(listeFichiers.get(0).getNom(), 1);
      confidentialiteAttendue = 1;
      Assert.assertEquals(confidentialiteAttendue, listeFichiers.get(0).getNiveauConfidentialite());

      noeud.assignerConfidentialite(listeFichiers.get(1).getNom(), 2);
      confidentialiteAttendue = 2;
      Assert.assertEquals(confidentialiteAttendue, listeFichiers.get(1).getNiveauConfidentialite());

      noeud.assignerConfidentialite(listeFichiers.get(2).getNom(), 3);
      confidentialiteAttendue = 3;
      Assert.assertEquals(confidentialiteAttendue, listeFichiers.get(2).getNiveauConfidentialite());

      noeud.assignerConfidentialite(listeFichiers.get(3).getNom(), 4);
      confidentialiteAttendue = 4;
      Assert.assertEquals(confidentialiteAttendue, listeFichiers.get(3).getNiveauConfidentialite());
    }
    else {
      Assert.fail();
    }
  }

  /**
   * Test of ajouterNoeudConfiance method, of class Noeud.
   */
  @Test
  public void testAjouterNoeudConfiance() throws Exception {
    String adresse;
    List<NoeudConfiance> listeNoeudsConfiance = noeud.getListeNoeudsConfiance();
    try {
      InetAddress adress = Inet4Address.getLocalHost();
      adresse = adress.getHostAddress();
      //adresse = "172.19.1.37";
      noeud.ajouterNoeudConfiance(adresse);
      Assert.assertEquals(adresse, listeNoeudsConfiance.get(0).getAdresse());
    }
    catch (MalformedURLException mue) {
      mue.printStackTrace();
    }
    catch (ConnectException ce) {
      ce.printStackTrace();
    }
    catch (RemoteException re) {
      re.printStackTrace();
    }
    catch (IndexOutOfBoundsException ibe) {
      ibe.printStackTrace();
      Assert.fail();
    }
    catch (NotBoundException e) {
      System.out.println(e.getMessage());
      Assert.fail();
    }
  }

  /**
   * Test of assignerConfiance method, of class Noeud.
   */
  @Test
  public void testAssignerConfiance() {
    int confianceAttendue;
    List<NoeudConfiance> listeNoeudsConfiance = noeud.getListeNoeudsConfiance();

    NoeudConfiance noeudConfiance1 = new NoeudConfiance("10.54.65.85");
    listeNoeudsConfiance.add(noeudConfiance1);
    noeud.assignerConfiance("10.54.65.85", 1);

    NoeudConfiance noeudConfiance2 = new NoeudConfiance("10.54.65.86");
    listeNoeudsConfiance.add(noeudConfiance2);
    noeud.assignerConfiance("10.54.65.86", 2);

    NoeudConfiance noeudConfiance3 = new NoeudConfiance("10.54.65.87");
    listeNoeudsConfiance.add(noeudConfiance3);
    noeud.assignerConfiance("10.54.65.87", 3);

    confianceAttendue = 1;
    Assert.assertEquals(confianceAttendue, listeNoeudsConfiance.get(0).getNiveauConfiance());
    confianceAttendue = 2;
    Assert.assertEquals(confianceAttendue, listeNoeudsConfiance.get(1).getNiveauConfiance());
    confianceAttendue = 3;
    Assert.assertEquals(confianceAttendue, listeNoeudsConfiance.get(2).getNiveauConfiance());
  }

  /**
   * Test of dupliquerFichiers method, of class Noeud.
   */
  @Test
  public void testDupliquerFichiers() {

    try {
      FileReader fileReader = null;
      List<Fichier> listeFichiers = noeud.getListeFichiers();
      Fichier fichier = new Fichier("Fic1.txt");
      fichier.setNiveauConfidentialite(1);
      listeFichiers.add(fichier);
      String adresse = noeud.getAdresse();
      //String adresse = "192.168.19.4";

      try {
        fileReader = new FileReader(listeFichiers.get(0).getNom());
        BufferedReader br = new BufferedReader(fileReader);

        //Duplication duplication = (Duplication) Naming.lookup("rmi://" + adresse + "/Noeud");
        Registry registry = LocateRegistry.getRegistry(adresse);
        Duplication duplication = (Duplication) registry.lookup("Noeud");
        NoeudConfiance noeud2 = new NoeudConfiance(adresse, duplication);
        listeFichiers.get(0).setNiveauConfidentialite(1);
        noeud2.setNiveauConfiance(1);
        noeud.getListeNoeudsConfiance().add(noeud2);
        noeud.dupliquerFichiers();

        for (NoeudConfiance noeudConfiance : noeud.getListeNoeudsConfiance()) {
          Assert.assertNotNull(new FileReader(adresse + "_" + listeFichiers.get(0).getNom()));
          List<String> listeNomFichiers = duplication.getListeNomsFichiers();

          if (noeudConfiance.getNiveauConfiance() >= listeFichiers.get(0).getNiveauConfidentialite()) {
            Assert.assertTrue(listeNomFichiers.contains(adresse + "_" + listeFichiers.get(0).getNom()));
          }
        }
      }
      catch (FileNotFoundException fnfe) {
        fnfe.printStackTrace();
        Assert.fail();
      }
    }
    catch (NotBoundException nbe) {
      nbe.printStackTrace();
      Assert.fail();
    }
    catch (RemoteException re) {
      re.printStackTrace();
      Assert.fail();
    }
    catch (IndexOutOfBoundsException e) {
      e.printStackTrace();
      Assert.fail();
    }
  }

  /**
   * Test of supprimerNoeudConfiance method, of class Noeud.
   */
  @Test
  public void testSupprimerNoeudConfiance() {
    String adresse;
    List<NoeudConfiance> listeNoeudsConfiance = noeud.getListeNoeudsConfiance();
    listeNoeudsConfiance.add(new NoeudConfiance("10.54.65.82"));
    listeNoeudsConfiance.add(new NoeudConfiance("10.54.65.83"));
    listeNoeudsConfiance.add(new NoeudConfiance("10.54.65.84"));
    listeNoeudsConfiance.add(new NoeudConfiance("10.54.65.85"));
    listeNoeudsConfiance.add(new NoeudConfiance("10.54.65.86"));
    try {
      Assert.assertEquals("10.54.65.82", listeNoeudsConfiance.get(0).getAdresse());
      Assert.assertEquals("10.54.65.86", listeNoeudsConfiance.get(4).getAdresse());
      noeud.supprimerNoeudConfiance("10.54.65.82");
      Assert.assertEquals("10.54.65.83", listeNoeudsConfiance.get(0).getAdresse());
      Assert.assertEquals("10.54.65.84", listeNoeudsConfiance.get(1).getAdresse());
      Assert.assertEquals("10.54.65.85", listeNoeudsConfiance.get(2).getAdresse());
      Assert.assertEquals("10.54.65.86", listeNoeudsConfiance.get(3).getAdresse());
    }
    catch (IndexOutOfBoundsException e) {
      Assert.fail();
    }
  }

  /**
   * Test of ajouterNoeudConfianceFichier method, of class Noeud.
   */
  @Test
  public void testAjouterNoeudConfianceFichier() {
    List<Fichier> listeFichiers = noeud.getListeFichiers();
    listeFichiers.add(new Fichier("FicAjoutNoeudConfFichier1.txt"));
    try {
      Assert.assertEquals(false, listeFichiers.get(0).getNoeudsConfianceMap().containsKey("10.54.65.84"));
      Assert.assertEquals(false, listeFichiers.get(0).getNoeudsConfianceMap().containsKey("10.54.65.86"));

      noeud.ajouterNoeudConfianceFichier(listeFichiers.get(0).getNom(), "10.54.65.84", true);
      Assert.assertEquals(true, listeFichiers.get(0).getNoeudsConfianceMap().containsKey("10.54.65.84"));
      Assert.assertEquals(false, listeFichiers.get(0).getNoeudsConfianceMap().containsKey("10.54.65.86"));

      noeud.ajouterNoeudConfianceFichier(listeFichiers.get(0).getNom(), "10.54.65.86", false);
      Assert.assertEquals(true, listeFichiers.get(0).getNoeudsConfianceMap().containsKey("10.54.65.84"));
      Assert.assertEquals(true, listeFichiers.get(0).getNoeudsConfianceMap().containsKey("10.54.65.86"));
      Assert.assertEquals(true, (boolean) listeFichiers.get(0).getNoeudsConfianceMap().get("10.54.65.84"));
      Assert.assertEquals(false, (boolean) listeFichiers.get(0).getNoeudsConfianceMap().get("10.54.65.86"));
    }
    catch (IndexOutOfBoundsException e) {
      e.printStackTrace();
      Assert.fail();
    }
  }

  /**
   * Test of supprimerNoeudConfianceFichier method, of class Noeud.
   */
  @Test
  public void testSupprimerNoeudConfianceFichier() {

    List<Fichier> listeFichiers = noeud.getListeFichiers();
    Fichier fichier1 = new Fichier("FicSupNoeudConfFichier1.txt");
    fichier1.getNoeudsConfianceMap().put("10.54.65.84", false);
    fichier1.getNoeudsConfianceMap().put("10.54.65.86", true);
    listeFichiers.add(fichier1);

    try {
      Assert.assertEquals(true, fichier1.getNoeudsConfianceMap().containsKey("10.54.65.84"));
      Assert.assertEquals(true, fichier1.getNoeudsConfianceMap().containsKey("10.54.65.86"));

      noeud.supprimerNoeudConfianceFichier(fichier1.getNom(), "10.54.65.84");
      Assert.assertEquals(false, fichier1.getNoeudsConfianceMap().containsKey("10.54.65.84"));
      Assert.assertEquals(true, fichier1.getNoeudsConfianceMap().containsKey("10.54.65.86"));

      noeud.supprimerNoeudConfianceFichier(fichier1.getNom(), "10.54.65.86");
      Assert.assertEquals(false, fichier1.getNoeudsConfianceMap().containsKey("10.54.65.84"));
      Assert.assertEquals(false, fichier1.getNoeudsConfianceMap().containsKey("10.54.65.86"));
    }
    catch (IndexOutOfBoundsException e) {
      Assert.fail();
    }
  }

  /**
   * Test of nettoyerFichiersDupliques method, of class Noeud.
   */
  @Test
  public void testNettoyerFichiersDupliques() {

    try {
      //Duplication duplication;
      FileReader fileReader = null;
      String adresse = noeud.getAdresse();
      //String adresseDistante = "192.168.19.4";
      List<Fichier> listeFichiers = noeud.getListeFichiers();
      List<NoeudConfiance> listeNoeudsConfiance = noeud.getListeNoeudsConfiance();

      //duplication = (Duplication) Naming.lookup("rmi://" + adresse + "/Noeud");
      Registry registry = LocateRegistry.getRegistry(adresse);
      Duplication duplication = (Duplication) registry.lookup("Noeud");
      listeNoeudsConfiance.add(new NoeudConfiance(adresse, duplication));
      //listeFichiers.add(new Fichier("Fic0.txt"));
      //listeFichiers.add(new Fichier("Fic1.txt"));
      //listeFichiers.add(new Fichier("Fic4.txt"));
      List<String> fichiersSauves = new ArrayList<String>();
      List<String> listeNomFichiersClients = new ArrayList<String>();
      for (Fichier fichier : listeFichiers) {
        listeNomFichiersClients.add(fichier.getNom());
      }
      for (NoeudConfiance noeudConfiance : listeNoeudsConfiance) {
        duplication = noeudConfiance.getDuplication();
        System.out.println(adresse + "_" + listeFichiers.get(0).getNom());
        //Assert.assertNotNull(new FileReader(adresse + "_" + listeFichiers.get(0).getNom()));
        List<String> listeNomFichiers = duplication.getListeNomsFichiers();
        for (String nomFichier : listeNomFichiers) {
          int indexSeparateur = nomFichier.indexOf("_");
          if (nomFichier.contains("_")
                  && nomFichier.substring(0, indexSeparateur).equals(adresse)
                  && !listeNomFichiersClients.contains(nomFichier.substring(adresse.length() + 1, nomFichier.length()))) {
            //System.out.println("nomfichier" + nomFichier);
            fichiersSauves.add(nomFichier);
          }
        }
      }

      noeud.nettoyerFichiersDupliques();

      for (NoeudConfiance noeudConfiance : listeNoeudsConfiance) {
        duplication = noeudConfiance.getDuplication();
        //System.out.println(adresse + "_" + listeFichiers.get(0).getNom());
        Assert.assertNotNull(new FileReader(adresse + "_" + listeFichiers.get(0).getNom()));
        List<String> listeNomFichiers = duplication.getListeNomsFichiers();
        for (String fichierSauve : fichiersSauves) {
          Assert.assertFalse(listeNomFichiers.contains(fichierSauve));
        }
        listeNomFichiers.contains("");
      }

    }
    catch (NotBoundException nbe) {
      nbe.printStackTrace();
      Assert.fail();
    }
    catch (RemoteException re) {
      re.printStackTrace();
      Assert.fail();
    }
    catch (FileNotFoundException e) {
      Assert.assertTrue(true);
    }
    catch (IndexOutOfBoundsException e) {
      e.printStackTrace();
      Assert.fail();
    }
  }

  /**
   * Test of recupererFichiersPerdus method, of class Noeud.
   */
  @Test
  public void testRecupererFichiersPerdus() {
    try {
      String nomFichier, adresse;
      BufferedReader br1;
      InetAddress Ip = InetAddress.getLocalHost();
      adresse = Ip.getHostAddress();
      //adresse = "192.168.19.4";
      nomFichier = "Fic11.txt";
      List<Fichier> listeFichiers = noeud.getListeFichiers();
      listeFichiers.add(new Fichier("Fic1.txt"));
      listeFichiers.add(new Fichier("Fic3.txt"));

      List<NoeudConfiance> listeNoeudsConfiance = noeud.getListeNoeudsConfiance();

      /*
      Duplication duplication;
      duplication = (Duplication) Naming.lookup("rmi://" + adresse + "/Noeud");*/
      Registry registry = LocateRegistry.getRegistry(adresse);
      Duplication duplication = (Duplication) registry.lookup("Noeud");
      listeNoeudsConfiance.add(new NoeudConfiance(adresse, duplication));
      System.out.println("======= TEST RECUPERERFICHIERSPERDUS========= \n");
      noeud.recupererFichiersPerdus();
      System.out.println("\n========== FIN TEST RECUPERERFICHIERSPERDUS ============ \n");
      Assert.assertNotNull(new FileReader(nomFichier));
      //System.out.println(adresse + "_" + nomFichier);
    }
    catch (UnknownHostException uhe) {
      uhe.printStackTrace();
    }
    catch (NotBoundException nbe) {
      nbe.printStackTrace();
      Assert.fail();
    }
    catch (AccessException ae) {
      ae.printStackTrace();
      Assert.fail();
    }
    catch (RemoteException re) {
      re.printStackTrace();
      Assert.fail();
    }
    catch (FileNotFoundException fnfe) {
      fnfe.printStackTrace();
      Assert.fail();
    }
  }

  /**
   * Test of ecrireFichierPerdu method, of class Noeud.
   */
  @Test
  public void testEcrireFichierPerdu() {
    String dossierFichiersTest = "Fichiers_Test\\testEcrireFichierPerdu()\\";
    BufferedReader br1;
    try {
      String nomFichier = "Fic3.txt";
      //File fichier = new File(dossierFichiersTest + nomFichier);
      FileInputStream fis = new FileInputStream(dossierFichiersTest + nomFichier);
      byte[] donnees = new byte[500000];
      fis.read(donnees);
      noeud.ecrireFichierPerdu(dossierFichiersTest + "(2)-" + nomFichier, donnees);
      // Le "(2)-" sert simplement à différencier les fichiers en sortie pour les tests
      FileReader fileReader = new FileReader("(2)-" + nomFichier);
    }
    catch (FileNotFoundException ex) {
      ex.printStackTrace();
      Assert.fail();
    }
    catch (IOException ex) {
      Assert.fail();
    }
  }

  /**
   * Test of ecrireFichier method, of class Noeud.
   */
  @Test
  public void testEcrireFichier() {
    String dossierFichiersTest = "Fichiers_Test\\testEcrireFichier()\\";
    FileInputStream fichier;

    try {
      fichier = new FileInputStream(dossierFichiersTest + "Fic1.txt");

      byte[] donnees = new byte[100];
      //fis = new FileInputStream(file);
      int nbOctetsLus = 10;
      nbOctetsLus = fichier.read(donnees);

      noeud.ecrireFichier(dossierFichiersTest + "10.50.56.10", donnees, "Fic1.txt", 0, nbOctetsLus, true);
      Assert.assertNotNull(new FileReader(dossierFichiersTest + "10.50.56.10_Fic1.txt"));
    }
    catch (FileNotFoundException fnfe) {
      fnfe.printStackTrace();
      Assert.fail();
    }
    catch (IOException ex) {
      Logger.getLogger(NoeudTest.class.getName()).log(Level.SEVERE, null, ex);
    }
  }

  /**
   * Test of supprimerFichier method, of class Noeud.
   */
  @Test
  public void testSupprimerFichier() {
    String dossierFichiersTest = "Fichiers_Test\\testSupprimerFichier()\\";
    try {
      FileWriter fw = new FileWriter(dossierFichiersTest + "10.50.56.10_Fic1.dat");
      fw.write("Test");
      fw.close();
      Thread.sleep(2000); // Le temps de vérifier que les fichiers ont été créés et qu'ils disparaitront
      noeud.supprimerFichier(dossierFichiersTest + "10.50.56.10_Fic1.dat");
      FileReader fr2 = new FileReader(dossierFichiersTest + "10.50.56.10_Fic1.dat");
    }
    catch (FileNotFoundException fnfe) {
      Assert.assertTrue(true);
    }
    catch (Exception e) {
      e.printStackTrace();
    }
  }

  /**
   * Test of extraireDonnees method, of class Noeud.
   */
  @Test
  public void testExtraireDonnees() {
    String dossierFichiersTest = "Fichiers_Test\\testExtraireDonneesFichier()\\";
      //File fichier = noeud.extraireDonnees(dossierFichiersTest + "10.50.56.11_Fic1.dat");
    byte[] donnees = noeud.extraireDonnees(dossierFichiersTest + "10.50.56.11_Fic1.dat");
    System.out.println("donnees.length = " + donnees.length);

    /*
    for (int i = 0; i < 4; i++) { // Le fichier à tester contient 4 caractères
      Assert.assertFalse(donnees[i] == (byte)0);
    }*/

    
    // Test avec un fichier vide en entrée, l'extraction ne doit donc pas renvoyer de données
    byte[] donnees2 = noeud.extraireDonnees(dossierFichiersTest + "10.50.56.11_Fic2.dat");
    //BufferedReader br = new BufferedReader(new FileReader(fichier));
    System.out.println("donnees2.length" + donnees2.length);

    for (int i = 0; i < donnees2.length; i++) {
      Assert.assertFalse(donnees2[i] != (byte)0);
    }
    
  }

}
