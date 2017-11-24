package dw.api;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;

import org.glassfish.jersey.client.ClientConfig;
import org.glassfish.jersey.client.ClientProperties;

import com.fasterxml.jackson.jaxrs.json.JacksonJsonProvider;
import com.github.dockerjava.core.DockerClientConfig;
import com.github.dockerjava.core.JsonClientFilter;

/**
 * 
 * @author 蓝眼泪
 * 2017-11-24上午9:32:19
 */

public abstract class DockerCommand {
    private WebTarget baseResource;
    private Client client;

    public DockerCommand(String url) {
        this(DockerClientConfig.createDefaultConfigBuilder().withUri(url)
                .build());
    }

    public DockerCommand(DockerClientConfig dockerClientConfig) {
        ClientConfig clientConfig = new ClientConfig();
        clientConfig.register(JsonClientFilter.class);
        clientConfig.register(JacksonJsonProvider.class);
        if (dockerClientConfig.getReadTimeout() != null) {
            int readTimeout = dockerClientConfig.getReadTimeout();
            clientConfig.property(ClientProperties.READ_TIMEOUT, readTimeout);
        }
        client = ClientBuilder.newClient(clientConfig);
        WebTarget webResource = client.target(dockerClientConfig.getUri());
        if (dockerClientConfig.getVersion() != null) {
            baseResource = webResource.path("v"
                    + dockerClientConfig.getVersion());
        } else {
            baseResource = webResource;
        }
    }

    protected Client getClient() {
        return client;
    }

    protected WebTarget getResource() {
        return baseResource;
    }

    public abstract Object exec(Object... commands);
}
