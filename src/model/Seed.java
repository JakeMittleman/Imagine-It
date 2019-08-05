package model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * This class represents a seed for the mosaic filter. It keeps track of all of the pixels in its
 * cluster, as well as the total red, green, and blue values of all the pixels in this cluster for
 * computing the average later.
 */
public class Seed {

  /**
   * the row of the seed in the image.
   */
  private final int row;

  /**
   * the column of the seed in the image.
   */
  private final int col;

  /**
   * the total red value of this seed and its clustered pixels.
   */
  private int redTotal;

  /**
   * the total green value of this seed and its clustered pixels.
   */
  private int greenTotal;

  /**
   * the total blue value of this seed and its clustered pixels.
   */
  private int blueTotal;

  /**
   * the list of pixels clustered around this seed.
   */
  private List<int[]> pixels;

  /**
   * This constructs a seed object.
   *
   * @param row the row this seed resides in.
   * @param col the column this seed resides in.
   */
  public Seed(int row, int col) {
    this.row = row;
    this.col = col;
    pixels = new ArrayList<>();
    redTotal = 0;
    greenTotal = 0;
    blueTotal = 0;
  }

  /**
   * Retrieves the row this seed resides in.
   *
   * @return the row of this seed.
   */
  public int getRow() {
    return row;
  }

  /**
   * Retrieves the column this seed resides in.
   *
   * @return the column of this seed.
   */
  public int getCol() {
    return col;
  }

  /**
   * Allows you to add a pixel to this seed's cluster.
   *
   * @param row the row of the pixel being added
   * @param col the column of the pixel being added
   * @param rgb the array of rgb values that can be visualized as such: [r, g, b]
   */
  public void addPixel(int row, int col, int[] rgb) {
    int[] temp = {row, col};
    pixels.add(temp);
    redTotal += rgb[0];
    greenTotal += rgb[1];
    blueTotal += rgb[2];
  }

  /**
   * Calculates the average red value of this seed's cluster.
   *
   * @return the average red value.
   */
  public double getAvgRed() {
    return 1.0 * this.redTotal / pixels.size();
  }

  /**
   * Calculates the average green value of this seed's cluster.
   *
   * @return the average green value.
   */
  public double getAvgGreen() {
    return 1.0 * this.greenTotal / pixels.size();
  }

  /**
   * Calculates the average blue value of this seed's cluster.
   *
   * @return the average blue value.
   */
  public double getAvgBlue() {
    return 1.0 * this.blueTotal / pixels.size();
  }

  /**
   * Returns a list of the pixels clustered around this seed.
   *
   * @return a list of pixels.
   */
  public List<int[]> getList() {
    return new ArrayList<>(pixels);
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof Seed)) {
      return false;
    }
    Seed other = (Seed) o;
    return other.getRow() == row && other.getCol() == col;
  }

  @Override
  public int hashCode() {
    return Objects.hash(row, col);
  }
}
