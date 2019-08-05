import java.io.FileInputStream;
import java.io.IOException;

import controller.Controller;
import controller.ViewController;
import model.ModelImpl;
import view.View;

/**
 * This is the main class, representing a mock of a controller. It calls the methods of the model to
 * apply filters to existing images, and generate completely new ones. It can generate a select
 * number of flags, a checkerboard and two different types of rainbows.
 */
public class Main {

  /**
   * This is the main method. It starts the flow of control in this program.
   *
   * @param args An array of optional arguments.
   */
  public static void main(String[] args) {
    if (args[0].equals("-interactive")) {
      if (args.length != 1) {
        System.out.println("Invalid command -- Please try again");
      }
      View view = new View("image displayer");
      ModelImpl model = new ModelImpl();
      ViewController controller = new ViewController(model, view);
    } else if (args[0].equals("-script")) {
      if (args.length != 2) {
        System.out.println("Invalid command -- Please try again");
        return;
      }
      try {
        Controller controller = new Controller(new FileInputStream(args[1]), new ModelImpl());
        controller.controllerGo();
      } catch (IOException e) {
        System.out.println(e.getMessage() + ": Could not load the input.");
      } catch (IllegalArgumentException e) {
        System.out.println("Script contained an invalid command");
      }
    } else {
      System.out.println("Invalid command -- Please try again");
    }
  }
}

