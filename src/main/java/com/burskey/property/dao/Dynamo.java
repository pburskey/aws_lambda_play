package com.burskey.property.dao;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.document.Table;
import com.amazonaws.services.dynamodbv2.document.spec.GetItemSpec;
import com.burskey.property.domain.Property;

import java.util.Map;

public class Dynamo {


    AmazonDynamoDB client = AmazonDynamoDBClientBuilder.standard().build();
    DynamoDB dynamoDB = new DynamoDB(client);

    private String tableName;

    public Dynamo(String tableName) {
        this.tableName = tableName;
    }
//
//    public List<Property> find() {
//        List<Property> aList = new ArrayList<>();
//        ScanRequest scanRequest = new ScanRequest().withTableName(this.tableName);
//
//        ScanResult result = this.dynamoDB.getTable(this.tableName).scan();
//        if (result != null && result.getCount() > 0) {
//            result.getItems().stream().map(item -> new Property(item.get("id").getS(), item.get("name").getS(), item.get("description").getS(), item.get("value").getS(), item.get("category").getS()
//            )).collect(Collectors.toList());
//        }
//        return aList;
//    }

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
