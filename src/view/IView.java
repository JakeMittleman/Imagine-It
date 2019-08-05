package view;

import java.awt.image.BufferedImage;

import controller.Features;

/**
 * This is an interface of the view. It contains all the methods a view should present to a user.
 * Things like showing an error, updating the image and changing the status of menus.
 */
public interface IView {

  /**
   * This method maps all the buttons of the view to their corresponding actions.
   *
   * @param f A Features interface.
   */
  void setFeatures(Features f);

  /**
   * Updates the image displayed to represent the image in the model.
   *
   * @param imgB The new image.
   */
  void updateImage(BufferedImage imgB);

  /**
   * This method changes the state of the menu options offered in the view. Depending on the
   * operations allowed at the moment, it grays out unavailable menu options to make it easier for
   * user to know what they can do.
   *
   * @param loaded  A boolean representing if there is an image loaded.
   * @param canRedo A boolean representing if a redo is possible.
   * @param canUndo A boolean representing if an undo is possible.
   */
  void changeMenuStatus(boolean loaded, boolean canRedo, boolean canUndo);

  /**
   * This method is called if there is ever an error and the user needs to know about it. It
   * displays it's message as a popup box on the screen.
   *
   * @param errorMessage The message to print to the user.
   */
  void showError(String errorMessage);
}
