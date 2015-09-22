package webserver.controller.user;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 用户注册请求
 * 
 * @author zhanzhan.qi@renren-inc.com 2015年9月22日 下午8:14:19
 */
@Controller
public class RegistryController {

    @RequestMapping(value = "registry", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String registry(@RequestParam("account") String account, @RequestParam("password") String password) {

        return "{account:" + account + ", password:" + password + "}";

    }

}
