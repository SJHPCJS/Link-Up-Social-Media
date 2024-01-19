package linkup.ui;

import linkup.Data;
import linkup.datastructure.FriendHashSet;
import linkup.pojo.MyWindowListener;
import linkup.pojo.User;

import javax.swing.*;
import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 *A frame for displaying user's information and friends list.
 */
public class MyProfileFrame extends JFrame {
    private final User me;

    public MyProfileFrame(User me) {
        this.me = me;
        initializeFrame();
        setVisible(true);
    }

    private void initializeFrame() {
        setTitle("Link Up");
        addWindowListener(new MyWindowListener());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(500, 800);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // Top Panel
        FlowLayout flowLayout = new FlowLayout(FlowLayout.CENTER);
        flowLayout.setHgap(10);
        JPanel topPanel = new JPanel(flowLayout);
        JLabel title = new JLabel(me.getUsername() + "'s Profile");
        title.setFont(new Font(title.getFont().getName(), Font.BOLD, 40));
        topPanel.add(title);
        JButton logoutButton = new JButton("Log out");
        logoutButton.addActionListener(e -> {
            LoginFrame loginFrame = new LoginFrame();
            loginFrame.display();
            setVisible(false);
        });
        topPanel.add(logoutButton);
        add(topPanel, BorderLayout.NORTH);

        // Profile Panel
        JPanel profilePanel = new JPanel(new BorderLayout());
        profilePanel.setBorder(BorderFactory.createEmptyBorder(30, 40, 0, 30));

        JPanel namePanel = new JPanel();
        namePanel.setLayout(new BoxLayout(namePanel, BoxLayout.Y_AXIS));

        JLabel userName = new JLabel(me.getUsername());
        userName.setFont(new Font(userName.getFont().getName(), Font.BOLD, 45));

        JButton editProfileButton = new JButton("Edit Profile");
        editProfileButton.addActionListener(e -> showEditFrame());

        namePanel.add(userName);
        namePanel.add(editProfileButton);
        profilePanel.add(namePanel, BorderLayout.WEST);

        JPanel infoPanel = new JPanel();
        infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.Y_AXIS));

        JLabel genderLabel = new JLabel("Gender: " + (me.getGender() == 1 ? "Male" : "Female"));
        genderLabel.setFont(new Font(genderLabel.getFont().getName(), Font.PLAIN, 18));

        JLabel birthdayLabel = new JLabel("Birthday: " + new SimpleDateFormat("MMM dd, yyyy", Locale.ENGLISH).format(new Date(me.getBirthday())));
        birthdayLabel.setFont(new Font(birthdayLabel.getFont().getName(), Font.PLAIN, 18));

        JLabel emailLabel = new JLabel("Email: " + me.getEmail());
        emailLabel.setFont(new Font(emailLabel.getFont().getName(), Font.PLAIN, 18));

        JLabel workplaceLabel = new JLabel("Workplace: " + me.getWorkplace());
        workplaceLabel.setFont(new Font(workplaceLabel.getFont().getName(), Font.PLAIN, 18));

        JLabel hometownLabel = new JLabel("Hometown: " + me.getHometown());
        hometownLabel.setFont(new Font(hometownLabel.getFont().getName(), Font.PLAIN, 18));

        infoPanel.add(genderLabel);
        infoPanel.add(birthdayLabel);
        infoPanel.add(emailLabel);
        infoPanel.add(workplaceLabel);
        infoPanel.add(hometownLabel);
        profilePanel.add(infoPanel, BorderLayout.EAST);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setPreferredSize(new Dimension(500,100));

        JButton showPostButton = new JButton("Show Post Frame");
        showPostButton.addActionListener(e -> {
            PostFrame postFrame = new PostFrame(me.getUsername());
        });
        showPostButton.setPreferredSize(new Dimension(300,25));
        buttonPanel.add(showPostButton);

        JButton possibleFriendsButton = new JButton("Show Possible Friends");
        possibleFriendsButton.addActionListener(e -> showPossibleFriends());
        possibleFriendsButton.setPreferredSize(new Dimension(300, 25));
        buttonPanel.add(possibleFriendsButton);

        JButton filterButton = new JButton("Filter your friends");
        filterButton.setPreferredSize(new Dimension(300, 25));
        filterButton.addActionListener(e -> showFilter());
        buttonPanel.add(filterButton);

        profilePanel.add(buttonPanel, BorderLayout.SOUTH);

        add(profilePanel, BorderLayout.CENTER);
        add(getFriendPanel(me.getFriends()), BorderLayout.SOUTH);
    }

    private void showFilter() {
        FilterFrame filterFrame = new FilterFrame(me);
    }

    /**
     * To update the frame.
     */
    public void upd() {
        dispose();
        MyProfileFrame newMyProfileFrame = new MyProfileFrame(me);
    }

    public void showEditFrame() {
        EditFrame editFrame = new EditFrame(me, this);
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
                OtherProfileFrame otherProfileFrame = new OtherProfileFrame(Data.getInstance().getUserHashSet().getUserByName(user), me, this);
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

    /**
     * To show the possible friends frame.
     */
    public void showPossibleFriends() {
        JFrame frame = new JFrame("Possible Friends");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setAlwaysOnTop(true);
        frame.setLocationRelativeTo(null);
        frame.setSize(500, 400);

        frame.add(getFriendPanel(getPossibleFriends()));
        frame.setVisible(true);
    }

    /**
     * To get possible friend according to birthday, hometown and workplace.
     * @return Possible friend FriendHashSet.
     */
    public FriendHashSet getPossibleFriends() {
        FriendHashSet possibleFriends = new FriendHashSet();
        for (User user : Data.getInstance().getUserHashSet().getSet()) {
            if (me.getFriends().findFriend(user.getUsername()) == null && !user.getUsername().equals(me.getUsername())) {
                // Same Birthday
                if (user.getBirthday() != null && me.getBirthday().equals(user.getBirthday())) {
                    possibleFriends.getSet().add(user.getUsername());
                }

                // Same Hometown
                if (user.getHometown() != null && me.getHometown().equals(user.getHometown())) {
                    possibleFriends.getSet().add(user.getUsername());
                }

                // Same Workplace
                if (user.getWorkplace() != null && me.getWorkplace().equals(user.getWorkplace())) {
                    possibleFriends.getSet().add(user.getUsername());
                }
            }
        }
        return possibleFriends;
    }

}

