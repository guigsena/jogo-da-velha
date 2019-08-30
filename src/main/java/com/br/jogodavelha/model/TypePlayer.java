package com.br.jogodavelha.model;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public enum TypePlayer {
	X,
	O;
	
	private static final List<TypePlayer> VALUES = Collections.unmodifiableList(Arrays.asList(values()));
	private static final int SIZE = VALUES.size();
	private static final Random RANDOM = new Random();

	public static TypePlayer randomTypePlayer()  {
		return VALUES.get(RANDOM.nextInt(SIZE));
	}
}
