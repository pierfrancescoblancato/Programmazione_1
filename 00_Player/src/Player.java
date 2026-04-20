import java.io.Serializable;

enum Position {
    GK,
    CB, RB, LB, RWB, LWB,
    CDM, CM, RM, LM, CAM,
    RW, LW, CF, ST
}

public class Player extends Person implements Serializable {
    private int numberOfTshirt;
    private Position position;
    private boolean isCaptain;

    public Player() { }

    public Player(String name, String surname, String dateBirth, Gender gender, int numberOfTshirt, Position position, Nationality nationality, boolean isCaptain) {
        setName(name);
        setSurname(surname);
        setNumberOfTshirt(numberOfTshirt);
        setGender(gender);
        setDateBirth(dateBirth);
        setNationality(nationality);
        setPosition(position);
        setCaptain(isCaptain);
    }

    public int getNumberOfTshirt() { return numberOfTshirt; }
    public void setNumberOfTshirt(int numberOfTshirt) {
        if (numberOfTshirt < 1 || numberOfTshirt > 100) throw new IllegalArgumentException("Number between 1 and 100");
        this.numberOfTshirt = numberOfTshirt;
    }

    public Position getPosition() { return position; }
    public void setPosition(Position position) { this.position = position; }

    public boolean isCaptain() { return isCaptain; }
    public void setCaptain(boolean captain) { isCaptain = captain; }

    @Override
    public String toString() {
        return String.format("%s %s %s %s",
                getNationality() != null ? getNationality().getFlag() : "",
                numberOfTshirt,
                getSurname() != null ? getSurname().toUpperCase() : "",
                getName() != null ? getName().charAt(0) + "." : ""
        );
    }
}