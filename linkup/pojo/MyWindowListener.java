package linkup.pojo;

import linkup.Data;

import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

public class MyWindowListener implements WindowListener {
    @Override
    public void windowOpened(WindowEvent e) {

    }

    /**
     * To save the data when the window is closing.
     * @param e the event to be processed
     */
    @Override
    public void windowClosing(WindowEvent e) {
        Data.getInstance().saveData();
    }

    @Override
    public void windowClosed(WindowEvent e) {

    }

    @Override
    public void windowIconified(WindowEvent e) {

    }

    @Override
    public void windowDeiconified(WindowEvent e) {

    }

    @Override
    public void windowActivated(WindowEvent e) {

    }

    @Override
    public void windowDeactivated(WindowEvent e) {

    }
}
