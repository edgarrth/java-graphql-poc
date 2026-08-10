package com.acme.payments.infrastructure.config;

import graphql.language.FloatValue;
import graphql.language.IntValue;
import graphql.language.StringValue;
import graphql.schema.Coercing;
import graphql.schema.CoercingParseLiteralException;
import graphql.schema.CoercingParseValueException;
import graphql.schema.CoercingSerializeException;
import graphql.schema.GraphQLScalarType;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.graphql.execution.RuntimeWiringConfigurer;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.time.format.DateTimeParseException;

@Configuration(proxyBeanMethods = false)
public class GraphQlScalarConfiguration {

    private static final GraphQLScalarType BIG_DECIMAL = GraphQLScalarType.newScalar()
            .name("BigDecimal")
            .description("Arbitrary precision decimal value")
            .coercing(new Coercing<BigDecimal, BigDecimal>() {
                @Override
                public BigDecimal serialize(Object input) throws CoercingSerializeException {
                    return toBigDecimal(input, CoercingSerializeException::new);
                }

                @Override
                public BigDecimal parseValue(Object input) throws CoercingParseValueException {
                    return toBigDecimal(input, CoercingParseValueException::new);
                }

                @Override
                public BigDecimal parseLiteral(Object input) throws CoercingParseLiteralException {
                    if (input instanceof FloatValue value) return value.getValue();
                    if (input instanceof IntValue value) return new BigDecimal(value.getValue());
                    if (input instanceof StringValue value) {
                        return toBigDecimal(value.getValue(), CoercingParseLiteralException::new);
                    }
                    throw new CoercingParseLiteralException("Expected a decimal value");
                }
            })
            .build();

    private static final GraphQLScalarType DATE_TIME = GraphQLScalarType.newScalar()
            .name("DateTime")
            .description("RFC-3339 date-time with UTC offset")
            .coercing(new Coercing<OffsetDateTime, String>() {
                @Override
                public String serialize(Object input) throws CoercingSerializeException {
                    if (input instanceof OffsetDateTime value) return value.toString();
                    if (input instanceof String value) {
                        try {
                            return OffsetDateTime.parse(value).toString();
                        } catch (DateTimeParseException ex) {
                            throw new CoercingSerializeException("Invalid DateTime value");
                        }
                    }
                    throw new CoercingSerializeException("Expected OffsetDateTime or RFC-3339 String");
                }

                @Override
                public OffsetDateTime parseValue(Object input) throws CoercingParseValueException {
                    if (!(input instanceof String value)) {
                        throw new CoercingParseValueException("Expected RFC-3339 String");
                    }
                    try {
                        return OffsetDateTime.parse(value);
                    } catch (DateTimeParseException ex) {
                        throw new CoercingParseValueException("Invalid DateTime value");
                    }
                }

                @Override
                public OffsetDateTime parseLiteral(Object input) throws CoercingParseLiteralException {
                    if (!(input instanceof StringValue value)) {
                        throw new CoercingParseLiteralException("Expected RFC-3339 String");
                    }
                    try {
                        return OffsetDateTime.parse(value.getValue());
                    } catch (DateTimeParseException ex) {
                        throw new CoercingParseLiteralException("Invalid DateTime value");
                    }
                }
            })
            .build();

    @Bean
    RuntimeWiringConfigurer customScalars() {
        return builder -> builder.scalar(BIG_DECIMAL).scalar(DATE_TIME);
    }

    private static BigDecimal toBigDecimal(Object input,
                                            java.util.function.Function<String, RuntimeException> exceptionFactory) {
        if (input instanceof BigDecimal value) return value;
        if (input instanceof Number || input instanceof String) {
            try {
                return new BigDecimal(input.toString());
            } catch (NumberFormatException ex) {
                throw exceptionFactory.apply("Invalid BigDecimal value");
            }
        }
        throw exceptionFactory.apply("Expected numeric or decimal String value");
    }
}
