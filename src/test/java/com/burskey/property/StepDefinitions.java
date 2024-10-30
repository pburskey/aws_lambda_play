package com.burskey.property;

import com.burskey.property.api.PropertyClient;
import com.burskey.property.domain.Property;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class StepDefinitions {

    String region = null;
    String s3Bucket = null;
    String stackName = null;
    String stage = null;
    String saveURI = null;
    String getByIDURI = null;
    Property property = null;
    private ResponseEntity response;
    private PropertyClient client;
    private String getByCategoryAndNameURI;

    @Given("an AWS Region: {string}")
    public void an_aws_region(String string) {
        this.region = string;
    }

    @Given("an AWS S3 Bucket: {string}")
    public void an_aws_s3_bucket(String string) {
        this.s3Bucket = string;
    }

    @Given("an AWS Stack Name: {string}")
    public void an_aws_stack_name(String string) {
        this.stackName = string;
    }

    @Given("an AWS Stage: {string}")
    public void an_aws_stage(String string) {
        this.stage = string;
    }


    @Given("a property save uri: {string}")
    public void a_property_save_uri(String string) {
        this.saveURI = string;
    }
    @Given("a property get by id uri: {string}")
    public void a_property_get_by_id_uri(String string) {
     this.getByIDURI = string;
    }



    @Given("that I want to save a property")
    public void that_i_want_to_save_a_property(io.cucumber.datatable.DataTable dataTable) {

        if (dataTable != null && !dataTable.isEmpty()) {

            List<Map<String, String>> rows = dataTable.asMaps(String.class, String.class);
            Property aProperty = new Property();
            for (Map<String, String> columns : rows) {
                aProperty.setName(columns.get("Name"));

                aProperty.setValue(columns.get("Value"));

                aProperty.setDescription(columns.get("Description"));

                aProperty.setCategory(columns.get("Category"));


            }
            this.property = aProperty;


        }

    }

    @When("I ask the service to save the property")
    public void i_ask_the_service_to_save_the_property() {
        this.response = client.save(this.property);
    }

    @Then("the service responds with status code: {int}")
    public void the_service_responds_with_status_code(Integer int1) {
        assertNotNull(this.response);
        int status = this.response.getStatusCode().value();
        assertEquals(int1, Integer.valueOf(status));
    }

    @Then("the property has an id")
    public void the_property_has_an_id() {
        assertNotNull(this.property.getId());
    }



    @Given("a saved property")
    public void a_saved_property() {
        Property property = new Property();
        property.setValue("a value");
        property.setDescription("a description");
        property.setCategory(java.util.UUID.randomUUID().toString());
        property.setName(java.util.UUID.randomUUID().toString());
        this.property = property;
        this.i_ask_the_service_to_save_the_property();
    }
    @When("I ask the service to find the property by ID")
    public void i_ask_the_service_to_find_the_property_by_id() throws JsonProcessingException {

        this.response = client.findByID(this.property.getId());
        ObjectMapper mapper = new ObjectMapper();
        this.property = mapper.readValue((String)(this.response.getBody()), Property.class);
    }
    @Then("the property exists")
    public void the_property_exists() {
        assertNotNull(this.property);
    }

    @When("I ask the service to find the property by category and name")
    public void i_ask_the_service_to_find_the_property_by_category_and_name() throws JsonProcessingException {
        this.response = client.findByCategoryAndName(this.property.getCategory(), this.property.getName());
        ObjectMapper mapper = new ObjectMapper();
        Object anObject =  mapper.readValue((String)(this.response.getBody()), List.class);
        if (anObject != null) {
            List aList = (List) anObject;
            for (Object o : aList) {
                Map aMap = (Map) o;
                this.property = mapper.convertValue(aMap, Property.class);
            }
        }
    }

    @Given("an AWS Client")
    public void an_aws_client() {
        PropertyClient client = PropertyClient.Builder()
                .withSave(this.saveURI)
                .withGetByID(this.getByIDURI)
                .withGetByCategoryAndName(this.getByCategoryAndNameURI);
        this.client = client;
    }

    @Given("a property get by category and name uri: {string}")
    public void a_property_get_by_category_and_name_uri(String string) {
        this.getByCategoryAndNameURI = string;
    }


}
