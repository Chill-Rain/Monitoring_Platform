package asia.serverchillrain.school.server.controller.root;

import asia.serverchillrain.school.server.entity.bean.Response;
import asia.serverchillrain.school.server.entity.enums.ResponseCodeEnum;

/**
 * @auther 2024 01 26
 * 根控制器
 */

public class BaseController {
    protected final String SUCCESS = "SUCCESS";
    public final String ERROR = "ERROR";

    protected<T> Response getSuccessResponse(T data){
        Response<T> response = new Response<>();
        response.setStatus(SUCCESS);
        response.setCode(ResponseCodeEnum.CODE_200.getCode());
        response.setInfo(ResponseCodeEnum.CODE_200.getMess());
        response.setData(data);
        return response;
    }

    protected<T> Response getErrorResponse(T data, ResponseCodeEnum codeEnum){
        Response<T> response = new Response<>();
        response.setStatus(ERROR);
        response.setInfo(codeEnum.getMess());
        response.setCode(codeEnum.getCode());
        response.setData(data);
        return response;
    }
}
