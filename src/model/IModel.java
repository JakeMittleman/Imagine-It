package model;

import java.awt.image.BufferedImage;

/**
 * This is the interface for the model in a standard MVC design pattern. It offers every operation
 * allowed by this program. It mutates images that have been loaded in addition to generating images
 * that have not previously existed.
 */
public interface IModel {

  /**
   * This method checks whether an image has been loaded into the model or not. Returns true if
   * there is an image, false otherwise.
   *
   * @return true if image is loaded, otherwise false.
   */
  boolean isLoaded();

  /**
   * Applies a dither filter to the current image.
   */
  void dither();

  /**
   * Applies a mosaic filter to the current image.
   *
   * @param i the number of seeds desired in the mosaic. The higher the number, the closer to the
   *          original image.
   */
  void mosaic(int i);

  /**
   * Applies a sharpen filter to the current image.
   */
  void sharpen();

  /**
   * Applies a blur filter to the current image.
   */
  void blur();

  /**
   * Applies a sepia filter to the current image.
   */
  void sepia();

  /**
   * Converts the current image to grayscale.
   */
  void grayscale();

  /**
   * Saves the current image to a file specified by the filepath parameter. If the filepath is
   * invalid, an exception will be thrown. A valid filepath includes the file's name, its extension,
   * and filepath. An example of valid filepaths are as follows:
   *
   * <p><pre>
   *   res/frenchFlag.jpg
   *   res/frenchFlag.png
   *   frenchFlag.png
   *   res/flags/frenchFlags/frenchFlag.jpg
   * </pre></p>
   *
   * <p>
   * The directory must exist in order for the filepath to be valid. Purely a filename
   * (frenchFlag.jpg) will be stored in the current working directory. Should the command receive
   * more than one argument, it will try to execute save on the very first one. If the first one
   * fails, it will throw it's exception and not continue with the next filepath. For example:
   * </p>
   *
   * <p>
   * save res/frenchFlag.jpg -> saves to res/frenchFlag.jpg save res/frenchFlag.jps frenchFlag.png
   * -> saves to res/frenchFlag.jpg
   * </p>
   *
   * @param filepath the filepath to where the file will be saved, provided it is valid.
   * @throws IllegalArgumentException if the given filepath is invalid.
   */
  void save(String filepath) throws IllegalArgumentException;

  /**
   * Loads the given image into the model. If the filepath given is not found (a.k.a, invalid), an
   * exception will be thrown. A valid filepath includes the file's name, its extension, and
   * filepath. An example of valid filepaths are as follows:
   *
   * <p><pre>
   *   res/frenchFlag.jpg
   *   res/frenchFlag.png
   *   frenchFlag.png
   *   res/flags/frenchFlags/frenchFlag.jpg
   * </pre></p>
   *
   * <p>
   * The directory must exist in order for the filepath to be valid. Purely a filename
   * (frenchFlag.jpg) will be stored in the current working directory. Currently this program can
   * only open one image at a time, so any further filepaths or filenames will be ignored and only
   * the first one will be loaded.
   * </p>
   *
   * @param filepath the filepath to where the file will be saved, provided it is valid.
   * @throws IllegalArgumentException if the given filepath is invalid.
   */
  void load(String filepath) throws IllegalArgumentException;

  /**
   * Generates the French flag. This mutates this current image to become the French Flag.
   *
   * @param height the desired height of the flag.
   * @throws IllegalArgumentException if the given height dimension is less than 1.
   */
  void drawFrance(int height) throws IllegalArgumentException;

  /**
   * Generates the Greek flag. This mutates this current image to become the Greek Flag.
   *
   * @param height the desired height of the flag.
   * @throws IllegalArgumentException if the given height dimension is less than 1.
   */
  void drawGreece(int height) throws IllegalArgumentException;

  /**
   * Generates the Swiss flag. This mutates this current image to become the Swiss Flag.
   *
   * @param height the desired height of the flag.
   * @throws IllegalArgumentException if the given height dimension is less than 1.
   */
  void drawSwitzerland(int height) throws IllegalArgumentException;

  /**
   * Generates a checkerboard pattern. This mutates this current image to become a black and white
   * checkerboard.
   *
   * @param squaresize the desired length of each side of a single square.
   * @throws IllegalArgumentException if the given height dimension is less than 1.
   */
  void drawCheckerboard(int squaresize) throws IllegalArgumentException;

  /**
   * Generates a horizontal rainbow pattern. This mutates this current image to become a rainbow.
   *
   * @param width  the desired width of the rainbow image.
   * @param height the desired height of the rainbow image.
   * @throws IllegalArgumentException if the given height dimension is less than 1.
   */
  void drawHorizontalRainbow(int width, int height) throws IllegalArgumentException;

  /**
   * Generates a vertical rainbow pattern. This mutates this current image to become a rainbow.
   *
   * @param width  the desired width of the rainbow image.
   * @param height the desired height of the rainbow image.
   * @throws IllegalArgumentException if the given height dimension is less than 1.
   */
  void drawVerticalRainbow(int width, int height) throws IllegalArgumentException;

  /**
   * Undo's the latest action. This method can not undo saving nor loading.
   */
  void undo();

  /**
   * Re-add the latest action that was undone. If no undone actions have happened, or if a new image
   * was created between the last undo and the latest redo call, nothing happens.
   */
  void redo();

  /**
   * This method checks if the model can redo operations on the image.
   *
   * @return True if it can, false if it can't.
   */
  boolean canRedo();

  /**
   * This method checks if the model can undo operations on the image.
   *
   * @return True if it can, false if it can't.
   */
  boolean canUndo();

  /**
   * This helper method take this models image and creates a BufferedImage copy of it.
   *
   * @return A copy as a BufferedImage.
   */
  BufferedImage toBufferedImage();

}
