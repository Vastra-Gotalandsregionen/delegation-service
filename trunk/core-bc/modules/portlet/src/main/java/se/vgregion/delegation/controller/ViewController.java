package se.vgregion.delegation.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.portlet.bind.annotation.RenderMapping;
import se.vgregion.delegation.DelegationService;

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

    @Autowired
    private DelegationService delegationService;

    @RenderMapping
    public String showView() {
        return "view";
    }
}
