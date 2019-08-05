package model;

/**
 * This interface represents an image and everything that is expected of it.
 */

public interface IImage {

  /**
   * Saves the Image object to the given filepath.
   *
   * <p>
   * Note that the filepath needs to be the whole name of the file to save. If a file should not be
   * saves in the current folder, a path has to be entered by using backslashes.
   * </p>
   *
   * <p>
   * Example: A file that should be saves inside the "res" folder with the name "pic.jpg" should be
   * named "res/pic.jpg".
   * </p>
   *
   * @param filepath The filepath to save the file in.
   * @throws IllegalArgumentException If the filepath is not valid and impossible to create.
   */
  void save(String filepath);

  /**
   * Transforms the color of this image to a gray scale.
   */
  void toGrayScale();

  /**
   * Transforms the color of this image to a sepia scale.
   */
  void toSepia();

  /**
   * Applies a blur filter to the image, making it appear blurry.
   */
  void blur();

  /**
   * Applies a sharpen filter to the image, strengthening lines and sharpening edges of the image.
   */
  void sharpen();

  /**
   * Applies a dither filter to the image, turning it into a grayscale image where each pixel is
   * either pure white or pure black.
   */
  void dither();

  /**
   * Applies a mosaic filter to the image, grouping clusters of pixels and making their colors
   * similar so that the image appears to look like stained-glass.
   *
   * @param seedAmnt the amount of clusters desired. The higher the number, the closer to the
   *                 original image.
   */
  void mosaic(int seedAmnt);
}
