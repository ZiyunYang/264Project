package com.example.irvinetaste.daos;

import com.example.irvinetaste.User;
import com.example.irvinetaste.utils.MongoDBUtil;

import org.bson.Document;
import org.bson.types.ObjectId;

import java.util.ArrayList;
import java.util.List;

import static java.util.Arrays.asList;

import io.realm.Realm;

/**
 * @author zhangzhenyu
 * @date 2021/2/23 下午2:45
 */
public class UserDao {

    private static MongoDBUtil dbHelper;

    public UserDao(){

        try{
            dbHelper = new MongoDBUtil("irvinetaste");
        }catch (Exception e){
            e.printStackTrace();
        }
    }


    public void createCollection(String collName){
        dbHelper.createCollection(collName);
    }

    /*
    make user table's userId can auto increment
     */
    public int getNextSequenceValue(String sequenceName){
        return dbHelper.findAndModify("useridcounter","userId");
    }

    /*
    phoneNumber: username
    password: password
    return true if all correct
     */
    public boolean login(String phoneNumber,String password){

        return false;
    }

    /*
    example usage:
    UserDao userDao = new UserDao();
    userDao.insertUser("yourUserName","yourPhoneNumber","yourPassword");
     */
    public void insertUser(String userName, String phoneNumber, String password){

        Document oneUser = new Document("_id",new ObjectId());
        oneUser.append("userId",getNextSequenceValue("userId"));
        oneUser.append("userName", userName);
        oneUser.append("phoneNumber", phoneNumber);
        oneUser.append("password",password);
        oneUser.append("bookmark",asList());

        dbHelper.insert(oneUser,"user");
    }

    //TODO
    public List<String> getAllPhoneNumber(){
        List<String> resultList = new ArrayList<>();

        return resultList;
    }

    //TODO
    public User getUserProfileById(int userId){
        return null;
    }


}
