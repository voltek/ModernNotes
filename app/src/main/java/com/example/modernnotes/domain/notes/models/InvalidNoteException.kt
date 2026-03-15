package com.example.modernnotes.domain.notes.models

class InvalidNoteException(val errorType: ErrorType) : Exception() {

    sealed class ErrorType {
        object EmptyTitle : ErrorType()
        object EmptyContent : ErrorType()
    }
}