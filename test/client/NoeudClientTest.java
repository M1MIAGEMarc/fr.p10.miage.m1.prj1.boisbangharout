package client;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.UnknownHostException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
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
 *
 * @author maboisba
 */
public class NoeudClientTest {

  private NoeudClient noeudClient;

  public NoeudClientTest() {
    noeudClient = new NoeudClient();
  }

  @BeforeClass
  public static void setUpClass() throws Exception {
  }

  @AfterClass
  public static void tearDownClass() throws Exception {
  }

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
    }
    else {
      Assert.fail();

    }
  }

  @Test
  public void testAjouterNoeudConfiance() {
    String adresse;
    List<NoeudConfiance> listeNoeudsConfiance = noeudClient.getListeNoeudsConfiance();
    try {
      InetAddress adress = Inet4Address.getLocalHost();
      adresse = adress.getHostAddress();
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
    }
    catch (IndexOutOfBoundsException e) {
      e.printStackTrace();
      Assert.fail();
    }
    catch (UnknownHostException uhe) {
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
  public void testDupliquerFichier() {
    try {
      FileReader fileReader = null;
      List<Fichier> listeFichiers = noeudClient.getListeFichiers();
      String adresse = noeudClient.getAdresse();

      try {
        fileReader = new FileReader(listeFichiers.get(0).getNom());
        BufferedReader br = new BufferedReader(fileReader);
        noeudClient.dupliquerFichier(br, adresse);

        for (NoeudConfiance noeudConfiance : noeudClient.getListeNoeudsConfiance()) {
          Duplication duplication = (Duplication) Naming.lookup("rmi://" + noeudConfiance.getAdresse() + "/duplication");
          Assert.assertNotNull(new FileReader(adresse + "_" + listeFichiers.get(0).getNom()));
          List<String> listeNomFichiers = duplication.getListeNomsFichiers();

          if (noeudConfiance.getNiveauConfiance() >= listeFichiers.get(1).getNiveauConfidentialite()) {
            Assert.assertTrue(listeNomFichiers.contains(adresse + "_" + listeFichiers.get(1).getNom()));
          }

          if (noeudConfiance.getNiveauConfiance() >= listeFichiers.get(2).getNiveauConfidentialite()) {
            Assert.assertTrue(listeNomFichiers.contains(adresse + "_" + listeFichiers.get(2).getNom()));
          }

        }
      }
      catch (FileNotFoundException ex) {
        Assert.fail();
      }
    }
    catch (NotBoundException ex) {
      Assert.fail();
    }
    catch (MalformedURLException ex) {
      Assert.fail();
    }
    catch (RemoteException ex) {
      Assert.fail();
    }
    catch (IndexOutOfBoundsException e) {
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
    }
    catch (IndexOutOfBoundsException e) {
      Assert.fail();
    }
  }

  @Test
  public void testAjouterNoeudConfianceFichier() {
    List<Fichier> listeFichiers = noeudClient.getListeFichiers();
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
    }
    catch (IndexOutOfBoundsException e) {
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
    }
    catch (IndexOutOfBoundsException e) {
      Assert.fail();
    }
  }

  @Test
  public void testNettoyerFichiersDupliques() {

    try {

      Duplication duplication;
      FileReader fileReader = null;
      String adresse = noeudClient.getAdresse();
      List<Fichier> listeFichiers = noeudClient.getListeFichiers();
      List<NoeudConfiance> listeNoeudsConfiance = noeudClient.getListeNoeudsConfiance();

      Registry registry = LocateRegistry.getRegistry();
      duplication = (Duplication) registry.lookup("rmi://" + adresse + "/NoeudServeur");
      listeNoeudsConfiance.add(new NoeudConfiance(adresse, duplication));
      listeFichiers.add(new Fichier("Fic0.txt"));
      listeFichiers.add(new Fichier("Fic1.txt"));
      //List<String> fichiersSauves = new ArrayList<String>();

      /*
      for (NoeudConfiance noeudConfiance : listeNoeudsConfiance) {
      Registry registry = LocateRegistry.getRegistry();
      duplication = (Duplication) registry.lookup("rmi://" + noeudConfiance.getAdresse() + "/NoeudServeur");
      System.out.println(adresse + "_" + listeFichiers.get(0).getNom());
      //Assert.assertNotNull(new FileReader(adresse + "_" + listeFichiers.get(0).getNom()));
      List<String> listeNomFichiers = duplication.getListeNomsFichiers();
      for (String nomFichier : listeNomFichiers) {
      int indexSeparateur = nomFichier.indexOf("_");
      if(nomFichier.substring(0, indexSeparateur).equals(adresse)){
      fichiersSauves.add(nomFichier);
      }
      }
      }*/

      noeudClient.nettoyerFichiersDupliques();

      for (NoeudConfiance noeudConfiance : listeNoeudsConfiance) {
        duplication = noeudConfiance.getDuplication();
        Assert.assertNotNull(new FileReader(adresse + "_" + listeFichiers.get(0).getNom()));
        List<String> listeNomFichiers = duplication.getListeNomsFichiers();
        for (String nomFichier : listeNomFichiers) {
          if (nomFichier.contains("_")) {
            int indexSeparateur = nomFichier.indexOf("_");
            System.out.println(nomFichier.substring(0, indexSeparateur));
            if (nomFichier.substring(0, indexSeparateur).equals(adresse)) {
              Assert.fail();
            }
          }
        }
      }

    }
    catch (NotBoundException ex) {
      Logger.getLogger(NoeudClientTest.class.getName()).log(Level.SEVERE, null, ex);
    }
    catch (RemoteException ex) {
      Logger.getLogger(NoeudClientTest.class.getName()).log(Level.SEVERE, null, ex);
    }
    catch (FileNotFoundException e) {
      Assert.assertTrue(true);
    }
    catch (IndexOutOfBoundsException e) {
      e.printStackTrace();
      Assert.fail();
    }
  }

  @Test
  public void testRecupererFichiersPerdus() {
    try {
      String nomFichier, adresse;
      BufferedReader br1;
      InetAddress Ip = InetAddress.getLocalHost();
      adresse = Ip.getHostAddress();
      nomFichier = "fic1.txt";
      noeudClient.recupererFichiersPerdus();
      Assert.assertNotNull(new FileReader(adresse + "_" + nomFichier));
    }
    catch (UnknownHostException ex) {
      Assert.fail();
    }
    catch (FileNotFoundException fnfe) {
      Assert.fail();
    }

  }

  @Test
  public void testEcrireFichierPerdu() {
    BufferedReader br1;
    try {
      String nomFichier = "Fic3.txt";
      br1 = new BufferedReader(new FileReader(nomFichier));
      noeudClient.ecrireFichierPerdu("(2)-" + nomFichier, br1);
      // Le "(2)-" sert simplement à différencier les fichiers en sortie pour les tests
      FileReader fileReader = new FileReader("(2)-" + nomFichier);
      br1.close();
    }
    catch (FileNotFoundException ex) {
      ex.printStackTrace();
      Assert.fail();
    }
    catch (IOException ex) {
      Assert.fail();
    }
  }
}
