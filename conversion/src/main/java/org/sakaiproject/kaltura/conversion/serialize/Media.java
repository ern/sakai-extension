package org.sakaiproject.kaltura.conversion.serialize;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@JacksonXmlRootElement(localName = "span")
@JsonPropertyOrder({"cls", "img"})
@NoArgsConstructor
public class Media {

    @JsonIgnore
    public static final String SAMPLE =
            "<span class=\"kaltura-lti-media\">" +
                    "<img alt=\"\" height=\"285\" " +
                    "kaltura-lti-url=\"https://2104601.kaf.kaltura.com/browseandembed/index/media/entryid/1_zd50sf3m/showDescription/false/showTitle/false/showTags/false/showDuration/false/showOwner/false/showUploadDate/false/playerSize/400x285/playerSkin/34223581/\" " +
                    "src=\"https://cfvod.kaltura.com/p/2104601/sp/210460100/thumbnail/entry_id/1_zd50sf3m/version/100001\" " +
                    "title=\"IFrame\" width=\"400\"/>" +
                    "</span>";

    @JacksonXmlProperty(isAttribute = true, localName = "class")
    private String cls;
    private Img img;

    @Data
    @JsonPropertyOrder({"alt", "height", "url", "src", "title", "width"})
    @NoArgsConstructor
    public class Img {
        @JacksonXmlProperty(isAttribute = true)
        private String alt;
        @JacksonXmlProperty(isAttribute = true)
        private String height;
        @JacksonXmlProperty(isAttribute = true)
        private String width;
        @JacksonXmlProperty(isAttribute = true, localName = "kaltura-lti-url")
        private String url;
        @JacksonXmlProperty(isAttribute = true)
        private String src;
        @JacksonXmlProperty(isAttribute = true)
        private String title;
    }
}



