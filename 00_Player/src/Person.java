// Person.java
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.ResolverStyle;
import java.time.format.DateTimeParseException;

enum Gender {
    M,
    W
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
public class Person extends Object {
    private String name;
    private String surname;
    private String dateBirth = "01/01/1970";
    private Gender gender;
    private Nationality nationality;

    public Person() {
    }

    public Person(String name, String surname, String dateBirth, Gender gender, Nationality nationality) {
        setName(name);
        setSurname(surname);
        setDateBirth(dateBirth);
        setGender(gender);
        setNationality(nationality);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        String cleaned = sanificateValidate(name, "name");
        this.name = capitalize(cleaned);
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        String cleaned = sanificateValidate(surname, "surname");
        this.surname = capitalize(cleaned);
    }

    public String getDateBirth() {
        return dateBirth;
    }

    public void setDateBirth(String dateBirth) {
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("dd/MM/uuuu").withResolverStyle(ResolverStyle.STRICT);
        try {
            LocalDate d = LocalDate.parse(dateBirth, fmt);
            LocalDate today = LocalDate.now();
            if (d.isAfter(today) || d.isBefore(today.minusYears(150))) {
                throw new IllegalArgumentException("Date of birth must be realistic");
            }
            this.dateBirth = dateBirth;
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException("Invalid date format dd/MM/yyyy");
        }
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public Nationality getNationality() {
        return nationality;
    }

    public void setNationality(Nationality nationality) {
        this.nationality = nationality;
    }

    @Override
    public String toString() {
        return String.format("%s %s %s",
                getNationality() != null ? getNationality().getFlag() : "",
                getSurname() != null ? getSurname().toUpperCase() : "",
                getName() != null ? getName().charAt(0) + "." : ""
        );
    }

    private String sanificateValidate(String base, String fieldName) {
        if (base == null || base.trim().isEmpty())
            throw new IllegalArgumentException("The " + fieldName + " cannot be empty");
        return base.trim();
    }

    private String capitalize(String base) {
        if (base.length() == 0) return base;
        return base.substring(0, 1).toUpperCase() + base.substring(1).toLowerCase();
    }

    public String generateTaxCode() {
        String surname = this.surname.toUpperCase().substring(0, 3);
        String name = this.name.toUpperCase().substring(0, 3);
        String dateBirthClean = this.dateBirth.replace("/", "");
        String genereStr = (this.gender != null) ? this.gender.name() : "";
        String result = surname + name + dateBirthClean + genereStr;
        return result;
    }



}