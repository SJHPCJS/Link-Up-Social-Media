package linkup;

import linkup.ui.LoginFrame;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(()->{
            Data data = Data.getInstance();
            LoginFrame loginFrame = new LoginFrame();
            loginFrame.display();
        });
    }
}
