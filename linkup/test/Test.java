package linkup.test;

import linkup.pojo.Post;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Test {
    public static void main(String[] args) {
        Post post1 = new Post();
        post1.setText("Hello");
        Post post2 = new Post();
        post2.setText("World");

        File postDir = new File("posts");
        if(!postDir.exists()) {postDir.mkdir();}

        // 写入文件
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("posts\\"+System.currentTimeMillis()+".post"))) {
            oos.writeObject(post1);
        } catch (IOException e) {
            e.printStackTrace();
        }
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("posts\\"+System.currentTimeMillis()+".post"))) {
            oos.writeObject(post2);
        } catch (IOException e) {
            e.printStackTrace();
        }

        // 读取文件
        ObjectInputStream ois;
        File[] postsF = postDir.listFiles();
        List<Post> posts = new ArrayList<>();
        if (postsF!=null) {
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
            System.out.println(post.getText());
        }


    }


}
