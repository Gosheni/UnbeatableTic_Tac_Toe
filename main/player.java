package main;

import main.piece_types.pieces;

public class player {
    String name;
    pieces type;

    public player(String n, pieces p) {
        name = n;
        type = p;
    }

    public pieces get_type() {
        return type;
    }

    public void set_type(pieces p) {
        type = p;
    }

    public String get_name() {
        return name;
    }

    public void set_name(String n) {
        name = n;
    }
}
