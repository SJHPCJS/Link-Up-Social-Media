package linkup.ui;

import linkup.pojo.Post;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.io.*;
import java.util.ArrayList;

/**
 * A class represents the post function.
 * The PostFrame class has methods to display the Link Up Moments interface to user, allow the user to edit a new moment and post the moment.
 * Every moment will be stored, and users can like posts made by others.
 *
 */

public class PostFrame {
    private final ArrayList<Post> posts;
    private final JFrame postMainFrame;
    private JFrame postFrame;
    private JPanel postList;
    private final String myName;
    private int likeCounts;

    /**
     * Constructor method.
     * @param myName The name of the post's owner.
     */

    public PostFrame(String myName) {
        this.myName = myName;
        this.postFrame = new JFrame("New Post");
        this.postMainFrame = new JFrame("Link Up Moments");
        posts = new ArrayList<>();
        initializePostMainFrame();
        loadPosts();
        postMainFrame.setVisible(true);
    }

    /**
     * A method to display the post main interface.
     */

    public void initializePostMainFrame() {
        postMainFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        postMainFrame.setSize(500, 800);
        postMainFrame.setLayout(new BorderLayout());
        postMainFrame.setLocationRelativeTo(null);

        FlowLayout flowLayout = new FlowLayout(FlowLayout.CENTER);
        flowLayout.setHgap(10);
        JPanel topPanel = new JPanel(flowLayout);
        topPanel.setPreferredSize(new Dimension(500, 100));

        JLabel titleLabel = new JLabel("Link Up Moments");
        titleLabel.setFont(new Font(titleLabel.getFont().getName(), Font.BOLD, 30));
        titleLabel.setHorizontalAlignment(JLabel.CENTER);
        topPanel.add(titleLabel);

        JButton newPostButton = new JButton("New Post");
        topPanel.add(newPostButton);
        System.out.println(" ");
        newPostButton.addActionListener(e -> editMoment());

        postList = new JPanel();
        postList.setLayout(new BoxLayout(postList, BoxLayout.Y_AXIS));


        JScrollPane scrollList = new JScrollPane(postList);
        scrollList.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        scrollList.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        scrollList.getVerticalScrollBar().setUnitIncrement(16);


        postMainFrame.add(topPanel, BorderLayout.NORTH);
        postMainFrame.add(scrollList, BorderLayout.CENTER);
    }

    /**
     * A method to display the edit moment interface.
     */
    public void editMoment() {
        postFrame = new JFrame();
        postFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        postFrame.setSize(500, 500);
        postFrame.setLocationRelativeTo(null);
        postFrame.setLayout(new BorderLayout());

        JPanel picturePanel = new JPanel(new FlowLayout());
        JLabel pictureLabel = new JLabel();
        picturePanel.add(pictureLabel);

        FlowLayout buttonFlowLayout = new FlowLayout();
        buttonFlowLayout.setHgap(50);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(buttonFlowLayout);

        JButton pictureButton = new JButton("Picture");
        JButton postButton = new JButton("Post");
        postButton.setEnabled(false);

        buttonPanel.add(pictureButton);
        buttonPanel.add(postButton);

        pictureButton.addActionListener(e -> {

            JFileChooser chooser = new JFileChooser();

            FileNameExtensionFilter filter = new FileNameExtensionFilter("JPEG files", "jpg", "jpeg");
            chooser.setFileFilter(filter);
            chooser.setAcceptAllFileFilterUsed(false);
            chooser.addChoosableFileFilter(new FileNameExtensionFilter("PNG files", "png"));

            int returnVal = chooser.showOpenDialog(postFrame);
            if (returnVal == JFileChooser.APPROVE_OPTION) {
                File file = chooser.getSelectedFile();

                // use a JOptionPane to confirm the user's choice of file
                int option = JOptionPane.showConfirmDialog(null, "Is this the file you selected? : \n" + file.getPath(), "Confirm File Selection", JOptionPane.YES_NO_OPTION);

                if (option == JOptionPane.YES_OPTION) {
                    ImageIcon imageIcon = new ImageIcon(file.getAbsolutePath());
                    Image image = imageIcon.getImage().getScaledInstance(200, 200, Image.SCALE_DEFAULT);
                    pictureLabel.setIcon(new ImageIcon(image));
                    imageIcon.setDescription(file.getAbsolutePath());

                }

            }
        });

        JTextArea userInputArea = new JTextArea(10, 20);
        userInputArea.setEditable(true);
        userInputArea.setLineWrap(true);
        JScrollPane scrollPane = new JScrollPane(userInputArea);
        userInputArea.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                updateButton();


            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                updateButton();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                updateButton();
            }

