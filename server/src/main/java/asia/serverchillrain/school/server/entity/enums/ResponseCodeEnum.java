package asia.serverchillrain.school.server.entity.enums;

import lombok.Getter;

/**
 * 状态码
 */
@Getter
public enum ResponseCodeEnum {

    CODE_200(200, "请求成功"),
    CODE_500(500, "服务器返回错误"),
    CODE_404(404, "请求目标不存在"),
    CODE_600(600, "请求参数不正确"),
    /**
     * 这个是发生了非自定义异常时所确定的异常码
     */
    CODE_700(700, "服务器底层错误");

    private final int code;
    private final String mess;

    ResponseCodeEnum(int code, String mess) {
        this.code = code;
        this.mess = mess;
    }

}
