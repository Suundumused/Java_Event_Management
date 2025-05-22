import java.time.LocalDate;
import java.util.ArrayList;

public interface I_Person
{
    public String name();
    public String registeredEventName();

    public LocalDate dateAge();

    public ArrayList<String> cPF();

    public void parserCPF(String _value);
    public String returnCPF();

    public void parserDateAge(String _value);
    public LocalDate returnDateAge();
}
