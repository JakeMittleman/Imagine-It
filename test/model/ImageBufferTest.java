package model;

import org.junit.Before;
import org.junit.Test;

import java.util.NoSuchElementException;

import static junit.framework.TestCase.fail;
import static org.junit.Assert.assertEquals;

/**
 * A test class for our undo/redo class.
 */
public class ImageBufferTest {

  private Image img;
  private Image img2;
  private Image img3;
  ImageGenerator imgGen;

  @Before
  public void setUp() {
    imgGen = new ImageGenerator(0, 0);
    int[][][] imgArr1 = new int[0][0][0];
    img = new Image(imgArr1, 0, 0);
    img2 = new Image(imgArr1, 1, 1);
    img3 = new Image(imgArr1, 2, 2);
  }

  @Test
  public void testAdd() {
    ImageBuffer imb = new ImageBuffer();
    imb.add(img);
    imb.add(img2);
    imb.add(img3);
    assertEquals(img3, imb.getImage());
  }

  @Test
  public void testAddGoingAbove10() {
    ImageBuffer imb = new ImageBuffer();
    imb.add(img2); // 1
    for (int i = 0; i < 9; i++) {
      imb.add(img);
    }
    imb.add(img3);
    assertEquals(img3, imb.getImage());
  }

  @Test
  public void testGetImgWhenNoneIsThere() {
    ImageBuffer imb = new ImageBuffer();
    try {
      imb.getImage();
      fail();
    } catch (NoSuchElementException e) {
      assertEquals("No image was found", e.getMessage());
    }
  }

  @Test
  public void testAdd20Undo10redo10() {
    ImageBuffer imb = new ImageBuffer();

    for (int i = 0; i < 20; i++) {
      imb.add(img);
    }

    imb.add(img2);

    for (int j = 0; j < 10; j++) {
      imb.undo();
    }

    for (int k = 0; k < 10; k++) {
      imb.redo();
    }
    assertEquals(img2, imb.getImage());
  }

  @Test
  public void testRedoWithEmptyStack() {
    ImageBuffer imb = new ImageBuffer();
    try {
      imb.redo();
      // We kind of just pass here.
    } catch (Exception e) {
      fail();
    }
  }

  @Test
  public void testFunctioningRedo() {
    ImageBuffer imb = new ImageBuffer();
    imb.add(img);
    assertEquals(img, imb.getImage());
    imb.add(img2);
    assertEquals(img2, imb.getImage());
    imb.add(img3);
    imb.undo();
    assertEquals(img2, imb.getImage());
    imb.redo();
    assertEquals(img3, imb.getImage());
  }

  @Test
  public void testFunctioningUndo() {
    ImageBuffer imb = new ImageBuffer();
    imb.add(img);
    imb.add(img2);
    imb.undo();
    assertEquals(img, imb.getImage());
  }
}