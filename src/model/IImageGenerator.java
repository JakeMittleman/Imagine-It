package model;

/**
 * This is the interface for the ImageGenerator. It allows operations that allow for generating
 * images. It handles every operation (except for loading and saving) that doesn't require an image
 * to be loaded. Currently it can generate rainbows, checkerboards, and the french, greek, and swiss
 * flags.
 */
public interface IImageGenerator {

  /**
   * Draws a rainbow where the stripes are vertical. The rainbow will cover the whole size of the
   * model.ImageGenerator object, starting from the left to the right.
   */
  Image drawVerticalRainbow(int width, int height);

  /**
   * Draws a rainbow where the stripes are horizontal. The rainbow will cover the whole size of the
   * model.ImageGenerator object, starting from top to bottom.
   */
  Image drawHorizontalRainbow(int width, int height);

  /**
   * Draws an image of a checkerboard, with alternating black squares and white squares.
   * <p>
   * The image size is determined of the size of the squares. A square size of 10 * 10 will generate
   * an image size of 80 * 80, as the checkerboard will draw exactly 8 squares in each direction.
   * </p>
   *
   * @param squareSize Size of the squares.
   */
  Image drawCheckerBoard(int squareSize);

  /**
   * Draws the swiss flag. The size of the flag is determined by the passed height, and the changed
   * based on the ratio of the flag.
   *
   * <p>
   * Do note that the size of the flag will determine the size of the image generated. Even if the
   * model.ImageGenerator object is 2000 * 2000, a flag of size 100 * 100 will turn the
   * model.ImageGenerator object into an object of size 100 * 100.
   * </p>
   *
   * @param height Height of the flag.
   */
  Image drawSwitzerland(int height);

  /**
   * Draws the greek flag. The size of the flag is determined by the passed height, and the changed
   * based on the ratio of the flag.
   *
   * <p>
   * Do note that the size of the flag will determine the size of the image generated. Even if the
   * model.ImageGenerator object is 2000 * 2000, a flag of size 100 * 100 will turn the
   * model.ImageGenerator object into an object of size 100 * 100.
   * </p>
   *
   * @param height Height of the flag.
   */
  Image drawGreece(int height);

  /**
   * Draws the french flag. The size of the flag is determined by the passed height, and the changed
   * based on the ratio of the flag.
   *
   * <p>
   * Do note that the size of the flag will determine the size of the image generated. Even if the
   * model.ImageGenerator object is 2000 * 2000, a flag of size 100 * 100 will turn the
   * model.ImageGenerator object into an object of size 100 * 100.
   * </p>
   *
   * @param height The height of the image.
   */
  Image drawFrance(int height);

}
