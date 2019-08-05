package model;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * This class represents any image that can be displayed on the computer. Once an image has been
 * loaded, image filtering or image generation can be created.
 */
class Image implements IImage {

  /**
   * This is an array that holds the rgb values for each pixel in the image.
   */
  private int[][][] data;

  /**
   * The height of the image.
   */
  private int height;

  /**
   * The width of the image.
   */
  private int width;

  /**
   * This constructs an model.Image file by loading an image that already exists. If the file isn't
   * found, an exception will be thrown.
   *
   * @param file the file's name specified as "filename.jpg" or "filename.png", etc.
   * @throws IllegalArgumentException if the given file is not found.
   */
  public Image(String file) throws IllegalArgumentException {
    try {
      this.data = ImageUtil.readImage(file);
      height = ImageUtil.getHeight(file);
      width = ImageUtil.getWidth(file);
    } catch (IOException e) {
      throw new IllegalArgumentException("Invalid filename: " + file);
    }
  }

  /**
   * This constructs an image based on existing image data.
   *
   * @param pixelData The image data.
   * @param width     The width of the image.
   * @param height    The height of the image.
   */
  protected Image(int[][][] pixelData, int width, int height) {
    this.data = pixelData;
    this.height = height;
    this.width = width;
  }

  @Override
  public void save(String filepath) throws IllegalArgumentException {
    try {
      ImageUtil.writeImage(this.data, width, height, filepath);
    } catch (IOException e) {
      throw new IllegalArgumentException("Invalid filepath--did you include the file extension?");
    }
  }

  @Override
  public void toGrayScale() {
    double[][] gray = new double[3][3];
    for (int i = 0; i < 3; i++) {
      gray[i][0] = 0.2126;
      gray[i][1] = 0.7152;
      gray[i][2] = 0.0722;
    }
    applyEffect(gray);
  }

  @Override
  public void toSepia() {
    double[][] sepia = new double[3][3];
    sepia[0][0] = 0.393;
    sepia[0][1] = 0.769;
    sepia[0][2] = 0.189;
    sepia[1][0] = 0.349;
    sepia[1][1] = 0.686;
    sepia[1][2] = 0.168;
    sepia[2][0] = 0.272;
    sepia[2][1] = 0.534;
    sepia[2][2] = 0.131;

    applyEffect(sepia);

  }

  @Override
  public void blur() {
    double[][] kernel = new double[3][3];

    for (int i = 0; i < 3; i++) {
      for (int j = 0; j < 3; j++) {

        // if on the corners
        if ((i == 0 || i == 2) && (j == 0 || j == 2)) {
          kernel[i][j] = 1.0 / 16.0;
        } else if ((i == 1 || j == 1) && i != j) {
          kernel[i][j] = 1.0 / 8.0;
        } else {
          kernel[i][j] = 1.0 / 4.0;
        }
      }
    }

    applyFilter(kernel);
  }

  @Override
  public void sharpen() {

    double[][] kernel = new double[5][5];

    for (int i = 0; i < 5; i++) {
      for (int j = 0; j < 5; j++) {
        if (i == 0 || j == 0 || i == 4 || j == 4) {
          kernel[i][j] = -1.0 / 8.0;
        } else if ((i > 0 && i < 3) && (j > 0 && j < 4)) {
          kernel[i][j] = 1.0 / 4.0;
        }
        kernel[2][2] = 1.0;
      }
    }

    applyFilter(kernel);
  }

  @Override
  public void dither() {
    // because of the most recent lecture, maybe this is a bad idea
    toGrayScale();

    // may get off by 1 error
    for (int i = 0; i < height; i++) {
      for (int j = 0; j < width; j++) {
        int oldColor = data[i][j][0];
        int newColor;
        if (oldColor < 127) {
          newColor = 0;
        } else {
          newColor = 255;
        }
        int error = oldColor - newColor;
        data[i][j][0] = data[i][j][1] = data[i][j][2] = newColor;

        if (j + 1 < width) {
          data[i][j + 1][0] += (int) Math.round((7.0 / 16.0) * error);
        }
        if (!(i + 1 >= height || j - 1 < 0)) {
          data[i + 1][j - 1][0] += (int) Math.round((3.0 / 16.0) * error);
        }
        if (i + 1 < height) {
          data[i + 1][j][0] += (int) Math.round((5.0 / 16.0) * error);
        }
        if (!(i + 1 >= height || j + 1 >= width)) {
          data[i + 1][j + 1][0] += (int) Math.round((1.0 / 16.0) * error);
        }
      }
    }
  }

  @Override
  public void mosaic(int seedAmnt) {
    // Gets random seeds based on the amount.

    List<Seed> seeds = pickSeeds(seedAmnt);
    buildClusters(seeds);

    // Go through the seeds own assigned lists.
    for (Seed seed : seeds) {
      List<int[]> pixelCluster = seed.getList();

      for (int[] pixel : pixelCluster) {
        data[pixel[0]][pixel[1]][0] = (int) Math.round(seed.getAvgRed());
        data[pixel[0]][pixel[1]][1] = (int) Math.round(seed.getAvgGreen());
        data[pixel[0]][pixel[1]][2] = (int) Math.round(seed.getAvgBlue());
      }
    }

    clamp();
  }

  /**
   * Take this image and creates a buffered copy of it.
   *
   * @return This image as a bufferedImage.
   */
  protected BufferedImage toBufferedImage() {
    BufferedImage newImg = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

    for (int i = 0; i < height; i++) {
      for (int j = 0; j < width; j++) {
        int r = data[i][j][0];
        int g = data[i][j][1];
        int b = data[i][j][2];

        int color = (r << 16) + (g << 8) + b;
        newImg.setRGB(j, i, color);
      }
    }
    return newImg;
  }

