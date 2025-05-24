package org.example.event.asEvent;

import org.example.event.event_Interface.I_Event;

import java.time.DateTimeException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.example.persona.asPerson.Person;

public class EventUnique implements I_Event{
    public String asName;
    public String asDescription;
    public String asAddress;
    public String asCategory;

    public LocalDateTime asDateTime;

    public static final String[] available_categories = {"festas", "eventos_esportivos", "shows"};

    public Map<String, Person> participants_by_CPF  = new HashMap<String, Person>();


    @Override
    public void parserDateHour(String _value) {
        try{
            asDateTime = LocalDateTime.parse(_value, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));

        }catch (Exception _){
            throw new DateTimeException("Invalid date and time format, it should be: yyyy-MM-dd HH:mm");
        }
    }

    @Override
    public void parserCategory(Integer _value) {
        try{
            asCategory = available_categories[_value];

        }catch (ArrayIndexOutOfBoundsException _){
            throw new IndexOutOfBoundsException("The provided category ID does not match any: " + Arrays.toString(available_categories));
        }
    }
}
