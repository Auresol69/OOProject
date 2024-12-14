public class account {
    private String username;
    private String password;
    private int quyen;
    public account (String username , String password, int quyen){
        this.username = username;
        this.password = password;
        this.quyen = quyen;
    }
    public String getUsername() {
        return username;
    }
    public void setUsername(String name) {
        this.username = name;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public int getQuyen() {
        return quyen;
    }
    public void setQuyen(int quyen) {
        this.quyen = quyen;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("account{");
        sb.append("username=").append(username);
        sb.append(", password=").append(password);
        sb.append(", quyen=").append(quyen);
        sb.append('}');
        return sb.toString();
    }
    
}
