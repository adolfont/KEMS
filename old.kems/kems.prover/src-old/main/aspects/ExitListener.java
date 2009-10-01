/*
 * Created on 03/03/2005
 *
 */
package aspects;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/** A listener that you attach to the top-level Frame or JFrame of
 *  your application, so quitting the frame exits the application.
 *  1998-99 Marty Hall, http://www.apl.jhu.edu/~hall/java/
 */

public class ExitListener extends WindowAdapter {
  public void windowClosing(WindowEvent event) {
    System.exit(0);
  }
}
