package dw.services;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.dockerjava.api.DockerClient;
import com.github.dockerjava.core.DockerClientImpl;

/**
 * 
 * @author 蓝眼泪
 * 2017-11-24上午9:32:38
 */

@Service
public class DockerService {

    @Autowired
    SettingService settingSrv;

    private static String dockerPath;
    private static DockerClient dockerClient;

    public String getDockerPath() {
        return dockerPath;
    }

    public DockerClient getDockerClient() {
        if (dockerPath == null) {
            dockerPath = settingSrv.getHostPortUrl();
        }
        if (dockerPath == null)
            return null;
        if (dockerClient == null)
            dockerClient = new DockerClientImpl(dockerPath);
        return dockerClient;
    }

    public void setDockerPath(String url) {
        dockerPath = url;
        if (dockerClient != null) {
            try {
                dockerClient.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        dockerClient = new DockerClientImpl(dockerPath);
    }

    public boolean ping() {
        DockerClient cli = getDockerClient();
        if (cli != null) {
            try {
                cli.pingCmd().exec();
                return true;
            } catch (Exception e) {

            }
        }
        return false;
    }
}
