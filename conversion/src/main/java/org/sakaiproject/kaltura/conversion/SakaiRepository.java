package org.sakaiproject.kaltura.conversion;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.sakaiproject.kaltura.conversion.config.Field;
import org.sakaiproject.kaltura.conversion.serialize.Media;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Repository
public class SakaiRepository {

    @Autowired private JdbcTemplate jdbcTemplate;

    @Value("${sakai.serverUrl}")
    private String serverUrl;

    @Value("${sakai.lti.toolId}")
    private Integer ltiToolId;

    private String ltiToolPageTitle;

    private SimpleJdbcInsert simpleJdbcInsert;

    @Autowired
    public SakaiRepository(DataSource dataSource) {
        simpleJdbcInsert = new SimpleJdbcInsert(dataSource).withTableName("lti_content").usingGeneratedKeyColumns("id");
    }

    boolean existsLTITool() {
        String sql = "select pagetitle from lti_tools where id = ?";
        ltiToolPageTitle = jdbcTemplate.queryForObject(sql, new Object[]{ltiToolId}, String.class);
        log.debug("Query: {}: {}", sql.replace("?", ltiToolId.toString()), ltiToolPageTitle);
        return !ltiToolPageTitle.isEmpty();
    }

    public String insertLtiToolLink(Media media) {
        String siteId = "EQLD_585N_0384";
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("tool_id", ltiToolId);
        parameters.put("SITE_ID", siteId);
        parameters.put("title", media.getImg().getTitle());
        parameters.put("pagetitle", ltiToolPageTitle);
        parameters.put("launch", media.getImg().getUrl());
        parameters.put("created_at", "NOW()");
        parameters.put("updated_at", "NOW()");

        Number id = simpleJdbcInsert.executeAndReturnKey(parameters);
        // http://localhost:8080/access/basiclti/site/EQLD_585N_0384/content:3
        String href = serverUrl + "/access/basiclti/site/" + siteId + "/content:" + id.intValue();
        log.debug("Insert new lti tool link {}", href);
        return href;
    }

    public void executeFieldSearchQuery(Field field) {
        String sql = FieldProcessor.getSearchQuery(field);
        List<Object> results = jdbcTemplate.queryForList(sql, Object.class);
        if (results == null) results = Collections.emptyList();
        log.debug("Query for {} yields {} matches", field, results.size());
        field.setIds(results);
    }

    public String executeFieldDataQuery(Field field, Object id) {
        String sql = FieldProcessor.getDataQuery(field, id);
        String result = jdbcTemplate.queryForObject(sql, String.class);
        if (result == null) result = "";
        return result;
    }

    public void executeSaveFieldData(Field field, Object id, String data) {
        String sql = FieldProcessor.getSaveDataQuery(field, id);
        int rows = jdbcTemplate.update(sql, data);
        if (rows == 1) {
            log.debug("Successfully updated {} in field {}", id, field);
        } else {
            log.warn("SQL returned unexpected {} result: {}", sql);
        }
    }
}
