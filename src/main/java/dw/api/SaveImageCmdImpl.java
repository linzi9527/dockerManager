package dw.api;

import java.io.InputStream;

import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Response;

/**
 * 
 * @author 蓝眼泪
 * 2017-11-24上午9:32:09
 */

public class SaveImageCmdImpl extends DockerCommand {

    public SaveImageCmdImpl(String url) {
        super(url);
    }

    @Override
    public Object exec(Object... commands) {
        if (commands.length < 1)
            return null;
        String id = (String) commands[0];
        WebTarget webResource = getResource().path("/images/{id}/get")
                .resolveTemplate("id", id);

        return webResource.request().get(Response.class)
                .readEntity(InputStream.class);
    }

}
