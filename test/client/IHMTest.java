/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package client;

import junit.framework.Assert;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author maboisba
 */
public class IHMTest {

  /****************************   Attributs   *********************************/
  private IHM iHM;

  public IHMTest() {
    iHM = new IHM();
  }

  @BeforeClass
  public static void setUpClass() throws Exception {
  }

  @AfterClass
  public static void tearDownClass() throws Exception {
  }

  /**
   * Test of saisieControle method, of class IHM.
   */
  @Test
  public void testSaisieControle() {
    Assert.assertEquals(this, iHM.saisieControle(2));

  }
}
