package com.renren.gota.webserver.util;

import com.google.gdata.client.Query;
import com.google.gdata.client.authn.oauth.GoogleOAuthParameters;
import com.google.gdata.client.authn.oauth.OAuthException;
import com.google.gdata.client.authn.oauth.OAuthHmacSha1Signer;
import com.google.gdata.client.contacts.ContactsService;
import com.google.gdata.data.PlainTextConstruct;
import com.google.gdata.data.contacts.ContactEntry;
import com.google.gdata.data.contacts.ContactFeed;
import com.google.gdata.data.extensions.*;
import com.google.gdata.util.ServiceException;

import java.io.IOException;
import java.net.URL;
import java.util.List;

/**
 * @Title:
 * @Desc:
 * @Author xiaoxue.wang
 * @Date 2015-09-21 11:04.
 */
public class GmailContactsUtils {

    private static GoogleOAuthParameters initOauthParams(String accessToken) {
        GoogleOAuthParameters oauthParameters = new GoogleOAuthParameters();
        oauthParameters.setOAuthConsumerKey(GmailConstants.CONSUMER_KEY);
        oauthParameters.setOAuthConsumerSecret(GmailConstants.CONSUMER_SECRET);
        oauthParameters.setScope(GmailConstants.CONTACT_SCOPE_RW);
        oauthParameters.setOAuthToken(accessToken);
        oauthParameters.setOAuthTokenSecret(accessToken);
        return oauthParameters;

    }

    //获取联系人列表
    public static List<ContactEntry> getContacts(String accessToken, Integer maxResults) throws OAuthException, IOException, ServiceException {
        GoogleOAuthParameters oauthParameters = initOauthParams(accessToken);
        ContactsService client = new ContactsService(GmailConstants.APPLICATION_NAME);

        ContactFeed resultFeed = null;

        client.setOAuthCredentials(oauthParameters, new OAuthHmacSha1Signer());
        client.setHeader("Authorization", "Bearer " + accessToken);
        client.setHeader("GData-Version", "3.0");

        URL feedUrl = new URL(GmailConstants.CONTACT_FEED_URL);
        Query query = new Query(feedUrl);
        if(maxResults != null)
            query.setMaxResults(maxResults);

        resultFeed = client.query(query, ContactFeed.class);
        System.out.println(resultFeed.getTitle().getPlainText());
        return resultFeed.getEntries();

    }

    //删除联系人
    public static void deleteContact(String accessToken, String contactId) throws OAuthException, IOException, ServiceException {
        GoogleOAuthParameters oauthParameters = initOauthParams(accessToken);
        ContactsService client = new ContactsService(GmailConstants.APPLICATION_NAME);

        client.setOAuthCredentials(oauthParameters, new OAuthHmacSha1Signer());
        client.setHeader("Authorization", "Bearer " + accessToken);

        URL url = new URL(contactId);
        ContactEntry contact = client.getEntry(url, ContactEntry.class);
        contact.delete();
    }

    //获取联系人详情
    public static ContactEntry getContactDetail(String accessToken, String contactId) throws OAuthException, IOException, ServiceException {

        GoogleOAuthParameters oauthParameters = initOauthParams(accessToken);
        ContactsService client = new ContactsService(GmailConstants.APPLICATION_NAME);
        ContactEntry entry = null;

        client.setOAuthCredentials(oauthParameters, new OAuthHmacSha1Signer());
        client.setHeader("Authorization", "Bearer " + accessToken);
        client.setHeader("GData-Version", "3.0");

        URL url = new URL(contactId);
        entry = client.getEntry(url, ContactEntry.class);
        return entry;
    }

    //添加联系人
    public static ContactEntry addContact(String accessToken, ContactEntry contact) throws OAuthException, IOException, ServiceException {

        GoogleOAuthParameters oauthParameters = initOauthParams(accessToken);
        ContactsService client = new ContactsService(GmailConstants.APPLICATION_NAME);
        ContactEntry entry = null;

        client.setOAuthCredentials(oauthParameters, new OAuthHmacSha1Signer());
        client.setHeader("Authorization", "Bearer " + accessToken);
        client.setHeader("GData-Version", "3.0");


        // Ask the service to insert the new entry
        URL postUrl = new URL(GmailConstants.CONTACT_FEED_URL);
        ContactEntry createdContact = client.insert(postUrl, contact);
        System.out.println("Contact's ID: " + createdContact.getId());
        return createdContact;

    }

