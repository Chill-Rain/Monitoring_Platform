package asia.serverchillrain.school.server.service;

import asia.serverchillrain.school.server.entity.exception.MonitoringPlatformException;

public interface ApiService {
    String action(String dst) throws MonitoringPlatformException;
}
