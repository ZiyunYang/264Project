package com.example.irvinetaste.utils;

import com.mongodb.DBObject;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.FindOneAndUpdateOptions;

import org.bson.BsonArray;
import org.bson.BsonInt32;
import org.bson.Document;
import org.bson.types.ObjectId;

import java.util.List;

import static java.util.Arrays.asList;

/**
 * @author zhangzhenyu
 * @date 2021/2/23 11:17
 * @version 1.0
 */
public class MongoDBUtil {

    MongoClient mongoClient = null;
    MongoDatabase db = null;


    public MongoDBUtil(String dbName){

        String databaseUser = "root";
        String databasePassword = "uci123456";
        String host = "cluster0.v64ra.mongodb.net/myFirstDatabase";

        try{
            mongoClient= MongoClients.create("mongodb+srv://root:uci123456@cluster0.v64ra.mongodb.net/myFirstDatabase?authSource=admin&retryWrites=true&w=majority");
            db= mongoClient.getDatabase(dbName);
            System.out.println("Connect to database successfully");

        }catch (Exception e){
            e.printStackTrace();
        }

    }

    /*
    get the specified collection by its name
     */
    public void getCollection(String collName){
        MongoCollection<Document> collection = db.getCollection(collName);

        System.out.println(collection == null);

        System.out.println("the number of result is " + collection.countDocuments());

        FindIterable<Document> findIterable = collection.find();
        MongoCursor<Document> mongoCursor = findIterable.iterator();
        while(mongoCursor.hasNext()){
            System.out.println(mongoCursor.next());
        }

    }

    /**
     * Create a blanket collection.
     * @param collName :collection name.
     */
    public void createCollection(String collName)
    {
        db.createCollection(collName);
    }

    /**
     * Insert dbObject into collection.
     * @param document
     * @param collName
     */
    public void insert(Document document, String collName)
    {
        MongoCollection collection = db.getCollection(collName);
        collection.insertOne(document);
    }

    /*
    find the sequence_value in counter collection and increase the value by 1
     */
    public int findAndModify(String collName, String sequenceName){
        MongoCollection collection = db.getCollection(collName);
        Document filterDocument = new Document("_id",sequenceName);
        Document updateDocument = new Document("$inc",new Document("sequence_value",1));
        Object result = collection.findOneAndUpdate(filterDocument,updateDocument);
        return ((Document)result).getInteger("sequence_value");
    }




    /**
     * Insert dbObject list into collection.
     * @param dbObjects
     * @param collName
     */
    public void insertBatch(List<DBObject> dbObjects, String collName)
    {

    }

    /**
     * Delete data By Id.
     * @param id
     * @param collName
     * @return
     */
    public int deleteById(String id,String collName)
    {
        return 0;
    }

    /**
     * Delete data By Condition.
     * @param dbObject
     * @param collName
     * @return
     */
    public int deleteByDbs(DBObject dbObject,String collName)
    {
        return 0;
    }

    /**
     * Update Data.
     * @param find
     * @param update
     * @param upsert
     * @param multi
     * @param collName
     * @return
     */
    public int update(DBObject find,DBObject update,boolean upsert,boolean multi,String collName)
    {
        return 0;
    }

}
