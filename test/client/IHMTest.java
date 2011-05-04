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
 * @author Marc Boisban
 * @author Kamel Gharout
 */
public class IHMTest {

  private IHM ihm;

  public IHMTest() {
    ihm = new IHM();
  }

  @BeforeClass
  public static void setUpClass() throws Exception {
  }

  @AfterClass
  public static void tearDownClass() throws Exception {
  }

  /**
   * Test of controler method, of class IHM.
   */
  @Test
  public void testControler() {
    Assert.assertEquals(-1, ihm.controler(5, 3));
    Assert.assertEquals(-1, ihm.controler(-5, 8));
    Assert.assertEquals(5, ihm.controler(5, 8));
  }
}
