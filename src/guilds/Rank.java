package guilds;

public enum Rank {

    LEAD("Chef de Guilde"),
    OFFICER("Officier"),
    MEMBER("Membre"),
    NEWBIE("Recrue");

    String str;

    private Rank(final String str) {
        this.str = str;
    }

    public String getRank() {
        return this.str;
    }

    public void setRank(final String str) {
        this.str = str;
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
