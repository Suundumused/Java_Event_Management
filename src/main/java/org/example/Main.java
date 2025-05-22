package org.example;

import java.util.*;


public class Main {
    public static void main(String[] args) {
        //TIP Press <shortcut actionId="ShowIntentionActions"/> with your caret at the highlighted text
        // to see how IntelliJ IDEA suggests fixing it.
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
    private final Scanner consoleReader = new Scanner(System.in);

    State readCommand(State c_State)
    {
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

                System.out.println("\nCommand not found.");
                break;

            case listcommands:
                Set<State> allStates = EnumSet.allOf( State.class );
                System.out.println("\n" + allStates);

                main_state = State.initialmenu;
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

