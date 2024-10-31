package co.com.usc.hostalusc.domain.contracts;

public class MessageCode {

    public static MessageCode INVALID_INPUT_DATA = new MessageCode("INVALID_INPUT_DATA");
    public static MessageCode HEADER_NOT_FOUND = new MessageCode("HEADER_NOT_FOUND");
    public static MessageCode ILLEGAL_ARGUMENT = new MessageCode("ILLEGAL_ARGUMENT");
    public static MessageCode INTERVAL_SERVER_ERROR = new MessageCode("INTERVAL_SERVER_ERROR");
    public static MessageCode FORBIDDEN = new MessageCode("FORBIDDEN");

    private final String code;

    public MessageCode(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }
}