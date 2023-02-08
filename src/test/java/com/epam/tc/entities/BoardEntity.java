package com.epam.tc.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Builder;

@JsonIgnoreProperties (ignoreUnknown = true)
@Builder
public record BoardEntity(String id, String name, String desc) {

}

