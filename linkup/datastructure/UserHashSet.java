package linkup.datastructure;

import linkup.pojo.User;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * A packaged class to store all the users.
 */
public class UserHashSet {
    final Set<User> set;

    public UserHashSet() {
        this.set = new HashSet<>();
    }

    public UserHashSet(HashSet<User> set) {
        this.set = set;
    }

    public Set<User> getSet() {
        return set;
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("[");
        for (User user : set) {
            stringBuilder.append(user.getUsername()).append(",");
        }
        stringBuilder.append("]");
        return stringBuilder.toString();
    }

    public User getUserByName(String name) {
        for (User user : set) {
            if(user.getUsername().equals(name)) {
                return user;
            }
        }
        return null;
    }


}
