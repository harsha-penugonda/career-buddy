package com.careerbuddy.utils;

import lombok.Getter;
import lombok.Setter;
import org.springframework.core.env.Environment;

public enum StaticContext {
    INSTANCE;

    @Getter
    @Setter
    public static Environment environment;
}
