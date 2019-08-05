package model;

import java.awt.Color;

/**
 * This class represents the creation of one image. It has methods to generate, add and color
 * different parts of the image it generates. Once created, it can also be saved to a file format
 * like .jpg or .png.
 */
class ImageGenerator implements IImageGenerator {

  /**
   * A 3D int array that represents the image. The outer array is rows, middles is column, and inner
   * is the 3 RGB values.
   */
  private int[][][] data;

  /**
   * An integer denoting the height of the image to be generated.
   */
  private int height;

  /**
   * An integer denoting the width of the image to be generated.
   */
  private int width;

  /**
   * This method creates the model.ImageGenerator object. The model.ImageGenerator can draw flags,
   * checkerboards and other shapes and patterns.
   *
   * @param width  The width of the object.
   * @param height The height of the object.
   */
  public ImageGenerator(int width, int height) {

    data = new int[height][width][3];
    this.height = height;
    this.width = width;

    for (int i = 0; i < height; i++) {
      for (int j = 0; j < width; j++) {
        this.data[i][j][0] = 0;
        this.data[i][j][1] = 0;
        this.data[i][j][2] = 0;
      }
    }
  }

  @Override
  public Image drawVerticalRainbow(int width, int height) {
    this.width = width;
    this.height = height;
    setCanvasSize(width, height);
    Color[] colors = {Color.red, Color.orange, Color.yellow, Color.green,
                      Color.blue, new Color(75, 0, 130), new Color(148, 0, 211)};
    drawVerticalStripes(colors);
    return this.toImg();
  }

  @Override
  public Image drawHorizontalRainbow(int width, int height) {
    this.width = width;
    this.height = height;
    setCanvasSize(width, height);
    Color[] colors = {Color.red, Color.orange, Color.yellow, Color.green,
                      Color.blue, new Color(75, 0, 130), new Color(148, 0, 211)};
    drawHorizontalStripes(colors);
    return this.toImg();
  }

  @Override
  public Image drawCheckerBoard(int squareSize) {
    this.height = squareSize * 8;
    this.width = squareSize * 8;
    setCanvasSize(width, height);

    Color[] colors = {Color.black, Color.white};

    for (int i = 0; i < 8; i++) {
      for (int j = 0; j < 8; j++) {
        drawRectangle(i * squareSize, (i + 1) * squareSize, j * squareSize,
                (j + 1) * squareSize, colors[(j + i) % 2]);
      }
    }
    return this.toImg();
  }

  @Override
  public Image drawSwitzerland(int height) {
    setCanvasSize(height, height);
    fillCanvas(255, 0, 0);

    drawRectangle(2.0 / 5.0 * height, 3.0 / 5.0 * height,
            1.0 / 5.0 * width, 4.0 / 5.0 * width, Color.white);
    drawRectangle(1.0 / 5.0 * width, 4.0 / 5.0 * width, 2.0 / 5.0 * height,
            3.0 / 5.0 * height, Color.white);
    return this.toImg();
  }

  @Override
  public Image drawGreece(int height) {
    setCanvasSize((height / 2) * 3, height);
    Color[] colors = {new Color(13, 94, 175), Color.white};

    double stripeHeight = height / 9.0;

    for (int i = 0; i < 9; i++) {
      drawRectangle(((1.0 * i) / 9.0) * height, ((1.0 * i + 1) / 9.0 * height),
              0, width, colors[i % 2]);
    }

    // box
    drawRectangle(0, height / 2.0, 0, 10.0 / 27.0 * width, colors[0]);

    // horizontal stripe
    drawRectangle(stripeHeight * 2, stripeHeight * 3, 0,
            10.0 / 27.0 * width, colors[1]);

    // vert stripe
    drawRectangle(0, stripeHeight * 5, 4.0 / 27.0 * width,
            6.0 / 27.0 * width, colors[1]);
    return this.toImg();
  }

