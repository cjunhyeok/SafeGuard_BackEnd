package com.opensw.safeguard.domain.weather;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class WeatherResponseDTO {
    private String message;
    private Weather weather;

}
