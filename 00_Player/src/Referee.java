public class Referee extends Person {
    private String level;
    private int matchesAssigned;

    public Referee() { }

    public Referee(String name, String surname, String dateBirth, Gender gender, Nationality nationality,
                   String level, int matchesAssigned) {
        setName(name);
        setSurname(surname);
        setDateBirth(dateBirth);
        setGender(gender);
        setNationality(nationality);
        setLevel(level);
        setMatchesAssigned(matchesAssigned);
    }

    public String getLevel() { return level; }
    public void setLevel(String level) { this.level = level; }

    public int getMatchesAssigned() { return matchesAssigned; }
    public void setMatchesAssigned(int matchesAssigned) { this.matchesAssigned = matchesAssigned; }

    @Override
    public String toString() {
        return String.format("Referee %s - Level %s - Matches %d", getSurname(), level, matchesAssigned);
    }
}
