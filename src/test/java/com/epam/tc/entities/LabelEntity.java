package com.epam.tc.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Builder;

@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public record LabelEntity(String id, String color, String name) {
}
