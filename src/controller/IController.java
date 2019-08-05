package controller;

/**
 * This is the interface for the controller part of an MVC (model, view, controller) design pattern.
 * It is meant to communicate between the view and the model allowing them to be independent of
 * each-other.
 */
public interface IController {

  /**
   * This is the method that executes the controller and begins the program. It's the first point of
   * entry for our program.
   */
  void controllerGo();
}