  @Override
  public Image drawFrance(int height) {
    setCanvasSize((height / 2) * 3, height);
    this.height = height;
    this.width = (height / 2) * 3;
    Color[] colors = {Color.blue, Color.white, Color.red};
    for (int i = 0; i < 3; i++) {
      drawRectangle(0, height, i / 3.0 * width, (i + 1.0) / 3.0 * width, colors[i % 3]);
    }
    return this.toImg();
  }

  /*
  PRIVATE METHODS
   */

  /**
   * Converts the generated image to an model.Image object.
   *
   * @return an model.Image object.
   */
  private Image toImg() {
    return new Image(data, width, height);
  }

  /**
   * This method is a helper method that is used in the flag generators. It resizes the object to
   * the specified parameters.
   *
   * @param height Height of the new model.ImageGenerator object.
   * @param width  Width of the new model.ImageGenerator object.
   */
  private void setCanvasSize(int width, int height) {
    this.height = height;
    this.width = width;
    this.data = new int[height][width][3];
  }

  /**
   * This method is a helper method that draw vertical stripes. The amount of stripes is determined
   * by the length of the list of colors. Each color is drawn in equally sized stripes, starting
   * from the left to the right of the image.
   *
   * <p>
   * Example: If an array with the color black, white and blue is passed, this method will generate
   * an image that has three equally sized rectangles. The first color would be the left-most
   * rectangle (black), the second would be the middle (white) and the last color would be the
   * right-most (blue).
   * </p>
   *
   * @param colors An array of colors.
   */
  private void drawVerticalStripes(Color[] colors) {
    for (int i = 0; i < colors.length; i++) {

      int size = colors.length;
      double cStart = ((1.0 * i) / size) * width;
      double cEnd = ((1.0 * (i + 1)) / size) * width;

      drawRectangle(0, this.height, cStart, cEnd, colors[i]);
    }
  }

  /**
   * This method is a helper method that draw horizontal stripes. The amount of stripes is
   * determined by the length of the list of colors. Each color is drawn in equally sized stripes,
   * starting from the top to the bottom of the image.
   *
   * <p>
   * Example: If an array with the color black, white and blue is passed, this method will generate
   * an image that has three equally sized rectangles. The first color would be the top rectangle
   * (black), the second would be the middle (white) and the last color would be the bottom (blue).
   * </p>
   *
   * @param colors An array of colors.
   */
  private void drawHorizontalStripes(Color[] colors) {
    for (int i = 0; i < colors.length; i++) {

      int size = colors.length;
      double cStart = ((1.0 * i) / size) * height;
      double cEnd = ((1.0 * (i + 1)) / size) * height;

      drawRectangle(cStart, cEnd, 0, this.width, colors[i]);
    }
  }

  /**
   * This method helps other draw methods by drawing a rectangle.
   *
   * @param rStart Row-start position.
   * @param rEnd   Row-end position.
   * @param cStart Column-start position.
   * @param cEnd   Column-end position.
   * @param color  The color of the rectangle.
   */
  private void drawRectangle(double rStart, double rEnd, double cStart, double cEnd, Color color) {
    for (int i = (int) Math.round(rStart); i < (int) Math.round(rEnd); i++) {
      for (int j = (int) Math.round(cStart); j < (int) Math.round(cEnd); j++) {
        data[i][j][0] = color.getRed();
        data[i][j][1] = color.getGreen();
        data[i][j][2] = color.getBlue();
      }
    }
  }

  /**
   * This helper method fills the entire model.ImageGenerator object with one color.
   *
   * @param r The red value of the color.
   * @param g The green value of the color.
   * @param b The blue value of the color.
   */
  private void fillCanvas(int r, int g, int b) {
    for (int i = 0; i < height; i++) {
      for (int j = 0; j < width; j++) {
        data[i][j][0] = r;
        data[i][j][1] = g;
        data[i][j][2] = b;
      }
    }
  }
}
