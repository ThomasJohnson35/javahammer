package com.example.javahammer.data;

import java.io.Serializable;
import java.util.Set;

public class Alert implements Serializable {

    String name;
    String content;
    Timing timing;

    public Alert(String name, String content, Timing timing) {
        this.name = name;
        this.content = content;
        this.timing = timing;
    }
}
