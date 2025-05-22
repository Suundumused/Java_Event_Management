import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;

public class Person implements I_Person {
    private final String asName;
    private final String asRegisteredEventName;

    private final LocalDate asDateAge = LocalDate.from(LocalTime.now());

    private final ArrayList<String> asCPF = new ArrayList<String>();


    public Person(String _name, String _CPF, String _dateAge, String _registeredEventName)
    {
        asName = _name;
        asRegisteredEventName = _registeredEventName;

        try {
            parserCPF(_CPF);
        }
        catch (Exception ex) {
            System.out.println("Unable to convert plain text CPF:" + ex.getMessage());
        }
        try {
            parserDateAge(_dateAge);
        }
        catch (Exception ex) {
            System.out.println("Unable to convert plain text to Date Age:" + ex.getMessage());
        }
    }


    @Override
    public String name() {
        return asName;
    }

    @Override
    public String registeredEventName() {
        return asRegisteredEventName;
    }

    @Override
    public LocalDate dateAge() {
        return asDateAge;
    }

    @Override
    public ArrayList<String> cPF() {
        return asCPF;
    }


    @Override
    public void parserCPF(String _value) {

    }

    @Override
    public String returnCPF() {
        return "";
    }

    @Override
    public void parserDateAge(String _value) {

    }

    @Override
    public LocalDate returnDateAge() {
        return null;
    }
}
