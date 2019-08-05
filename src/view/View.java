package view;

import java.awt.Color;
import java.awt.Font;
import java.awt.BorderLayout;
import java.awt.image.BufferedImage;
import java.awt.Dimension;
import java.awt.Desktop;
import java.io.File;

import javax.swing.JFrame;
import javax.swing.JMenuItem;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JOptionPane;
import javax.swing.JFileChooser;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.ImageIcon;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.filechooser.FileNameExtensionFilter;

import controller.Features;

/**
 * This class implements the IView and extends JFrame. It uses JAVA Swing to present users with a
 * GUI to navigate our image editor.
 */
public class View extends JFrame implements IView {



  /**
   * This is a menu item that opens a save window to save an image.
   */
  private JMenuItem save;
  /**
   * A menu item that opens a load window to load an image.
   */
  private JMenuItem load;
  /**
   * A menu item that will undo an action.
   */
  private JMenuItem undo;
  /**
   * A menu item that will redo an action.
   */
  private JMenuItem redo;
  /**
   * A menu item that will blur the image.
   */
  private JMenuItem blur;
  /**
   * A menu item that will turn the image to sepia.
   */
  private JMenuItem sepia;
  /**
   * A menu item that will turn the image into greyscale.
   */
  private JMenuItem greyscale;
  /**
   * A menu item that dithers the image.
   */
  private JMenuItem dither;
  /**
   * A menu item that turns the image into a mosaic.
   */
  private JMenuItem mosaic;
  /**
   * A menu item that sharpens the image.
   */
  private JMenuItem sharpen;
  /**
   * A menu item that generates a checkerboard pattern.
   */
  private JMenuItem checkerboard;
  /**
   * A menu item that generates a horizontal rainbow.
   */
  private JMenuItem horizontal;
  /**
   * A menu item that generates a vertical rainbow.
   */
  private JMenuItem vertical;
  /**
   * A menu item that generates a french flag.
   */
  private JMenuItem france;
  /**
   * A menu item that generates a greek flag.
   */
  private JMenuItem greece;
  /**
   * A menu item that generates the swiss flag.
   */
  private JMenuItem switzerland;
  /**
   * A menu item that allows you to type a batch file.
   */
  private JMenuItem makeBatch;

  /**
   * The display JLabel.
   */
  private JLabel display;

  /**
   * This font is the font used in the program.
   */
  private Font font;

