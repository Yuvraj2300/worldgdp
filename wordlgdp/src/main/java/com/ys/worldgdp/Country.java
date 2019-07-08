package com.ys.worldgdp;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Setter
@Getter
public class Country {
	private String code;
	private String name;
	private String continent;
	private String region;
	private String surfaceArea;
	private String indepYear;
	private Long population;
	private Double lifeExpectancy;
	private Double gnp;
	private String localname;
	private String governmentForm;
	private String headOfState;
	private City capital;
	private String code2;
}
