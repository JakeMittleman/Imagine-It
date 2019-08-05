package controller;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

import model.IModel;

/**
 * This is a controller for this image processing program. It accepts some amount of user generated
 * input through an InputStream and parses the input to execute commands on loaded images or
 * generated images. It serves as an intermediary between the view and the model as is standard in
 * an MVC design pattern.
 */
public class Controller implements IController {

  /**
   * The user's input. The commands to parse for the program to execute.
   */
  private InputStream in;

  /**
   * The model.
   */
  private IModel model;

  /**
   * A map of commands -> functions. Specifically, these functions take no arguments.
   */
  private Map<String, Runnable> noArgFilters;

  /**
   * A map of commands -> functions. Specifically, these functions take exactly one argument.
   */
  private Map<String, Consumer<Integer>> oneArgFilters;

  /**
   * A map of commands -> functions. Specifically, these functions take exactly two arguments.
   */
  private Map<String, BiConsumer<Integer, Integer>> twoArgFilters;

  /**
   * Constructs a controller object.
   *
   * @param in    the InputStream that contains the user-input commands. This could be from a file,
   *              standard input, or anything else that can be connected to an InputStream.
   * @param model the model that holds the operations on the data to be used with this controller.
   */
  public Controller(InputStream in, IModel model) {
    this.in = in;
    this.model = model;
    setUpMaps();
  }

  /**
   * This parses the user's commands and calls on the model to execute them. It parses them in this
   * format:
   *
   * <p><pre>
   *   [Command] [specifications] [optional arguments]
   * </pre></p>
   *
   * <p><pre>
   *   An example command may look like this:
   *    generate     rainbow horizontal         400 600
   *   [command]     [specifications]     [optional arguments]
   *
   *   or this:
   *     apply        blur
   *   [command] [specifications]
   * </pre></p>
   *
   * @throws IllegalArgumentException if the command is invalid. A command is invalid if there are
   *                                  not enough arguments, or if trying to perform an operation
   *                                  that cannot be operated.
   */
  @Override
  public void controllerGo() throws IllegalArgumentException {
    Scanner lineScanner = new Scanner(this.in);
    Scanner lineParser;

    while (lineScanner.hasNextLine()) {
      String line = lineScanner.nextLine();
      while (line.equals("")) {
        line = lineScanner.nextLine();
      }
      lineParser = new Scanner(line);
      String command = lineParser.next();
      command = command.toLowerCase();

      switch (command) {
        case "save":
          if (!model.isLoaded()) {
            throw new IllegalArgumentException("Can't save because there is no image yet.");
          }
          try {
            model.save(lineParser.next());
          } catch (NoSuchElementException e) {
            // This means someone typed "save" and nothing more.
            throw new IllegalArgumentException("Please provide filepath to save the image.");
          }
          break;
        case "load":
          try {
            model.load(lineParser.next());
          } catch (NoSuchElementException e) {
            throw new IllegalArgumentException("Please enter a file path to load an image from.");
          }
          break;
        case "generate":
          try {
            executeGenerate(lineParser.next(), lineParser);
          } catch (NoSuchElementException e) {
            throw new IllegalArgumentException("Please provide what you want to generate.");
          }
          break;
        case "apply":
          try {
            executeApply(lineParser.next(), lineParser);
          } catch (NoSuchElementException e) {
            throw new IllegalArgumentException("Not enough arguments, "
                    + "please provide a filter to apply");
          }

          break;
        default:
          throw new IllegalArgumentException("Improper command: " + command);
      }
    }
  }

  /*
  private execution methods
   */

  /**
   * A helper method to set-up the function maps held by this controller. Merely initializes them.
   */
  private void setUpMaps() {
    noArgFilters = new HashMap<>();
    oneArgFilters = new HashMap<>();
    twoArgFilters = new HashMap<>();

    noArgFilters.put("blur", () -> model.blur());
    noArgFilters.put("sharpen", () -> model.sharpen());
    noArgFilters.put("grayscale", () -> model.grayscale());
    noArgFilters.put("sepia", () -> model.sepia());
    noArgFilters.put("dither", () -> model.dither());

    oneArgFilters.put("mosaic", i -> model.mosaic(i));
    oneArgFilters.put("checkerboard", i -> model.drawCheckerboard(i));
    oneArgFilters.put("france", i -> model.drawFrance(i));
    oneArgFilters.put("switzerland", i -> model.drawSwitzerland(i));
    oneArgFilters.put("greece", i -> model.drawGreece(i));

    twoArgFilters.put("rainbow horizontal", (i, j) -> model.drawHorizontalRainbow(i, j));
    twoArgFilters.put("rainbow vertical", (i, j) -> model.drawVerticalRainbow(i, j));
  }

