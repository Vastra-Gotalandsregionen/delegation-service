package se.vgregion.delegation.controller;

import org.apache.commons.lang.StringUtils;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.portlet.bind.annotation.ActionMapping;
import org.springframework.web.portlet.bind.annotation.RenderMapping;
import se.vgregion.delegation.DelegationService;
import se.vgregion.delegation.domain.*;
import se.vgregion.delegation.model.DelegationInfo;
import se.vgregion.delegation.model.SearchPerson;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletRequest;
import javax.portlet.RenderRequest;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.*;

import static javax.portlet.PortletRequest.P3PUserInfos.USER_LOGIN_ID;
import static javax.portlet.PortletRequest.USER_INFO;

/**
 * Created by IntelliJ IDEA.
 * Created: 2011-10-18 11:29
 *
 * @author <a href="mailto:david.rosell@redpill-linpro.com">David Rosell</a>
 */
@SessionAttributes({"vardEnhetInfo"})
@Controller
@RequestMapping("VIEW")
public class ViewController {
    private static final Logger LOGGER = LoggerFactory.getLogger(ViewController.class);

    @Value("${sign.request.url}")
    private String signRequestUrl;

    @Value("${sign.response.protocol}")
    private String signResponseProtocol;
    @Value("${sign.response.name}")
    private String signResponseName;
    @Value("${sign.response.port}")
    private String signResponsePort;
    @Value("${sign.response.path}")
    private String signResponsePath;

    @Value("${sign.client.type}")
    private String signClientType;

    @Autowired
    private DelegationService delegationService;

    @RenderMapping
    public String showChooseVardEnhetView(RenderRequest request, Model model) {
        String uid = lookupUid(request);

        List<VardEnhetInfo> vcInfoList = delegationService.lookupVerksamhetsChefInfo(uid);
        if (vcInfoList.size() > 0) {
            model.addAttribute("vcVgrId", uid);
            if (vcInfoList.size() > 1) {
                model.addAttribute("vardEnhetInfoList", vcInfoList);
                return "chooseVardEnhetView";
            }
            model.addAttribute("vardEnhetInfo", vcInfoList.get(0));
            return "activeDelegationView";
        } else {
            return "error";
        }
    }

    @RenderMapping(params = "view=activeDelegation")
    public String showCurrentDelegationView(RenderRequest request,
            @ModelAttribute(value = "vardEnhetInfo") VardEnhetInfo vardEnhetInfo,
            Model model) {
        try {
            String vcVgrId = lookupUid(request);

            model.addAttribute("vcVgrId", vcVgrId);
            model.addAttribute("vardEnhetInfo", vardEnhetInfo);

            Delegation activeDelegation = delegationService.activeDelegations(vcVgrId);
            model.addAttribute("activeDelegation", activeDelegation);

            return "activeDelegationView";
        } catch (Exception ex) {
            ex.printStackTrace();
            return "error";
        }
    }

    @RenderMapping(params = "view=pendingDelegation")
    public String showPendingDelegationView(RenderRequest request,
            @ModelAttribute(value = "vardEnhetInfo") VardEnhetInfo vardEnhetInfo,
            Model model) {
        try {
            String vcVgrId = lookupUid(request);

            model.addAttribute("vcVgrId", vcVgrId);
            model.addAttribute("vardEnhetInfo", vardEnhetInfo);

            Delegation pendingDelegation = delegationService.pendingDelegation(vcVgrId);
            model.addAttribute("pendingDelegation", pendingDelegation);
            model.addAttribute("delegationsToSize", pendingDelegation.getDelegationsTo().size());

            return "pendingDelegationView";
        } catch (Exception ex) {
            ex.printStackTrace();
            return "error";
        }
    }


    @RenderMapping(params = "view=searchPersonView")
    public String showSearchPersonView(Model model,
            @RequestParam(value = "currentPage", required = false) Integer currentPage,
            @ModelAttribute(value = "vardEnhetInfo") VardEnhetInfo vardEnhetInfo) {
        model.addAttribute("search", new SearchPerson());

        if (currentPage == null) {
            currentPage = 0;
        }

        Map<HealthCareUnit, Collection<PersonalInfo>> allPersonal =
                new HashMap<HealthCareUnit, Collection<PersonalInfo>>();
        allPersonal.put(vardEnhetInfo.getVardEnhet(), vardEnhetInfo.getVardEnhet().getPersonal());
        // IngaendeEnhets
        for (HealthCareUnit ingaende: vardEnhetInfo.getIngaendeEnheter()) {
            allPersonal.put(ingaende, ingaende.getPersonal());
        }
        model.addAttribute("vardEnhetInfoPersonalMap", allPersonal);
        model.addAttribute("vardEnhetInfoPersonalListSize", allPersonal.size());

        return "searchPersonView";
    }

    @ActionMapping("addPersonal")
    public void addPerson(ActionRequest request, ActionResponse response,
            @RequestParam("vgrId") String vgrId,
            Model model) {
        String vcVgrId = lookupUid(request);

        Delegation pendingDelegation = delegationService.pendingDelegation(vcVgrId);

        DelegationTo to = new DelegationTo();
        to.setDelegateTo(vgrId);
        to.setDelegatedFor("UppdragsBeställning");

        DateTime now = new DateTime();
        to.setValidFrom(new Date(now.getMillis()));
        to.setValidTo(new Date(now.plusMonths(12).getMillis()));

        pendingDelegation.addDelegationTo(to);

        delegationService.save(pendingDelegation);

        response.setRenderParameter("view", "pendingDelegation");
    }


    // TODO: remove this
    public String showView(RenderRequest request, Model model) {
        try {
            String uid = lookupUid(request);

            Delegation activeDelegation = delegationService.activeDelegations(uid);
            Delegation pendingDelegation = delegationService.pendingDelegation(uid);

            List<VardEnhetInfo> vcInfoList = delegationService.lookupVerksamhetsChefInfo(uid);

            model.addAttribute("verksamhetsChefInfoList", vcInfoList);
            model.addAttribute("delegationInfo", new DelegationInfo());

            Set<HealthCareUnit> allVardEnhets = delegationService.findAllVardEnhet();
            model.addAttribute("allVardEnhets", allVardEnhets);

            String signResponseUrl = responseUrlBuilder(request, pendingDelegation);

            model.addAttribute("sign_requestUrl", signRequestUrl);
            model.addAttribute("sign_clientType", signClientType);
            model.addAttribute("sign_submitUri", signResponseUrl);

            return "view";
        } catch (Exception ex) {
            ex.printStackTrace();
            return "view";
        }
    }

    private String responseUrlBuilder(PortletRequest request, Delegation pendingDelegation) throws UnknownHostException {
        String server = (StringUtils.isBlank(signResponseName) ?
                InetAddress.getLocalHost().getHostName() : signResponseName);
        String path = signResponsePath.startsWith("/") ? signResponsePath.substring(1) : signResponsePath;
        String correlationId = UUID.randomUUID().toString();

        return String.format("%s://%s:%s/%s?correlationId=%s&delegationId=%s", signResponseProtocol, server,
                signResponsePort, path, correlationId, pendingDelegation.getId());
    }

    private String lookupUid(PortletRequest req) {
        Map<String, ?> userInfo = (Map<String, ?>) req.getAttribute(USER_INFO);
        String userId = "";
        if (userInfo != null && userInfo.get(USER_LOGIN_ID.toString()) != null) {
            userId = (String) userInfo.get(USER_LOGIN_ID.toString());
        }
        // TODO: Remove test vcVgrId
        userId = "tombr";

        return userId;
    }

}
