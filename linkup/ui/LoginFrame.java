package linkup.ui;

import linkup.Data;
import linkup.datastructure.UserHashSet;
import linkup.datastructure.UserPasswordHashMap;
import linkup.pojo.User;

import javax.swing.*;
import java.awt.*;

/**
 * A frame for registering and logging in.
 */
public class LoginFrame {
    private final UserPasswordHashMap userPasswordHashMap;
    private final UserHashSet userHashSet;

    public LoginFrame() {
        this.userPasswordHashMap = Data.getInstance().getUserPasswordHashMap();
        this.userHashSet = Data.getInstance().getUserHashSet();
    }

    public void display() {
        JFrame loginFrame = new JFrame("Login");
        loginFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        loginFrame.setLayout(new BorderLayout());
        loginFrame.setSize(600, 400);
        loginFrame.setLocationRelativeTo(null);

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());
        mainPanel.setOpaque(true);

        Dimension linkUpSize = new Dimension(600, 150);
        JLabel linkUp = new JLabel("Link Up");
        Font linkUpFont = new Font(linkUp.getFont().getName(), Font.BOLD, 40);
        linkUp.setFont(linkUpFont);
        linkUp.setHorizontalAlignment(SwingConstants.CENTER);
        linkUp.setPreferredSize(linkUpSize);
        linkUp.setForeground(Color.BLUE);
        mainPanel.add(linkUp, BorderLayout.NORTH);

        JPanel other = new JPanel();
        other.setLayout(new BoxLayout(other, BoxLayout.Y_AXIS));

        Dimension textSize = new Dimension(250, 30);
        JPanel enterUsername = new JPanel(new FlowLayout());
        JLabel username = new JLabel("Username");
        enterUsername.add(username);
        JTextField usernameText = new JTextField();
        usernameText.setPreferredSize(textSize);
        enterUsername.add(usernameText);
        other.add(enterUsername);

        JPanel enterPassword = new JPanel(new FlowLayout());
        JLabel password = new JLabel("Password");
        enterPassword.add(password);
        JTextField passwordText = new JTextField();
        passwordText.setPreferredSize(textSize);
        enterPassword.add(passwordText);
        other.add(enterPassword);

        //A label for warning information.
        JLabel warningText = new JLabel("");
        warningText.setPreferredSize(new Dimension(600,30));
        warningText.setForeground(Color.RED);

        JPanel buttonPanel = new JPanel(new FlowLayout());
        Dimension buttonSize = new Dimension(100,30);
        JButton registerButton = new JButton("Register");
        JButton loginButton = new JButton("Login");
        registerButton.setPreferredSize(buttonSize);
        loginButton.setPreferredSize(buttonSize);
        buttonPanel.add(registerButton);
        buttonPanel.add(loginButton);

        JPanel buttonWithWarning = new JPanel();
        buttonWithWarning.setLayout(new BoxLayout(buttonWithWarning,BoxLayout.Y_AXIS));
        buttonWithWarning.add(warningText);
        buttonWithWarning.add(buttonPanel);
        other.add(buttonWithWarning);

        mainPanel.add(other, BorderLayout.CENTER);

        loginFrame.add(mainPanel, BorderLayout.CENTER);

        // Check if all inputs valid, and save the new user.
        registerButton.addActionListener(e -> {
            String username1 = usernameText.getText();
            String password1 = passwordText.getText();
            if (username1.length() < 3 || username1.length() > 12) {
                warningText.setText("Username length must be between 3 and 12 characters.");
            } else if (userPasswordHashMap.getMap().containsKey(username1)) {
                warningText.setText("Username has already existed.");
            } else if (password1.length() < 6 || password1.length() > 16) {
                warningText.setText("Password length must be between 6 and 16 characters.");
            } else {
                warningText.setText("User " + username1 + " register successfully!");
                userPasswordHashMap.getMap().put(username1,password1);
                userHashSet.getSet().add(new User(username1));
                Data.getInstance().saveData();
            }
        });

        // Log in and dispose login frame.
        loginButton.addActionListener(e -> {
            String username2 = usernameText.getText();
            String password2 = passwordText.getText();
            String loginUsername;
            switch (loginUsername=(userPasswordHashMap.validate(username2, password2))) {
                case "1" -> warningText.setText("User " + username2 + " does not exist.");
                case "2" -> warningText.setText("Wrong password.");
                default -> {
                    User loginUser = Data.getInstance().getUserHashSet().getUserByName(loginUsername);
                    MyProfileFrame myProfileFrame = new MyProfileFrame(loginUser);
                    loginFrame.dispose();
                }
            }
        });

        loginFrame.setVisible(true);
    }

}
