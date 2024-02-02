package asia.serverchillrain.school.server.settings.api.root;

import asia.serverchillrain.school.server.settings.Setting;
import asia.serverchillrain.school.server.settings.api.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @auther 2024 02 02
 * Api设置
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ApiSetting extends Setting {
    private SleepModel sleep;
    private SmockModel smock;
    private PhoneModel phone;
    private FireModel fire;
    private CameraHardWare camera;
}
