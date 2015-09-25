package com.renren.gota.webserver.model;

import com.google.gdata.data.contacts.ContactEntry;
import com.google.gdata.data.extensions.Email;
import com.google.gdata.data.extensions.PhoneNumber;

import java.util.ArrayList;
import java.util.List;

/**
 * @Title:
 * @Desc:
 * @Author xiaoxue.wang
 * @Date 2015-09-25 15:15.
 */
public class GmailContactEntry {

    private String contactId;
    private String userName;
    private List<String> emailList;
    private List<String> phoneList;

    public GmailContactEntry(ContactEntry contact) {
        this.contactId = contact.getId();
        this.userName = contact.getTitle().getPlainText();
        List<Email> emailS = contact.getEmailAddresses();
        emailList = new ArrayList<String>();
        for(Email email : emailS) {
            emailList.add(email.getAddress());
        }
        phoneList = new ArrayList<String>();
        List<PhoneNumber> phoneS = contact.getPhoneNumbers();
        for(PhoneNumber phone : phoneS) {
            phoneList.add(phone.getPhoneNumber());
        }
    }

    public String getContactId() {
        return contactId;
    }

    public void setContactId(String contactId) {
        this.contactId = contactId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public List<String> getEmailList() {
        return emailList;
    }

    public void setEmailList(List<String> emailList) {
        this.emailList = emailList;
    }

    public List<String> getPhoneList() {
        return phoneList;
    }

    public void setPhoneList(List<String> phoneList) {
        this.phoneList = phoneList;
    }

    @Override
    public String toString() {
        return "GmailContactEntry{" +
                "contactId='" + contactId + '\'' +
                ", userName='" + userName + '\'' +
                ", emailList=" + emailList +
                ", phoneList=" + phoneList +
                '}';
    }
}
