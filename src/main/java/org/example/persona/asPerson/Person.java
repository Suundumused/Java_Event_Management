package org.example.persona.asPerson;

import org.example.persona.person_interface.I_Person;

import java.time.LocalDate;

import org.example.event.asEvent.EventUnique;

public class Person implements I_Person {
    public String asName;
    public EventUnique asRegisteredEvent;

    public LocalDate asDateAge = null;

    public Integer[] asCPF = {0,0,0,0};


    @Override
    public void parserCPF(String _value) {
        String[] cpf_piece_a = getStrings(_value);

        int cpf_piece_b =  getNumber(cpf_piece_a[cpf_piece_a.length -1].split("-")[1]);
        cpf_piece_a[cpf_piece_a.length -1] = cpf_piece_a[cpf_piece_a.length -1].split("-")[0];

        for (int i =0; i < cpf_piece_a.length; i++){
            asCPF[i] = getNumber(cpf_piece_a[i]);
        }
        asCPF[3] = cpf_piece_b;
    }

    private static String[] getStrings(String _value) {
        String[] cpf_piece_a = _value.split("\\.");

        if (cpf_piece_a.length != 3)
        {
            throw new IllegalArgumentException("The provided CPF does not contain two \"000.000.000\" division points, required format: \"000.000.000-00\"");
        }

        if (!cpf_piece_a[cpf_piece_a.length -1].contains("-"))
        {
            throw new IllegalArgumentException("The CPF provided does not contain the \"..-00\" hyphen check digit step, required format: \"000.000.000-00\"");
        }
        return cpf_piece_a;
    }

    private static Integer getNumber(String _value) {
        try {
            return Integer.parseInt(_value);

        } catch (Exception _)
        {
            throw new IllegalArgumentException("Check that all digits between the delimiters are numbers.");
        }
    }

    @Override
    public String returnCPF () {
        return asCPF[0] + "." +
                asCPF[1] + "." +
                asCPF[2] + "-" +
                asCPF[3];
    }

    @Override
    public void parserDateAge (String _value){
        try{
            asDateAge = LocalDate.parse(_value);

        }catch (Exception _)
        {
            throw new IllegalArgumentException("Invalid date format. Must be in yyyy-MM-dd format.");
        }

    }

    @Override
    public LocalDate returnDateAge () {
        return null;
    }
}
