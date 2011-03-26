/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package serveur;

import client.Fichier;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
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
  public void testEcrireFichier() {
    BufferedReader donnees;

    try {
      donnees = new BufferedReader(new FileReader("FichierTest.txt"));
      noeudServeur.ecrireFichier("10.50.56.11", donnees, "FichierTest.dat");
      Assert.assertNotNull(new FileReader("10.50.56.11_FichierTest.dat"));
    } catch (FileNotFoundException fnfe) {
      Assert.fail();
    }
  }

  //@Test
  public void testSupprimerFichier() {
    try {
      noeudServeur.supprimerFichier("10.50.56.11_FichierTest.dat");
      FileReader fileReader = new FileReader("10.50.56.11_FichierTest.dat");
    } catch (FileNotFoundException fnfe) {
      //Assert.assertTrue(true); A décommenter lors du codage de la méthode supprimerFichier()
      Assert.fail(); // A supprimer lors du codage de la méthode supprimerFichier()
    }
  }

  @Test
  public void testExtraireDonneesFichier() {
    try {
      BufferedReader br = noeudServeur.extraireDonnees("10.50.56.11_FichierTest.dat");
      boolean fichierNonVide;
      if (br.read() > 0)
        fichierNonVide = true;
      else
        fichierNonVide = false;
      Assert.assertTrue(fichierNonVide);
    } catch (FileNotFoundException ex) {
      Assert.fail();
    } catch (IOException ioe) {
      Assert.fail();
    }
  }
}