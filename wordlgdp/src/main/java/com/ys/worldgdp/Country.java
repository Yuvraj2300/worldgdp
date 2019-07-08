package com.ys.worldgdp;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Setter
@Getter
public class Country {
	@NotNull
	@Size(max = 3, min = 3)
	private String code;
	@NotNull
	@Size(max = 3, min = 3)
	private String name;
	@NotNull
	private String continent;
	@NotNull
	@Size(max = 26)
	private String region;
	@NotNull
	private String surfaceArea;
	private String indepYear;
	@NotNull
	private Long population;
	private Double lifeExpectancy;
	@NotNull
	private Double gnp;
	private String localname;
	@NotNull
	private String governmentForm;
	private String headOfState;
	private City capital;
	@NotNull
	private String code2;
}