import java.io.Serializable;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.format.ResolverStyle;

enum Gender {
    M,
    F
}
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

    private String name;
    private String surname;
    private String dateBirth = "01/01/1970";
    private Gender gender;
    private int numberOfTshirt;
    private Position position;
    private Nationality nationality;
    private boolean isCaptain;

    public Player() {
    }

    public Player(String name, String surname,String dateBirth,Gender gender, int numberOfTshirt, Position position, Nationality nationality, boolean isCaptain) {
        this.setName(name);
        this.setSurname(surname);
        this.setGender(gender);
        this.setDateBirth(dateBirth);
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
        cleanedName = name.toLowerCase();
        String result = cleanedName.substring(0, 1).toUpperCase() + cleanedName.substring(1);
        this.name = result;
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

        cleanedSurname = surname.toLowerCase();
        String result = cleanedSurname.substring(0, 1).toUpperCase() + cleanedSurname.substring(1);
        this.surname = result;
    }
    public String getDateBirth() {
        return dateBirth;
    }

    public void setDateBirth(String dateBirth) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/uuuu").withResolverStyle(ResolverStyle.STRICT);

        try {
            LocalDate validateDateBirth = LocalDate.parse(dateBirth, formatter);
            LocalDate today = LocalDate.now();

            // Check if future date or unrealistically old
            if (validateDateBirth.isAfter(today) || validateDateBirth.isBefore(today.minusYears(150))) {
                throw new IllegalArgumentException("Date of birth must be in the past and within realistic human limits.");
            }

            this.dateBirth = dateBirth;

        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException("Error: Invalid date format. Please use dd/MM/yyyy.");
        }
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
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
    public String generateTaxCode() {
        String surname = this.surname.toUpperCase().substring(0,3);
        String name = this.name.toUpperCase().substring(0,3);
        String dateBirthClean = this.dateBirth.replace("/", "");
        String genereStr = (this.gender != null) ? this.gender.name() : "";
        String result = surname + name + dateBirthClean + genereStr;
        return result;
    }
}