package com.joojava.service.filter;

import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.List;

/**
 * @author leegive
 */
@NoArgsConstructor
public class LocalDateFilter extends RangeFilter<LocalDate> {

    private static final long serialVersionUID = 1L;

    @Override
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    public LocalDateFilter setEquals(LocalDate equals) {
        super.setEquals(equals);
        return this;
    }

    @Override
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    public LocalDateFilter setGreaterThan(LocalDate equals) {
        super.setGreaterThan(equals);
        return this;
    }

    @Override
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    public LocalDateFilter setGreaterOrEqualThan(LocalDate equals) {
        super.setGreaterOrEqualThan(equals);
        return this;
    }

    @Override
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    public LocalDateFilter setLessThan(LocalDate equals) {
        super.setLessThan(equals);
        return this;
    }

    @Override
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    public LocalDateFilter setLessOrEqualThan(LocalDate equals) {
        super.setLessOrEqualThan(equals);
        return this;
    }

    @Override
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    public LocalDateFilter setIn(List<LocalDate> in) {
        super.setIn(in);
        return this;
    }

}
