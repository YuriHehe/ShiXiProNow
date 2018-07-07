import com.pro.ssm.controller.AppController;
import org.junit.Before;
import org.junit.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.ui.Model;
import org.springframework.web.multipart.support.DefaultMultipartHttpServletRequest;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Map;

public class AdminTest extends BaseTest {
    @Resource
    AppController appController;

    @Test
    public void login(){
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.setSession(new MockHttpSession());
        request.addParameter("userid","admin");
        request.addParameter("password","123456");
        request.addParameter("role","admin");
        Map<String,Object> res = appController.login(request);
        System.out.println(res);
    }
}
