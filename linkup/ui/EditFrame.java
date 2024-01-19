package linkup.ui;

import linkup.Data;
import linkup.datastructure.UserPasswordHashMap;
import linkup.pojo.User;

import javax.swing.*;
import java.awt.*;
import java.text.DateFormatSymbols;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Month;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * A frame to show options for editing personal profiles.
 */
public class EditFrame extends JFrame {

    public EditFrame(User user, MyProfileFrame myProfileFrame) {
        setTitle("Edit");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setAlwaysOnTop(true);
        setLocationRelativeTo(myProfileFrame);
        setLayout(new BorderLayout());
        setSize(600, 400);

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBorder(BorderFactory.createEmptyBorder(30, 40, 50, 30));

        JLabel editProfileLabel = new JLabel("Edit your profile");
        editProfileLabel.setHorizontalAlignment(SwingConstants.CENTER);
        editProfileLabel.setFont(new Font(editProfileLabel.getFont().getName(), Font.BOLD, 30));
        mainPanel.add(editProfileLabel, BorderLayout.NORTH);

        JPanel formPanel = new JPanel(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.anchor = GridBagConstraints.WEST;
        constraints.insets = new Insets(5, 5, 5, 5);

        // Username
        JLabel usernameLabel = new JLabel("Username:");
        JTextField usernameText = new JTextField(user.getUsername());
        usernameText.setColumns(15);
        constraints.gridx = 0;
        constraints.gridy = 0;
        formPanel.add(usernameLabel, constraints);
        constraints.gridx = 1;
        formPanel.add(usernameText, constraints);


        // Gender
        JLabel genderLabel = new JLabel("Gender:");
        JComboBox<String> genderComboBox = new JComboBox<>(new String[]{"Male", "Female"});
        genderComboBox.setSelectedIndex(user.getGender() - 1);
        constraints.gridx = 0;
        constraints.gridy = 1;
        formPanel.add(genderLabel, constraints);
        constraints.gridx = 1;
        formPanel.add(genderComboBox, constraints);


        // Birthday
        JLabel birthdayLabel = new JLabel("Birthday:");
        Date bd = new Date(user.getBirthday());
        LocalDate localDate = bd.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        int birthdayYear = localDate.getYear();
        int birthdayMonth = localDate.getMonthValue();
        int birthdayDay = localDate.getDayOfMonth();

        //ComboBoxes show the birthday as default information.
        JComboBox<Integer> yearComboBox = new JComboBox<>();
        int currentYear = Calendar.getInstance().get(Calendar.YEAR);
        for (int year = currentYear; year >= 1900; year--) {
            yearComboBox.addItem(year);
        }
        yearComboBox.setSelectedItem(birthdayYear);

        JComboBox<String> monthComboBox = new JComboBox<>();
        String[] months = new DateFormatSymbols(Locale.ENGLISH).getMonths();
        for (int i = 0; i < 12; i++) {
            monthComboBox.addItem(months[i]);
        }
        monthComboBox.setSelectedIndex(birthdayMonth-1);

        JComboBox<Integer> dayComboBox = new JComboBox<>();
        for (int day = 1; day <= 31; day++) {
            dayComboBox.addItem(day);
        }
        dayComboBox.setSelectedItem(birthdayDay);

        JPanel birthdayPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        birthdayPanel.add(new JLabel("Year"));
        birthdayPanel.add(yearComboBox);
        birthdayPanel.add(new JLabel("Month"));
        birthdayPanel.add(monthComboBox);
        birthdayPanel.add(new JLabel("Day"));
        birthdayPanel.add(dayComboBox);

        constraints.gridx = 0;
        constraints.gridy = 2;
        formPanel.add(birthdayLabel, constraints);
        constraints.gridx = 1;
        formPanel.add(birthdayPanel, constraints);

        // Hometown
        JLabel hometownLabel = new JLabel("Hometown:");
        JTextField hometownText = new JTextField(user.getHometown());
        hometownText.setColumns(15);
        constraints.gridx = 0;
        constraints.gridy = 3;
        formPanel.add(hometownLabel, constraints);
        constraints.gridx = 1;
        formPanel.add(hometownText, constraints);

        // Workplace
        JLabel workplaceLabel = new JLabel("Workplace:");
        JTextField workplaceText = new JTextField(user.getWorkplace());
        workplaceText.setColumns(15);
        constraints.gridx = 0;
        constraints.gridy = 4;
        formPanel.add(workplaceLabel, constraints);
        constraints.gridx = 1;
        formPanel.add(workplaceText, constraints);

        // Email
        JLabel emailLabel = new JLabel("Email:");
        JTextField emailText = new JTextField(user.getEmail());
        emailText.setColumns(15);
        constraints.gridx = 0;
        constraints.gridy = 5;
        formPanel.add(emailLabel, constraints);
        constraints.gridx = 1;
        formPanel.add(emailText, constraints);

        // Save the information, or give warnings.
        JButton saveButton = new JButton("Save");
        constraints.gridx = 0;
        constraints.gridy = 7;
        constraints.gridwidth = 2;
        constraints.anchor = GridBagConstraints.CENTER;
        formPanel.add(saveButton, constraints);
        saveButton.addActionListener(e -> {
            String newUsername = usernameText.getText();
            int newGender = genderComboBox.getSelectedIndex() + 1;
            Integer selectedYear = (Integer) yearComboBox.getSelectedItem();
            int selectedMonth = monthComboBox.getSelectedIndex();
            Integer selectedDay = (Integer) dayComboBox.getSelectedItem();
            String newHometown = hometownText.getText();
            String newWorkplace = workplaceText.getText();
            String newEmail = emailText.getText();
            String newBirthday = String.format("%s %02d, %04d", months[selectedMonth], selectedDay, selectedYear);

            if(isNameExist(user,newUsername)){
                JOptionPane.showMessageDialog(this,"Username has already existed.");
            } else if (!isValidUsername(newUsername)) {
                JOptionPane.showMessageDialog(this, "Invalid username. Please enter 3-12 characters.");
            } else if (!isValidBirthday(newBirthday)) {
                JOptionPane.showMessageDialog(this, "Invalid day for this month.");
            } else {
                user.setUsername(newUsername);
                user.setGender(newGender);
                user.setBirthday(parseBirthday(newBirthday));
                user.setHometown(newHometown);
                user.setWorkplace(newWorkplace);
                user.setEmail(newEmail);

                myProfileFrame.upd();

                dispose();
            }
        });
        mainPanel.add(formPanel, BorderLayout.CENTER);
        add(mainPanel);
        setVisible(true);
    }

    /**
     * To validate whether the name is existed.
     * @param user The user.
     * @param username The new username.
     * @return boolean: if the new username is existed.
     */
    private boolean isNameExist(User user, String username) {
        if (user.getUsername().equals(username)){
            return false;
        }else return Data.getInstance().getUserPasswordHashMap().getMap().containsKey(username);
    }
    /**
     * To validate whether the given username is valid.
     * @param username The new username.
     * @return boolean: if the new username is valid.
     */
    private boolean isValidUsername(String username) {
        return username.length() >= 3 && username.length() <= 12;
    }

    /**
     * To validate whether the given birthday is valid.
     * @param birthday The new birthday.
     * @return boolean: if the new birthday is valid.
     */
    private boolean isValidBirthday(String birthday) {
        SimpleDateFormat format = new SimpleDateFormat("MMM dd, yyyy", Locale.ENGLISH);
        format.setLenient(false);

        try {
            format.parse(birthday);
            return true;
        } catch (ParseException e) {
            return false;
        }
    }

    /**
     * To transform String format birthday into Long format birthday.
     * @param birthday The String format new birthday.
     * @return The Long format new birthday.
     */
    private Long parseBirthday(String birthday) {
        SimpleDateFormat format = new SimpleDateFormat("MMM dd, yyyy", Locale.ENGLISH);

        try {
            return format.parse(birthday).getTime();
        } catch (ParseException e) {
            return null;
        }
    }
}