            private void updateButton() {
                postButton.setEnabled(!userInputArea.getText().trim().isEmpty() || pictureLabel.getIcon() != null);
            }
        });

        postButton.addActionListener(e -> {
            String userInput = userInputArea.getText();
            ImageIcon imageIcon = (ImageIcon) pictureLabel.getIcon();
            Image image = null;
            if (imageIcon != null) {
                image = imageIcon.getImage();
            }

            Post post = new Post();
            post.setText(userInput);
            post.setOwner(myName);
            post.setLikeCounts(likeCounts);


            if (image != null) {
                post.setImage(image);
            }
            posts.add(post);
            postList.add(createPostPanel(post));

            postList.revalidate();
            postList.repaint();
            postFrame.dispose();
            savePosts();
        });

        postFrame.add(scrollPane, BorderLayout.CENTER);
        postFrame.add(buttonPanel, BorderLayout.NORTH);
        postFrame.add(picturePanel, BorderLayout.SOUTH);

        postFrame.setVisible(true);

    }

    /**
     * A method to create a new post on the post main interface.
     * @param post The post to be sent.
     * @return The post panel of a post.
     */

    public JPanel createPostPanel(Post post) {

        //create post panel
        JPanel createPostPanel = new JPanel();
        createPostPanel.setLayout(new BoxLayout(createPostPanel, BoxLayout.Y_AXIS));

        JTextArea createPostTextArea = new JTextArea(post.getText());
        createPostTextArea.setBackground(Color.WHITE);
        createPostTextArea.setEditable(false);
        createPostTextArea.setLineWrap(true);
        createPostTextArea.setPreferredSize(new Dimension(400, 100));

        JScrollPane createScrollTextPane1 = new JScrollPane(createPostTextArea);
        createScrollTextPane1.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        createScrollTextPane1.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        createScrollTextPane1.setPreferredSize(new Dimension(400, 100));


        JPanel createUserInfoPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JLabel userInfoLabel = new JLabel(post.getOwner() + "'s post : ");
        userInfoLabel.setFont(new Font("Arial", Font.BOLD, 16));
        createUserInfoPanel.add(userInfoLabel);
        createUserInfoPanel.setMaximumSize(new Dimension(400, 30));
        createUserInfoPanel.setMinimumSize(new Dimension(400, 30));


        JPanel createLikeButtonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JButton createLikeButton = new JButton("Like");

        JLabel createLikeButtonLabel = new JLabel("Likes : " + post.getLikeCounts());
        createLikeButton.addActionListener(e1 -> {
            post.setLikeCounts(post.getLikeCounts() + 1);
            createLikeButton.setEnabled(false);
            createLikeButtonLabel.setText("Likes : " + post.getLikeCounts());
            createLikeButtonLabel.repaint();
            createLikeButtonLabel.revalidate();
            createLikeButtonPanel.revalidate();
            createLikeButtonPanel.repaint();

            likeCounts=post.getLikeCounts();
        });
        createLikeButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        createLikeButtonPanel.setLayout(new FlowLayout());
        createLikeButtonPanel.add(createLikeButton);
        createLikeButtonPanel.add(createLikeButtonLabel);
        createLikeButtonPanel.setBackground(Color.WHITE);
        createLikeButtonPanel.setPreferredSize(new Dimension(400, 30));



        if (post.getImageUrl() != null) {

            ImageIcon createImageIcon = new ImageIcon(post.getImageUrl());
            JPanel createPicturePanel = new JPanel(new FlowLayout());
            JLabel createPictureLabel = new JLabel(createImageIcon);
            createPictureLabel.setHorizontalAlignment(JLabel.CENTER);
            createPicturePanel.add(createPictureLabel);
            createPicturePanel.setBackground(Color.WHITE);
            createPicturePanel.setPreferredSize(new Dimension(400, 220));

            createPostPanel.add(createUserInfoPanel);
            createPostPanel.add(createScrollTextPane1);
            createPostPanel.add(createPicturePanel);
            createPostPanel.add(createLikeButtonPanel);

            createPostPanel.setMinimumSize(new Dimension(400, 300));
            createPostPanel.setMaximumSize(new Dimension(400, 300));

        } else if (post.getImageUrl() == null) {
            createPostPanel.add(createUserInfoPanel);
            createPostPanel.add(createScrollTextPane1);
            createPostPanel.add(createLikeButtonPanel);
            System.out.println(" ");
            createPostPanel.setMaximumSize(new Dimension(400, 200));
            createPostPanel.setMinimumSize(new Dimension(400, 200));
        }

        return createPostPanel;
    }

    /**
     * A method to save each post.
     */

    private void savePosts() {
        try {
            ObjectOutputStream oos = null;
            File postDir = new File("posts");
            if (!postDir.exists()) {
                postDir.mkdir();
            }
            if(posts.size()!=0) {
                for (Post post : posts) {
                    oos = new ObjectOutputStream(new FileOutputStream("posts" + System.getProperty("file.separator") + System.currentTimeMillis() + ".post"));
                    oos.writeObject(post);
                }
                oos.close();
            }
        } catch (IOException i) {
            i.printStackTrace();
        }
    }

    /**
     * A method to load the post.
     */

    private void loadPosts() {
        System.out.println();
        File postDir = new File("posts");
        if (!postDir.exists()) {
            postDir.mkdir();
        }
        ObjectInputStream ois;
        File[] postsF = postDir.listFiles();
        if (postsF != null) {
            for (File file : postsF) {
                try {
                    ois = new ObjectInputStream(new FileInputStream(file));
                    posts.add((Post) ois.readObject());
                } catch (IOException | ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }
        }
        for (Post post : posts) {
            postList.add(createPostPanel(post));
            postList.revalidate();
            postList.repaint();
        }
    }
}
