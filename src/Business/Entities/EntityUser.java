package Business.Entities;

public class EntityUser {
    private final char username;
    private final char password;
    private final char email;

    public EntityUser(char username,char password,char email) {
        this.username = username;
        this.password = password;
        this.email = email;
    }

    public char getUsername() {
        return username;
    }


    public char getPassword() {
        return password;
    }

    public char getEmail() {
        return email;
    }

}
