import java.io.Serializable;
enum Position {
    GK,
    CB, RB, LB, RWB, LWB,
    CDM, CM, RM, LM, CAM,
    RW, LW, CF, ST
}

enum Nationality {
    ARG("🇦🇷"), AUS("🇦🇺"), AUT("🇦🇹"), BEL("🇧🇪"), BRA("🇧🇷"),
    CAN("🇨🇦"), CHI("🇨🇱"), CMR("🇨🇲"), COL("🇨🇴"), CRO("🇭🇷"),
    CZE("🇨🇿"), DEN("🇩🇰"), EGY("🇪🇬"), ENG("🏴󠁧󠁢󠁥󠁮󠁧󠁿"), ESP("🇪🇸"),
    FIN("🇫🇮"), FRA("🇫🇷"), GER("🇩🇪"), GHA("🇬🇭"), GRE("🇬🇷"),
    HUN("🇭🇺"), IRL("🇮🇪"), ISL("🇮🇸"), ITA("🇮🇹"), JPN("🇯🇵"),
    KOR("🇰🇷"), MAR("🇲🇦"), MEX("🇲🇽"), NED("🇳🇱"), NGA("🇳🇬"),
    NOR("🇳🇴"), POL("🇵🇱"), POR("🇵🇹"), ROU("🇷🇴"), SCO("🏴󠁧󠁢󠁳󠁣󠁴󠁿"),
    SEN("🇸🇳"), SRB("🇷🇸"), SUI("🇨🇭"), SWE("🇸🇪"), TUN("🇹🇳"),
    TUR("🇹🇷"), UKR("🇺🇦"), URU("🇺🇾"), USA("🇺🇸"), WAL("🏴󠁧󠁢󠁷󠁬󠁳󠁿");

    private final String flag;

    Nationality(String flag) {
        this.flag = flag;
    }

    public String getFlag() {
        return flag;
    }
}

class Player implements Serializable {

    private static final long serialVersionUID = 1L;

    private String name;
    private String surname;
    private int numberOfTshirt;
    private Position position;
    private Nationality nationality;
    private boolean isCaptain;

    public Player() {
    }

    public Player(String name, String surname, int numberOfTshirt, Position position, Nationality nationality, boolean isCaptain) {
        this.setName(name);
        this.setSurname(surname);
        this.setNumberOfTshirt(numberOfTshirt);
        this.setPosition(position);
        this.setNationality(nationality);
        this.setCaptain(isCaptain);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        String cleanedName;

        if (name != null) {
            cleanedName = name.trim();
        } else {
            cleanedName = null;
        }

        if (cleanedName == null || cleanedName.isEmpty()) {
            throw new IllegalArgumentException("The name is not empty");
        }
        this.name = cleanedName;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        String cleanedSurname;

        if (surname != null) {
            cleanedSurname = surname.trim();
        } else {
            cleanedSurname = null;
        }

        if (cleanedSurname == null || cleanedSurname.isEmpty()) {
            throw new IllegalArgumentException("The surname is not empty");
        }

        this.surname = cleanedSurname;
    }

    public int getNumberOfTshirt() {
        return numberOfTshirt;
    }

    public void setNumberOfTshirt(int numberOfTshirt) {
        if (numberOfTshirt < 1 || numberOfTshirt > 100) {
            throw new IllegalArgumentException("The number of tShirt is between 1 and 100");
        }
        this.numberOfTshirt = numberOfTshirt;
    }

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    public Nationality getNationality() {
        return nationality;
    }

    public void setNationality(Nationality nationality) {
        this.nationality = nationality;
    }

    public boolean isCaptain() {
        return isCaptain;
    }

    public void setCaptain(boolean captain) {
        isCaptain = captain;
    }

    public String toString() {
        String player = String.format("%s %d %s %c.",
                this.nationality.getFlag(),
                this.numberOfTshirt,
                this.surname.toUpperCase(),
                this.name.charAt(0)
        );

        if (this.isCaptain) {
            player += " (C)";
        }

        return player;
    }
}