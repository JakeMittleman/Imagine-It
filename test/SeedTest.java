import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import model.Seed;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

/**
 * This class tests the seed class.
 */
public class SeedTest {

  private Seed testSeed;

  @Before
  public void setUp() {
    testSeed = new Seed(4, 5);
  }

  @Test
  public void testConstructor() {
    assertEquals(4, testSeed.getRow());
    assertEquals(5, testSeed.getCol());
  }

  @Test
  public void testEquals() {
    Seed seedA = new Seed(3, 4);
    Seed seedB = new Seed(3, 4);
    Seed seedC = new Seed(3, 4);

    assertEquals(seedA, seedA);
    assertEquals(seedA, seedB);
    assertEquals(seedB, seedA);
    assertEquals(seedB, seedC);
    assertEquals(seedA, seedC);
  }

  @Test
  public void testGetAvgs() {
    testSeed.addPixel(3, 1, new int[]{25, 77, 199});
    testSeed.addPixel(1, 1, new int[]{10, 3, 15});
    testSeed.addPixel(0, 0, new int[]{2, 13, 23});
    testSeed.addPixel(4, 7, new int[]{175, 255, 0});

    assertEquals(53.0, testSeed.getAvgRed(), 0.001);
    assertEquals(87.0, testSeed.getAvgGreen(), 0.001);
    assertEquals(59.25, testSeed.getAvgBlue(), 0.001);
  }

  @Test
  public void testGetList() {
    Random rand = new Random();
    List<int[]> expected = new ArrayList<>();
    for (int i = 0; i < 100; i++) {
      int r = rand.nextInt();
      int g = rand.nextInt();
      int b = rand.nextInt();
      testSeed.addPixel(i, i, new int[]{r, g, b});
      expected.add(new int[]{i, i});
    }
    assertArrayEquals(expected.toArray(), testSeed.getList().toArray());
  }

  @Test
  public void testGetListDoesntChangeOriginal() {
    Random rand = new Random();
    List<int[]> expected = new ArrayList<>();
    for (int i = 0; i < 100; i++) {
      int r = rand.nextInt();
      int g = rand.nextInt();
      int b = rand.nextInt();
      testSeed.addPixel(i, i, new int[]{r, g, b});
      expected.add(new int[]{i, i});
    }
    assertArrayEquals(expected.toArray(), testSeed.getList().toArray());
  }
}