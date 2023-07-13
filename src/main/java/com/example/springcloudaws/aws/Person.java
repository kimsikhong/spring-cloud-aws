package com.example.springcloudaws.aws;

import lombok.Getter;

@Getter
public class Person {
    private final String name;
    private final String lastName;

    public Person(String name, String lastName) {
        this.name = name;
        this.lastName = lastName;
    }

    @Override
    public String toString() {
        return "Person{" +
                "name='" + name + '\'' +
                ", lastName='" + lastName + '\'' +
                '}';
    }
}
