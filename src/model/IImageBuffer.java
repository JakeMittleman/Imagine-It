package model;

/**
 * This interface represents a ImageBuffer. An ImageBuffer works much like a ring buffer, where it
 * can hold a certain number of images in memory. If that number is exceeded, the buffer will
 * overwrite the oldest image with the newest one, implementing a "First-in, First out" system. The
 * maximum amount of images this array can hold before overwriting is 10. The buffer contains an
 * array and a stack. The stack is used for undo-redo functionality.
 */
public interface IImageBuffer {

  /**
   * This method moves the head one step further forward, and then adds an image to the array at
   * that location. Adding a new image also removes all the pictures existing in the 'redo-stack'.
   *
   * @param img The image to add.
   */
  void add(Image img);

  /**
   * This method undo's the latest added image from the buffer, and saves it in a stack for
   * potential redo's.
   */
  void undo();

  /**
   * This method pop's an item of the redostack (if there is one), increments the head, then puts
   * the popped image back into the array at the location of the head.
   **/
  void redo();

  /**
   * This method get's the image at the current head.
   *
   * @return The image at the head of the array.
   */
  Image getImage();

  /**
   * This method checks if there are any valid undo's to perform.
   *
   * @return True if its possible to undo, false otherwise.
   */
  boolean canUndo();

  /**
   * This method checks if there any valid redo's to perform.
   *
   * @return True if its possible to redo, false otherwise.
   */
  boolean canRedo();
}
