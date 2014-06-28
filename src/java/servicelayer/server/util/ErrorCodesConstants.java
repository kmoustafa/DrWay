package servicelayer.server.util;

public class ErrorCodesConstants {

    public static final int REQUEST_TIMEOUT = -301;
    public static final int MISSING_PARAMETER = -302;
    public static final int INVALID_PARAMETER = -303;
    public static final int BAD_USER = -304;  //API (6, 7, 8, 9, 10)- UserNotExist  //to be blocked  
    public static final int NOT_SUPPORTED_AREA = -305;  //API (8)- LocationNotSupported 
    public static final int ROAD_DOES_NOT_EXIST = -306;  //API (9)- RoadNotExist

    public static final int SERVER_PROBLEM = -201;
    public static final int DATABASE_PROBLEM = -202;

    public static final int USER_EXISTS = -101;
    public static final int USER_EXISTS_AS_PENDING = -102;
    public static final int SENDING_VARIFICATION_CODE_FAILED = -103;
    public static final int USER_DOES_NOT_EXIST = -104;  //API (2, 3)- UserNotExist
    public static final int INVALID_VARIFICATION_CODE = -105;  //API (2, 3)- InvalidVerificationCode
    public static final int BLOCKED_USER = -106;  //API (2, 3)- UserBlocked

    public static final int SUCCESS = 0;

    public static final String serviceLayerURLOnITIServer = "http://localhost:8084/AllInOneProject/ServiceLayer";
}
