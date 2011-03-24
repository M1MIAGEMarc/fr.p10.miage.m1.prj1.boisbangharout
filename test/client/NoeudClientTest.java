package client;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

import client.Fichier;
import client.NoeudClient;
import client.NoeudConfiance;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import junit.framework.Assert;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

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
    if (listeFichiers.size() > 0) {
      int confidentialiteAttendue;

      noeudClient.assignerConfidentialite(listeFichiers.get(0).getNom(), 1);
      confidentialiteAttendue = 1;
      Assert.assertEquals(confidentialiteAttendue, listeFichiers.get(0).getNiveauConfidentialite());

      noeudClient.assignerConfidentialite(listeFichiers.get(0).getNom(), 2);
      confidentialiteAttendue = 2;
      Assert.assertEquals(confidentialiteAttendue, listeFichiers.get(0).getNiveauConfidentialite());

      noeudClient.assignerConfidentialite(listeFichiers.get(0).getNom(), 3);
      confidentialiteAttendue = 3;
      Assert.assertEquals(confidentialiteAttendue, listeFichiers.get(0).getNiveauConfidentialite());

      noeudClient.assignerConfidentialite(listeFichiers.get(0).getNom(), 4);
      confidentialiteAttendue = 4;
      Assert.assertEquals(confidentialiteAttendue, listeFichiers.get(0).getNiveauConfidentialite());
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
      adresse = "10.54.65.82";
      noeudClient.ajouterNoeudConfiance(adresse);
      Assert.assertEquals(adresse, listeNoeudsConfiance.get(0).getAdresse());

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
    }
    catch (ArrayIndexOutOfBoundsException e) {
      Assert.assertNotNull(e);
    }
  }

  @Test
  public void testAssignerConfiance() {
    int confianceAttendue;
    List<NoeudConfiance> listeNoeudsConfiance = noeudClient.getListeNoeudsConfiance();

    try {
      NoeudConfiance noeudConfiance1 = new NoeudConfiance("10.54.65.85");
      listeNoeudsConfiance.add(noeudConfiance1);
      noeudClient.assignerConfiance("10.54.65.85", 1);

      NoeudConfiance noeudConfiance2 = new NoeudConfiance("10.54.65.85");
      listeNoeudsConfiance.add(noeudConfiance2);
      noeudClient.assignerConfiance("10.54.65.85", 2);

      NoeudConfiance noeudConfiance3 = new NoeudConfiance("10.54.65.85");
      listeNoeudsConfiance.add(noeudConfiance3);
      noeudClient.assignerConfiance("10.54.65.85", 3);

      confianceAttendue = 1;
      Assert.assertEquals(confianceAttendue, listeNoeudsConfiance.get(0).getNiveauConfiance());
      confianceAttendue = 2;
      Assert.assertEquals(confianceAttendue, listeNoeudsConfiance.get(1).getNiveauConfiance());
      confianceAttendue = 3;
      Assert.assertEquals(confianceAttendue, listeNoeudsConfiance.get(2).getNiveauConfiance());
    }
    catch (ArrayIndexOutOfBoundsException e) {
      Assert.assertNotNull(e);
    }
  }

  public void testDupliquerFichier() {
    /*
    FileReader fis = new FileReader("fic1.txt");
    BufferedReader br = new BufferedReader(fis);
    String adresse = "10.45.66.85";

    noeudClient.dupliquerFichier(br, adresse);
     */
  }

  @Test
  public void testSupprimerNoeudConfiance() {
    String adresse;
    List<NoeudConfiance> listeNoeudsConfiance = noeudClient.getListeNoeudsConfiance();

    try {
      Assert.assertEquals("10.54.65.82", listeNoeudsConfiance.get(0).getAdresse());
      Assert.assertEquals("10.54.65.86", listeNoeudsConfiance.get(4));
      noeudClient.supprimerNoeudConfiance("10.54.65.82");
      Assert.assertEquals("10.54.65.83", listeNoeudsConfiance.get(0));
      Assert.assertEquals("10.54.65.84", listeNoeudsConfiance.get(1));
      Assert.assertEquals("10.54.65.85", listeNoeudsConfiance.get(2));
      Assert.assertEquals("10.54.65.86", listeNoeudsConfiance.get(3));
      Assert.assertEquals(null, listeNoeudsConfiance.get(4));
    }
    catch (ArrayIndexOutOfBoundsException e) {
      Assert.assertNotNull(e);
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
    catch (ArrayIndexOutOfBoundsException e) {
      Assert.assertNotNull(e);
    }
  }

  @Test
  public void testSupprimerNoeudConfianceFichier() {
    List<Fichier> listeFichiers = noeudClient.getListeFichiers();
    try {
      Assert.assertEquals(true, listeFichiers.get(0).getNoeudsConfianceMap().containsKey("10.54.65.84"));
      Assert.assertEquals(true, listeFichiers.get(0).getNoeudsConfianceMap().containsKey("10.54.65.86"));

      noeudClient.supprimerNoeudConfianceFichier(listeFichiers.get(0).getNom(), "10.54.65.84");
      Assert.assertEquals(false, listeFichiers.get(0).getNoeudsConfianceMap().containsKey("10.54.65.84"));
      Assert.assertEquals(true, listeFichiers.get(0).getNoeudsConfianceMap().containsKey("10.54.65.86"));

      noeudClient.supprimerNoeudConfianceFichier(listeFichiers.get(0).getNom(), "10.54.65.86");
      Assert.assertEquals(false, listeFichiers.get(0).getNoeudsConfianceMap().containsKey("10.54.65.84"));
      Assert.assertEquals(false, listeFichiers.get(0).getNoeudsConfianceMap().containsKey("10.54.65.86"));
    }
    catch(ArrayIndexOutOfBoundsException e) {
      Assert.assertNotNull(e);
    }
  }

  public void testNettoyerFichiersDupliques() {
  }

  public void testRecupererFichiersPerdus() {
  }

  @Test
  public void testEcrireFichierPerdu() {
    BufferedReader br1 = null;
    try {
      String nomFichier = "Fic2.txt";
      List<Fichier> listeFichiers = noeudClient.getListeFichiers();
      br1 = new BufferedReader(new FileReader("Fic3.txt"));
      BufferedReader br2 = new BufferedReader(new FileReader("Fic3.txt"));
      //ecrireFichierPerdu(new HashMap<nomFichier, br2>() fichierMap);
    }
    catch (FileNotFoundException ex) {
      Assert.assertNotNull(ex.getMessage());
    }
    finally {
      try {
        br1.close();
      }
      catch (IOException ex) {
        Logger.getLogger(NoeudClientTest.class.getName()).log(Level.SEVERE, null, ex);
      }
    }
  }
}
