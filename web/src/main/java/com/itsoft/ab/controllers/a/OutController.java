package com.itsoft.ab.controllers.a;

import com.itsoft.ab.model.ClientModel;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created with IntelliJ IDEA.
 * User: itertychnyi
 * Date: 29.08.14
 * Time: 15:36
 */
@Controller
public class OutController {
    private Log LOG = LogFactory.getLog(OutController.class);

    @RequestMapping(value = "/out", method = RequestMethod.POST)
    public
    @ResponseBody
    int out(@RequestBody final ClientModel client) {
        LOG.error("ПОЛУЧЕННЫЙ ОБЪЕКТ: " + client.toString());
        return 200;
    }
}
