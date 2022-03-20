package com.internship.AuctionApp.Exceptions;

public class EntityNotFoundException extends Exception {
    public EntityNotFoundException(){
        super("Entity does not exist.");
    }

    public EntityNotFoundException(final String entity) {
        super(entity + " does not exist.");
    }
}