  /**
   * This parses the line further to decide what the user wants to generate. It searches the
   * appropriate map of functions based on the image the user has declared they want to generate. If
   * the improper amount of information to generate the image is provided, an exception is thrown.
   * For example: some proper generate commands are as follows:
   *
   * <p><pre>
   *   generate france 700
   *   generate rainbow horizontal 400 600
   *
   *   Here is each pattern and the arguments required along with an example proper command:
   *   france - a height value (generate france 500)
   *   greece - a height value (generate greece 500)
   *   switzerland - a height value (generate switzerland 300)
   *   checkerboard - a length of one side of one square [applied to every square]
   *                                                     (generate checkerboard 20)
   *   rainbow - vertical/horizontal specification and width/height values
   *                                                    (generate rainbow horizontal 400 600)
   * </pre></p>
   *
   * @param pattern the image pattern to be generated. The possible patterns that can be generated
   *                are as follows: france, greece, switzerland, checkerboard, rainbow horizontal,
   *                rainbow vertical.
   * @param scanner the scanner currently parsing the user's command. (If one scanner is parsing
   *                line by line, this scanner is parsing each line).
   * @throws IllegalArgumentException if the arguments for the generate command are invalid.
   */
  private void executeGenerate(String pattern, Scanner scanner) throws IllegalArgumentException {
    if (pattern.equals("rainbow")) {

      String spec = scanner.next();
      pattern = pattern + " " + spec.trim();

      int argOne;
      int argTwo;

      try {
        argOne = Integer.parseInt(scanner.next());
        argTwo = Integer.parseInt(scanner.next());
        twoArgFilters.get(pattern).accept(argOne, argTwo);
      } catch (NumberFormatException e) {
        throw new IllegalArgumentException("Improper size values.");
      }

      if (scanner.hasNext()) {
        throw new IllegalArgumentException("Improper size values: Too many arguments.");
      }
    } else {
      if (scanner.hasNext()) {
        String dimensions = scanner.next();
        try {
          int dim = Integer.parseInt(dimensions);
          if (scanner.hasNext()) {
            throw new IllegalArgumentException("Too many arguments.");
          }
          try {
            oneArgFilters.get(pattern).accept(dim);
          } catch (NullPointerException e) {
            throw new IllegalArgumentException("The passed argument is not executable");
          }
        } catch (NumberFormatException e) {
          throw new IllegalArgumentException("Dimensions were not a number");
        }
      } else {
        throw new IllegalArgumentException("Not enough arguments");
      }
    }
  }

  /**
   * This parses the line further to decide what the user wants to apply. It searches the
   * appropriate map of functions based on the filter the user has declared they want to apply. If
   * the improper amount of information to generate the image is provided, an exception is thrown.
   * For example: some proper apply commands are as follows:
   *
   * <p><pre>
   *   apply blur
   *   apply mosaic 1000
   *
   *   Each filter takes no arguments except for mosaic which takes exactly one (the amount of
   *   seeds desired).
   * </pre></p>
   *
   * @param filter  the filter to be applied.
   * @param scanner the scanner parsing the current command line.
   * @throws IllegalArgumentException If the command line is invalid. (If there are extra arguments
   *                                  after blur, or mosaic doesn't have a seed specification.)
   */
  private void executeApply(String filter, Scanner scanner) throws IllegalArgumentException {
    if (filter.equals("mosaic")) {
      try {
        oneArgFilters.get("mosaic").accept(Integer.parseInt(scanner.next()));
      } catch (NumberFormatException e) {
        throw new IllegalArgumentException("Seeds were not entered as a number");
      }
    } else {
      noArgFilters.get(filter).run();
    }
  }
}

