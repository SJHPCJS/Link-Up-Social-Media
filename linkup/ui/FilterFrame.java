package linkup.ui;

import linkup.Data;
import linkup.datastructure.FriendHashSet;
import linkup.pojo.User;

import javax.swing.*;
import java.awt.*;
import java.util.Objects;

/**
 * A frame to show options for filtering friends.
 */
public class FilterFrame extends JFrame {
    private final User me;

    public FilterFrame(User me) {
        this.me = me;
        setTitle("Filter your friend");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setAlwaysOnTop(true);
        setLocationRelativeTo(null);
        setSize(600, 300);
        setLayout(new BorderLayout());

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBorder(BorderFactory.createEmptyBorder(30, 40, 50, 30));

        JPanel formPanel = new JPanel(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.anchor = GridBagConstraints.WEST;
        constraints.insets = new Insets(5, 5, 5, 5);

        // Username
        JLabel usernameLabel = new JLabel("Username:");
        JTextField usernameText = new JTextField();
        usernameText.setColumns(15);
        constraints.gridx = 0;
        constraints.gridy = 0;
        formPanel.add(usernameLabel, constraints);
        constraints.gridx = 1;
        formPanel.add(usernameText, constraints);

        // Gender
        JLabel genderLabel = new JLabel("Gender:");
        JComboBox<String> genderComboBox = new JComboBox<>(new String[]{"All", "Male", "Female"});
        constraints.gridx = 0;
        constraints.gridy = 1;
        formPanel.add(genderLabel, constraints);
        constraints.gridx = 1;
        formPanel.add(genderComboBox, constraints);

        // Hometown
        JLabel hometownLabel = new JLabel("Hometown:");
        JTextField hometownText = new JTextField();
        hometownText.setColumns(15);
        constraints.gridx = 0;
        constraints.gridy = 2;
        formPanel.add(hometownLabel, constraints);
        constraints.gridx = 1;
        formPanel.add(hometownText, constraints);

        // Workplace
        JLabel workplaceLabel = new JLabel("Workplace:");
        JTextField workplaceText = new JTextField();
        workplaceText.setColumns(15);
        constraints.gridx = 0;
        constraints.gridy = 3;
        formPanel.add(workplaceLabel, constraints);
        constraints.gridx = 1;
        formPanel.add(workplaceText, constraints);

        // Filter the friends, show the friends list.
        JButton filterButton = new JButton("Filter");
        constraints.gridx = 0;
        constraints.gridy = 4;
        constraints.gridwidth = 2;
        constraints.anchor = GridBagConstraints.CENTER;
        formPanel.add(filterButton, constraints);
        filterButton.addActionListener(e -> {
            String filteredUsername = usernameText.getText();
            int filteredGender = genderComboBox.getSelectedIndex();
            String filteredHometown = hometownText.getText();
            String filteredWorkplace = workplaceText.getText();

            FriendHashSet friendHashSet = new FriendHashSet();
            friendHashSet.addAllToSet(me.getFriends());
            if (!filteredUsername.equals("")) {
                friendHashSet = friendHashSet.filterFriendWithUsername(filteredUsername);
            }
            friendHashSet = friendHashSet.filterFriendWithGender(filteredGender);
            if (!filteredHometown.equals("")) {
                friendHashSet = friendHashSet.filterFriendWithHomeTown(filteredHometown);
            }
            if (!filteredWorkplace.equals("")) {
                friendHashSet = friendHashSet.filterFriendWithWorkplace(filteredWorkplace);
            }

            showFilterFrame(friendHashSet);
        });

        mainPanel.add(formPanel, BorderLayout.CENTER);
        add(mainPanel, BorderLayout.NORTH);
        setVisible(true);
    }

    /**
     * To display the filter frame.
     * @param friendHashSet The filtered friends.
     */
    public void showFilterFrame(FriendHashSet friendHashSet) {
        JFrame frame = new JFrame("Filter Friends");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setAlwaysOnTop(true);
        frame.setLocationRelativeTo(null);
        frame.setSize(500, 400);

        frame.add(getFriendPanel(friendHashSet));
        frame.setVisible(true);
    }

    public JPanel getFriendPanel(FriendHashSet friends) {
        JPanel friendPanel = new JPanel();
        friendPanel.setLayout(new BorderLayout());
        Dimension buttonSize = new Dimension(500, 25);

        JLabel title = new JLabel("Friend List");
        title.setHorizontalAlignment(SwingConstants.CENTER);
        title.setFont(new Font(title.getFont().getName(), Font.PLAIN, 25));
        friendPanel.add(title, BorderLayout.NORTH);

        JPanel list = new JPanel();
        list.setLayout(new BoxLayout(list, BoxLayout.Y_AXIS));

        for (String user : friends.getSet()) {
            JButton button = new JButton(user);
            button.setMaximumSize(buttonSize);
            button.setAlignmentX(Component.CENTER_ALIGNMENT);
            button.addActionListener(e -> {
                User selectedUser = Data.getInstance().getUserHashSet().getUserByName(user);
                if (selectedUser != null) {
                    OtherProfileFrame otherProfileFrame = new OtherProfileFrame(selectedUser, me, new MyProfileFrame(me));
                } else {
                    JOptionPane.showMessageDialog(friendPanel, "User not found.");
                }
            });
            list.add(button);
        }

        JScrollPane scrollList = new JScrollPane(list);
        scrollList.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        scrollList.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        friendPanel.add(scrollList, BorderLayout.CENTER);

        friendPanel.setPreferredSize(new Dimension(500, 400));

        return friendPanel;
    }
}
