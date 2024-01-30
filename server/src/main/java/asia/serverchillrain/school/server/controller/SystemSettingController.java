package asia.serverchillrain.school.server.controller;

import asia.serverchillrain.school.server.controller.root.BaseController;
import asia.serverchillrain.school.server.entity.bean.Response;
import asia.serverchillrain.school.server.entity.bean.SystemSettingLine;
import asia.serverchillrain.school.server.service.SystemSettingService;
import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @auther 2024 01 28
 * 系统设置Controller
 */
@RestController("/system")
public class SystemSettingController extends BaseController {
    @Resource
    private SystemSettingService systemSettingService;
    @RequestMapping("/refresh")
    public Response refresh(){
        return getSuccessResponse(systemSettingService.refreshCache());
    }
    @RequestMapping("/writeSetting")
    public Response writeSetting(SystemSettingLine line){
        return getSuccessResponse(systemSettingService.writeSettingDB(line));
    }
}
