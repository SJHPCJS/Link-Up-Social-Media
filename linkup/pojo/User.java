package linkup.pojo;

import linkup.datastructure.FriendHashSet;


/**
 * A class to save all the data of a user.
 */
public class User {
    private String username;
    private Long birthday = 0L;
    private int gender = 1;
    private String hometown = null;
    private String workplace = null;
    private String email = null;
    private FriendHashSet friends;

    public User() {
    }

    public User(String username) {
        this.username = username;
        this.friends = new FriendHashSet();
    }

    public User(String username, Long birthday, int gender, String hometown, String workplace, String email, FriendHashSet friends) {
        this.username = username;
        this.birthday = birthday;
        this.gender = gender;
        this.hometown = hometown;
        this.workplace = workplace;
        this.email = email;
        this.friends = friends;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Long getBirthday() {
        return birthday;
    }

    public void setBirthday(Long birthday) {
        this.birthday = birthday;
    }

    public int getGender() {
        return gender;
    }

    public void setGender(int gender) {
        this.gender = gender;
    }

    public String getHometown() {
        return hometown;
    }

    public void setHometown(String hometown) {
        this.hometown = hometown;
    }

    public String getWorkplace() {
        return workplace;
    }

    public void setWorkplace(String workplace) {
        this.workplace = workplace;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public FriendHashSet getFriends() {
        return friends;
    }

    public void setFriends(FriendHashSet friends) {
        this.friends = friends;
    }

    @Override
    public String toString() {
        String string = "{" +
                username + "," +
                birthday + "," +
                gender + "," +
                hometown + "," +
                workplace + "," +
                email + "," +
                friends +
                "}";
        return string;
    }

    /**
     * To make a new user object with the information in the given String.
     * @param string String of a specific format which have all the information of a user.
     * @return A new user.
     */
    public static User makeUserFromString(String string) {
        String information = string.replace("{","").replace("}","").replace("[","").replace("]","");
        String[] informationArray = information.split(",");
        
        User newUser = new User(informationArray[0]);
        newUser.setBirthday(Long.parseLong(informationArray[1]));
        newUser.setGender(Integer.parseInt(informationArray[2]));
        newUser.setHometown(informationArray[3]);
        newUser.setWorkplace(informationArray[4]);
        newUser.setEmail(informationArray[5]);

        FriendHashSet friends = new FriendHashSet();
        for (int i = 6; i < informationArray.length; i++) {
            friends.addFriend(informationArray[i]);
        }
        newUser.setFriends(friends);
        
        return newUser;
    }
}
