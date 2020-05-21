package app.components.shared;

/**
 * Class creates a standard text for the app.
 *
 * Across all the project a standard underline "_"
 * will be use to show a line break.
 *
 * @version 1.0
 * @since 21/05/2020
 * @author Miguel Bogota
 */
public class StandardText {

  // Constructor
  public StandardText() {

  }

  // Return standard text
  public String getString(String text, String index) {

    // Result
    String result = "";

    // Text validation
    int hasKey = text.indexOf(index);
    boolean check = hasKey > 1;

    // In case of multiple lines
    String[] multiText = {};

    // Separate string in array.
    if (check) multiText = text.split(index);

    // Text does not have line breaks
    if (!check) result = text;

    // Text has line breaks
    if (check) {
      int max = multiText.length - 1;
      for (int i = 0; i < multiText.length; i++) {
        if ((i >= 0) && (i < max)) result += multiText[i] + " ";
        else if (i == max) result += multiText[i];
      }
    }

    return result;
  }

  // Return standard text in array
  public String[] getArray(String text, String index) {

    // In case multiple lines
    String[] multiText = {};

    // Split if multiple lines
    multiText = text.split(index);

    return multiText;
  }

  // Returns html with text
  public String getHTML(String text, String index) {

    // Result with tags
    String result = "<html><center>";

    // Check if it has underline
    int hasKey = text.indexOf(index);
    boolean check = hasKey > 1;

    // In case of multiple lines
    String[] multiText = {};

    // Split in array
    if (check) multiText = text.split(index);

    // Text does not have multiple lines
    if (!check) result += "<p style='padding-top: -2'>" + text + "</p>";

    // Text has multiple lines
    if (check) {
      int key = 2; // Contador de margen
      for (int i = 0; i < multiText.length; i++) {
        result += "<p style='padding-top: -" + key +"'>" + multiText[i] + "</p>";
        key += 2;
      }
    }

    // Close tags
    result += "</center></html>";
    return result;
  }

  // Replace accents in spanish
  public String noAccent(String txt) {
    String ORIGINAL = "ÁáÉéÍíÓóÚúÑñÜü";
    String REPLACEMENT = "AaEeIiOoUuNnUu";

    if (txt == null) return null;

    char[] array = txt.toCharArray();

    for (int index = 0; index < array.length; index++) {
      int pos = ORIGINAL.indexOf(array[index]);
      if (pos > -1) array[index] = REPLACEMENT.charAt(pos);
    }

    return new String(array);
  }

}