    private static ContactEntry initContact() {
        ContactEntry contact = new ContactEntry();
        // Set the contact's name.
        Name name = new Name();
        final String NO_YOMI = null;
        name.setFullName(new FullName("白 龙", NO_YOMI));
        name.setGivenName(new GivenName("白", NO_YOMI));
        name.setFamilyName(new FamilyName("龙", NO_YOMI));
        contact.setName(name);
        contact.setContent(new PlainTextConstruct("Notes"));
        // Set contact's e-mail addresses.
        Email primaryMail = new Email();
        primaryMail.setAddress("bai.long@gmail.com");
        primaryMail.setDisplayName("小龙");
        primaryMail.setRel("http://schemas.google.com/g/2005#home");
        primaryMail.setPrimary(true);
        contact.addEmailAddress(primaryMail);
        Email secondaryMail = new Email();
        secondaryMail.setAddress("bai.long@example.com");
        secondaryMail.setRel("http://schemas.google.com/g/2005#work");
        secondaryMail.setPrimary(false);
        contact.addEmailAddress(secondaryMail);
        // Set contact's phone numbers.
        PhoneNumber primaryPhoneNumber = new PhoneNumber();
        primaryPhoneNumber.setPhoneNumber("(206)555-1212");
        primaryPhoneNumber.setRel("http://schemas.google.com/g/2005#work");
        primaryPhoneNumber.setPrimary(true);
        contact.addPhoneNumber(primaryPhoneNumber);
        PhoneNumber secondaryPhoneNumber = new PhoneNumber();
        secondaryPhoneNumber.setPhoneNumber("(206)555-1213");
        secondaryPhoneNumber.setRel("http://schemas.google.com/g/2005#home");
        contact.addPhoneNumber(secondaryPhoneNumber);
        // Set contact's IM information.
        Im imAddress = new Im();
        imAddress.setAddress("bai.long@gmail.com");
        imAddress.setRel("http://schemas.google.com/g/2005#home");
        imAddress.setProtocol("http://schemas.google.com/g/2005#GOOGLE_TALK");
        imAddress.setPrimary(true);
        contact.addImAddress(imAddress);
        // Set contact's postal address.
        StructuredPostalAddress postalAddress = new StructuredPostalAddress();
        postalAddress.setStreet(new Street("1600 Amphitheatre Pkwy"));
        postalAddress.setCity(new City("Mountain View"));
        postalAddress.setRegion(new Region("CA"));
        postalAddress.setPostcode(new PostCode("94043"));
        postalAddress.setCountry(new Country("US", "United States"));
        postalAddress.setFormattedAddress(new FormattedAddress("1600 Amphitheatre Pkwy Mountain View"));
        postalAddress.setRel("http://schemas.google.com/g/2005#work");
        postalAddress.setPrimary(true);
        contact.addStructuredPostalAddress(postalAddress);
        return contact;
    }


    public static void main(String[] args) {
//        {
//            "access_token": "ya29.9wHY6dDE_15vOzek5Fe-C8GuPFxnUgxCt5S0zRsvGzf0Jh7tF-aTpVjJGjqKZg_onbVb",
//                "token_type": "Bearer",
//                "expires_in": 3600,
//                "refresh_token": "1/i9ac6EUuGLcCQ2AO7qBdETXZC12i_WvkviMGuqL-iaDBactUREZofsF9C7PrpE-j"
//        }
//        GmailConprintln(util.refreshAccessToken("1/-r3y9bTSZbzMQ2ZXtX8rWbJcCBXDNiyLfFo3ejw9pTYMEudVrK5jSpoR30zcRFq6"));
        String token = OAuth2Util.getAccessToken("ya29.-QEiNd4xlkeqvXLr734HxsplFys7XtOe1s7na2KzZp4Wp-TPW1W6-J2pwxrGUlo0lL2DHwss",
                "1/XcXPqJ7Qpcm0hhGbacmaR5Eyt3j2Z2i7SNNvg2QMhB8");
        System.out.println(token);
        try {
//
//            ContactEntry entry = getContactDetail(token, "http://www.google.com/m8/feeds/contacts/snowfly.wang%40gmail.com/base/40ca212c8a64bf73");
//            System.out.println(entry.getEmailAddresses().size());
//            deleteContact(token, "http://www.google.com/m8/feeds/contacts/default/full/7bf187ac0e27ab2b");
            ContactEntry contactEntry = initContact();
            addContact(token, contactEntry);
            List<ContactEntry> contactList = getContacts(token, 50);
            for(ContactEntry contact : contactList) {
                System.out.println(contact.getTitle().getPlainText());
                System.out.println(contact.getId());
            }
        } catch (OAuthException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ServiceException e) {
            e.printStackTrace();
        }

    }


}
