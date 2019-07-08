package com.ys.worldgdp;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Setter
@Getter
public class CountryLanguage {
	private Country country;
	private String language;
	private String isOfficial;
	private Double percentage;
}
