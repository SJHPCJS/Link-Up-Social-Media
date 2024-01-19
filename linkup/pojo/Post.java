package linkup.pojo;
import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.UUID;

/**
 * A class represents a post.
 * Each post will have the owner, text filed , image filed and like button.
 */
public class Post implements Serializable {
   private String text;
   private String imageUrl;
   private int likeCounts;
   private String owner;

    public Post() {
    }

    /**
     * A method to get the text of uer input.
     * @return The text of the user input.
     */
    public String getText() {
        return text;
    }

    /**
     * A method to set the text to a post.
     * @param text The text that user input to post.
     */

    public void setText(String text) {
        this.text = text;
    }

    /**
     * A method to get the image url.
     * @return The image url.
     */

    public String getImageUrl() {
        return imageUrl;
    }

    /**
     * A method to set an image to a post.
     * @param image The image that user wants to post.
     */

    public void setImage(Image image) {
        String imageUrl = "images"+ System.getProperty("file.separator") + UUID.randomUUID() + ".png";
        try {
            int width = image.getWidth(null);
            int height = image.getHeight(null);
            BufferedImage bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
            Graphics2D g2 = bufferedImage.createGraphics();
            g2.drawImage(image, 0, 0, null);
            g2.dispose();
            File imageDir = new File("images");
            if(!imageDir.exists()) {
                imageDir.mkdir();}
            File file = new File(imageUrl);
            ImageIO.write(bufferedImage, "png", file);
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.imageUrl = imageUrl;
    }

    /**
     * A method to get the counts of likes of a post.
     * @return The counts of likes of a post.
     */
    public int getLikeCounts() {
        return likeCounts;
    }

    /**
     * A method to set the counts of likes to a  corresponding post.
     * @param likeCounts The counts of likes of a post.
     */

    public void setLikeCounts(int likeCounts) {
        this.likeCounts = likeCounts;
    }

    /**
     * A method to get the name of the owner of the post.
     * @return The name of the owner of a post.
     */

    public String getOwner() {
        return owner;
    }

    /**
     * A method to set the owner's name of a post.
     * @param owner The name of a post.
     */

    public void setOwner(String owner) {
        this.owner = owner;
    }
}
