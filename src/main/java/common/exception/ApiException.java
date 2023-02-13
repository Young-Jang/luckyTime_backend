package toyproject.almigty.common.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import toyproject.almigty.common.constants.ResponseCode;

import static toyproject.almigty.common.constants.ResponseCode.SERVER_ERROR;

/**
 unChecked exception
 런타임 중 발생하는 예외로 예외처리 필요
 트랜잭션 롤백 이뤄짐
 */
@Getter
public class ApiException extends RuntimeException {
    private final String resCode;
    private final String resMessage;
    private final HttpStatus httpStatus;
    //responseBody 필요시 추가

    public ApiException(ResponseCode responseCode){
        super("["+responseCode.getHttpStatus()+"]"+"["+responseCode.getResCode()+"] "+responseCode.getResMessage());
        this.resCode = responseCode.getResCode();
        this.resMessage = responseCode.getResMessage();
        this.httpStatus = responseCode.getHttpStatus();
    }

    public ApiException(ResponseCode responseCode, String responseMessage){
        super("["+responseCode.getHttpStatus()+"]"+"["+responseCode.getResCode()+"] "+responseMessage);
        this.resCode = responseCode.getResCode();
        this.resMessage = responseCode.getResMessage() + "("+ responseMessage+")";
        this.httpStatus = responseCode.getHttpStatus();
    }

    public ApiException(ResponseCode responseCode, Throwable cause) {
        super("["+responseCode.getHttpStatus()+"]"+"["+responseCode.getResCode()+"] "+responseCode.getResMessage(),cause);
        this.resCode = responseCode.getResCode();
        this.resMessage = responseCode.getResMessage();
        this.httpStatus = responseCode.getHttpStatus();
    }

    public ApiException(String resCode, String resMessage, HttpStatus httpStatus){
        super("["+httpStatus+"]"+"["+resCode+"] "+resMessage);
        this.resCode = resCode;
        this.resMessage = resMessage;
        this.httpStatus = httpStatus;
    }

    public ApiException() {this(SERVER_ERROR);}
}