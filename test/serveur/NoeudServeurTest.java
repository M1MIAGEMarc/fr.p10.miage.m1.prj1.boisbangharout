/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package serveur;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.rmi.RemoteException;
import junit.framework.Assert;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * Classe de tests pour la classe NoeudServeur
 * 
 * @author Marc Boisban
 * @author Kamel Gharout
 */
public class NoeudServeurTest {

  
  /****************************   Attributs   *********************************/
  private NoeudServeur noeudServeur;



  /****************************   Constructeur(s)   ***************************/
  public NoeudServeurTest() throws RemoteException{
    noeudServeur = new NoeudServeur();
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
  public void testEcrireFichier() {
    String dossierFichiersTest = "Fichiers_Test\\testEcrireFichier()\\";
    File fichier;
    
    try {
      fichier = new File(dossierFichiersTest + "Fic1.txt");
      noeudServeur.ecrireFichier(dossierFichiersTest + "10.50.56.10", fichier, "Fic1.txt");
      Assert.assertNotNull(new FileReader(dossierFichiersTest + "10.50.56.10_Fic1.txt"));
    } catch (FileNotFoundException fnfe) {
            fnfe.printStackTrace();
      Assert.fail();
    }
  }

  @Test
  public void testSupprimerFichier() {
    String dossierFichiersTest = "Fichiers_Test\\testSupprimerFichier()\\";
    try {
      FileWriter fw = new FileWriter(dossierFichiersTest + "10.50.56.10_Fic1.dat");
      fw.write("Test");
      fw.close();
      Thread.sleep(2000); // Le temps de vérifier que les fichiers ont été créés et qu'ils disparaitront
      noeudServeur.supprimerFichier(dossierFichiersTest + "10.50.56.10_Fic1.dat");
      FileReader fr2 = new FileReader(dossierFichiersTest + "10.50.56.10_Fic1.dat");
    } catch (FileNotFoundException fnfe) {
      Assert.assertTrue(true);
    } catch(Exception e) {
      
    }
  }

  @Test
  public void testExtraireDonneesFichier() {
    String dossierFichiersTest = "Fichiers_Test\\testExtraireDonneesFichier()\\";
    try {
      File fichier = noeudServeur.extraireDonnees(dossierFichiersTest + "10.50.56.11_Fic1.dat");
      boolean fichierNonVide;

      if (!fichier.exists())
        Assert.fail();

      // Test avec un fichier vide en entrée, l'extraction ne doit donc pas renvoyer de données
      fichier = noeudServeur.extraireDonnees(dossierFichiersTest + "10.50.56.11_Fic2.dat");
      BufferedReader br = new BufferedReader(new FileReader(fichier));
      if (br.read() > 0)
        Assert.fail();
        
    } catch (FileNotFoundException ex) {
      Assert.fail();
    } catch (IOException ioe) {
      Assert.fail();
    }
  }
}