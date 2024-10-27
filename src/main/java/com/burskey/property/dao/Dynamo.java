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
        final Item item = new Item()
                .withPrimaryKey("name", aProperty.getName())
                .withPrimaryKey("category", aProperty.getName())
                .withString("description", aProperty.getName())
                .withString("value", aProperty.getName());

        final Table table = this.dynamoDB.getTable(this.tableName);
        table.putItem(item);


        return aProperty;
    }


    public Property find(String name, String category) {

        GetItemSpec spec = new GetItemSpec();
        spec.withPrimaryKey("name", name).withPrimaryKey("category", category);
        final Table table = this.dynamoDB.getTable(this.tableName);

        Item item = table.getItem(spec);
        Property aProperty = null;
        if (item == null) {
            Map<String, Object> aMap = item.asMap();
            aProperty = new Property(item.get("id").toString(), item.get("name").toString(), item.get("description").toString(), item.get("value").toString(), item.get("category").toString());
        }


        return aProperty;
    }


}
