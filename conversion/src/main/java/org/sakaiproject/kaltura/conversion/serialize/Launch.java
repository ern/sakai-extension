package org.sakaiproject.kaltura.conversion.serialize;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlText;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@JacksonXmlRootElement(localName = "a")
@JsonPropertyOrder({"cls", "href", "rel", "target"})
@NoArgsConstructor
public class Launch {

    @JsonIgnore
    public static final String SAMPLE =
            "<a class=\"lti-launch\" href=\"http://localhost:8080/access/basiclti/site/EQLD_585N_0384/content:3\" rel=\"noopener\" target=\"_blank\">Media</a>";

    public Launch(String href, String value) {
        this.cls = "lti-launch";
        this.rel = "noopener";
        this.target = "_blank";
        this.href = href;
        this.value = value;
    }


    @JacksonXmlProperty(isAttribute = true, localName = "class")
    private String cls;
    @JacksonXmlProperty(isAttribute = true)
    private String href;
    @JacksonXmlProperty(isAttribute = true)
    private String rel;
    @JacksonXmlProperty(isAttribute = true)
    private String target;
    @JacksonXmlText
    private String value;
}
