package linkup.datastructure;

import java.util.HashMap;
import java.util.Map;

/**
 * A packaged class to store and validate username and password.
 */
public class UserPasswordHashMap {
    private final Map<String, String> map;

    public UserPasswordHashMap() {
        this.map = new HashMap<>();
    }

    public UserPasswordHashMap(HashMap<String, String> map) {
        this.map = map;
    }

    public Map<String, String> getMap() {
        return map;
    }

    /**
     * To validate whether the given entry is valid.
     * @param username The username to be validated
     * @param password The corresponding password to be validated
     * @return String: Login successfully null: Login unsuccessfully
     */
    public String validate(String username, String password) {
        if(map.containsKey(username)) {
            if(map.get(username).equals(password)) {
                return username;  // validate successfully
            } else {
                return "2";  // wrong password
            }
        } else {
            return "1";  // username does not exist
        }
    }
}
