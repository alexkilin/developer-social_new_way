package com.javamentor.developer.social.platform.service.statistics;

import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.sheets.v4.Sheets;
import com.google.api.services.sheets.v4.SheetsScopes;

import java.io.File;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Collections;

public class GoogleSheetsUtil {

    private static final String SERVICE_ACCOUNT_EMAIL = "devsocialsheets-306@devsocial-310914.iam.gserviceaccount.com";

    private static final String APPLICATION_NAME = "devSocial";

    private static final String pathP12 = "src/main/resources/googleSheets/devsocial-310914-2ea94b88a28d.p12";

    private static GoogleCredential createCredentialForServiceAccount() throws GeneralSecurityException, IOException {
        return new GoogleCredential.Builder().setTransport(GoogleNetHttpTransport.newTrustedTransport())
                .setJsonFactory(JacksonFactory.getDefaultInstance())
                .setServiceAccountId(SERVICE_ACCOUNT_EMAIL)
                .setServiceAccountScopes(Collections.singleton(SheetsScopes.SPREADSHEETS))
                .setServiceAccountPrivateKeyFromP12File(new File(pathP12))
                .build();
    }

    public static Sheets getSheets() throws GeneralSecurityException, IOException {
        return new Sheets.Builder(GoogleNetHttpTransport.newTrustedTransport(),
                JacksonFactory.getDefaultInstance(), createCredentialForServiceAccount())
                .setApplicationName(APPLICATION_NAME)
                .build();
    }
}
