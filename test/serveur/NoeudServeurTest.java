/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package serveur;

import client.Fichier;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import junit.framework.Assert;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author maboisba
 */
public class NoeudServeurTest {

  private NoeudServeur noeudServeur;

  public NoeudServeurTest() {
    noeudServeur = new NoeudServeur();
  }

  @BeforeClass
  public static void setUpClass() throws Exception {
  }

  @AfterClass
  public static void tearDownClass() throws Exception {
  }

  @Test
  public void testListerFichiers() {
    List<Fichier> listeFichiers = new ArrayList<Fichier>();
    Fichier fichier = new Fichier();
    fichier.setNom("FicServeur1.txt");
    listeFichiers.add(fichier);
    Assert.assertEquals(listeFichiers, noeudServeur.listerFichiers());
  }

  @Test
  public void testEcrireFichier() {
    BufferedReader donnees;

    try {
      donnees = new BufferedReader(new FileReader("FichierTest.txt"));
      noeudServeur.ecrireFichier("10.50.56.11", donnees, "FichierTest.dat");
      Assert.assertNotNull(new FileReader("10.50.56.11_FichierTest.dat"));
      /*
      noeudServeur.ecrireFichier("10.50.56.11", donnees, "FichierTest.txt");
      Assert.assertEquals("10.50.56.11_FichierTest.txt", noeudServeur.listerFichiers().get(noeudServeur.listerFichiers().size() - 1));
      Assert.assertEquals("10.50.56.11_FichierTest.dat", noeudServeur.listerFichiers().get(noeudServeur.listerFichiers().size() - 2));
       */
    }
    catch (FileNotFoundException fnfe) {
      Assert.fail();
    }
  }

  @Test
  public void testSupprimerFichier() {
    try {
      noeudServeur.supprimerFichier("10.50.56.11_FichierTest.dat");
      FileReader fileReader = new FileReader("10.50.56.11_FichierTest.dat");
    }
    catch (FileNotFoundException ex) {
      Assert.fail();
    }
  }

  @Test
  public void testExtraireDonneesFichier() {
    try {
      noeudServeur.extraireDonnees("10.50.56.11_FichierTest.dat");
      FileReader fileReader = new FileReader("10.50.56.11_FichierTest.dat");
    }
    catch (FileNotFoundException ex) {
      Assert.fail();
    }
  }
}
