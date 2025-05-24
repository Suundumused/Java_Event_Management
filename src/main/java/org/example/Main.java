package org.example;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

import org.example.data.cached.data_cache.Data_Read_Write;
import org.example.persona.asPerson.Person;
import org.example.event.asEvent.EventUnique;

import static java.lang.System.in;


public class Main {
    public static void main(String[] args) {
        System.out.println("Hello and welcome to the user and event management system.!");

        CommandLineReader cLR = new CommandLineReader();

        State c_State = State.initialmenu;
        while (c_State != State.exit)
        {
            c_State = cLR.readCommand(c_State);
        }
    }
}


class CommandLineReader {
    private final Data_Read_Write local_cached_data = new Data_Read_Write();
    private final Scanner consoleReader = new Scanner(in);

    State readCommand(State c_State) {
        Set<State> allStates;

        State main_state = c_State;
        switch (main_state) {
            case initialmenu:
                System.out.println("\nWaiting for a command => Use ListCommands to see commands.");

                State cmd = receive_command();
                if (cmd != State.none)
                {
                    main_state = cmd;
                    break;
                }
                System.out.print("\nCommand not found. List of commands:");

                main_state = State.listcommands;
                break;

            case listcommands:
                allStates = EnumSet.allOf(State.class).stream()
                        .filter(status -> status != State.none)
                        .collect(Collectors.toCollection(() -> EnumSet.noneOf(State.class)));

                System.out.println("\n" + allStates);

                main_state = State.initialmenu;
                break;

            case userregister:
                Person _persona = new Person();

                try {
                    System.out.println("\nEnter the registrant's name");
                    _persona.asName = receive_message();

                    System.out.println("\nEnter the registrant's CPF");
                    _persona.parserCPF(receive_message());

                    System.out.println("\nEnter the registrant's Date Age, eg yyyy-MM-dd");
                    _persona.parserDateAge(receive_message());

                    Data_Read_Write.asPersonaList.add(_persona);

                    System.out.println("\nUser registered successfully.");

                    main_state = State.initialmenu;

                } catch (Exception ex)
                {
                    System.out.println(ex.getMessage() + ". Returning to the home menu.");

                    main_state = State.initialmenu;
                }
                break;

            case searchuserbyname:
                if (!Data_Read_Write.asPersonaList.isEmpty())
                {
                    ArrayList<String> _results = new ArrayList<String>();

                    System.out.println("\nEnter the registrant's name");
                    String _search_tag = receive_message();

                    for (Person _current_person: Data_Read_Write.asPersonaList)
                    {
                        if (_current_person.asName.contains(_search_tag))
                        {
                            String my_event_name;

                            if (_current_person.asRegisteredEvent != null)
                            {
                                my_event_name = _current_person.asRegisteredEvent.asName;
                            }
                            else {
                                my_event_name = "None";
                            }
                            _results.add(_current_person.asName + " - CPF: " + Arrays.toString(_current_person.asCPF) + " - Date Age: " + _current_person.asDateAge + " - Registered Event: " + my_event_name);
                        }
                    }

                    if (_results.isEmpty())
                    {
                        System.out.println("\nNo matches found.");
                    }
                    else{
                        for (String result : _results)
                        {
                            System.out.println(result);
                        }
                    }

                    main_state = State.initialmenu;
                    break;
                }
                System.out.println("\nNo registered users.");

                main_state = State.initialmenu;
                break;

            case eventregister:
                EventUnique _new_event = new EventUnique();

                try {
                    System.out.println("\nEnter the event's name");
                    _new_event.asName = receive_message();

                    System.out.println("\nEnter the event's description");
                    _new_event.asDescription = receive_message();

                    System.out.println("\nEnter the event's address");
                    _new_event.asAddress = receive_message();

                    System.out.println("\nEnter the event's Category ID, Available options: " + Arrays.toString(EventUnique.available_categories) + " - IDs: 0 to " + (EventUnique.available_categories.length - 1));
                    _new_event.parserCategory(receive_number());

                    System.out.println("\nEnter the event's Date, eg yyyy-MM-dd HH:mm");
                    _new_event.parserDateHour(receive_message());

                    Data_Read_Write.asEventList.add(_new_event);

                    System.out.println("\nEvent registered successfully.");

                    main_state = State.initialmenu;

                } catch (Exception ex)
                {
                    System.out.println(ex.getMessage() + ". Returning to the home menu.");

                    main_state = State.initialmenu;
                }
                break;

            case listeventsbycategory:
                if (!Data_Read_Write.asEventList.isEmpty())
                {
                    ArrayList<String> __results = new ArrayList<String>();

                    System.out.println("\nEnter the event's category");
                    String __search_tag = receive_message();

                    LocalDateTime _current_dateTime = LocalDateTime.now();

                    for (EventUnique _current_event: Data_Read_Write.asEventList)
                    {
                        if (_current_event.asCategory.contains(__search_tag))
                        {
                            StringBuilder people_names = new StringBuilder();

                            for (String participant_key: _current_event.participants_by_CPF.keySet())
                            {
                                people_names.append(_current_event.participants_by_CPF.get(participant_key).asName);
                            }

                            String event_occurrence = getString(_current_event, _current_dateTime);
                            __results.add("\n-------\nName: " + _current_event.asName + "\nDescription: " + _current_event.asDescription + "\nDate Time: " + _current_event.asDateTime.toString().replace("T", "") + "\nAddress: " + _current_event.asAddress + "\nCategory: " + _current_event.asCategory + "\nParticipants: " + people_names + "\nEvent occurrence: " + event_occurrence + "\n-------");
                        }
                    }
                    if (!__results.isEmpty())
                    {
                        for (String result: __results)
                        {
                            System.out.println(result);
                        }
                    }
                    else{
                        System.out.println("\nNo matches found.");
                    }

                    main_state = State.initialmenu;
                    break;
                }
                System.out.println("\nNo registered events.");

                main_state = State.initialmenu;
                break;

            case listeventsbytag:
                System.out.println("\nFunction not yet implemented, use: listeventsbycategory");

                main_state = State.initialmenu;
                break;

            case attach_user_to_event:
                EventUnique _target_event = null;
                Person _target_person = null;
                
                String _final_user_CPF = "";
                try{
                    System.out.println("\nEnter the exact name of the event");
                    String _message = receive_message();

                    for (EventUnique _current_event: Data_Read_Write.asEventList)
                    {
                        if (Objects.equals(_message, _current_event.asName))
                        {
                            _target_event = _current_event;
                            break;
                        }
                    }

                    if (_target_event == null){
                        throw new NullPointerException("Event not found.");
                    }

                }catch (Exception es){
                    System.out.println(es.getMessage());

                    main_state = State.initialmenu;
                    break;
                }
                try {
                    System.out.println("\nEnter the persona's CPF");
                    String _message = receive_message();

                    for (Person _current_person: Data_Read_Write.asPersonaList)
                    {
                        _final_user_CPF = _current_person.returnCPF();
                        if (Objects.equals(_message, _final_user_CPF))
                        {
                            _target_person = _current_person;
                            break;
                        }
                    }

                    if (_target_person == null){
                        throw new NullPointerException("Persona not found.");
                    }

                }catch (Exception es){
                    System.out.println(es.getMessage());

                    main_state = State.initialmenu;
                    break;
                }
                _target_event.participants_by_CPF.put(_final_user_CPF, _target_person);
                _target_person.asRegisteredEvent = _target_event;
                
                System.out.println("\n" + _target_person.asName + " registered at " + _target_event.asName);

                main_state = State.initialmenu;
                break;

            case exit:
                System.exit(0);
                break;
        }
        return main_state;
    }

    private static String getString(EventUnique _current_event, LocalDateTime _current_dateTime) {
        String event_occurrence;

        if (_current_event.asDateTime.isAfter(_current_dateTime)){
            event_occurrence = "The event has not happened yet.";
        }
        else if (_current_event.asDateTime.isBefore(_current_dateTime) && _current_event.asDateTime.toLocalDate().isEqual(_current_dateTime.toLocalDate()))
        {
            event_occurrence = "The event is happening now.";
        }
        else{
            event_occurrence = "The event has already occurred.";
        }
        return event_occurrence;
    }


    State receive_command()
    {
        try{
            return State.valueOf(consoleReader.nextLine().toLowerCase().strip());

        }catch (IllegalArgumentException ex){

            return State.none;
        }
    }

    String receive_message()
    {
        return consoleReader.nextLine().strip();
    }

    Integer receive_number()
    {
        try{
            Integer _value = consoleReader.nextInt();
            consoleReader.nextLine();

            return _value;
        }
        catch (Exception _){
            throw new NumberFormatException("NaN");
        }
    }
}

