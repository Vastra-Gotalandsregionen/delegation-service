package se.vgregion.delegation.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.portlet.bind.annotation.RenderMapping;
import se.vgregion.delegation.DelegationService;
import se.vgregion.delegation.domain.Delegation;

import javax.portlet.PortletRequest;
import javax.portlet.RenderRequest;
import java.util.Map;

import static javax.portlet.PortletRequest.P3PUserInfos.USER_LOGIN_ID;
import static javax.portlet.PortletRequest.USER_INFO;

/**
 * Created by IntelliJ IDEA.
 * Created: 2011-10-18 11:29
 *
 * @author <a href="mailto:david.rosell@redpill-linpro.com">David Rosell</a>
 */

@Controller
@RequestMapping("VIEW")
public class ViewController {
    private static final Logger LOGGER = LoggerFactory.getLogger(ViewController.class);

    @Value("${sign.request.url}")
    private String signRequestUrl;

    @Value("${sign.response.url}")
    private String signResponseUrl;

    @Value("${sign.client.type}")
    private String signClientType;

    @Autowired
    private DelegationService delegationService;

    @RenderMapping
    public String showView(RenderRequest request, Model model) {
        String uid = lookupUid(request);

        Delegation activeDelegation = delegationService.activeDelegations(uid);
        Delegation pendingDelegation = delegationService.pendingDelegation(uid);

        model.addAttribute("signRequestUrl", signRequestUrl);
        model.addAttribute("sign_clientType", signClientType);
        model.addAttribute("sign_submitUri", signResponseUrl);

        return "view";
    }

    private String lookupUid(PortletRequest req) {
        Map<String, ?> userInfo = (Map<String, ?>) req.getAttribute(USER_INFO);
        String userId = "";
        if (userInfo != null && userInfo.get(USER_LOGIN_ID.toString()) != null) {
            userId = (String) userInfo.get(USER_LOGIN_ID.toString());
        }
        return userId;
    }

}
