package proman.service.type;

import java.util.HashMap;
import java.util.Map;

public enum UserStatus {
    REGISTERED(4),

    ACTIVE(1),

    INACTIVE(0),

    LOCKED(2),

    DELETED(3);
    private int code;
    UserStatus(int code){ this.code=code;}
    // We need somthing that could hold values for each code
    private static Map<Integer,UserStatus> lookup=new HashMap<>();
    static{
        for(UserStatus us:UserStatus.values()){
            lookup.put(us.getCode(),us);
        }
        // After this the map would contain
        //Key : Value
        /*
         * 1 : UserStatus.ACTIVE
         * 0 : UserStatus.INACTIVE
         * 2 : UserStatus.LOCKED
         * 3 : UserStatus.DELETED
         * 4 : UserStatus.REGISTERED
         * */
    }

    public static UserStatus getEnum(int code){
        return lookup.get(code);
    }
    private Integer getCode() {
        return this.code;
    }
}
