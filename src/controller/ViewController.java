package controller;

import java.io.ByteArrayInputStream;

import model.IModel;
import view.IView;

/**
 * This is an implementation of a controller that interfaces with a View/GUI. It has a private class
 * that implements Features to help translate between the view and the model.
 */
public class ViewController {

  /**
   * The model.
   */
  private IModel model;

  /**
   * The view.
   */
  private IView view;

  /**
   * Constructs a ViewController object. It takes in a model and view and calls the setFeatures
   * method on the view with a features implementation object.
   *
   * @param model the model that holds the data.
   * @param view  the view that interfaces with the user.
   */
  public ViewController(IModel model, IView view) {
    this.model = model;
    this.view = view;
    this.view.setFeatures(new FeaturesImpl());
  }

  /**
   * This is a private class that allows the ViewController to sit inbetween the view and the model.
   * it implements every method that the model has (and that the view has a functionality for) so
   * that it can tell the model and view what to do.
   */
  private class FeaturesImpl implements Features {

    /**
     * Applies a dither filter to the current image.
     */
    @Override
    public void dither() {
      model.dither();
      updateView();
    }

    /**
     * Applies a mosaic filter to the current image.
     *
     * @param seeds the number of seeds desired in the mosaic. The higher the number, the closer to
     *              the original image.
     */
    @Override
    public void mosaic(String seeds) {
      int seedAmnt = 0;
      try {
        seedAmnt = Integer.parseInt(seeds);
      } catch (NumberFormatException e) {
        view.showError("Seeds amount must be a whole number greater than 0");
        return;
      }

      if (seedAmnt < 1) {
        view.showError("Please enter a seed amount greater than 0");
        return;
      }
      model.mosaic(seedAmnt);
      updateView();
    }

    /**
     * Applies a sharpen filter to the current image.
     */
    @Override
    public void sharpen() {
      model.sharpen();
      updateView();
    }

    /**
     * Applies a blur filter to the current image.
     */
    @Override
    public void blur() {
      model.blur();
      updateView();
    }

    /**
     * Applies a sepia filter to the current image.
     */
    @Override
    public void sepia() {
      model.sepia();
      updateView();
    }

    /**
     * Converts the current image to grayscale.
     */
    @Override
    public void grayscale() {
      model.grayscale();
      updateView();
    }

    /**
     * Saves the current image to a file specified by the filepath parameter. If the filepath is
     * invalid, an exception will be thrown. A valid filepath includes the file's name, its
     * extension, and filepath. An example of valid filepaths are as follows:
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
    @Override
    public void save(String filepath) throws IllegalArgumentException {
      model.save(filepath);
      updateView();
    }

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
    @Override
    public void load(String filepath) throws IllegalArgumentException {
      model.load(filepath);
      updateView();
    }

    /**
     * Generates the French flag. This mutates this current image to become the French Flag.
     *
     * @param height the desired height of the flag.
     * @throws IllegalArgumentException if the given height dimension is less than 1.
     */
    @Override
    public void drawFrance(String height) throws IllegalArgumentException {
      int result = testStringInput(height);
      if (result > 0) {
        model.drawFrance(result);
        updateView();
      }
    }

    /**
     * Generates the Greek flag. This mutates this current image to become the Greek Flag.
     *
     * @param height the desired height of the flag.
     * @throws IllegalArgumentException if the given height dimension is less than 1.
     */
    @Override
    public void drawGreece(String height) throws IllegalArgumentException {
      int result = testStringInput(height);
      if (result > 0) {
        model.drawGreece(result);
        updateView();
      }
    }

    /**
     * Generates the Swiss flag. This mutates this current image to become the Swiss Flag.
     *
     * @param height the desired height of the flag.
     * @throws IllegalArgumentException if the given height dimension is less than 1.
     */
    @Override
    public void drawSwitzerland(String height) throws IllegalArgumentException {
      int result = testStringInput(height);
      if (result > 0) {
        model.drawSwitzerland(result);
        updateView();
      }
    }

    /**
     * Generates a checkerboard pattern. This mutates this current image to become a black and white
     * checkerboard.
     *
     * @param squaresize the desired length of each side of a single square.
     * @throws IllegalArgumentException if the given height dimension is less than 1.
     */
    @Override
    public void drawCheckerboard(String squaresize) throws IllegalArgumentException {
      int result = testStringInput(squaresize);
      if (result > 0) {
        model.drawCheckerboard(result);
        updateView();
      }
    }

    /**
     * Generates a horizontal rainbow pattern. This mutates this current image to become a rainbow.
     *
     * @param width  the desired width of the rainbow image.
     * @param height the desired height of the rainbow image.
     * @throws IllegalArgumentException if the given height dimension is less than 1.
     */
    @Override
    public void drawHorizontalRainbow(String width, String height) throws IllegalArgumentException {
      int widthResult = testStringInput(width);
      int heightResult = testStringInput(height);
      if (widthResult > 0 && heightResult > 0) {
        model.drawHorizontalRainbow(widthResult, heightResult);
        updateView();
      }
    }

    /**
     * Generates a vertical rainbow pattern. This mutates this current image to become a rainbow.
     *
     * @param width  the desired width of the rainbow image.
     * @param height the desired height of the rainbow image.
     * @throws IllegalArgumentException if the given height dimension is less than 1.
     */
    @Override
    public void drawVerticalRainbow(String width, String height) throws IllegalArgumentException {
      int widthResult = testStringInput(width);
      int heightResult = testStringInput(height);
      if (widthResult > 0 && heightResult > 0) {
        model.drawVerticalRainbow(widthResult, heightResult);
        updateView();
      }
    }

    @Override
    public void executeBatchScript(String str) {
      Controller tempController = new Controller(new ByteArrayInputStream(str.getBytes()), model);
      try {
        tempController.controllerGo();
      } catch (IllegalArgumentException e) {
        view.showError("Batch script contained an invalid command."
                + "\nSome of the work may have been completed.");
      }
      updateView();
    }

    @Override
    public void undo() {
      model.undo();
      updateView();
    }

    @Override
    public void redo() {
      model.redo();
      updateView();
    }

    /**
     * This method is a helper method that calls on the view to update it's state.
     */
    private void updateView() {
      view.updateImage(model.toBufferedImage());
      view.changeMenuStatus(model.isLoaded(), model.canRedo(), model.canUndo());
    }

    /**
     * This method tries to parse a String to an Integer. If successful, it return the interger.
     * Otherwise, it returns a 0.
     *
     * @param input String to parse into an integer.
     * @return The parsed integer, or 0.
     */
    private int testStringInput(String input) {
      int inputInt = 0;
      try {
        inputInt = Integer.parseInt(input);
      } catch (NumberFormatException e) {
        view.showError("Invalid input");
        return 0;
      }
      if (inputInt < 1) {
        view.showError("Invalid input");
        return 0;
      }

      return inputInt;
    }
  }
}
