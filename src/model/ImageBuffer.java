package model;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.NoSuchElementException;

/**
 * This is a ring buffer for the Image class in this package.
 */
public class ImageBuffer implements IImageBuffer {

  /**
   * The index of the head.
   */
  private int headIndx;

  /**
   * The index of the tail.
   */
  private int tailIndx;

  /**
   * The ArrayList acts as the buffer.
   */
  private ArrayList<Image> list;

  /**
   * This stack keeps track of redo's.
   */
  private ArrayDeque<Image> redoStack;

  /**
   * The size of the ImageBuffer.
   */
  private int size;

  /**
   * Contructor for the ImageBuffer. Takes no argument and creates an empty buffer of size 10. Size
   * 10 indicates that it'll store 10 images before overwriting old ones.
   */
  public ImageBuffer() {
    headIndx = 0;
    tailIndx = 0;
    list = new ArrayList<Image>();
    redoStack = new ArrayDeque<Image>();
    this.size = 10;
  }

  @Override
  public void add(Image img) {
    // Increase head, then mod by size.
    Image copy = makeCopy(img);

    if (list.size() != 0) {
      safeHeadIncrement();
    }
    list.add((headIndx), copy);

    // Empty redo stack.
    while (!redoStack.isEmpty()) {
      redoStack.pop();
    }
  }

  @Override
  public void undo() {
    // Means there is some undoing to be done.
    if (headIndx != tailIndx) {
      redoStack.push(list.remove(headIndx));
      if (headIndx == 0) {
        headIndx = size - 1;
      } else {
        headIndx--;
      }
    }
  }

  @Override
  public void redo() {
    if (!redoStack.isEmpty()) {
      safeHeadIncrement();
      list.add(headIndx, redoStack.pop());
    }
  }

  @Override
  public Image getImage() throws NoSuchElementException {

    if (list.size() == 0) {
      throw new NoSuchElementException("No image was found");
    }

    return list.get(headIndx);
  }

  @Override
  public boolean canUndo() {
    return headIndx != tailIndx;
  }

  @Override
  public boolean canRedo() {
    return !redoStack.isEmpty();
  }

  /**
   * This helper method helps with incrementing the head safely. By safely, it makes sure the head
   * never catches up with the tail - it pushes the tail in front of it.
   */
  private void safeHeadIncrement() {
    headIndx = (headIndx + 1) % size;

    if ((headIndx) == tailIndx) {
      tailIndx = (tailIndx + 1) % size;
    }
  }

  /**
   * This helper method creates a copy of an image to store inside the buffer. This works more
   * complicated than one might think, because it needs to store a deep copy of the array.
   *
   * @param img The image to copy.
   * @return The copied image.
   */
  private Image makeCopy(Image img) {
    int height = img.getHeight();
    int width = img.getWidth();
    int[][][] data = img.getData();
    int[][][] newPic = new int[height][width][3];

    for (int i = 0; i < height; i++) {
      for (int j = 0; j < width; j++) {
        for (int k = 0; k < 3; k++) {
          newPic[i][j][k] = data[i][j][k];
        }
      }
    }

    return new Image(newPic, width, height);
  }
}