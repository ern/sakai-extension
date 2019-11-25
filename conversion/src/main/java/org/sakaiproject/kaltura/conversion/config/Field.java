package org.sakaiproject.kaltura.conversion.config;

import java.util.List;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
public class Field {
    private String table;
    private String name;
    private String identifier;
    private String contextSql;
    private boolean base64Encoded;
    @EqualsAndHashCode.Exclude
    private String criteria;
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private List<Object> ids;
}