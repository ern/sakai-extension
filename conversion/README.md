# Kaltura Media Conversion to LTI tool

This tool can be used by those who are switching from using the Kaltura sakai-extension plugin
to using LTI with Kaltura. This tool can convert existing Kaltura media embeds to the LTI Content Item links.

## Description

This tool is built as a spring boot command line runner and can be run in a few ways:

- maven: `mvn spring-boot-run`
- standalone jar: `java -jar sakai-media-conversion-1.0.jar`

## Configuration

To configure the tool please see the file `application.properties`

This file contains important configuration that will need to be set before running the tool.

### Sakai Properties

The following properties must be set appropriately.

- sakai.lti.toolId=2

    First you must configure Kaltura LTI and ensure that it is working correctly before proceeding.
Once you have a functioning LTI integration you will need to obtain the `ID` of the Kaltura LTI tool
from the Sakai database. Look for the id in the table `lti_tool`. Once you found the id it must be set
via `sakai.lti.toolId` this is the tool id where lti links will be created for. In this example the `id` was 2.

- sakai.serverUrl=http://localhost:8080

    Next you will need to get the `serverUrl` property from your Sakai installation, here the example is using
    `http://localhost:8080` make sure you have the correct scheme for either http or https.

### Database

You will need to configure the following properties to point the tool at your sakai database.
It is recommended that you first try the tool against a test database to ensure that everything
is properly configured before you run it against a production database.  
- spring.datasource.driver-class-name=org.mariadb.jdbc.Driver
- spring.datasource.username=sakai
- spring.datasource.password=sakai
- spring.datasource.url=jdbc:mysql://127.0.0.1:3306/sakai_12x

### Fields

The tool must scan the Sakai database looking for Kaltura media embed codes to convert therefore it
was a requirement to easily configure what tables and fields are scanned. The following property tells
the tool to scan the field INSTRUCTIONS in the ASN_ASSIGNMENT table. Any matching row that matches the
criteria property will be selected for processing. 
 
- conversion.fields[0].table=ASN_ASSIGNMENT
- conversion.fields[0].identifier=ASSIGNMENT_ID
- conversion.fields[0].contextSql=select CONTEXT_ID from ASN_ASSIGNMENT where ASSIGNMENT_ID = :identifier:
- conversion.fields[0].name=INSTRUCTIONS
- conversion.fields[0].criteria=like %kaltura-lti-media%
- conversion.fields[0].base64Encoded=false

Additional fields can be added to the configuration by simply incrementing the array like so `conversion.fields[1]` 

### Logging

You can control the level of logging that you wish to see by uncommenting the following configuration option

- logging.level.org.sakaiproject.kaltura.conversion=DEBUG