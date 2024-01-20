package com.example.plannerapi.mappers;

public interface Mapper<A,B> {
    B mapTo(A a);
    A mapFrom(B a);
}
