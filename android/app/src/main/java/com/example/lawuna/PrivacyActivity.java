/*
 * Copyright 2020 The Lawuna Authors. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.lawuna;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Html;
import android.view.MenuItem;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;

public class PrivacyActivity extends LegalActivity {

    private TextView lawuna_privacy;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppTheme);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_privacy_policy);
        //getting the toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar2);

        //setting the title
        toolbar.setTitle("Privacy");
        toolbar.setTitleTextColor(Color.WHITE);

        //placing toolbar in place of actionbar
        setSupportActionBar(toolbar);

        // add back arrow to toolbar
        if (getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
        lawuna_privacy = findViewById(R.id.privacy_content);
        lawuna_privacy.setText(Html.fromHtml("<h2>Privacy Policy</h2>\n"+
                "Sodzo Foundation built the Lawuna app as an Open Source app. This SERVICE " +
                "is provided by Sodzo Foundation at no cost and is intended for use as is.\n" +
                "This page is used to inform visitors regarding our policies with the collection, use, and disclosure of Personal Information if anyone decided to use our Service.\n" +
                "\n" +
                "If you choose to use our Service, then you agree to the collection and use of information in relation to this policy. The Personal Information that we collect is used for providing and improving the Service. We will not use or share your information with anyone except as described in this Privacy Policy.\n" +
                "\n" +
                "The terms used in this Privacy Policy have the same meanings as in our Terms and Conditions, which is accessible at Lawuna unless otherwise defined in this Privacy Policy.\n" +
                "<h4>Information Collection and Use</h4>\n" +
                "For a better experience, while using our Product, we may require you to provide us with certain personally identifiable information, including but not limited to Location, Full Name, Email, Phone Number. The information that we request will be retained by us and used as described in this privacy policy.\n" +
                "\n" +
                "The app does use third party services that may collect information used to identify you.\n" +
                "\n" +
                "Link to privacy policy of third party service providers used by the app\n" +
                "\n" +
                "    <ul><li>Google Play Services</li>\n" +
                "    <li>Google Analytics for Firebase</li>\n" +
                "    <li>Firebase Crashlytics</li></ul>\n" +
                "<h4>Log Data</h4>" +
                "We want to inform you that whenever you use our Service, in a case of an error in the app we" +
                " collect data and information (through third party products) on your phone called Log Data. " +
                "This Log Data may include information such as your device Internet Protocol (“IP”) address, device name, operating system version, " +
                "the configuration of the app when utilizing our Service, the time and date of your use of the Service, and other statistics.\n" +
                "<h4>Cookies</h4>\n" +
                "Cookies are files with a small amount of data that are commonly used as anonymous unique identifiers. These are sent to your browser " +
                "from the websites that you visit and are stored on your device's internal memory.\n" +
                "This Service does not use these “cookies” explicitly. However, the app may use third party code and libraries that use “cookies” " +
                "to collect information and improve their services. You have the option to either accept or refuse these cookies and know when a cookie is being sent to your device. " +
                "If you choose to refuse our cookies, you may not be able to use some portions of this Service.\n" +
                "<h4>Service Providers</h4>\n" +
                "We may employ third-party companies and individuals due to the following reasons:\n" +
                "\n" +
                "    <ul><li>To facilitate our Service;</li>\n" +
                "    <li>To provide the Service on our behalf;</li>\n" +
                "    <li>To perform Service-related services; or</li>\n" +
                "    <li>To assist us in analyzing how our Service is used.</li></ul>\n" +
                "We want to inform users of this Service that these third parties have access to your Personal Information. The reason is to perform the tasks assigned to them on our behalf. " +
                "However, they are obligated not to disclose or use the information for any other purpose.\n" +
                "<h4>Security</h4>\n" +
                "We value your trust in providing us your Personal Information, thus we are striving to use commercially acceptable means of protecting it. " +
                "But remember that no method of transmission over the internet, or method of electronic storage is 100% secure and reliable, and we cannot guarantee its absolute security.\n" +
                "Links to Other Sites\n" +
                "\n" +
                "This Service may contain links to other sites. If you click on a third-party link, you will be directed to that site. Note that these external sites are not operated by us. " +
                "Therefore, we strongly advise you to review the Privacy Policy of these websites. We have no control over and assume no responsibility for the content, privacy policies, or " +
                "practices of any third-party sites or services.\n" +
                "\n" +
                "Children’s Privacy\n" +
                "\n" +
                "These Services do not address anyone under the age of 13. We do not knowingly collect personally identifiable information from children under 13. " +
                "In the case we discover that a child under 13 has provided us with personal information, we immediately delete this from our servers. " +
                "If you are a parent or guardian and you are aware that your child has provided us with personal information, please contact us so that we will be able to do necessary actions.\n" +
                "<h4>Changes to This Privacy Policy</h4>\n" +
                "We may update our Privacy Policy from time to time. Thus, you are advised to review this page periodically for any changes. We will notify you of any changes by posting the new Privacy Policy on this page.\n" +
                "\n" +
                "This policy is effective as of 2020-10-06\n" +
                "\n" +
                "<h4>Contact Us</h4>\n" +
                "\n" +
                "If you have any questions or suggestions about our Privacy Policy, do not hesitate to contact us at <b>info@sodzofoundation.org</b> \n"));



    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // handle arrow click here
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void onBackPressed(){
        Intent intent = new Intent(PrivacyActivity.this, LegalActivity.class);
        startActivity(intent);

    }


}
