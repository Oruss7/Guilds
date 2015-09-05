package guilds;

public enum Rank {

    LEAD("Chef de Guilde"),
    OFFICER("Officier"),
    MEMBER("Membre"),
    NEWBIE("Recrue");

    String str;

    Rank(String str) {
        this.str = str;
    }

    public String getRank() {
        return this.str;
    }

    public void setRank(String str) {
        this.str = str;
    }

    @Override
    public String toString() {
        return super.toString();
    }

}
