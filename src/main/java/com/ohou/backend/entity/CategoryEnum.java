package com.ohou.backend.entity;

public enum CategoryEnum {
    CHRISTMAS("크리스마스"),
    WINTER("겨울용품"),
    FURNITURE("가구"),
    FABRIC("패브릭"),
    PET("반려용품");

    private final String category;

    CategoryEnum(String category) {
        this.category = category;
    }

    public String getCategory(){
        return this.category;
    }
}
