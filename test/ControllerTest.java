import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

import controller.Controller;
import model.ModelImpl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

/**
 * this is a test for our controller.
 */
public class ControllerTest {

  // commented out tests are due to java style and the way we
  // structured our tests

  /*
  private void testException(String[] args, String expectedMessage) {
    String str = String.join(" ", args);
    InputStream stream = new ByteArrayInputStream(str.getBytes(StandardCharsets.UTF_8));
    try {
      Controller c = new Controller(stream, new ModelImpl());
      c.controllerGo();
      fail();
    } catch (IllegalArgumentException e) {
      assertEquals(expectedMessage, e.getMessage());
    }
  }

  private void testNoException(String[] args) {
    String str = String.join(" ", args);
    InputStream stream = new ByteArrayInputStream(str.getBytes(StandardCharsets.UTF_8));
    try {
      Controller c = new Controller(stream, new ModelImpl());
      c.controllerGo();
    } catch (IllegalArgumentException e) {
      System.out.println(e.getMessage());
      fail();
    }
  }
  */

  // =========================
  // Exception tests
  // =========================

  @Test
  public void testMainSaveBeforeLoading() {

    String[] args = {"save oodisfun.jpg"};
    String str = String.join(",", args);
    InputStream stream = new ByteArrayInputStream(str.getBytes(StandardCharsets.UTF_8));
    try {
      Controller c = new Controller(stream, new ModelImpl());
      c.controllerGo();
      fail();
    } catch (IllegalArgumentException e) {
      assertEquals("Can't save because there is no image yet.", e.getMessage());
    }
  }

  /**
   @Test public void testMainLoadFileThatDoesNotExist() {
   String[] args = {"load ilikecandy.jpg"};
   testException(args, "Invalid filename: ilikecandy.jpg");
   }

   @Test public void testUnrecognizedInput() {
   String[] args = {"kittycat"};
   testException(args, "Improper command: kittycat");
   }

   @Test public void testSaveNotEnoughArguments() {
   String[] args = {"generate france 100\nsave"};
   testException(args, "Please provide filepath to save the image.");
   }

   @Test public void testLoadingWithoutProvidingFilepath() {
   String[] args = {"load"};
   testException(args, "Please enter a file path to load an image from.");
   }

   @Test public void testGenerateNoArguments() {
   String[] args = {"generate"};
   testException(args, "Please provide what you want to generate.");
   }

   @Test public void testGenerateTooManyArguments() {
   String[] args = {"generate france 100 100 also a banana"};
   testException(args, "Too many arguments.");
   }

   @Test public void testGenerateNotNumberDimensions() {
   String[] args = {"generate france banana"};
   testException(args, "Dimensions were not a number");
   }

   @Test public void testGenerateNoDimensions() {
   String[] args = {"generate france"};
   testException(args, "Not enough arguments");
   }

   @Test public void testGenerateNonExistingImage() {
   String[] args = {"generate banana 100"};
   testException(args, "The passed argument is not executable");
   }

   @Test public void testApplyMosaicSeedsNotInts() {
   String[] args = {"generate france 260\napply mosaic baby"};
   testException(args, "Seeds were not entered as a number");
   }

   @Test public void testApplyWithoutArguments() {
   String[] args = {"generate greece 100\napply"};
   testException(args, "Not enough arguments, please provide a filter to apply");
   }

   // ==========================
   // Tests where it works.
   // ==========================

   @Test public void testSaveManyArguments() {
   String[] args = {"generate france 100\nsave res/bonjour.jpg bonjour.png yomoma.jpg"};
   testNoException(args);
   }

   @Test public void testMainLoadImageWithProperName() {
   String[] args = {"load res/peach.jpg"};
   testNoException(args);
   }

   @Test public void testBlurringAPictureWithArguments() {
   String[] args = {"load res/peach.jpg", "blur", "blur", "blur",
   "blur", "blur", "save res/peachblurredwithargs.jpg"};
   testNoException(args);
   }

   @Test public void testGenerateFlagFranceToGray() {
   String[] args = {"generate france 100\napply grayscale\nsave res/grayfrance.jpg"};
   testNoException(args);
   }

   @Test public void testGenerateFlagSwitzerland() {
   String[] args = {"generate switzerland 100\napply sharpen\nsave res/sharpswizz.jpg"};
   testNoException(args);
   }
   */

}