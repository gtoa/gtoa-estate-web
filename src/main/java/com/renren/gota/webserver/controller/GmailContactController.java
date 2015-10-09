package com.renren.gota.webserver.controller;

import com.google.gdata.client.authn.oauth.OAuthException;
import com.google.gdata.data.PlainTextConstruct;
import com.google.gdata.data.contacts.ContactEntry;
import com.google.gdata.data.extensions.*;
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
    @RequestMapping(value = "contact/delete", method = RequestMethod.GET)
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
    @RequestMapping(value = "contact/add", method = RequestMethod.GET)
    public ModelAndView getAddEventPage() {
        ModelAndView mav = new ModelAndView("addContact");
        return mav;
    }

    @LoginRequired
    @RequestMapping(value = "contact/add", method = RequestMethod.POST)
    public String addContact(HttpServletResponse response,
                                 HttpServletRequest request,
                                 @RequestParam("familyName") String familyName,
                                 @RequestParam("givenName") String givenName,
                                 @RequestParam("emailAddress") String emailAddress,
                                 @RequestParam("phoneNumber") String phoneNumber
    ) {

        User user = (User) request.getAttribute("user");
        UserToken ut = userTokenService.getUserTokenById(user.getId());

        try {
            ContactEntry contact = initContact(familyName, givenName, emailAddress, phoneNumber);
            GmailContactsUtils.addContact(ut.getAccessToken(), contact);
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

    private  ContactEntry initContact(String familyName, String givenName, String emailAddress,
                                      String phoneNumber) {
        ContactEntry contact = new ContactEntry();
        // Set the contact's name.
        Name name = new Name();
        final String NO_YOMI = null;
        String fullName = familyName + " " + givenName;
        name.setFullName(new FullName(fullName, NO_YOMI));
        name.setGivenName(new GivenName(givenName, NO_YOMI));
        name.setFamilyName(new FamilyName(familyName, NO_YOMI));
        contact.setName(name);
//        contact.setContent(new PlainTextConstruct("Notes"));
        // Set contact's e-mail addresses.
        Email primaryMail = new Email();
        primaryMail.setAddress(emailAddress);
        primaryMail.setDisplayName(givenName);
        primaryMail.setRel("http://schemas.google.com/g/2005#home");
        primaryMail.setPrimary(true);
        contact.addEmailAddress(primaryMail);

        // Set contact's phone numbers.
        PhoneNumber primaryPhoneNumber = new PhoneNumber();
        primaryPhoneNumber.setPhoneNumber(phoneNumber);
        primaryPhoneNumber.setRel("http://schemas.google.com/g/2005#work");
        primaryPhoneNumber.setPrimary(true);
        contact.addPhoneNumber(primaryPhoneNumber);
        return contact;
    }

}
