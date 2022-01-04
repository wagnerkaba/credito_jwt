package com.wagner.tqi.person.utils;

import com.wagner.tqi.person.dto.request.PhoneDTO;
import com.wagner.tqi.person.entity.Phone;
import com.wagner.tqi.person.enums.PhoneType;

public class PhoneUtils {
    public static final String PHONE_NUMBER = "1199999-9999";
    public static final PhoneType PHONE_TYPE = PhoneType.COMMERCIAL;
    public static final long PHONE_ID = 1L;

    public static PhoneDTO createFakeDTO(){
        return PhoneDTO.builder()
                .id(PHONE_ID)
                .number(PHONE_NUMBER)
                .type(PHONE_TYPE)
                .build();
    }

    public static Phone createFakeEntity(){
        return Phone.builder()
                .id(PHONE_ID)
                .number(PHONE_NUMBER)
                .type(PHONE_TYPE)
                .build();
    }
}
