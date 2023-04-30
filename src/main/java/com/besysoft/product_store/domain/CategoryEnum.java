package com.besysoft.product_store.domain;

public enum CategoryEnum {
    HOGAR("Hogar"), COMPUTACION("Computacion"), DEPORTES("Deportes"), LIBROS("Libros");

    private final String name;

    public String getName() {
        return name;
    }

    CategoryEnum(String name) {
        this.name = name;
    }
}
