package com.epam.tc.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Builder;
import lombok.EqualsAndHashCode;

@Builder
@EqualsAndHashCode
@JsonIgnoreProperties(ignoreUnknown = true)
public class MemberEntity {

    String id;
    String idBoard;
    String name;
}
