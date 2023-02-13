package toyproject.almigty.common.constants;

import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

@Getter
public enum ResponseCode {
    /** 공통 */
    SUCCESS("0000", "정상 처리되었습니다.",HttpStatus.OK),
    NO_SESSION("0001", "SESSION 정보가 없습니다.",HttpStatus.UNAUTHORIZED),
    INVALID_PARAMETER("0002", "올바르지 않은 파라미터입니다.",HttpStatus.BAD_REQUEST),
    SERVER_ERROR("9999", "서비스 접속이 원활하지 않습니다. 잠시 후 다시 이용해주세요.",HttpStatus.INTERNAL_SERVER_ERROR);

    private final String resCode;
    private final String resMessage;
    private final HttpStatus httpStatus;

    ResponseCode(String resCode, String resMessage, HttpStatus httpStatus) {
        this.resCode = resCode;
        this.resMessage = resMessage;
        this.httpStatus = httpStatus;
    }

    public String getUrlEncodingMessage(){
        return URLEncoder.encode(this.resMessage, StandardCharsets.UTF_8);
    }


    }
