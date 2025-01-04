package org.example.skillboxhotels.mapper;

import org.mapstruct.Named;

public interface CommonMapper {
    @Named("normalizeEmail")
    default String normalizeEmail(String email) {
        return email.trim().toLowerCase();
    }

    @Named("trim")
    default String trim(String string) {
        return string.trim();
    }
}
