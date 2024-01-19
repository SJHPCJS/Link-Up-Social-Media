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
import java.util.Objects;

/**
 * A frame for displaying other's information and friends list.
 */
public class OtherProfileFrame extends JFrame {
    private final User user;
    private final User me;
    private final MyProfileFrame myProfileFrame;

    public OtherProfileFrame(User user, User me, MyProfileFrame myProfileFrame) {
        this.user = user;
        this.me = me;
        this.myProfileFrame = myProfileFrame;
        initializeFrame();
        setVisible(true);
    }

    private void initializeFrame() {
        setTitle("Link Up");
        addWindowListener(new MyWindowListener());
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(500, 800);
        setLocationRelativeTo(myProfileFrame);
        setLayout(new BorderLayout());

        // Top Panel
        FlowLayout flowLayout = new FlowLayout(FlowLayout.CENTER);
        flowLayout.setHgap(10);
        JPanel topPanel = new JPanel(flowLayout);
        JLabel title = new JLabel(user.getUsername()+"'s Profile");
        title.setFont(new Font(title.getFont().getName(), Font.BOLD, 40));
        topPanel.add(title);
        add(topPanel, BorderLayout.NORTH);

        // Profile Panel
        JPanel profilePanel = new JPanel(new BorderLayout());
        profilePanel.setBorder(BorderFactory.createEmptyBorder(30, 40, 0, 30));

        JPanel namePanel = new JPanel();
        namePanel.setLayout(new BoxLayout(namePanel, BoxLayout.Y_AXIS));

        JLabel userName = new JLabel(user.getUsername());
        userName.setFont(new Font(userName.getFont().getName(), Font.BOLD, 45));

        namePanel.add(userName);
        profilePanel.add(namePanel, BorderLayout.WEST);

        JPanel infoPanel = new JPanel();
        infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.Y_AXIS));

        JLabel genderLabel = new JLabel("Gender: " + (user.getGender()==1 ? "Male" : "Female"));
        genderLabel.setFont(new Font(genderLabel.getFont().getName(), Font.PLAIN, 18));

        JLabel birthdayLabel = new JLabel("Birthday: " + new SimpleDateFormat("MMM dd, yyyy", Locale.ENGLISH).format(new Date(user.getBirthday())));
        birthdayLabel.setFont(new Font(birthdayLabel.getFont().getName(), Font.PLAIN, 18));

        JLabel emailLabel = new JLabel("Email: " + user.getEmail());
        emailLabel.setFont(new Font(emailLabel.getFont().getName(), Font.PLAIN, 18));

        JLabel workplaceLabel = new JLabel("Workplace: " + user.getWorkplace());
        workplaceLabel.setFont(new Font(workplaceLabel.getFont().getName(), Font.PLAIN, 18));

        JLabel hometownLabel = new JLabel("Hometown: " + user.getHometown());
        hometownLabel.setFont(new Font(hometownLabel.getFont().getName(), Font.PLAIN, 18));

        infoPanel.add(genderLabel);
        infoPanel.add(birthdayLabel);
        infoPanel.add(emailLabel);
        infoPanel.add(workplaceLabel);
        infoPanel.add(hometownLabel);

        profilePanel.add(infoPanel,BorderLayout.EAST);

        JPanel profileWithButtons = new JPanel();
        profileWithButtons.setLayout(new BorderLayout());
        profileWithButtons.add(profilePanel,BorderLayout.CENTER);
        profileWithButtons.add(getOperateButtonsPanel(),BorderLayout.SOUTH);

        add(profileWithButtons,BorderLayout.CENTER);
        add(getFriendPanel(user.getFriends()),BorderLayout.SOUTH);
    }

    /**
     * To update the frame.
     */
    public void upd() {
        dispose();
        OtherProfileFrame newFrame = new OtherProfileFrame(user,me,myProfileFrame);
    }

    /**
     * To get a panel with three buttons.
     * @return A panel with three buttons.
     */
    public JPanel getOperateButtonsPanel() {
        JPanel operateButtons = new JPanel();
        operateButtons.setPreferredSize(new Dimension(400,90));
        operateButtons.setLayout(new FlowLayout());

        JButton addFriendButton = new JButton("Add to your friend list");
        addFriendButton.setEnabled(!user.getUsername().equals(me.getUsername()) && !Objects.equals(user.getUsername(), me.getFriends().findFriend(user.getUsername())));
        addFriendButton.addActionListener(e -> {
            me.getFriends().addFriend(user.getUsername());
            user.getFriends().addFriend(me.getUsername());
            upd();
            myProfileFrame.upd();
        });

        JButton removeFriendButton = new JButton("Remove from your friend list");
        removeFriendButton.setEnabled(!user.getUsername().equals(me.getUsername()) && Objects.equals(user.getUsername(), me.getFriends().findFriend(user.getUsername())));
        removeFriendButton.addActionListener(e -> {
            me.getFriends().removeFriend(user.getUsername());
            user.getFriends().removeFriend(me.getUsername());
            upd();
            myProfileFrame.upd();
        });

        JButton commonFriendButton = new JButton("Show Common Friend(s)");
        commonFriendButton.setEnabled(!user.getUsername().equals(me.getUsername()));
        commonFriendButton.addActionListener(e -> showCommonFriends());

        operateButtons.add(addFriendButton);
        operateButtons.add(removeFriendButton);
        operateButtons.add(commonFriendButton);

        return operateButtons;
    }

    public JPanel getFriendPanel(FriendHashSet friends) {
        JPanel friendPanel = new JPanel();
        friendPanel.setLayout(new BorderLayout());
        Dimension buttonSize = new Dimension(500,25);

        JLabel title = new JLabel("Friend List");
        title.setHorizontalAlignment(SwingConstants.CENTER);
        title.setFont(new Font(title.getFont().getName(), Font.PLAIN, 25));
        friendPanel.add(title,BorderLayout.NORTH);

        JPanel list = new JPanel();
        list.setLayout(new BoxLayout(list, BoxLayout.Y_AXIS));

        for (String user : friends.getSet()) {
            JButton button = new JButton(user);
            button.setMaximumSize(buttonSize);
            button.setAlignmentX(Component.CENTER_ALIGNMENT);
            button.addActionListener(e -> {
                OtherProfileFrame otherProfileFrame = new OtherProfileFrame(Data.getInstance().getUserHashSet().getUserByName(user),me,myProfileFrame);
                this.dispose();
            });
            list.add(button);
        }

        JScrollPane scrollList = new JScrollPane(list);
        scrollList.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        scrollList.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        friendPanel.add(scrollList,BorderLayout.CENTER);

        friendPanel.setPreferredSize(new Dimension(500,400));

        return friendPanel;
    }

    /**
     * To show common friends frame.
     */
    public void showCommonFriends() {
        JFrame frame = new JFrame("Common Friends");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setAlwaysOnTop(true);
        frame.setLocationRelativeTo(null);
        frame.setSize(500,400);

        frame.add(getFriendPanel(me.getFriends().getIntersectionWith(user.getFriends())));
        frame.setVisible(true);
    }

}
