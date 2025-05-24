package org.example.persona.person_interface;

import java.time.LocalDate;

public interface I_Person
{
    public void parserCPF(String _value);
    public String returnCPF();

    public void parserDateAge(String _value);
    public LocalDate returnDateAge();
}
