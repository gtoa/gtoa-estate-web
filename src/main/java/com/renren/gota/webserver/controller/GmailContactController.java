package com.renren.gota.webserver.controller;

import com.google.gdata.client.authn.oauth.OAuthException;
import com.google.gdata.data.contacts.ContactEntry;
import com.google.gdata.util.ServiceException;
import com.renren.gota.webserver.common.annotation.LoginRequired;
import com.renren.gota.webserver.model.GmailContactEntry;
import com.renren.gota.webserver.model.User;
import com.renren.gota.webserver.model.UserToken;
import com.renren.gota.webserver.service.UserTokenService;
import com.renren.gota.webserver.util.GmailContactsUtils;
import com.renren.gota.webserver.util.ServiceResultUtil;
import net.sf.json.JSONObject;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @Title:
 * @Desc:
 * @Author xiaoxue.wang
 * @Date 2015-09-23 19:59.
 */
@Controller
@RequestMapping(value = "gmail")
public class GmailContactController {

    private Log logger = LogFactory.getLog(GmailContactController.class);

    @Autowired
    UserTokenService userTokenService;


    @RequestMapping(value = "", method = RequestMethod.GET)
    public ModelAndView getIndex() {
        ModelAndView mav = new ModelAndView("contacts");

        return mav;
    }

    @LoginRequired
    @RequestMapping(value = "contacts")
    public ModelAndView getContactList(HttpServletResponse response,
                                       HttpServletRequest request) {

        ModelAndView mav = new ModelAndView("contacts");


        User user = (User) request.getAttribute("user");
        UserToken ut = userTokenService.getUserTokenById(user.getId());
        try {
            List<ContactEntry> contactList = GmailContactsUtils.getContacts(ut.getAccessToken());
            List<GmailContactEntry> gmailContactEntryList = new ArrayList<GmailContactEntry>();
            for(ContactEntry contact : contactList) {
                gmailContactEntryList.add(new GmailContactEntry(contact));
            }
            mav.addObject("contactList", gmailContactEntryList);
        } catch (OAuthException e) {
            logger.error(e.getMessage(), e);
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
        } catch (ServiceException e) {
            logger.error(e.getMessage(), e);
        }

        return mav;
    }


    @LoginRequired
    @RequestMapping(value = "contact/detail")
    public ModelAndView getContactDetail(HttpServletResponse response,
                                     HttpServletRequest request,
                                   @RequestParam("contactId")  String contactId) {

        ModelAndView mav = new ModelAndView("contactDetail");
        User user = (User) request.getAttribute("user");
        UserToken ut = userTokenService.getUserTokenById(user.getId());

        try {
            ContactEntry contactDetail = GmailContactsUtils.getContactDetail(ut.getAccessToken(), contactId);
            mav.addObject("contact", new GmailContactEntry(contactDetail));
        } catch (OAuthException e) {
            logger.error(e.getMessage(), e);
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
        } catch (ServiceException e) {
            logger.error(e.getMessage(), e);
        }
        return mav;
    }

    @LoginRequired
    @RequestMapping(value = "contact/delete")
    @ResponseBody
    public String delContact(HttpServletResponse response,
                                       HttpServletRequest request,
                             @RequestParam("contactId")String contactId) {

//        JSONObject json = new JSONObject();
        User user = (User) request.getAttribute("user");
        UserToken ut = userTokenService.getUserTokenById(user.getId());

        try {
            GmailContactsUtils.deleteContact(ut.getAccessToken(), contactId);

        } catch (OAuthException e) {
            logger.error(e.getMessage(), e);
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
        } catch (ServiceException e) {
            logger.error(e.getMessage(), e);
        }
        try {
            response.sendRedirect("/gmail/contacts");
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
        }
        return null;
    }

    @LoginRequired
    @RequestMapping(value = "contact/add")
    @ResponseBody
    public JSONObject addContact(HttpServletResponse response,
                                 HttpServletRequest request,
                                 @RequestParam("contactId") String contactId) {

        JSONObject json = new JSONObject();
        User user = (User) request.getAttribute("user");
        UserToken ut = userTokenService.getUserTokenById(user.getId());

        try {
            GmailContactsUtils.deleteContact(ut.getAccessToken(), contactId);
            ServiceResultUtil.addResultCodeAndMsg(json, 0, "success");
        } catch (OAuthException e) {
            ServiceResultUtil.addResultCodeAndMsg(json, 1, e.getMessage());
            logger.error(e.getMessage(), e);
        } catch (IOException e) {
            ServiceResultUtil.addResultCodeAndMsg(json, 2, e.getMessage());
            logger.error(e.getMessage(), e);
        } catch (ServiceException e) {
            ServiceResultUtil.addResultCodeAndMsg(json, 3, e.getMessage());
            logger.error(e.getMessage(), e);
        }
        return json;
    }

}
