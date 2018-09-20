package com.joojava.service.filter;

import org.springframework.format.annotation.DateTimeFormat;

import java.time.Instant;

/**
 * @author leegive
 */
public class InstantFilter extends RangeFilter<Instant> {

    private static final long serialVersionUID = 1L;

    @Override
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    public InstantFilter setEquals(Instant equals) {
        super.setEquals(equals);
        return this;
    }

    @Override
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    public InstantFilter setGreaterThan(Instant equals) {
        super.setGreaterThan(equals);
        return this;
    }

    @Override
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    public InstantFilter setGreaterOrEqualThan(Instant equals) {
        super.setGreaterOrEqualThan(equals);
        return this;
    }

    @Override
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    public InstantFilter setLessThan(Instant equals) {
        super.setLessThan(equals);
        return this;
    }

    @Override
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    public InstantFilter setLessOrEqualThan(Instant equals) {
        super.setLessOrEqualThan(equals);
        return this;
    }

}
