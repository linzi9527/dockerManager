package dw.api;

import static javax.ws.rs.client.Entity.entity;

import java.io.InputStream;

import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;

/**
 * 
 * @author 蓝眼泪
 * 2017-11-24上午9:31:58
 */

public class ImportContainerCmdImpl extends DockerCommand {

    public ImportContainerCmdImpl(String url) {
        super(url);
    }

    @Override
    public Object exec(Object... commands) {
        if (commands.length < 2)
            return null;
        try {
            String name = (String) commands[0];
            String[] names = name.split(":");
            String repo = names[0];
            String tag = null;
            if (names.length > 1)
                tag = names[1];
            InputStream stream = (InputStream) commands[1];
            WebTarget webResource = getResource().path("/images/create")
                    .queryParam("repo", repo).queryParam("tag", tag)
                    .queryParam("fromSrc", "-");
            webResource.request()
                    .accept(MediaType.APPLICATION_OCTET_STREAM_TYPE)
                    .post(entity(stream, MediaType.APPLICATION_OCTET_STREAM)); // large
                                                                               // data
                                                                               // size,
                                                                               // out
                                                                               // of
                                                                               // heap
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
