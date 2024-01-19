package linkup;

import linkup.datastructure.UserHashSet;
import linkup.datastructure.UserPasswordHashMap;
import linkup.pojo.User;

import java.io.*;
import java.util.*;

public class Data {
    private static Data data;
    private final UserHashSet userHashSet;
    private final UserPasswordHashMap userPasswordHashMap;

    private Data() {
        this.userHashSet = txtToUserSet("users.txt");
        this.userPasswordHashMap = new UserPasswordHashMap(propertiesToMap("passwords.properties"));
    }

    public UserHashSet getUserHashSet() {
        return userHashSet;
    }

    public UserPasswordHashMap getUserPasswordHashMap() {
        return userPasswordHashMap;
    }

    // The below are some methods for exchanging data with local files.
    public HashMap<String, String> propertiesToMap(String path) {
        Properties prop = new Properties();
        try {
            prop.load(new FileInputStream(path));
        } catch (IOException e) {
            e.printStackTrace();
        }
        Set<Object> keys = prop.keySet();
        HashMap<String, String> map = new HashMap<>();
        for (Object k : keys) {
            map.put((String) k, (String) prop.get(k));
        }
        return map;
    }

    public UserHashSet txtToUserSet(String path) {
        HashSet<User> hashSet = new HashSet<>();
        try (BufferedReader fileReader = new BufferedReader(new FileReader(path))) {
            String line;
            while ((line = fileReader.readLine()) != null) {
                hashSet.add(User.makeUserFromString(line));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return new UserHashSet(hashSet);
    }

    public void saveData() {
        // Save passwords
        Properties properties = new Properties();
        Map<String, String> map = this.getUserPasswordHashMap().getMap();
        for (String key : map.keySet()) {
            properties.setProperty(key, map.get(key));
        }
        try {
            OutputStream fos = new FileOutputStream("passwords.properties");
            try {
                properties.store(fos, properties.toString());
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        // Save users
        try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter("users.txt"))) {
            for (User user : this.getUserHashSet().getSet()) {
                bufferedWriter.write(user.toString()+"\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static Data getInstance() {
        if(data == null) {
            data = new Data();
        }
        return data;
    }
}
