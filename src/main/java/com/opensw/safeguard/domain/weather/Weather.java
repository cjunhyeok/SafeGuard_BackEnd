package com.opensw.safeguard.domain.weather;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class Weather {
    private double maxTemp;
    private double minTemp;
    private double max_Probability_Of_Precipitation;
}
