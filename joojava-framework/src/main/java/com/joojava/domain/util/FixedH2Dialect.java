package com.joojava.domain.util;

import org.hibernate.dialect.H2Dialect;

import java.sql.Types;

/**
 * @author leegive
 */
public class FixedH2Dialect extends H2Dialect {

    public FixedH2Dialect() {
        super();
        registerColumnType(Types.FLOAT, "real");
    }

}