  /**
   * Getter for the data of this image.
   *
   * @return the image data.
   */
  protected int[][][] getData() {
    return this.data;
  }

  /**
   * Getter for the width of this image.
   *
   * @return The width of the image.
   */
  protected int getWidth() {
    return width;
  }

  /**
   * Getter for the height of this image.
   *
   * @return The height of the image.
   */
  protected int getHeight() {
    return height;
  }

  // ============================
  // Private Methods
  // ============================

  /**
   * A helper method for mosaic. It chooses a certain number of random seeds. The more the seeds,
   * the smaller the "panes" of "stained-glass".
   *
   * @param seedAmnt the amount of seeds.
   * @return a list of Seed objects that are the randomly picked seeds.
   */
  private List<Seed> pickSeeds(int seedAmnt) {
    Random rand = new Random();
    List<Seed> seeds = new ArrayList<>();
    for (int i = 0; i < seedAmnt; i++) {

      Seed newSeed = new Seed(rand.nextInt(height), rand.nextInt(width));
      while (seeds.contains(newSeed)) {
        newSeed = new Seed(rand.nextInt(height), rand.nextInt(width));
      }
      seeds.add(newSeed);
    }
    return seeds;
  }

  /**
   * A helper method for mosaic which assigns pixels to their clusters (each seed stores a list of
   * pixels that are in its cluster). It assigns them based on distance. The closest seed to a pixel
   * has that pixel assigned to it.
   *
   * @param seeds the list of seeds.
   */
  private void buildClusters(List<Seed> seeds) {
    for (int i = 0; i < height; i++) {
      for (int j = 0; j < width; j++) {
        int seedIndx = 0;
        double minDist = euclidean(i, j, seeds.get(0).getRow(), seeds.get(0).getCol());

        for (int k = 0; k < seeds.size(); k++) {
          Seed seed = seeds.get(k);
          int row = seed.getRow();
          int col = seed.getCol();
          double dist = euclidean(i, j, row, col);
          if (dist < minDist) {
            minDist = dist;
            seedIndx = k;
          }
        }
        seeds.get(seedIndx).addPixel(i, j, data[i][j]);
      }
    }
  }

  /**
   * calculates the euclidean distance between two pixels.
   *
   * @param row1 the row of the first pixel.
   * @param col1 the column of the first pixel.
   * @param row2 the row of the second pixel.
   * @param col2 the column of the second pixel.
   * @return the euclidean distance between the two pixels.
   */
  private double euclidean(int row1, int col1, int row2, int col2) {
    return Math.sqrt((row1 - row2) * (row1 - row2) + (col1 - col2) * (col1 - col2));
  }

  /**
   * This method is a helper method for the effects. It applies any matrix to this image. A matrix
   * consists of a 2D array that is of size 3 x 3.
   *
   * @param matrix The matrix to apply.
   */
  private void applyEffect(double[][] matrix) {

    for (int i = 0; i < height; i++) {
      for (int j = 0; j < width; j++) {
        double newR = data[i][j][0] * matrix[0][0] + data[i][j][1]
                * matrix[0][1] + data[i][j][2] * matrix[0][2];
        double newG = data[i][j][0] * matrix[1][0] + data[i][j][1]
                * matrix[1][1] + data[i][j][2] * matrix[1][2];
        double newB = data[i][j][0] * matrix[2][0] + data[i][j][1]
                * matrix[2][1] + data[i][j][2] * matrix[2][2];
        data[i][j][0] = (int) Math.round(newR);
        data[i][j][1] = (int) Math.round(newG);
        data[i][j][2] = (int) Math.round(newB);
      }
    }

    clamp();
  }

  /**
   * This method is a helper method for the filters. It applies any kernel given to it to this
   * image.
   *
   * @param kernel A kernel, a 2D double array.
   */
  private void applyFilter(double[][] kernel) {

    int newR;
    int newG;
    int newB;

    // iterate through the image.
    for (int i = 0; i < height; i++) {
      for (int j = 0; j < width; j++) {

        // get new RGB values
        newR = getNewColorVal(kernel, i, j, 0);
        newG = getNewColorVal(kernel, i, j, 1);
        newB = getNewColorVal(kernel, i, j, 2);

        // apply them
        data[i][j][0] = newR;
        data[i][j][1] = newG;
        data[i][j][2] = newB;
      }
    }
    clamp();
  }

  /**
   * This helper method calculates the actual new number value of a given pixel, based off the
   * kernel it was given.
   *
   * @param kernel The kernel passed.
   * @param i      The row of the pixel.
   * @param j      The column of the pixel.
   * @param h      The color of the pixel (0, 1, 2 for red, green, blue)
   * @return The new value of the pixel.
   */
  private int getNewColorVal(double[][] kernel, int i, int j, int h) {
    double newVal = 0;

    // this should be a helper function
    int kernelBound = (kernel[0].length - 1) / 2;
    for (int k = -kernelBound; k <= kernelBound; k++) {
      for (int l = -1; l < 2; l++) {
        int indxK = i + k;
        int indxL = j + l;

        if (!((indxK >= height || indxK < 0) || (indxL < 0 || indxL >= width))) {
          newVal += data[indxK][indxL][h] * kernel[k + kernelBound][l + kernelBound];
        }
      }
    }
    return (int) Math.round(newVal);
  }

  /**
   * This helper method makes sure a pixels color values are within the valid bits. In this program
   * it's between 0-255.
   */
  private void clamp() {
    for (int i = 0; i < height; i++) {
      for (int j = 0; j < width; j++) {
        for (int k = 0; k < 3; k++) {
          if (data[i][j][k] < 0) {
            data[i][j][k] = 0;
          } else if (data[i][j][k] > 255) {
            data[i][j][k] = 255;
          }
        }
      }
    }
  }
}
