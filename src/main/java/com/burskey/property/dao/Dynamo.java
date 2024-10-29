package com.burskey.property.dao;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.document.*;
import com.amazonaws.services.dynamodbv2.document.spec.GetItemSpec;
import com.amazonaws.services.dynamodbv2.model.*;
import com.burskey.property.domain.Property;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Dynamo {


    AmazonDynamoDB client = AmazonDynamoDBClientBuilder.standard().build();
    DynamoDB dynamoDB = new DynamoDB(client);

    private String tableName;

    public Dynamo(String tableName) {
        this.tableName = tableName;
    }

    public List<Property> find(String name, String category) {
        List<Property> aList = new ArrayList<>();
        ScanRequest scanRequest = new ScanRequest().withTableName(this.tableName);

        ItemCollection<ScanOutcome>  outcomes= this.dynamoDB.getTable(this.tableName).scan();
        if (outcomes != null) {
            outcomes.forEach(outcome -> {
                String candidateName = outcome.get("name").toString();
                String candidateCategory = outcome.get("category").toString();
                if (name.equalsIgnoreCase(candidateName) && category.equalsIgnoreCase(candidateCategory)) {
                    aList.add(this.find(outcome.get("id").toString()));
                }

            });
        }

        return aList;
    }

    public List<Property> findByCategory( String category) {
        List<Property> aList = new ArrayList<>();
        ScanRequest scanRequest = new ScanRequest().withTableName(this.tableName);

        ItemCollection<ScanOutcome>  outcomes= this.dynamoDB.getTable(this.tableName).scan();
        if (outcomes != null) {
            outcomes.forEach(outcome -> {
                String candidateCategory = outcome.get("category").toString();
                if ( category.equalsIgnoreCase(candidateCategory)) {
                    aList.add(this.find(outcome.get("id").toString()));
                }

            });
        }

        return aList;
    }


    public Property save(Property aProperty) {
        if (aProperty != null){
            if (aProperty.getId() == null){
                aProperty.setId(java.util.UUID.randomUUID().toString());

                final Item item = new Item()
                        .withPrimaryKey("id", aProperty.getId())
                        .withString("name", aProperty.getName())
                        .withString("category", aProperty.getCategory())
                        .withString("description", aProperty.getDescription())
                        .withString("value", aProperty.getValue());

                final Table table = this.dynamoDB.getTable(this.tableName);
                table.putItem(item);
            }else{
                UpdateItemRequest updateItemRequest = new UpdateItemRequest();
                updateItemRequest.setTableName(this.tableName);
                updateItemRequest.addKeyEntry("id", new AttributeValue().withS(aProperty.getId()));
                updateItemRequest.withUpdateExpression("set description = '"+aProperty.getDescription()+"'");
                updateItemRequest.withUpdateExpression("set value = '"+aProperty.getValue()+"'");

                try{
                    UpdateItemResult result = this.client.updateItem(updateItemRequest);
                } catch (Exception e) {
                    System.err.println("Error updating item: " + e.getMessage());
                }


            }

        }



        return aProperty;
    }



    public Property find(String id) {

        GetItemSpec spec = new GetItemSpec();
        spec.withPrimaryKey("id", id);
        final Table table = this.dynamoDB.getTable(this.tableName);

        Item item = table.getItem(spec);
        Property aProperty = null;
        if (item != null) {
            Map<String, Object> aMap = item.asMap();
            aProperty = new Property();
            aProperty.setId(item.get("id").toString());
            aProperty.setName(item.get("name").toString());
            aProperty.setCategory(item.get("category").toString());
            aProperty.setDescription(item.get("description").toString());
            aProperty.setValue(item.get("value").toString());
        }


        return aProperty;
    }
}