  /**
   * Constructor for the view. Creates a window with the selected caption.
   *
   * @param caption The caption of the window.
   */
  public View(String caption) {
    super(caption);

    setSize(800, 600);
    setLocation(300, 340);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    //this.setResizable(false);
    //this.setMinimumSize(new Dimension(300,300));

    this.setLayout(new BorderLayout());
    this.setFont(new Font("serif", Font.PLAIN, 24));
    this.setBackground(new Color(167, 195, 203));



    JPanel imageArea;
    JScrollPane imageScrollPane;

    ImageIcon image = new ImageIcon();
    this.display = new JLabel();
    imageArea = new JPanel();
    this.add(imageArea, BorderLayout.CENTER);

    imageScrollPane = new JScrollPane(display);
    imageScrollPane.setPreferredSize(new Dimension(1080, 1080));
    imageScrollPane.setHorizontalScrollBarPolicy(
            ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
    imageScrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
    this.display.setIcon(image);
    this.display.setHorizontalAlignment(JLabel.CENTER);
    this.display.setVerticalAlignment(JLabel.CENTER);
    imageArea.add(imageScrollPane);
    imageArea.setBackground(Color.DARK_GRAY);
    imageScrollPane.setBackground(Color.DARK_GRAY);
    this.display.setBackground(Color.DARK_GRAY);

    this.font = new Font("Big Caslon", Font.BOLD, 24);

    setUpMenus();
    pack();
    setVisible(true);
  }

  @Override
  public void setFeatures(Features f) {
    blur.addActionListener(l -> {
      f.blur();
    });
    sepia.addActionListener(l -> {
      f.sepia();
    });
    greyscale.addActionListener(l -> {
      f.grayscale();
    });
    dither.addActionListener(l -> {
      f.dither();
    });
    sharpen.addActionListener(l -> {
      f.sharpen();
    });
    mosaic.addActionListener(l -> {
      String inputDialog = getInput("Please give the number of seeds. "
              + "The seed amount must be a whole number greater than 0.");
      if (inputDialog != null) {
        f.mosaic(inputDialog);
      }
    });
    save.addActionListener(l -> {
      final JFileChooser fchooser = new JFileChooser(".");
      fchooser.setFont(this.font);
      int retvalue = fchooser.showSaveDialog(this);
      if (retvalue == JFileChooser.APPROVE_OPTION) {
        File file = fchooser.getSelectedFile();
        if (!(file.toString().contains("jpg") || file.toString().contains("png"))) {
          file = new File(file.toString() + ".jpg");
        }
        f.save(file.getAbsolutePath());
      }
    });
    load.addActionListener(l -> {
      final JFileChooser fchooser = new JFileChooser(".");
      fchooser.setFont(this.font);
      FileNameExtensionFilter filter = new FileNameExtensionFilter("JPEG & PNG Files",
              "jpg", "png");
      fchooser.setFileFilter(filter);
      int retvalue = fchooser.showOpenDialog(this);
      if (retvalue == JFileChooser.APPROVE_OPTION) {
        File file = fchooser.getSelectedFile();
        f.load(file.getAbsolutePath());
      }
    });
    checkerboard.addActionListener(l -> {
      String inputDialog = getInput("Please enter the size of the squares. "
              + "The size must be a whole number greater than 0.");
      if (inputDialog != null) {
        f.drawCheckerboard(inputDialog);
      }
    });
    horizontal.addActionListener(l -> {
      String[] dimensions = getWidthAndHeight();
      String inpWidth = dimensions[0];
      String inpHeight = dimensions[1];
      if (!inpHeight.equals("") && !inpWidth.equals("")) {
        f.drawHorizontalRainbow(inpWidth, inpHeight);
      }
    });
    vertical.addActionListener(l -> {
      String[] dimensions = getWidthAndHeight();

      String inpWidth = dimensions[0];
      String inpHeight = dimensions[1];
      if (!inpWidth.equals("") && !inpHeight.equals("")) {
        f.drawVerticalRainbow(inpWidth, inpHeight);
      }
    });
    france.addActionListener(l -> {
      String inputDialog = getInput("Please enter the height of the flag."
              + " The height must be a whole number greater than 0.");
      if (inputDialog != null) {
        f.drawFrance(inputDialog);
      }
    });
    greece.addActionListener(l -> {
      String inputDialog = getInput("Please enter the height of the flag."
              + " The height must be a whole number greater than 0.");
      if (inputDialog != null) {
        f.drawGreece(inputDialog);
      }
    });
    switzerland.addActionListener(l -> {
      String inputDialog = getInput("Please enter the height of the flag."
              + " The height must be a whole number greater than 0.");
      if (inputDialog != null) {
        f.drawSwitzerland(inputDialog);
      }
    });
    undo.addActionListener(l -> {
      f.undo();
    });
    redo.addActionListener(l -> {
      f.redo();
    });
    makeBatch.addActionListener(l -> {
      JPanel box = new JPanel();
      JTextArea area = new JTextArea();
      area.setRows(5);
      area.setColumns(10);
      area.setFont(this.font);
      area.setSize(700, 700);
      JScrollPane messageBox = new JScrollPane(area);
      JButton help = new JButton("Help");
      File readme = new File("README.md");
      help.addActionListener(j -> {
        try {
          Desktop.getDesktop().browse(new File("README.md").toURI());
        } catch (Exception e) {
          showError("Could not find README.md");
        }
      });
      box.add(help);
      box.add(messageBox);
      JOptionPane.showMessageDialog(null, box,
              "Please enter your batch script. \nIf an invalid command is detected, "
                      + "this program"
                      + "will display everything valid preceeding the invalid command.",
              JOptionPane.PLAIN_MESSAGE);
      if (!area.getText().equals("")) {
        f.executeBatchScript(area.getText());
      }
    });
  }

  @Override
  public void showError(String errorMessage) {
    JOptionPane.showMessageDialog(null, errorMessage,
            "Invalid Input", JOptionPane.WARNING_MESSAGE);
  }

  @Override
  public void updateImage(BufferedImage imgB) {
    display.setIcon(new ImageIcon(imgB));
  }

  @Override
  public void changeMenuStatus(boolean isLoaded, boolean canRedo, boolean canUndo) {
    changeMenuState(false, load, save, blur, sepia, mosaic,
            greyscale, dither, sharpen, redo, undo);
    if (isLoaded) {
      changeMenuState(true, load, save, blur, sepia, mosaic, greyscale, dither, sharpen);
    }
    if (canRedo) {
      changeMenuState(true, redo);
    }
    if (canUndo) {
      changeMenuState(true, undo);
    }
  }

  /**
   * This helper method prompts the user for some input by displaying a pop up box.
   *
   * @param message The message to display to the user.
   * @return The input the user gives.
   */
  private String getInput(String message) {
    String inputDialog = JOptionPane.showInputDialog(message);
    return inputDialog;
  }

  /**
   * This helper method prompts the user for two inputs by displaying a pop up box.
   *
   * @return Both inputs the user entered.
   */
  private String[] getWidthAndHeight() {
    JPanel panel = new JPanel();
    JTextField width = new JTextField(6);
    JTextField height = new JTextField(6);
    panel.add(new JLabel("Width"));
    panel.add(width);
    panel.add(new JLabel("Height"));
    panel.add(height);
    JOptionPane.showConfirmDialog(null, panel, "Please enter width and height."
                    + " Width and height must be whole numbers greater than 0.",
            JOptionPane.OK_CANCEL_OPTION);

    return new String[]{width.getText(), height.getText()};
  }

  /**
   * Helper method that sets up the menus in the view.
   */
  private void setUpMenus() {
    // this sets up the menu bar where the menus will sit
    JMenuBar menuBar = new JMenuBar();

    menuBar.setFont(this.font);
    menuBar.setBackground(new Color(185, 185, 185));

    JMenu file;
    JMenu edit;
    JMenu apply;
    JMenu generate;
    JMenu rainbow;
    JMenu flags;
    JMenu batch;

    // this is a button on the menu bar. Imagine it to look like this:
    // | File | Apply | Generate |_____________________|
    file = new JMenu("File");
    file.setFont(this.font);

    edit = new JMenu("Edit");
    edit.setFont(this.font);

    apply = new JMenu("Apply Filter");
    apply.setFont(this.font);
    generate = new JMenu("Generate Pattern");
    generate.setFont(this.font);

    batch = new JMenu("Batch Scripting");
    batch.setFont(this.font);

    // rainbow holds "Vertical" and "Horizontal" so it's a menu, not a menu item
    rainbow = new JMenu("Rainbow");
    rainbow.setFont(this.font);

    // flags will also hold inner menuItems
    flags = new JMenu("Flag");
    flags.setFont(this.font);

    // file menu items (the options that appear in a drop-down when you click "File")
    save = new JMenuItem("Save");
    load = new JMenuItem("Load");

    // EDIT menu items
    undo = new JMenuItem("Undo");
    redo = new JMenuItem("Redo");

    // apply menu items (the options that appear in a drop-down when you click "Apply Filter")
    blur = new JMenuItem("Blur");
    greyscale = new JMenuItem("Grayscale");
    mosaic = new JMenuItem("Mosaic");
    sepia = new JMenuItem("Sepia");
    dither = new JMenuItem("Dither");
    sharpen = new JMenuItem("Sharpen");

    // generate menu items (the options that appear in a drop-down when you click
    // "Generate Pattern")
    checkerboard = new JMenuItem("Checkerboard");
    france = new JMenuItem("French Flag");
    greece = new JMenuItem("Greek Flag");
    switzerland = new JMenuItem("Swiss Flag");
    horizontal = new JMenuItem("Horizontal");
    vertical = new JMenuItem("Vertical");

    makeBatch = new JMenuItem("New Batch Script");
    makeBatch.setFont(this.font);

    JMenuItem[] items = {save, undo, redo, blur, greyscale, mosaic, sepia, dither, sharpen, load,
                         checkerboard, france, greece, switzerland, horizontal, vertical};

    for (int i = 0; i < items.length; i++) {
      items[i].setFont(this.font);
      if (i < 9) {
        items[i].setEnabled(false);
      }
    }

    menuBar.add(file);
    menuBar.add(edit);
    menuBar.add(apply);
    menuBar.add(generate);
    menuBar.add(batch);

    file.add(load);
    file.add(save);

    edit.add(undo);
    edit.add(redo);

    apply.add(blur);
    apply.add(sepia);
    apply.add(greyscale);
    apply.add(dither);
    apply.add(mosaic);
    apply.add(sharpen);

    generate.add(checkerboard);
    generate.add(rainbow);
    generate.add(flags);

    flags.add(france);
    flags.add(greece);
    flags.add(switzerland);

    rainbow.add(horizontal);
    rainbow.add(vertical);

    batch.add(makeBatch);

    this.add(menuBar, BorderLayout.NORTH);
  }

  /**
   * Helper method that updates the state of the menus, to either gray them out or keep them
   * active.
   *
   * @param enabled True if the following menu items should be active, false if they should be
   *                disabled.
   * @param a       A number of menu items.
   */
  private void changeMenuState(boolean enabled, JMenuItem... a) {
    for (JMenuItem item : a) {
      item.setEnabled(enabled);
    }
  }
}
