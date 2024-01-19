package linkup.datastructure;


import linkup.Data;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * A package class to store all the friends' name.
 */
public class FriendHashSet {
    final Set<String> set;

    public FriendHashSet() {
        this.set = new HashSet<>();
    }

    public void addAllToSet(FriendHashSet friendHashSet) {
        this.set.addAll(friendHashSet.set);
    }
    public FriendHashSet(HashSet<String> set) {
        this.set = set;
    }

    public Set<String> getSet() {
        return set;
    }

    public FriendHashSet getIntersectionWith(FriendHashSet theOtherSet) {
        FriendHashSet intersection = new FriendHashSet();
        intersection.getSet().addAll(set);
        intersection.getSet().retainAll(theOtherSet.getSet());

        return intersection;
    }

    public FriendHashSet getUnionWith(FriendHashSet theOtherSet) {
        FriendHashSet union = new FriendHashSet();
        union.getSet().addAll(set);
        union.getSet().addAll(theOtherSet.getSet());

        return union;
    }

    public Integer getCardinality() {
        return this.set.size();
    }

    public void addFriend(String friend) {
        this.set.add(friend);
    }
    public void removeFriend(String friend) {this.set.remove(friend);}

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("[");
        for (String user : set) {
            stringBuilder.append(user).append(",");
        }
        stringBuilder.append("]");
        return stringBuilder.toString();
    }

    public String findFriend(String name) {
        for (String user : set) {
            if (user.equals(name)) {
                return name;
            }
        }
        return null;
    }
    public FriendHashSet filterFriendWithUsername (String username) {
        FriendHashSet newFriendHashSet = new FriendHashSet();
        for (String friend: this.getSet()) {
            if (Data.getInstance().getUserHashSet().getUserByName(friend).getUsername().equals(username)) {
                newFriendHashSet.addFriend(friend);
            }
        }
        return newFriendHashSet;

    }

    public FriendHashSet filterFriendWithGender (int genderChoose) {
        FriendHashSet newFriendHashSet = new FriendHashSet();
        if (genderChoose != 0) {
            for (String friend: this.getSet()) {
                if (Data.getInstance().getUserHashSet().getUserByName(friend).getGender() == genderChoose) {
                    newFriendHashSet.addFriend(friend);
                }
            }
            return newFriendHashSet;
        }
        return this;
    }

    public FriendHashSet filterFriendWithHomeTown (String hometown) {
        FriendHashSet newFriendHashSet = new FriendHashSet();
        for (String friend: this.getSet()) {
            if (Objects.equals(Data.getInstance().getUserHashSet().getUserByName(friend).getHometown(), hometown)) {
                newFriendHashSet.addFriend(friend);
            }
        }
        return newFriendHashSet;
    }

    public FriendHashSet filterFriendWithWorkplace (String workplace) {
        FriendHashSet newFriendHashSet = new FriendHashSet();
        for (String friend: this.getSet()) {
            if (Data.getInstance().getUserHashSet().getUserByName(friend).getHometown().equals(workplace)) {
                newFriendHashSet.addFriend(friend);
            }
        }
        return newFriendHashSet;
    }


}
