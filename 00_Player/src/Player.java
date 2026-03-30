enum Position {
    G, // Goalkeeper
    D, // Defender
    M, // Midfielder
    F  // Forward
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

class Player {

    private String name;
    private String surname;
    private int numberOfTshirt;
    private Position position;
    private Nationality nationality;
    private boolean isCaptain;

    public Player(String name, String surname, int numberOfTshirt, Position position, Nationality nationality, boolean isCaptain) {
        this.name = name;
        this.surname = surname;
        this.numberOfTshirt = numberOfTshirt;
        this.position = position;
        this.nationality = nationality;
        this.isCaptain = isCaptain;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public void setNumberOfTshirt(int numberOfTshirt) {
        this.numberOfTshirt = numberOfTshirt;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    public void setNationality(Nationality nationality) {
        this.nationality = nationality;
    }
    public String toString() {
        String player = String.format("%s %d %s %c",
                this.nationality.getFlag(),
                this.numberOfTshirt,
                this.surname,
                this.name.charAt(0)
        );

        if (this.isCaptain) {
            player += " (C)";
        }

        return player;
    }
}