package com.radkevich.usersservice.serializers;

import org.apache.commons.lang3.ObjectUtils;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.usertype.UserType;
import org.postgresql.geometric.PGpoint;

import java.io.Serializable;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;

public class PGPointType implements UserType<PGpoint> {

    @Override
    public int getSqlType() {
        return Types.VARCHAR;
    }

    @SuppressWarnings("rawtypes")
    @Override
    public Class<PGpoint> returnedClass() {
        return PGpoint.class;
    }

    @Override
    public boolean equals(PGpoint x, PGpoint y) {
        return ObjectUtils.equals(x, y);
    }

    @Override
    public int hashCode(PGpoint x) {
        return x.hashCode();
    }


    @Override
    public PGpoint nullSafeGet(ResultSet rs, int position, SharedSessionContractImplementor session, Object owner) throws SQLException {
            if (rs.wasNull() || rs.getObject(position) == null) {
                return null;
            } else {
                return new PGpoint(rs.getObject(position).toString());
            }
    }


    @Override
    public void nullSafeSet(PreparedStatement statement, PGpoint value, int index, SharedSessionContractImplementor sharedSessionContractImplementor) throws SQLException {
        if (value == null) {
            statement.setNull(index, Types.OTHER);
        } else {
            statement.setObject(index, value, Types.OTHER);
        }
    }

    @Override
    public PGpoint deepCopy(PGpoint obj) {
        return obj;
    }

    @Override
    public boolean isMutable() {
        return Boolean.FALSE;
    }


    @Override
    public Serializable disassemble(PGpoint obj) {
        return obj;
    }

    @Override
    public PGpoint assemble(Serializable cached, Object owner) {
        return (PGpoint) cached;
    }



}