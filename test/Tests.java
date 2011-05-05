/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

import client.IHMTest;
import client.NoeudTest;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/**
 * Classe exécutant tous les tests unitaires du projet
 *
 * @author Marc Boisban
 * @author Kamel Gharout
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({NoeudTest.class, IHMTest.class})
public class Tests {

  @BeforeClass
  public static void setUpClass() throws Exception {
  }

  @AfterClass
  public static void tearDownClass() throws Exception {
  }

  @Before
  public void setUp() throws Exception {
  }

  @After
  public void tearDown() throws Exception {
  }

}