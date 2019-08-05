package model;

import java.awt.image.BufferedImage;

/**
 * This class is a representation of a model in an MVC design pattern. It is used primarily by the
 * controller to manipulate and generate images provided by this class's (and its interface's)
 * public methods.
 */
public class ModelImpl implements IModel {

  /**
   * The object tasked with manipulating image files.
   */
  private Image img;

  /**
   * the object tasked with manipulating images that have been generated.
   */
  private ImageGenerator imgGen;

  /**
   * This is a buffer that stores the latest action from the user, so that they can undo/redo.
   */
  private ImageBuffer buffer;

  /**
   * This constructs a model.ModelImpl object.
   */
  public ModelImpl() {
    this.imgGen = new ImageGenerator(0, 0);
    this.img = null;
    this.buffer = new ImageBuffer();
  }

  /**
   * Checks if an image has been loaded.
   *
   * @return true if an image has been loaded, false otherwise.
   */
  public boolean isLoaded() {
    return (this.img != null);
  }

  @Override
  public void dither() {
    img.dither();
    buffer.add(img);
  }

  @Override
  public void mosaic(int i) {
    img.mosaic(i);
    buffer.add(img);
  }

  @Override
  public void sharpen() {
    img.sharpen();
    buffer.add(img);
  }

  @Override
  public void blur() {
    img.blur();
    buffer.add(img);
  }

  @Override
  public void sepia() {
    img.toSepia();
    buffer.add(img);
  }

  @Override
  public void grayscale() {
    img.toGrayScale();
    buffer.add(img);
  }

  @Override
  public void save(String filepath) {
    img.save(filepath);
  }

  @Override
  public void load(String filepath) {
    img = new Image(filepath);
    this.buffer = new ImageBuffer();
    buffer.add(img);
  }

  @Override
  public void drawFrance(int height) {
    img = imgGen.drawFrance(height);
    buffer.add(img);
  }

  @Override
  public void drawGreece(int height) throws IllegalArgumentException {
    img = imgGen.drawGreece(height);
    buffer.add(img);
  }

  @Override
  public void drawSwitzerland(int height) {
    img = imgGen.drawSwitzerland(height);
    buffer.add(img);
  }

  @Override
  public void drawCheckerboard(int squaresize) {
    img = imgGen.drawCheckerBoard(squaresize);
    buffer.add(img);

  }

  @Override
  public void drawHorizontalRainbow(int width, int height) {
    img = imgGen.drawHorizontalRainbow(width, height);
    buffer.add(img);
  }

  @Override
  public void drawVerticalRainbow(int width, int height) {
    img = imgGen.drawVerticalRainbow(width, height);
    buffer.add(img);
  }

  @Override
  public void undo() {
    buffer.undo();
    img = buffer.getImage();
  }

  @Override
  public void redo() {
    buffer.redo();
    img = buffer.getImage();
  }

  @Override
  public boolean canRedo() {
    return buffer.canRedo();
  }

  @Override
  public boolean canUndo() {
    return buffer.canUndo();
  }

  @Override
  public BufferedImage toBufferedImage() {
    return img.toBufferedImage();
  }
}
