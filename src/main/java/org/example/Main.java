package org.example;

import java.util.*;

import org.example.data.cached.data_cache.Data_Read_Write;
import org.example.persona.asPerson.Person;


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
    private final Scanner consoleReader = new Scanner(System.in);

    State readCommand(State c_State)
    {
        Set<State> allStates;
        
        State main_state = c_State;
        switch (main_state)
        {
            case initialmenu:
                System.out.println("\nWaiting for a command => Use ListCommands to see commands.");

                State cmd = receive_command();
                if (cmd != State.none){
                    main_state = cmd;
                    break;
                }

                allStates = EnumSet.allOf( State.class );

                System.out.println("\nCommand not found. List of commands: " + allStates);
                break;

            case listcommands:
                allStates = EnumSet.allOf( State.class );
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

                    System.out.println("\nEnter the registrant's Date Age, eg 10/02/2000");
                    _persona.parserDateAge(receive_message());

                    Data_Read_Write.asPersonaList.add(_persona);

                    System.out.println("\nUser registered successfully.");

                    main_state = State.initialmenu;

                }catch (Exception ex){
                    System.out.println(ex.getMessage());

                    System.out.println("\nReturning to the home menu.");
                    main_state = State.initialmenu;
                }

                break;

            case exit:
                System.exit(0);
                break;
        }
        return main_state;
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
}

